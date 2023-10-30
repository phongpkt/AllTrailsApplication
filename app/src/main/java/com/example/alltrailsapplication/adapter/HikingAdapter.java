package com.example.alltrailsapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.db.entity.User;
import com.example.alltrailsapplication.hikingActivity.HikingActivity;
import com.example.alltrailsapplication.hikingActivity.TrailDetailActivity;

import java.util.ArrayList;

public class HikingAdapter extends RecyclerView.Adapter<HikingAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Trails> trailsList;
    private HikingActivity hikingActivity;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView id, name, location, difficulty, user;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.trail_id);
            this.name = itemView.findViewById(R.id.trail_name);
            this.location = itemView.findViewById(R.id.trail_location);
            this.difficulty = itemView.findViewById(R.id.trail_difficulty);
            this.user = itemView.findViewById(R.id.trail_user);
        }
    }
    public HikingAdapter(Context context, ArrayList<Trails> trailsList, HikingActivity hikingActivity) {
        this.context = context;
        this.trailsList = trailsList;
        this.hikingActivity = hikingActivity;
    }
    @NonNull
    @Override
    public HikingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_item,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull HikingAdapter.MyViewHolder holder, final int position) {
        final Trails trail = trailsList.get(position);
        holder.id.setText(String.valueOf(trail.getId()));
        holder.name.setText(trail.getName());
        holder.location.setText(trail.getLocation());
        holder.difficulty.setText(trail.getDifficulty());
        holder.user.setText(retrieveUserName(trail.getUser_id()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TrailDetailActivity.class);
                intent.putExtra("id", String.valueOf(trail.getId()));
                intent.putExtra("user_id", String.valueOf(trail.getUser_id()));
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return trailsList.size();
    }

    public void setFilter(ArrayList<Trails> newList)
    {
        trailsList = new ArrayList<>();
        trailsList.addAll(newList);
        notifyDataSetChanged();
    }
    private String retrieveUserName(long userId) {
        if(userId != 0){
            DatabaseHelper db = new DatabaseHelper(hikingActivity);
            User user = db.getUser(userId);
            String user_name = user.getName();
            return user_name;
        }else {
            return null;
        }
    }
}
