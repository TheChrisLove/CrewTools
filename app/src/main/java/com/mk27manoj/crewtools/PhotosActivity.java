package com.mk27manoj.crewtools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotosActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private ImageView imgCancel, imgAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        initViews();
    }

    private void initViews() {
        mContext = PhotosActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_photos_top);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_photos_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgAdd = (ImageView) findViewById(R.id.imageview_toolbar_photos_add);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new AlertDialog.Builder(mContext)
                        .setTitle("Add Photo")
                        .setNegativeButton("Cancel", null)
                        .setItems(new String[]{"Take Picture", "Existing Picture"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg, int position) {
                                if (position == 0) {
                                    //TODO open camera
                                    Toast.makeText(mContext, "Open Camera and capture picture", Toast.LENGTH_SHORT).show();
                                } else if (position == 1) {
                                    //TODO open galary
                                    Toast.makeText(mContext, "Open Gallery and get picture", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create();
                d.show();

            }
        });
    }
}