package com.mk27manoj.crewtools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewLineItemActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_line_item_editor);
        initViews();
    }

    private void initViews() {
        mContext = NewLineItemActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_new_line_item_editor_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_new_line_item_editor_title);

        txtTitle.setText("NEW LINE ITEM");

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_new_line_item_editor_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
