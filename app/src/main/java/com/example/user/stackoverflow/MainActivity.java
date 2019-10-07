package com.example.user.stackoverflow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.stackoverflow.adapter.UserAdapter;
import com.example.user.stackoverflow.constants.Globals;
import com.example.user.stackoverflow.dao.UserLocalDataSource;
import com.example.user.stackoverflow.database.AppDatabase;
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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity implements UserAdapter.ItemClickListener {
    private CompositeDisposable mCompositeDisposable;
    private StackService stackService;
    private RecyclerView rvUserList;
    private UserRepository mUserRepository;
    private UserAdapter adapter;
    boolean isLoading = false;
    boolean isEndOfList = false;
    boolean isFavoriteFilter = false;
    private int page = 1;
    private List<User> apiUserList = new ArrayList<User>();
    private List<User> showList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase userDatabase = AppDatabase.getInMemoryDatabase(this);
        mCompositeDisposable = new CompositeDisposable();
        mUserRepository =
                UserRepository.getInstance(UserLocalDataSource.getInstance(userDatabase.userDAO()));
        getData();
        initView();
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnFavoriteFilter) {
            if (isFavoriteFilter) {
                item.setIcon(R.drawable.star_on);
                resetDataList(apiUserList);
                isFavoriteFilter = false;
            } else {
                item.setIcon(R.drawable.star_off);
                resetDataList(Globals.dbUser);
                isFavoriteFilter = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        rvUserList = (RecyclerView) findViewById(R.id.rvUserList);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        rvUserList.addItemDecoration(decoration);
        generateDataList(apiUserList);
        getUserList(page);
        rvUserList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && !isEndOfList && !isFavoriteFilter) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == apiUserList.size() - 1) {
                        page++;
                        getUserList(page);
                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void getUserList(final int page) {
        stackService = StackApiUtil.getStackService();
        Call<String> call = stackService.getAllUsersJSON(page, 30, "stackoverflow");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Gson gson = new Gson();
                List<User> returnList = null;
                JsonParser parser = new JsonParser();
                JsonObject element = null;
                if (response.body() != null) {
                    element = (JsonObject) parser.parse(response.body());
                    JsonElement responseWrapper = element.get("items");
                    Type collectionType = new TypeToken<Collection<User>>() {
                    }.getType();

                    returnList = gson.fromJson(responseWrapper, collectionType);
                    if (returnList.size() > 0) {
                        if (page == 1) {
                            apiUserList.addAll(returnList);
                            generateDataList(returnList);
                        } else {
                            apiUserList.addAll(returnList);
                            addDataList(returnList);
                            isLoading = false;
                        }
                    } else {
                        isEndOfList = true;
                    }
                } else {
                    generateDataList(apiUserList);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                onError();
            }

        });
    }

    private void onError(){
        Toast.makeText(this, "Error getting data", Toast.LENGTH_SHORT).show();
    }


    private void generateDataList(List<User> returnList) {
        showList.addAll(returnList);
        adapter = new UserAdapter(this, showList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addDataList(List<User> returnList) {
        showList.addAll(returnList);
        adapter.notifyDataSetChanged();
    }

    private void resetDataList(List<User> returnList) {
        showList.clear();
        showList.addAll(returnList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(final User user) {
        getData();
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("userId", user.getUserId());
        startActivity(intent);
    }

    @Override
    public void insertUser(final User user) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

                mUserRepository.insertUser(user);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //no ops
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        getData();
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void removeUser(final User user) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                mUserRepository.deleteUser(user);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //no ops
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isFavoriteFilter) {
                            resetDataList(Globals.dbUser);
                        }
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    private void getData() {
        Disposable disposable = mUserRepository.getAllUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        onGetAllUserSuccess(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    private void onGetAllUserSuccess(List<User> users) {
        Globals.dbUser = users;
        Log.e("Database", users.size() + "");
    }
}
