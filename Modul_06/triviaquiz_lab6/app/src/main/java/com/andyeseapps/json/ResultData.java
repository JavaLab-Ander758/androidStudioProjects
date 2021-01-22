package com.andyeseapps.json;

import com.andyeseapps.model.Question;
import com.andyeseapps.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResultData {
    private List<Result> results = new ArrayList<>();

    public ResultData() {
    }

    public ResultData(JsonArray resultJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type ResultListType = new TypeToken<ArrayList<Result>>(){}.getType();
        this.results = gson.fromJson(resultJson.toString(), ResultListType);
    }

    public ResultData(List<Result> results) {
        this.results = results;
    }

    public ResultData(String resultListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Result>>(){}.getType();
        this.results = gson.fromJson(resultListAsJson, type);
    }

    public ResultData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M. d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Result>>(){}.getType();
        this.results = gson.fromJson(jsonObject.toString(), type);
    }

    public void addResult(Result result) {
        results.add(result);
    }

    /* Getters & Setters */
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> resultList) {
        this.results = resultList;
    }
}
