package com.example.alltrailsapplication.hikingActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.db.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTrailActivity extends AppCompatActivity {
    private DatabaseHelper db = new DatabaseHelper(AddTrailActivity.this);
    private long user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail);
        //Toolbar
        ToolBar();
        //Input
        final EditText name_input = findViewById(R.id.name);
        final EditText location_input = findViewById(R.id.location);
        final EditText date_input = findViewById(R.id.date);
        final RadioButton rb_yes = findViewById(R.id.rb_Yes);
        final RadioButton rb_no = findViewById(R.id.rb_No);
        final Spinner difficulty_spin = findViewById(R.id.difficulty);
        final EditText description = findViewById(R.id.description);
        final TextView user = findViewById(R.id.trail_user);

        user_id = Long.parseLong(getIntent().getStringExtra("user_id"));
        user.setText(String.valueOf(db.getUser(user_id).getName()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateAndTime = sdf.format(new Date());
        date_input.setText(currentDateAndTime);

        final Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_input.getText().toString().trim();
                String location = location_input.getText().toString().trim();
                String date = date_input.getText().toString().trim();
                String difficulty = difficulty_spin.getSelectedItem().toString();
                String descriptionText = description.getText().toString();
                String parking = "";
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(date)){
                    Toast.makeText(AddTrailActivity.this, "Please fill out the form", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(descriptionText)){
                    descriptionText = "";
                }
                if (rb_yes.isChecked()) {
                    parking = rb_yes.getText().toString().trim();
                } else if (rb_no.isChecked()) {
                    parking = rb_no.getText().toString().trim();
                }
                CreateTrail(name, location, date, parking, difficulty, descriptionText, Integer.parseInt(getIntent().getStringExtra("user_id")));
            }
        });
    }
    private  void CreateTrail(String name, String location, String date, String parking, String difficulty, String description, int user_id){
        db.insertTrail(name, location, date, parking, difficulty, description, user_id);
        Intent intent = new Intent(AddTrailActivity.this, HikingActivity.class);
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