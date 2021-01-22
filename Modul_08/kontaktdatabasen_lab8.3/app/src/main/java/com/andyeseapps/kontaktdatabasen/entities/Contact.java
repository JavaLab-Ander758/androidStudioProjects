package com.andyeseapps.kontaktdatabasen.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contact_table")
public class Contact implements Serializable {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "contactID")
    private int contactID;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "birth_year")
    private int birthYear;
    @ColumnInfo(name = "category_categoryID")
    private int category_categoryID;

    public Contact(@NonNull String lastName, @NonNull String firstName, @NonNull String email, @NonNull String phoneNumber, @NonNull int birthYear, @NonNull int category_categoryID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthYear = birthYear;
        this.category_categoryID = category_categoryID;
    }

    public Contact() {

    }

    /* Getters and Setters */
    public int getContactID() {
        return contactID;
    }
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public int getBirthYear() {
        return birthYear;
    }
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public int getCategory_categoryID() {
        return category_categoryID;
    }
    public void setCategory_categoryID(int category_categoryID) {
        this.category_categoryID = category_categoryID;
    }
}