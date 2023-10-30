package com.example.alltrailsapplication.db.entity;

public class Trails {
    public static final String TABLE_NAME = "Trails";
    public static final String COLUMN_ID = "trail_id";
    public static final String COLUMN_NAME = "trail_name";
    public static final String COLUMN_LOCATION = "trail_location";
    public static final String COLUMN_DATE = "trail_date";
    public static final String COLUMN_PARKING = "trail_parking";
    public static final String COLUMN_DIFFICULTY = "trail_difficulty";
    public static final String COLUMN_DESCRIPTION = "trail_description";
    public static final String COLUMN_USER = "created_by";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_PARKING + " TEXT,"
                    + COLUMN_DIFFICULTY + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_USER + " INTEGER"
                    + ")";

    private String name;
    private String location;
    private String date;
    private String parking;
    private String difficulty;
    private String description;
    private long user_id;
    private int id;

    public Trails(String name, String location, String date, String parking, String difficulty, String description, long user_id, int id) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.parking = parking;
        this.difficulty = difficulty;
        this.description = description;
        this.user_id = user_id;
        this.id = id;
    }

    public Trails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
