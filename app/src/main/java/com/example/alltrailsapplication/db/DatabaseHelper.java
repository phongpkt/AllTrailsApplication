package com.example.alltrailsapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.alltrailsapplication.db.entity.Observations;
import com.example.alltrailsapplication.db.entity.Trails;
import com.example.alltrailsapplication.hikingActivity.AddTrailActivity;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "alltrails_db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Trails.CREATE_TABLE);
        sqLiteDatabase.execSQL(Observations.CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Trails.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Observations.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertTrail(String name, String location, String date, String parking, String difficulty, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Trails.COLUMN_NAME, name);
        values.put(Trails.COLUMN_LOCATION, location);
        values.put(Trails.COLUMN_DATE, date);
        values.put(Trails.COLUMN_PARKING, parking);
        values.put(Trails.COLUMN_DIFFICULTY, difficulty);
        values.put(Trails.COLUMN_DESCRIPTION, description);

        long id = db.insert(Trails.TABLE_NAME, null, values);
        if (id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Succeed", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void updateTrail(Trails trail)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Trails.COLUMN_NAME, trail.getName());
        values.put(Trails.COLUMN_LOCATION, trail.getLocation());
        values.put(Trails.COLUMN_DATE, trail.getDate());
        values.put(Trails.COLUMN_PARKING, trail.getParking());
        values.put(Trails.COLUMN_DIFFICULTY, trail.getDifficulty());
        values.put(Trails.COLUMN_DESCRIPTION, trail.getDescription());

        int rowsUpdated = db.update(Trails.TABLE_NAME, values, Trails.COLUMN_ID + " =?",
                new String[]{String.valueOf(trail.getId())}
        );
        if (rowsUpdated == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void deleteTrail(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Trails.TABLE_NAME, Trails.COLUMN_ID + " = ?",
                new String[]{row_id}
        );
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void deleteAllTrails()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Trails.TABLE_NAME);
        db.close();
    }
    public Trails getTrail(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Trails.TABLE_NAME,
                new String[]{
                        Trails.COLUMN_ID,
                        Trails.COLUMN_NAME,
                        Trails.COLUMN_LOCATION,
                        Trails.COLUMN_DATE,
                        Trails.COLUMN_PARKING,
                        Trails.COLUMN_DIFFICULTY,
                        Trails.COLUMN_DESCRIPTION},
                Trails.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        Trails trail = new Trails(
                cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_PARKING)),
                cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_DIFFICULTY)),
                cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Trails.COLUMN_ID))
        );
        cursor.close();
        return trail;
    }
    public ArrayList<Trails> getAllTrails()
    {
        ArrayList<Trails> trailsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+Trails.TABLE_NAME+ " ORDER BY "+
                Trails.COLUMN_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Trails trail = new Trails();
                trail.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Trails.COLUMN_ID)));
                trail.setName(cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_NAME)));
                trail.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_LOCATION)));
                trail.setDifficulty(cursor.getString(cursor.getColumnIndexOrThrow(Trails.COLUMN_DIFFICULTY)));
                trailsList.add(trail);
            }while (cursor.moveToNext());
        }
        db.close();
        return trailsList;
    }
    public void createObservation(String name, String date, String comments, int trail_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Observations.COLUMN_NAME, name);
        values.put(Observations.COLUMN_DATE, date);
        values.put(Observations.COLUMN_COMMENTS, comments);
        values.put(Observations.COLUMN_TRAIL, trail_id);

        long id = db.insert(Observations.TABLE_NAME, null, values);
        if (id == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Succeed", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void updateObservation(Observations observation)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Observations.COLUMN_NAME, observation.getName());
        values.put(Observations.COLUMN_COMMENTS, observation.getComments());
        values.put(Observations.COLUMN_DATE, observation.getDate());

        int rowsUpdated = db.update(Observations.TABLE_NAME, values, Observations.COLUMN_ID + " =?",
                new String[]{String.valueOf(observation.getId())}
        );
        if (rowsUpdated == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public Observations getObservation(long id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Observations.TABLE_NAME,
                new String[]{
                        Observations.COLUMN_ID,
                        Observations.COLUMN_NAME,
                        Observations.COLUMN_DATE,
                        Observations.COLUMN_COMMENTS,
                        Observations.COLUMN_TRAIL},
                Observations.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();
        Observations observation = new Observations(
                cursor.getString(cursor.getColumnIndexOrThrow(Observations.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Observations.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Observations.COLUMN_COMMENTS)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Observations.COLUMN_TRAIL)),
                cursor.getInt(cursor.getColumnIndexOrThrow(Observations.COLUMN_ID))
        );
        cursor.close();
        return observation;
    }
    public ArrayList<Observations> getAllObservations(long trail_id)
    {
        ArrayList<Observations> observationsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+Observations.TABLE_NAME+ " WHERE "+ Observations.COLUMN_TRAIL +
                " = " + trail_id + " ORDER BY "+ Observations.COLUMN_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Observations observation = new Observations();
                observation.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Observations.COLUMN_ID)));
                observation.setName(cursor.getString(cursor.getColumnIndexOrThrow(Observations.COLUMN_NAME)));
                observation.setDate(cursor.getString(cursor.getColumnIndexOrThrow(Observations.COLUMN_DATE)));
                observation.setComments(cursor.getString(cursor.getColumnIndexOrThrow(Observations.COLUMN_COMMENTS)));
                observation.setTrail(cursor.getInt(cursor.getColumnIndexOrThrow(Observations.COLUMN_TRAIL)));
                observationsList.add(observation);
            }while (cursor.moveToNext());
        }
        db.close();
        return observationsList;
    }
    public void deleteObservation(String row_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Observations.TABLE_NAME, Observations.COLUMN_ID + " = ?",
                new String[]{row_id}
        );
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
