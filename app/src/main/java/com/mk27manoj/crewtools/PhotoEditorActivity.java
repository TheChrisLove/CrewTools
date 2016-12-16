package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Renovated by The Chris Love (thechrislove@icloud.com) on 2016-07-09.
 */
public class PhotoEditorActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone, txtCancel;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);
        initViews();
    }

    private void initViews() {
        mContext = PhotoEditorActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_photo_editor_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_photo_editor_title);
        txtTitle.setText("EDIT PHOTO");

        txtDone = (TextView) findViewById(R.id.textview_toolbar_photo_editor_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//finishing activity
            }
        });
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_photo_editor_done);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave = (Button) findViewById(R.id.button_photo_editor_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CompanyActivity.class));
            }
        });
    }
}
