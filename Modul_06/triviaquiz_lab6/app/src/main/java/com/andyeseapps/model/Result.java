package com.andyeseapps.model;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    private int response_code;
    private List<Question> results;

    public Result (int response_code, List<Question> results) {
        this.response_code = response_code;
        this.results = results;
    }

    public void setResponse_code(int response_code){
        this.response_code = response_code;
    }
    public int getResponse_code(){
        return this.response_code;
    }
    public void setResults(List<Question> results){
        this.results = results;
    }
    public List<Question> getResults(){
        return this.results;
    }
}
