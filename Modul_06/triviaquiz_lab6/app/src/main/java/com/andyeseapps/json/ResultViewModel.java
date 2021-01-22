package com.andyeseapps.json;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultViewModel extends ViewModel {
    private ResultRepository resultRepository;
    private MutableLiveData<ResultData> resultData;

    public ResultViewModel() {
        this.resultRepository = new ResultRepository();
        this.resultData = this.resultRepository.getResultData();
    }

    public LiveData<ResultData> getResultData() {
        return this.resultData;
    }

    public void requestDownload(Context guiContext, String defaultUrlAPI) {
        Log.d("testing", "requestDownload in QuestionViewModel ran...");

        this.resultRepository.download/*Users*/(guiContext, defaultUrlAPI);


    }
}