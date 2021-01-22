package com.andyeseapps.model;

import java.io.Serializable;
import java.util.ArrayList;

public class IncorrectAnswers implements Serializable {

    private ArrayList<String> incorrect_answers;

    public IncorrectAnswers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }


    /* Getters and Setters */
    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }
    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }
}
