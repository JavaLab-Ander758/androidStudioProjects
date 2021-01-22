package com.andyeseapps.new_triviaquiz.json;

import com.andyeseapps.new_triviaquiz.model.Question;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionData {
    private List<Question> questions = new ArrayList<>();

    public QuestionData() {
    }

    public QuestionData(JsonArray usersJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type questionListType = new TypeToken<ArrayList<Question>>(){}.getType();
        this.questions = gson.fromJson(usersJson.toString(), questionListType);
    }

    public QuestionData(List<Question> questions) {
        this.questions = questions;
    }

    public QuestionData(String userListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Question>>(){}.getType();
        this.questions = gson.fromJson(userListAsJson, type);
    }

    public QuestionData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M. d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Question>>(){}.getType();
        this.questions = gson.fromJson(jsonObject.toString(), type);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    /* Getters & Setters */
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questionList) {
        this.questions = questionList;
    }
}
