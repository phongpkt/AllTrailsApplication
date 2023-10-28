package com.example.alltrailsapplication.hikingActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alltrailsapplication.MainActivity;
import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.adapter.HikingAdapter;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.userActivity.UserActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class HikingActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private ArrayList<Trails> trailsArrayList = new ArrayList<>();
    private DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
    private HikingAdapter hikingAdapter =  new HikingAdapter(this, trailsArrayList, HikingActivity.this);
    private ImageView empty_imageView;
    private TextView no_data;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiking);
        //Navigation
        bottomNavigationView();
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        empty_imageView = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);
        //Recycle view
        recyclerView = findViewById(R.id.recycle_view_trails);
        trailsArrayList.addAll(myDatabaseHelper.getAllTrails());
        if(trailsArrayList.isEmpty()){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else{
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(hikingAdapter);
        }
        //Create
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> switchToActivity(AddTrailActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id == R.id.delete_all){
            Toast.makeText(this, "clicked on delete", Toast.LENGTH_SHORT).show();
//            confirmDeleteDialog();
        }
        if(item_id == R.id.search){
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    query = query.toLowerCase();
                    ArrayList<Trails> newList = new ArrayList<>();
                    for(Trails trails : trailsArrayList){
                        String name = trails.getName().toLowerCase();
                        if(name.contains(query)){
                            newList.add(trails);
                        }
                    }
                    hikingAdapter.setFilter(newList);
                    return true;
                }
            });
        }
        if(item_id == R.id.setting) {
            Toast.makeText(this, "clicked on setting", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all trails");
        builder.setMessage("Are you sure you want to delete all trails ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabaseHelper.deleteAllTrails();
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
    public void bottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.trails);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if(itemId == R.id.home){
                switchToActivity(MainActivity.class);
                return true;
            } else if (itemId == R.id.profile) {
                switchToActivity(UserActivity.class);
                return true;
            } else return itemId == R.id.trails;
        });
    }
    public void switchToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}