package com.andyeseapps.trengingsprogram.data;

import com.andyeseapps.trengingsprogram.entity.Exercise;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExerciseData {
    private List<Exercise> exercises = new ArrayList<>();

    public ExerciseData() {
    }

    public ExerciseData(JsonArray exerciseJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type exerciseListType = new TypeToken<ArrayList<Exercise>>(){}.getType();
        this.exercises = gson.fromJson(exerciseJson.toString(), exerciseListType);
    }

    public ExerciseData(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public ExerciseData(String exerciseListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        this.exercises = gson.fromJson(exerciseListAsJson, type);
    }

    public ExerciseData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M. d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        this.exercises = gson.fromJson(jsonObject.toString(), type);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    /* Getters & Setters */
    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercisesList) {
        this.exercises = exercisesList;
    }
}