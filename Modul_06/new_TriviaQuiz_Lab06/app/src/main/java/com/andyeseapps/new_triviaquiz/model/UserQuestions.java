package com.andyeseapps.new_triviaquiz.model;

import java.io.Serializable;
import java.util.List;

public class UserQuestions implements Serializable {
    private List<Question> questions;
    private int currentIndex = 0;
    private int numOfIncorrectAnswers = 0;
    private int numOfCorrectAnswers = 0;

    public UserQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void appendCurrentIndex() {
        currentIndex++;
    }

    public void appendNumOfIncorrectAnswers() {
        numOfIncorrectAnswers++;
    }

    public void appendNumOfCorrectAnswers() {
        numOfCorrectAnswers++;
    }

    /* Getters and Setters */
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public int getCurrentIndex() {
        return currentIndex;
    }
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
    public int getNumOfIncorrectAnswers() {
        return numOfIncorrectAnswers;
    }
    public void setNumOfIncorrectAnswers(int numOfIncorrectAnswers) {
        this.numOfIncorrectAnswers = numOfIncorrectAnswers;
    }
    public int getNumOfCorrectAnswers() {
        return numOfCorrectAnswers;
    }
    public void setNumOfCorrectAnswers(int numOfCorrectAnswers) {
        this.numOfCorrectAnswers = numOfCorrectAnswers;
    }
}
