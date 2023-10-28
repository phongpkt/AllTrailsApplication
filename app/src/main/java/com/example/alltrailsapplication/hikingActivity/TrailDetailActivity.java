package com.example.alltrailsapplication.hikingActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.adapter.ObservationAdapter;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Observations;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.observationActivity.AddObservationActivity;

import java.util.ArrayList;

public class TrailDetailActivity extends AppCompatActivity {
    private DatabaseHelper db = new DatabaseHelper(this);
    private TextView name_view, location_view, date_view, parking_view, difficulty_view, description_view;
    private String title;
    private RecyclerView recyclerView;
    private ArrayList<Observations> observationsArrayList = new ArrayList<>();
    private ObservationAdapter observationAdapter = new ObservationAdapter(this, observationsArrayList, TrailDetailActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);
        //Toolbar
        Toolbar();
        //get and set view
        name_view = findViewById(R.id.name);
        location_view = findViewById(R.id.location);
        date_view = findViewById(R.id.date);
        parking_view = findViewById(R.id.parking);
        difficulty_view = findViewById(R.id.difficulty);
        description_view = findViewById(R.id.description);
        Button update = findViewById(R.id.updateBtn);
        Button delete = findViewById(R.id.deleteBtn);

        getAndSetDetailData();
        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(title);
        }

        //Recycle view
        recyclerView = findViewById(R.id.recycle_view_observation);
        observationsArrayList.addAll(db.getAllObservations(Long.parseLong(getIntent().getStringExtra("id"))));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(observationAdapter);

        //Update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrailDetailActivity.this, UpdateTrailActivity.class);
                intent.putExtra("update_id", getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });
        //Delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog();
            }
        });
        //Observation
        Button observation = findViewById(R.id.addObservationBtn);
        observation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrailDetailActivity.this, AddObservationActivity.class);
                intent.putExtra("trail_id", getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });
    }
    public void getAndSetDetailData(){
        if(getIntent().hasExtra("id")){
            Trails trail = db.getTrail(Long.parseLong(getIntent().getStringExtra("id")));
            title = trail.getName();
            name_view.setText(trail.getName());
            location_view.setText(trail.getLocation());
            date_view.setText(trail.getDate());
            parking_view.setText(trail.getParking());
            difficulty_view.setText(trail.getDifficulty());
            description_view.setText(trail.getDescription());
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    private void confirmDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete '" + title + "' ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteTrail(getIntent().getStringExtra("id"));
                switchToActivity(HikingActivity.class);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    public void switchToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
    private void Toolbar() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }
}