package com.andyeseapps.volley_og_rest.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyeseapps.volley_og_rest.Data.UserData;
import com.andyeseapps.volley_og_rest.Repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<UserData> userData;

    public UserViewModel() {
        this.userRepository = new UserRepository();
        this.userData = this.userRepository.getUserData();
    }

    public LiveData<UserData> getUserData() {
        return this.userData;
    }

    public void requestDownload(Context guiContext) {
        this.userRepository.download/*Users*/(guiContext);
    }
}