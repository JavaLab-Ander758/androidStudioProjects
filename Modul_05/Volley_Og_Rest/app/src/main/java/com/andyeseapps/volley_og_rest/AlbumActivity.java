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

import com.andyeseapps.volley_og_rest.Data.AlbumData;
import com.andyeseapps.volley_og_rest.ViewModel.AlbumsViewModel;
import com.andyeseapps.volley_og_rest.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    private int userId;
    private String name;
    private boolean downloadingAlbumStatus = false;
    private AlbumsViewModel albumViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_view);

        Intent userIdIntent = getIntent();
        userId = userIdIntent.getIntExtra("userId", -1);
        name = userIdIntent.getStringExtra("name");

        TextView textView = findViewById(R.id.userNameTextView);
        textView.setText(name);

        albumViewModel = new ViewModelProvider(this).get(AlbumsViewModel.class);
        this.subscribe();
    }

    private void subscribe() {
        final Observer<AlbumData> albumDataObserver = new Observer<AlbumData>() {
            @Override
            public void onChanged(AlbumData albumData) {
                if (downloadingAlbumStatus) {
                    downloadingAlbumStatus = false;
                    listAlbums();
                }
            }
        };
        albumViewModel.getPhotoData().observe(this, albumDataObserver);
    }


    /**
     * Downloads all albums to albumViewModel
     * @param view View from Button
     */
    public void downloadPhotos(View view) {
        Utils.displayToast(this, getString(R.string.downloading_albums));

        if (albumViewModel != null) {
            albumViewModel.startDownload(getApplicationContext());
            downloadingAlbumStatus = true;
        } else
            downloadingAlbumStatus = false;
    }

    /**
     * List albusm in a ListView and add onItemClick listeners to each item
     */
    public void listAlbums() {
        final List<Album> albums = albumViewModel.getPhotoData().getValue().getAllAlbums();
        final ArrayList<String> titles = new ArrayList<>();
        final ArrayList<Integer> albumIds = new ArrayList<>();
        if (albums != null) {
            for (int i = 0; i < albums.size(); i++)
                if (albums.get(i).getUserId() == userId) {
                    titles.add(albums.get(i).getTitle());
                    albumIds.add(albums.get(i).getId());
                }
        }



        ListView listView = (ListView)findViewById(R.id.albumListViewID);
        createListViewString(titles, listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent albumIdIntent = new Intent(AlbumActivity.this, PhotoActivity.class);
                albumIdIntent.putExtra("albumId", albumIds.get(i)); // +1 since json data is 1-indexed
                albumIdIntent.putExtra("title", titles.get(i));
                AlbumActivity.this.startActivity(albumIdIntent);
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