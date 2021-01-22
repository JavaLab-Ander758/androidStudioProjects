package com.andyeseapps.volleyogrest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.andyeseapps.volleyogrest.Data.UserData;
import com.andyeseapps.volleyogrest.ViewModel.AlbumsViewModel;
import com.andyeseapps.volleyogrest.ViewModel.UserViewModel;
import com.andyeseapps.volleyogrest.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private UserViewModel userViewModel;
    private AlbumsViewModel albumsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        listView = (ListView)findViewById(R.id.listId);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("asdf");
        arrayList.add("a3425f");
        arrayList.add("asdf");
        arrayList.add("a3425f");
        arrayList.add("asdf");
        arrayList.add("a3425f");
        arrayList.add("asdf");
        arrayList.add("a3425f");

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, andriod.R.layout.simple_list_item_1, arrayList);





        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        albumsViewModel = new ViewModelProvider(this).get(AlbumsViewModel.class);
        this.subscribe();
    }

    private void subscribe() {
        final Observer<UserData> userDataObserver = new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                System.out.println("User data updated.");
            }
        };
        userViewModel.getUserData().observe(this, userDataObserver);
    }

    public void download(View view) {
        if (userViewModel != null /*&& !mDownloading*/) {
            userViewModel.requestDownload(getApplicationContext());/*startDownload(getApplicationContext());*/
        }

        TextView textView = findViewById(R.id.testID);
        List<User> users = userViewModel.getUserData().getValue().getUsers();
        String[] userNames;
        if (users != null) {
            userNames = new String[users.size()];
            for (int i = 0; i < users.size(); i++) {
                userNames[i] = users.get(i).getName();
            }
        }



        if (users != null)
            if (!users.isEmpty()) {
                textView.setText(users.get(9).getWebsite());
            } else {
                textView.setText("users was empty");
            }
        else
            textView.setText("users was null");
    }
}