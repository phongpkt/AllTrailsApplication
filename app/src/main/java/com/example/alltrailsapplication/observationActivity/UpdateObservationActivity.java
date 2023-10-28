package com.example.alltrailsapplication.observationActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alltrailsapplication.R;
import com.example.alltrailsapplication.db.DatabaseHelper;
import com.example.alltrailsapplication.db.entity.Observations;
import com.example.alltrailsapplication.hikingActivity.HikingActivity;

public class UpdateObservationActivity extends AppCompatActivity {
    private EditText date_input, comment_input;
    private Spinner name_input;
    private String name, date, comments;
    private long ob_id;
    private DatabaseHelper db = new DatabaseHelper(UpdateObservationActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);
        //Toolbar
        ToolBar();
        //Inputs
        name_input = findViewById(R.id.ob_name);
        date_input = findViewById(R.id.ob_date);
        comment_input = findViewById(R.id.ob_comment);
        getAndSetData();
        Button update = findViewById(R.id.updateBtn);
        Button delete = findViewById(R.id.deleteBtn);
        //Update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = name_input.getSelectedItem().toString();
                date = date_input.getText().toString();
                comments = comment_input.getText().toString();
                UpdateObservation(name, date, comments, ob_id);
            }
        });
        //Delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeleteDialog();
            }
        });
    }
    private void getAndSetData(){
        if(getIntent().hasExtra("ob_id")){
            ob_id = Long.parseLong(getIntent().getStringExtra("ob_id"));
            Observations observation = db.getObservation(ob_id);
            name_input.setSelection(getSpinnerSelection(observation.getName()));;
            date_input.setText(observation.getDate());
            comment_input.setText(observation.getComments());
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    private static int getSpinnerSelection(String ob_name) {
        int selection = 0;
        if(ob_name.equals("Sightings of animals")){
            selection = 0;
        } else if(ob_name.equals("Types of vegetation encountered")){
            selection = 1;
        } else if(ob_name.equals("Weather conditions")){
            selection = 2;
        } else if(ob_name.equals("Geological features")){
            selection = 3;
        } else if(ob_name.equals("Water sources")){
            selection = 4;
        } else if(ob_name.equals("Trail conditions")){
            selection = 5;
        } else if(ob_name.equals("Insects discovered")){
            selection = 6;
        } else if(ob_name.equals("Other...")){
            selection = 7;
        }
        return selection;
    }
    private void UpdateObservation(String name, String date, String comments, long id){
        Observations observation = db.getObservation(id);
        observation.setName(name);
        observation.setDate(date);
        observation.setComments(comments);
        db.updateObservation(observation);
        onBackPressed();
    }
    private void confirmDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete observation ?");
        builder.setMessage("Are you sure you want to delete this ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.deleteObservation(String.valueOf(ob_id));
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
    public void switchToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}