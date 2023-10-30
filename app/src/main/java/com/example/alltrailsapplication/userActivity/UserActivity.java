package com.example.alltrailsapplication.userActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alltrailsapplication.MainActivity;
import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.adapter.HikingAdapter;
import com.example.alltrailsapplication.adapter.User_Trails_Adapter;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.db.entity.User;
import com.example.alltrailsapplication.hikingActivity.HikingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private DatabaseHelper db = new DatabaseHelper(UserActivity.this);
    private TextView user_name, user_dob, user_email;
    private ImageView empty_imageView;
    private TextView no_data;
    private long user_id;
    private RecyclerView recyclerView;
    private ArrayList<Trails> user_trailsArrayList = new ArrayList<>();
    private User_Trails_Adapter user_trails_adapter =  new User_Trails_Adapter(this, user_trailsArrayList, UserActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Navigation
        bottomNavigationView();
        //Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        //View
        user_name = findViewById(R.id.user_name);
        user_dob = findViewById(R.id.user_dob);
        user_email = findViewById(R.id.user_email);
        recyclerView = findViewById(R.id.recycle_view_user_trails);
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);
        //Get and set information
        User user = getUser();
        user_name.setText(user.getName());
        user_dob.setText(user.getDob());
        user_email.setText(user.getEmail());

        user_trailsArrayList.addAll(db.getAllUserTrails(user_id));
        if(user_trailsArrayList.isEmpty()){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(user_trails_adapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id == R.id.logout){
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private User getUser(){
        user_id = Long.parseLong(getIntent().getStringExtra("user_id"));
        return db.getUser(user_id);
    }
    public void bottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home){
                    switchToActivity(MainActivity.class);
                    return true;
                } else if (itemId == R.id.profile) {
                    return true;
                } else if (itemId == R.id.trails) {
                    switchToActivity(HikingActivity.class);
                    return true;
                }
                return false;
            }
        });
    }
    private void switchToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
        startActivity(intent);
    }
}