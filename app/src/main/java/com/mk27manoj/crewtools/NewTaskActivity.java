package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk27manoj.crewtools.jobs.PlacePickerActivity;
import com.mk27manoj.crewtools.jobs.PriorityPickerActivity;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private Button btnSave;
    private TextView txtJob, txtPlace, txtPriority, txtDate, txtTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        initViews();
    }

    private void initViews() {
        mContext = NewTaskActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_new_task_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_new_task_title);

        txtTitle.setText("VIEW INVOICE");

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_new_task_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave = (Button) findViewById(R.id.button_new_task_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        txtJob = (TextView) findViewById(R.id.textview_new_task_job);
        txtPlace = (TextView) findViewById(R.id.textview_new_task_place);
        txtPriority = (TextView) findViewById(R.id.textview_new_task_priority);
        txtDate = (TextView) findViewById(R.id.textview_new_task_date);
        txtTime = (TextView) findViewById(R.id.textview_new_task_time);
        txtJob.setOnClickListener(this);
        txtPlace.setOnClickListener(this);
        txtPriority.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_JOB) {
                txtJob.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
            }
            if (requestCode == CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE) {
                txtPlace.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
            }
            if (requestCode == CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PRIORITY) {
                txtPriority.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_new_task_job:
                startActivityForResult(new Intent(mContext, JobPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_JOB);
                break;
            case R.id.textview_new_task_place:
                startActivityForResult(new Intent(mContext, PlacePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE);
                break;
            case R.id.textview_new_task_priority:
                startActivityForResult(new Intent(mContext, PriorityPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PRIORITY);
                break;
            case R.id.textview_new_task_date:
                CommonUtilities.setDateOnView(mContext, txtDate);
                break;
            case R.id.textview_new_task_time:
                CommonUtilities.setTimeOnView(mContext, txtTime);
                break;
        }
    }
}