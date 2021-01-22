package com.andyeseapps.volleyogrest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.andyeseapps.volleyogrest.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataRepository {
    private RequestQueue queue;

    public DataRepository() {
    }

    /*public void downloadUsers(Context context) {
        String mUrlString = "https://jsonplaceholder.typicode.com/users/";

        // Laster ned en LISTE med JSON-objekter:
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mUrlString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Gson gson = new Gson();
                            // Loop gjennom returnert JSON-array:
                            ArrayList<User> tmpList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject userAsJson = response.getJSONObject(i);
                                User user = gson.fromJson(userAsJson.toString(), User.class);
                                tmpList.add(user);
                            }
                            users.postValue(tmpList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorMessage.postValue(error.getMessage());
                    }
                });

        queue.add(jsonArrayRequest);


    }*/
}