package com.andyeseapps.volley_og_rest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andyeseapps.volley_og_rest.Data.UserData;
import com.andyeseapps.volley_og_rest.ViewModel.UserViewModel;
import com.andyeseapps.volley_og_rest.model.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean downloadingUserStatus = false;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        this.subscribe();
    }


    /**
     * Subscribe for download
     */
    private void subscribe() {
        final Observer<UserData> userDataObserver = new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if (downloadingUserStatus) {
                    downloadingUserStatus = false;
                    listUsers();
                    Log.d("testing", "Finished downloading");

                }
            }
        };
        userViewModel.getUserData().observe(this, userDataObserver);
    }


    /**
     * Downloads all users to userViewModel
     *
     * @param view View from Button
     */
    public void downloadUsers(View view) {
        Utils.displayToast(this, getString(R.string.downloading_users));
        if (userViewModel != null) {
            userViewModel.requestDownload(getApplicationContext());
            downloadingUserStatus = true;
        } else
            downloadingUserStatus = false;
    }

    /**
     * List users in a ListView and add onItemClick listeners to each item
     */
    public void listUsers() {
        final List<User> users = userViewModel.getUserData().getValue().getUsers();
        final ArrayList<String> userNames = new ArrayList<>();
        if (users != null) {
            for (int i = 0; i < users.size(); i++)
                userNames.add(users.get(i).getName());
        }

        ListView listView = (ListView) findViewById(R.id.listId);
        createListViewString(userNames, listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent userIdIntent = new Intent(MainActivity.this, AlbumActivity.class);
                userIdIntent.putExtra("userId", i + 1); // +1 since json data is 1-indexed
                userIdIntent.putExtra("name", users.get(i).getName());
                MainActivity.this.startActivity(userIdIntent);
            }
        });
    }

    /**
     * Creates a ListView and puts all elements from ArrayList arrayListElements in it
     *
     * @param arrayListElements ArrayList with elements for ListView
     */
    public void createListViewString(ArrayList<String> arrayListElements, ListView listView) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1, arrayListElements);
        listView.setAdapter(arrayAdapter);
    }
}