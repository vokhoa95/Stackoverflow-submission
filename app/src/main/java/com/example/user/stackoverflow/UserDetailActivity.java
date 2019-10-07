package com.example.user.stackoverflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.user.stackoverflow.adapter.ReputationAdapter;
import com.example.user.stackoverflow.adapter.UserAdapter;
import com.example.user.stackoverflow.model.Reputation;
import com.example.user.stackoverflow.model.User;
import com.example.user.stackoverflow.util.StackApiUtil;
import com.example.user.stackoverflow.util.StackService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class UserDetailActivity extends AppCompatActivity {
    private StackService stackService;
    private RecyclerView rvReputationList;
    private ReputationAdapter adapter;
    boolean isLoading = false;
    boolean isEndOfList = false;
    private int page = 1;
    private List<Reputation> reputationList = new ArrayList<Reputation>();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        rvReputationList = (RecyclerView) findViewById(R.id.rvReputationList);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        rvReputationList.addItemDecoration(decoration);
        userId = getIntent().getExtras().getString("userId");
        getReputationList(userId, page);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);   //show back button

        rvReputationList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && !isEndOfList) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == reputationList.size() - 1) {
                        //bottom of list!
                        page++;
                        getReputationList(userId,page);
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void getReputationList(final String userid, final int page) {
        stackService = StackApiUtil.getStackService();
        Call<String> call = stackService.getAllReputationJSON(userid, page , 30, "stackoverflow");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Gson gson = new Gson();
                List<Reputation> returnList = null;
                Log.e("Retrofit", "Success" + response.raw().request().url());
                JsonParser parser = new JsonParser();
                JsonObject element = null;
                if (response.body() != null) {
                    element = (JsonObject) parser.parse(response.body());
                    JsonElement responseWrapper = element.get("items");
                    Type collectionType = new TypeToken<Collection<Reputation>>() {
                    }.getType();

                    returnList = gson.fromJson(responseWrapper, collectionType);
                    if (returnList.size() > 0) {
                        if (page == 1) {
                            generateDataList(returnList);
                        } else {
                            updateDataList(returnList);
                            isLoading = false;
                        }
                    } else {
                        isEndOfList = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e("Retrofit", "FAIL" + t.getMessage() + " " + call.request());
            }

        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Reputation> returnList) {
        reputationList.addAll(returnList);
        adapter = new ReputationAdapter(this, reputationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserDetailActivity.this);
        rvReputationList.setLayoutManager(layoutManager);
        rvReputationList.setAdapter(adapter);
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void updateDataList(List<Reputation> returnList) {
        reputationList.addAll(returnList);
        adapter.notifyDataSetChanged();
    }
}
