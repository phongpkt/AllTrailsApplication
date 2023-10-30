package com.example.alltrailsapplication.db.entity;

import java.util.ArrayList;

public class User {
    public static final String TABLE_NAME = "Users";
    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_NAME = "user_name";
    public static final String COLUMN_DOB = "user_dob";
    public static final String COLUMN_EMAIL = "user_email";
    public static final String COLUMN_PASSWORD = "user_password";
    public static final String COLUMN_TRAILS = "user_trails";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DOB + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_TRAILS + " INTEGER"
                    + ")";

    private String name;
    private String dob;
    private String email;
    private String password;
    private ArrayList<Integer> trails;
    private int id;

    public User(String name, String dob, String email, String password, ArrayList<Integer> trails, int id) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.trails = trails;
        this.id = id;
    }

    public User() {
    }

    public User(String name, String dob, String email, int id) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Integer> getTrails() {
        return trails;
    }
    public void addTrails(int trailId) {
        trails.add(trailId);
    }
    public void removeTrail(int trailId) {
        trails.remove(trailId);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
