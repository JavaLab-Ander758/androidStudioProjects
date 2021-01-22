package com.andyeseapps.volleyogrest.ViewModel;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andyeseapps.volleyogrest.Data.UserData;
import com.andyeseapps.volleyogrest.Repository.UserRepository;

public class UserViewModel extends ViewModel {
    private ArrayAdapter arrayAdapter;
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
