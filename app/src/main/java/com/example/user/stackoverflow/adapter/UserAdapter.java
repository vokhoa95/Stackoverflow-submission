package com.example.user.stackoverflow.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.stackoverflow.R;
import com.example.user.stackoverflow.UserRepository;
import com.example.user.stackoverflow.constants.Globals;
import com.example.user.stackoverflow.dao.UserLocalDataSource;
import com.example.user.stackoverflow.database.AppDatabase;
import com.example.user.stackoverflow.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomViewHolder> {

    private List<User> dataList;
    private Context context;
    private ItemClickListener itemClickListener;

    public UserAdapter(Context context, List<User> dataList, ItemClickListener itemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.itemClickListener = itemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView tvUsername;
        TextView tvLocation;
        TextView tvReputation;
        TextView tvLastAccessed;
        ImageView ivUserDisplay;
        ToggleButton tgFavorite;
        ConstraintLayout clUserRow;
        String userId;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvUsername = (TextView) mView.findViewById(R.id.tvUsername);
            tvLocation = (TextView) mView.findViewById(R.id.tvLocation);
            tvReputation = (TextView) mView.findViewById(R.id.tvReputation);
            tvLastAccessed = (TextView) mView.findViewById(R.id.tvLastAccessed);
            ivUserDisplay = (ImageView) mView.findViewById(R.id.ivUserDisplay);
            clUserRow = (ConstraintLayout) mView.findViewById(R.id.clUserRow);
            tgFavorite = (ToggleButton) mView.findViewById(R.id.tgFavorite);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.tvUsername.setText(dataList.get(position).getDisplayName());
        holder.tvLocation.setText(dataList.get(position).getLocation());
        holder.tvReputation.setText(dataList.get(position).getReputation() + "");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(dataList.get(position).getLastAccessDate() * 1000);
        holder.tvLastAccessed.setText(sdf.format(date));

        Glide.with(context)
                .load(dataList.get(position).getProfileImage())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(holder.ivUserDisplay);
        holder.userId = dataList.get(position).getUserId();

        holder.clUserRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = dataList.get(position);
                itemClickListener.onClick(user);
            }
        });

        boolean isExistInDB = checkExist(dataList.get(position));
        if (isExistInDB) {
            holder.tgFavorite.setChecked(true);
        } else {
            holder.tgFavorite.setChecked(false);
        }

        holder.tgFavorite.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if (toggleButton.isPressed()) {
                    if (isChecked) {
                        itemClickListener.insertUser(dataList.get(position));
                    } else {
                        remove(dataList.get(position));
                        itemClickListener.removeUser(dataList.get(position));
                    }
                }
            }
        }) ;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private boolean checkExist(User user) {
        for (User userDB: Globals.dbUser){
            if (userDB.getUserId().equals(user.getUserId())){
                return true;
            }
        }
        return false;
    }

    private void remove(User user) {
        for (User userDB: Globals.dbUser){
            if (userDB.getUserId().equals(user.getUserId())){
                Globals.dbUser.remove(userDB);
                return;
            }
        }
        return;
    }

    public interface ItemClickListener {
        void onClick(final User user);

        void insertUser(final User user);

        void removeUser(final User user);
    }
}