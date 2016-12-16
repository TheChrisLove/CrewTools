package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

public class TimePickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtCancel, txtDone;
    private TextView txtStartTime, txtEndTIme;
    private ListView mListView;
    private TimePicker startTimePicker, endTimePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        initViews();
    }

    private void initViews() {
        mContext = TimePickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_time_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtCancel = (TextView) findViewById(R.id.textview_toolbar_time_picker_menu_close);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDone = (TextView) findViewById(R.id.textview_toolbar_time_picker_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtEndTIme.getText().equals("") && !txtStartTime.getText().equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, txtStartTime.getText().toString() + " - " + txtEndTIme.getText().toString());
                    setResult(CrewToolsConstants.REQUEST_TIME_CREW_PICKER_TIME, intent);
                    finish();//finishing activity
                }
            }
        });
        startTimePicker = (TimePicker) findViewById(R.id.timepicker_start_time);
        startTimePicker.setVisibility(View.GONE);

        endTimePicker = (TimePicker) findViewById(R.id.timepicker_end_time);
        endTimePicker.setVisibility(View.GONE);

        txtStartTime = (TextView) findViewById(R.id.textview_time_picker_start_time);
        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilities.setTimeOnView(mContext, txtStartTime);
            }
        });
        txtEndTIme = (TextView) findViewById(R.id.textview_time_picker_end_time);
        txtEndTIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilities.setTimeOnView(mContext, txtEndTIme);
            }
        });
    }
}
