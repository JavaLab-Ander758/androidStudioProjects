package com.andyeseapps.treningsprogram_navigation.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Exercise implements Serializable, Parcelable {
    private int id;
    private String name;
    private String description;
    private String icon;
    private String infobox_color;

    public Exercise() {
    }

    public Exercise(int id, String name, String description, String icon, String infobox_color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.infobox_color = infobox_color;
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
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getInfobox_color() {
        return infobox_color;
    }
    public void setInfobox_color(String infobox_color) {
        this.infobox_color = infobox_color;
    }

    /* Implementation */
    protected Exercise(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        icon = in.readString();
        infobox_color = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(icon);
        dest.writeString(infobox_color);
    }
}