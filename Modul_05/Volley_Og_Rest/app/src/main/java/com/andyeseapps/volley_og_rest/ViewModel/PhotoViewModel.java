package com.andyeseapps.volley_og_rest.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyeseapps.volley_og_rest.Data.PhotoData;
import com.andyeseapps.volley_og_rest.Repository.PhotoRepository;

public class PhotoViewModel extends ViewModel {
    private PhotoRepository photoRepository;
    private MutableLiveData<PhotoData> photoData;

    public PhotoViewModel() {
        this.photoRepository = new PhotoRepository();
        this.photoData = this.photoRepository.getPhotoData();
    }

    public LiveData<PhotoData> getPhotoData() {
        return this.photoData;
    }

    public void startDownload(Context guiContext) {
        this.photoRepository.download(guiContext);
    }
}