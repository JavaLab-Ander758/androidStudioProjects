package com.andyeseapps.volley_og_rest.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.andyeseapps.volley_og_rest.Data.UserData;
import com.andyeseapps.volley_og_rest.Utils;
import com.andyeseapps.volley_og_rest.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private MutableLiveData<UserData> userData = new MutableLiveData<>(new UserData(""));
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private String mUrlString = "https://jsonplaceholder.typicode.com/users/";
    private RequestQueue queue;

    public UserRepository() {
    }

    public MutableLiveData<UserData> getUserData() {
        return userData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void download(Context context) {
        Utils.logToLogcat(this.getClass().getSimpleName(), new Object(){});

        queue = Volley.newRequestQueue(context);

        // Laster ned en LISTE med JSON-objekter:
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mUrlString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            Gson gson = new Gson();

                            // Loop gjennom returnert JSON-array:
                            ArrayList<User> tmpList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject albumJson = response.getJSONObject(i);
                                User user = gson.fromJson(albumJson.toString(), User.class);
                                tmpList.add(user);
                            }

                            UserData tmpAlbumData = new UserData(tmpList);
                            userData.postValue(tmpAlbumData);

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
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(jsonArrayRequest);
    }
}