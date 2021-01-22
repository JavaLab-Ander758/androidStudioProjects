package com.andyeseapps.volleyogrest.Data;

import com.andyeseapps.volleyogrest.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    private List<User> users = new ArrayList<>();

    public UserData() {
    }

    public UserData(JsonArray usersJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        this.users = gson.fromJson(usersJson.toString(), userListType);
    }










    public UserData(List<User> users) {
        this.users = users;
    }

    public UserData(String userListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        this.users = gson.fromJson(userListAsJson, type);
    }

    public UserData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M.d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        this.users = gson.fromJson(jsonObject.toString(), type);
    }

    public void addUser(User user) {
        users.add(user);
    }

    /* Getters & Setters */
    public List<User> getUsers() {
        return users;
    }

    public void serUsers(List<User> userList) {
        this.users = userList;
    }
}
