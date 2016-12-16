package com.mk27manoj.crewtools;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Renovated by The Chris Love (thechrislove@icloud.com) on 11-30-2016.
 */
public class FullScreenPhotoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        ImageView imageView = (ImageView) findViewById(R.id.imageview_full_screen_photo_picture);
        if (getIntent() != null){
            String imageUrl = getIntent().getStringExtra("FILE_URL");
            Picasso.with(this).load(imageUrl).into(imageView);
        }

    }
}
