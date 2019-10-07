package com.example.user.stackoverflow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.stackoverflow.R;
import com.example.user.stackoverflow.model.Reputation;
import com.example.user.stackoverflow.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReputationAdapter extends RecyclerView.Adapter<ReputationAdapter.CustomViewHolder> {

    private List<Reputation> dataList;
    private Context context;

    public ReputationAdapter(Context context, List<Reputation> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView tvReputationChange;
        TextView tvReputationHistoryType;
        TextView tvCreationDate;
        TextView tvPostId;
        ConstraintLayout clUserRow;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            tvReputationChange = (TextView) mView.findViewById(R.id.tvReputationChange);
            tvReputationHistoryType = (TextView) mView.findViewById(R.id.tvReputationHistoryType);
            tvCreationDate = (TextView) mView.findViewById(R.id.tvCreationDate);
            tvPostId = (TextView) mView.findViewById(R.id.tvPostId);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reputation_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (dataList.get(position).getReputationChange() > 0){
            holder.tvReputationChange.setTextColor(Color.GREEN);
        } else if (dataList.get(position).getReputationChange() < 0){
            holder.tvReputationChange.setTextColor(Color.RED);
        } else {
            holder.tvReputationChange.setTextColor(Color.BLACK);
        }
        holder.tvReputationChange.setText(dataList.get(position).getReputationChange()+"");
        holder.tvPostId.setText("Post id: " + dataList.get(position).getPostId()+"");
        holder.tvReputationHistoryType.setText(dataList.get(position).getReputationHistoryType());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(dataList.get(position).getCreationDate() * 1000);
        holder.tvCreationDate.setText("Last accessed: " + sdf.format(date));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}