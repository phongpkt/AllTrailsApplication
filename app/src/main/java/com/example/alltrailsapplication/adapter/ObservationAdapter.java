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
import com.example.alltrailsapplication.db.entity.Observations;
import com.example.alltrailsapplication.hikingActivity.TrailDetailActivity;
import com.example.alltrailsapplication.observationActivity.UpdateObservationActivity;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Observations> observationsList;
    private TrailDetailActivity trailDetailActivity;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, date, comment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.ob_name);
            this.date = itemView.findViewById(R.id.ob_date);
            this.comment = itemView.findViewById(R.id.ob_comment);
        }
    }
    public ObservationAdapter(Context context, ArrayList<Observations> observationsList, TrailDetailActivity trailDetailActivity) {
        this.context = context;
        this.observationsList = observationsList;
        this.trailDetailActivity = trailDetailActivity;
    }
    @NonNull
    @Override
    public ObservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.observation_item,parent,false);
        return new ObservationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationAdapter.MyViewHolder holder, final int position) {
        final Observations ob = observationsList.get(position);
        holder.name.setText(ob.getName());
        holder.date.setText(ob.getDate());
        holder.comment.setText(ob.getComments());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateObservationActivity.class);
                intent.putExtra("ob_id", String.valueOf(ob.getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return observationsList.size();
    }
}
