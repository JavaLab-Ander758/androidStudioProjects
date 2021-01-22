package com.andyeseapps.trengingsprogram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyeseapps.trengingsprogram.data.ExerciseData;
import com.andyeseapps.trengingsprogram.repository.ExerciseRepository;

public class ExerciseViewModel extends ViewModel {
    private ExerciseRepository exerciseRepository;
    private MutableLiveData<ExerciseData> exerciseData;

    public ExerciseViewModel() {
        this.exerciseRepository = new ExerciseRepository();
        this.exerciseData = this.exerciseRepository.getExerciseData();
    }

    public LiveData<ExerciseData> getExerciseData() {
        return this.exerciseData;
    }

    public void requestDownload(Context guiContext, int programId) {
        this.exerciseRepository.download(guiContext, programId);
    }
}