package com.andyeseapps.json;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.andyeseapps.model.Question;
import com.andyeseapps.model.Result;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultRepository {
    private MutableLiveData<ResultData> resultData = new MutableLiveData<>(new ResultData(""));
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private String mUrlString = "https://opentdb.com/api.php?amount=10";
    private RequestQueue queue;

    public ResultRepository() {
    }

    public MutableLiveData<ResultData> getResultData() {
        return resultData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void download(Context context, String defaultUrlAPI) {
        queue = Volley.newRequestQueue(context);

        Log.d("testing", "download in ResultRepository ran...");

        // Laster ned en LISTE med JSON-objekter:
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, defaultUrlAPI, null, new Response.Listener<JSONArray>() {
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
                            ArrayList<Result> tmpList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject resultJson = response.getJSONObject(i);
                                Result result = gson.fromJson(resultJson.toString(), Result.class);
                                tmpList.add(result);
                            }

                            ResultData tmpResultData = new ResultData(tmpList);
                            resultData.postValue(tmpResultData);

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