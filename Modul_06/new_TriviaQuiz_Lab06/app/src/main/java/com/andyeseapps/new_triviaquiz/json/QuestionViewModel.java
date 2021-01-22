package com.andyeseapps.new_triviaquiz.json;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuestionViewModel extends ViewModel {
    private QuestionRepository questionRepository;
    private MutableLiveData<QuestionData> questionData;

    public QuestionViewModel() {
        this.questionRepository = new QuestionRepository();
        this.questionData = this.questionRepository.getQuestionData();
    }

    public LiveData<QuestionData> getQuestionData() {
        return this.questionData;
    }

    public void requestDownload(Context guiContext, String defaultUrlAPI) {
        Log.d("testing", "requestDownload in QuestionViewModel ran...");

        this.questionRepository.download/*Users*/(guiContext, defaultUrlAPI);


    }
}