package com.andyeseapps.trengingsprogram.data;

import com.andyeseapps.trengingsprogram.entity.Program;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProgramData {
    private List<Program> programs = new ArrayList<>();

    public ProgramData() {
    }

    public ProgramData(JsonArray programJson) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        Type programListType = new TypeToken<ArrayList<Program>>(){}.getType();
        this.programs = gson.fromJson(programJson.toString(), programListType);
    }

    public ProgramData(List<Program> programs) {
        this.programs = programs;
    }

    public ProgramData(String programListAsJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Program>>(){}.getType();
        this.programs = gson.fromJson(programListAsJson, type);
    }

    public ProgramData(JSONObject jsonObject) {
        Gson gson;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M. d.yy hh:mm a");
        gson = gsonBuilder.create();

        Type type = new TypeToken<ArrayList<Program>>(){}.getType();
        this.programs = gson.fromJson(jsonObject.toString(), type);
    }

    public void addProgram(Program program) {
        programs.add(program);
    }

    /* Getters & Setters */
    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programsList) {
        this.programs = programsList;
    }
}
