package com.example.alltrailsapplication.observationActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.hikingActivity.AddTrailActivity;
import com.example.alltrailsapplication.hikingActivity.HikingActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddObservationActivity extends AppCompatActivity {
    private EditText ob_date, ob_comment;
    private Spinner ob_name;
    private DatabaseHelper db = new DatabaseHelper(AddObservationActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);
        //Toolbar
        ToolBar();
        //Input
        ob_name = findViewById(R.id.ob_name);
        ob_date = findViewById(R.id.ob_date);
        ob_comment = findViewById(R.id.ob_comment);
        Button add_btn = findViewById(R.id.addBtn);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateAndTime = sdf.format(new Date());
        ob_date.setText(currentDateAndTime);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ob_name.getSelectedItem().toString().trim();
                String date = ob_date.getText().toString().trim();
                String comment = ob_comment.getText().toString();
                if(TextUtils.isEmpty(comment) || TextUtils.isEmpty(date)){
                    Toast.makeText(AddObservationActivity.this, "Please fill out the form", Toast.LENGTH_SHORT).show();
                    return;
                }
                CreateObservation(name, date, comment, Integer.parseInt(getIntent().getStringExtra("trail_id")));
            }
        });
    }
    private void CreateObservation(String name, String date, String comment, int trail_id){
        db.createObservation(name, date, comment, trail_id);
        Intent intent = new Intent(AddObservationActivity.this, HikingActivity.class);
        intent.putExtra("user_id", getIntent().getStringExtra("user_id"));
        startActivity(intent);
    }
    private void ToolBar() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        String userId = getIntent().getStringExtra("user_id");
        if (userId != null) {
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            upIntent.putExtra("user_id", userId);
            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }
        return super.onSupportNavigateUp();
    }
}