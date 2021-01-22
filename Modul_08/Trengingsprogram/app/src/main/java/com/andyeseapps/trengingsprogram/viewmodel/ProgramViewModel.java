package com.andyeseapps.trengingsprogram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyeseapps.trengingsprogram.data.ProgramData;
import com.andyeseapps.trengingsprogram.repository.ProgramRepository;

public class ProgramViewModel extends ViewModel {
    private ProgramRepository programRepository;
    private MutableLiveData<ProgramData> programData;

    public ProgramViewModel() {
        this.programRepository = new ProgramRepository();
        this.programData = this.programRepository.getProgramData();
    }

    public LiveData<ProgramData> getProgramData() {
        return this.programData;
    }

    public void requestDownload(Context guiContext) {
        this.programRepository.download(guiContext);
    }
}