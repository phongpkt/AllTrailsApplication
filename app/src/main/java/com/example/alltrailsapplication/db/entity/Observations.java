package com.example.alltrailsapplication.db.entity;

public class Observations {
    public static final String TABLE_NAME = "Observations";
    public static final String COLUMN_ID = "obv_id";
    public static final String COLUMN_NAME = "obv_name";
    public static final String COLUMN_DATE = "obv_date";
    public static final String COLUMN_COMMENTS = "obv_comments";
    public static final String COLUMN_TRAIL = "obv_trail";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_COMMENTS + " TEXT,"
                    + COLUMN_TRAIL + " INTEGER"
                    + ")";

    private String name;
    private String date;
    private String comments;
    private int id;
    private int trail_id;

    public Observations(String name, String date, String comments, int trail_id, int id) {
        this.name = name;
        this.date = date;
        this.comments = comments;
        this.trail_id = trail_id;
        this.id = id;
    }

    public Observations() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrail() {
        return trail_id;
    }

    public void setTrail(int trail_id) {
        this.trail_id = trail_id;
    }
}
