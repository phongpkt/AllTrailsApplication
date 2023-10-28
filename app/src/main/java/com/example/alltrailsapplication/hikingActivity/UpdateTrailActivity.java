package com.example.alltrailsapplication.hikingActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Trails;

public class UpdateTrailActivity extends AppCompatActivity {
    private EditText name_input, location_input, date_input, description;
    private long trail_id;
    private DatabaseHelper db = new DatabaseHelper(this);
    private Spinner difficulty_spin;
    private RadioButton rb_yes, rb_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trail);
        //Toolbar
        ToolBar();
        //Input
        name_input = findViewById(R.id.name);
        location_input = findViewById(R.id.location);
        date_input = findViewById(R.id.date);
        rb_yes = findViewById(R.id.rb_Yes);
        rb_no = findViewById(R.id.rb_No);
        difficulty_spin = findViewById(R.id.difficulty);
        description = findViewById(R.id.description);
        getAndSetData();

        Button confirm = findViewById(R.id.confirm_btn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_input.getText().toString().trim();
                String location = location_input.getText().toString().trim();
                String date = date_input.getText().toString().trim();
                String difficulty = difficulty_spin.getSelectedItem().toString();
                String descriptionText = description.getText().toString();
                String parking = "";
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(date)){
                    Toast.makeText(UpdateTrailActivity.this, "Please fill out the required field", Toast.LENGTH_SHORT).show();
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
                UpdateTrail(name, location, date, parking, difficulty, descriptionText, trail_id);
            }
        });
    }
    private void getAndSetData(){
        if(getIntent().hasExtra("update_id")){
            trail_id = Long.parseLong(getIntent().getStringExtra("update_id"));
            Trails trail = db.getTrail(trail_id);
            name_input.setText(trail.getName());
            location_input.setText(trail.getLocation());
            date_input.setText(trail.getDate());
            if(trail.getParking().equals("Yes")){
                rb_no.setChecked(false);
                rb_yes.setChecked(true);
            }else if (trail.getParking().equals("No")) {
                rb_no.setChecked(true);
                rb_yes.setChecked(false);
            }
            difficulty_spin.setSelection(getSpinnerSelection(trail.getDifficulty()));
            description.setText(trail.getDescription());
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    private static int getSpinnerSelection(String difficulty) {
        int selection = 0;
        if(difficulty.equals("Easy")){
            selection = 0;
        } else if(difficulty.equals("Medium")){
            selection = 1;
        } else if(difficulty.equals("Hard")){
            selection = 2;
        }
        return selection;
    }
    private void UpdateTrail(String name, String location, String date, String parking, String difficulty, String description, long id){
        Trails trail = db.getTrail(id);
        trail.setName(name);
        trail.setLocation(location);
        trail.setDate(date);
        trail.setParking(parking);
        trail.setDifficulty(difficulty);
        trail.setDescription(description);
        db.updateTrail(trail);
        onBackPressed();
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
    public void onBackPressed(){
        NavUtils.navigateUpFromSameTask(this);
    }
}