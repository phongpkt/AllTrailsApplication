package com.example.alltrailsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.db.entity.User;
import com.example.alltrailsapplication.hikingActivity.HikingActivity;
import com.example.alltrailsapplication.userActivity.LoginActivity;
import com.example.alltrailsapplication.userActivity.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private DatabaseHelper db = new DatabaseHelper(MainActivity.this);
    private long user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Navigation
        bottomNavigationView();
        //Toolbar
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        //View
        final TextView user_loggedin_Tv = findViewById(R.id.user_loggedin_Tv);
        User user = getUser();
        user_loggedin_Tv.setText("User logged in as: "+user.getName());

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
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home){
                    return true;
                } else if (itemId == R.id.profile) {
                    switchToActivity(UserActivity.class);
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