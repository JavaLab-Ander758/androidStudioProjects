package com.andyeseapps.kontaktdatabasen.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "category_table")
public class Category implements Serializable {
    @PrimaryKey(autoGenerate = true)
//    @NonNull
    @ColumnInfo(name = "categoryID")
    private int categoryID;
    @ColumnInfo(name = "description")
    private String description;


    public Category(@NonNull String description) {
        this.description = description;
    }

    public Category() {

    }

    /* Getters and Setters */
    public int getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}