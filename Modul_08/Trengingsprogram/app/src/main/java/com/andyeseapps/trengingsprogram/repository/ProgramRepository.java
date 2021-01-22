package com.andyeseapps.trengingsprogram.repository;

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
import com.andyeseapps.trengingsprogram.data.ProgramData;
import com.andyeseapps.trengingsprogram.entity.Program;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProgramRepository {
    private MutableLiveData<ProgramData> programData = new MutableLiveData<>(new ProgramData(""));
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private String mUrlString = "http://tusk.systems/trainingapp/v2/api.php/programs";
    private RequestQueue queue;

    public ProgramRepository() {
    }

    public MutableLiveData<ProgramData> getProgramData() {
        return programData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public void download(Context context) {
        Log.d("Testing", mUrlString);

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
                            ArrayList<Program> tmpList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject albumJson = response.getJSONObject(i);
                                Program program = gson.fromJson(albumJson.toString(), Program.class);
                                tmpList.add(program);
                            }

                            ProgramData tmpAlbumData = new ProgramData(tmpList);
                            programData.postValue(tmpAlbumData);

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