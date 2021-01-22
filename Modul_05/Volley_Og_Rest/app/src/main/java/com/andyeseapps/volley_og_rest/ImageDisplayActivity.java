package com.andyeseapps.volley_og_rest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends AppCompatActivity {
    private int photoId;
    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display_view);

        Intent photoIdIntent = getIntent();
        photoId = photoIdIntent.getIntExtra("photoId", -1);
        title = photoIdIntent.getStringExtra("title");
        url = photoIdIntent.getStringExtra("url");

        TextView imageDisplayUrlTextView = findViewById(R.id.imageDisplayUrlTextView);
        imageDisplayUrlTextView.setText(url);

        TextView textView = findViewById(R.id.photoTitleTextView);
        textView.setText(title);
    }

    public void displayImage(View view) {
        Utils.displayToast(this, getString(R.string.displaying_image));
        ImageView imageView = findViewById(R.id.imageDisplayer);
        Picasso.get().load(url).into(imageView);
        Utils.displayToast(this, getString(R.string.image_displayed));
    }
}