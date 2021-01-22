package com.andyeseapps.volleyogrest;

import com.andyeseapps.volleyogrest.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    private List<User> userList = new ArrayList<>();

    public UserData(List<User> userList) {
        this.userList = userList;
    }

    public UserData(String userListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        this.userList = gson.fromJson(userListAsJson, type);
    }

    public UserData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M.d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        this.userList = gson.fromJson(jsonObject.toString(), type);
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public void setAllUsers(List<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }




}
