package com.andyeseapps.volley_og_rest.Data;

import com.andyeseapps.volley_og_rest.model.Photo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhotoData {
    private List<Photo> photos = new ArrayList<>();

    public PhotoData() {
    }

    public PhotoData(JsonArray photoJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type photoListType = new TypeToken<ArrayList<Photo>>(){}.getType();
        this.photos = gson.fromJson(photoJson.toString(), photoListType);
    }

    public PhotoData(List<Photo> photos) {
        this.photos = photos;
    }

    public PhotoData(String photoListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Photo>>(){}.getType();
        this.photos = gson.fromJson(photoListAsJson, type);
    }

    public PhotoData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M. d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Photo>>(){}.getType();
        this.photos = gson.fromJson(jsonObject.toString(), type);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    /* Getters & Setters */
    public List<Photo> getAllPhotos() {
        return photos;
    }

    public void serAlbums(List<Photo> photoList) {
        this.photos = photoList;
    }
}