package com.andyeseapps.volley_og_rest.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyeseapps.volley_og_rest.Data.AlbumData;
import com.andyeseapps.volley_og_rest.Repository.AlbumRepository;

public class AlbumsViewModel extends ViewModel {
    private AlbumRepository albumRepository;
    private MutableLiveData<AlbumData> albumData;

    public AlbumsViewModel() {
        this.albumRepository = new AlbumRepository();
        this.albumData = this.albumRepository.getAlbumData();
    }

    public LiveData<AlbumData> getPhotoData() {
        return this.albumData;
    }

    public void startDownload(Context guiContext) {
        this.albumRepository.download(guiContext);
    }
}