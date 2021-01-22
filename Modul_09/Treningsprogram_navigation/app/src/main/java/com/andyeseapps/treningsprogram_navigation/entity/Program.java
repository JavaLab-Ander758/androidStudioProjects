package com.andyeseapps.treningsprogram_navigation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Program implements Serializable, Parcelable {
    private int id;
    private String name;
    private String description;

    public Program() {
    }

    public Program(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /* Getters and Setters */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    /* Implementation */
    protected Program(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Program> CREATOR = new Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };
}
