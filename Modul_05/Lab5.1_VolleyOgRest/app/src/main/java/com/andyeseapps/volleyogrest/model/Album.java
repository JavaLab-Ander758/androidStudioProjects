package com.andyeseapps.volleyogrest.model;

public class Album {
    int userId;
    int id;
    String title;

    public Album() {
        this.userId = -1;
        this.id = -1;
        this.title = "nullTitle";
    }

    public Album(int userId, int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }



    /* Getters & Setters */
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("userId=%d - id=%d - title=%s", userId, id, title);
    }
}
