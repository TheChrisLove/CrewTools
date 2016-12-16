package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CommonUtilities;


/**
 * Renovated by The Chris Love on 2016-06-29.
 */
public class AddPlaceActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtFind, txtTitle;
    private ImageView imgCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtilities.hideKeyBoard(mContext);
    }

    private void initViews() {
        mContext = AddPlaceActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_add_place_top);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_add_place_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle.setText("ADD ADDRESS");
        imgCancel = (ImageView) findViewById(R.id.imageview_add_place_menu_close);
        txtFind = (TextView) findViewById(R.id.textview_toolbar_add_place_find);
        txtFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Find address", Toast.LENGTH_SHORT).show();
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}