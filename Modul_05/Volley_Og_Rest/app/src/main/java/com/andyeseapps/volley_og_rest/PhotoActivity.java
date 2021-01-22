package com.andyeseapps.volley_og_rest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andyeseapps.volley_og_rest.Data.PhotoData;
import com.andyeseapps.volley_og_rest.ViewModel.PhotoViewModel;
import com.andyeseapps.volley_og_rest.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    private int albumId;
    private String title;
    private boolean downloadingPhotoStatus = false;
    private PhotoViewModel photoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_view);

        Intent albumIdIntent = getIntent();
        albumId = albumIdIntent.getIntExtra("albumId", -1);
        title = albumIdIntent.getStringExtra("title");

        TextView textView = findViewById(R.id.userNameTextView);
        textView.setText(title);

        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        this.subscribe();
    }


    private void subscribe() {
        final Observer<PhotoData> photoDataObserver = new Observer<PhotoData>() {
            @Override
            public void onChanged(PhotoData photoData) {
                if (downloadingPhotoStatus) {
                    downloadingPhotoStatus = false;
                    listPhoto();
                }
            }
        };
        photoViewModel.getPhotoData().observe(this, photoDataObserver);
    }


    /**
     * Downloads all photos to photoViewModel
     * @param view View from Button
     */
    public void downloadPhotos(View view) {
        Utils.displayToast(this, getString(R.string.downloading_photos));

        if (photoViewModel != null) {
            photoViewModel.startDownload(getApplicationContext());
            Utils.displayToast(this, getString(R.string.downloading_photos));
            downloadingPhotoStatus = true;
        } else
            downloadingPhotoStatus = false;
    }

    /**
     * List albusm in a ListView and add onItemClick listeners to each item
     */
    public void listPhoto() {
        List<Photo> photos = photoViewModel.getPhotoData().getValue().getAllPhotos();
        final ArrayList<Photo> photoArrayList = new ArrayList<>();

        final ArrayList<String> photoTitlesAndId = new ArrayList<>();
        final ArrayList<Integer> photoIds = new ArrayList<>();
        if (photos != null) {
            for (int i = 0; i < photos.size(); i++)
                if (photos.get(i).getAlbumId() == albumId) {
                    photoTitlesAndId.add(String.format("id[%d] - %s", photos.get(i).getId(), photos.get(i).getTitle()));
                    photoArrayList.add(photos.get(i));
                    photoIds.add(photos.get(i).getId());
                }
        }

        ListView listView = (ListView)findViewById(R.id.listId);
        createListViewString(photoTitlesAndId, listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent photoIdIntent = new Intent(PhotoActivity.this, ImageDisplayActivity.class);
                photoIdIntent.putExtra("photoId", photoIds.get(i));
                photoIdIntent.putExtra("title", photoTitlesAndId.get(i));
                photoIdIntent.putExtra("url", photoArrayList.get(i).getUrl());
                PhotoActivity.this.startActivity(photoIdIntent);
            }
        });
    }

    /**
     * Creates a ListView and puts all elements from ArrayList arrayListElements in it
     * @param arrayListElements ArrayList with elements for ListView
     */
    public void createListViewString(ArrayList<String> arrayListElements, ListView listView) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1, arrayListElements);
        listView.setAdapter(arrayAdapter);
    }
}