package com.andyeseapps.treningsprogram_navigation.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.andyeseapps.treningsprogram_navigation.data.ExerciseData;
import com.andyeseapps.treningsprogram_navigation.entity.Exercise;
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
        String mURL = String.format("%s%s%s", "http://tusk.systems/trainingapp/v2/api.php/programs/", String.valueOf(programId), "?_expand_children=true");
        Log.d("Testing", String.format("newUrl=%s", mURL));

//        queue = SingletonQueue.getInst
        queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mURL,
                null,
                jsonObject -> {
                    try {
                        Gson gson = new Gson();
                        ArrayList<Exercise> tmpList = new ArrayList<>();
                        JSONArray program_exercises = jsonObject.getJSONArray("exercises");
                        for (int i = 0; i < program_exercises.length(); i++) {
                            JSONObject programExerciseAsJson = program_exercises.getJSONObject(i);
                            JSONObject exerciseAsJsonObject = programExerciseAsJson.getJSONObject("exercise");
                            Exercise exercise = gson.fromJson(exerciseAsJsonObject.toString(), Exercise.class);
                            tmpList.add(exercise);
                        }
                        ExerciseData tmpExerciseData = new ExerciseData(tmpList);
                        exerciseData.postValue(tmpExerciseData);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }, error -> errorMessage.postValue(error.getMessage())) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}