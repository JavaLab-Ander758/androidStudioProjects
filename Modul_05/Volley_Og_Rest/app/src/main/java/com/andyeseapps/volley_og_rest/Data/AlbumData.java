package com.andyeseapps.volley_og_rest.Data;

import com.andyeseapps.volley_og_rest.model.Album;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlbumData {
    private List<Album> albums = new ArrayList<>();

    public AlbumData() {
    }

    public AlbumData(JsonArray albumJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type albumListType = new TypeToken<ArrayList<Album>>(){}.getType();
        this.albums = gson.fromJson(albumJson.toString(), albumListType);
    }

    public AlbumData(List<Album> albums) {
        this.albums = albums;
    }

    public AlbumData(String albumListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Album>>(){}.getType();
        this.albums = gson.fromJson(albumListAsJson, type);
    }

    public AlbumData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M. d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Album>>(){}.getType();
        this.albums = gson.fromJson(jsonObject.toString(), type);
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    /* Getters & Setters */
    public List<Album> getAllAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albumList) {
        this.albums = albumList;
    }
}