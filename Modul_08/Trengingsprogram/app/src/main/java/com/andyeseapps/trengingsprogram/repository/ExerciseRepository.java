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
import com.andyeseapps.trengingsprogram.data.ExerciseData;
import com.andyeseapps.trengingsprogram.entity.Exercise;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExerciseRepository {
    private MutableLiveData<ExerciseData> exerciseData = new MutableLiveData<>(new ExerciseData(""));
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private String mUrlString = "http://tusk.systems/trainingapp/v2/api.php/program_exercises";
    private RequestQueue queue;

    public ExerciseRepository() {
    }

    public MutableLiveData<ExerciseData> getExerciseData() {
        return exerciseData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void download(Context context, int programId) {
        // TODO: Virker ikke som at GET forespørsler funker for V2-linkene

        String newUrl = mUrlString;
        /*newUrl += "?program_id=" + programId;
        Log.d("Testing", "Exercise_URL=" + newUrl + " ProgramId=" + programId);*/

        queue = Volley.newRequestQueue(context);

        // Laster ned en LISTE med JSON-objekter:
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                newUrl,
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
                            ArrayList<Exercise> tmpList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject albumJson = response.getJSONObject(i);
                                Exercise exercise = gson.fromJson(albumJson.toString(), Exercise.class);
                                tmpList.add(exercise);
                            }

                            ExerciseData tmpAlbumData = new ExerciseData(tmpList);
                            exerciseData.postValue(tmpAlbumData);

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