package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.TimeCrewPickerActivity;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

public class ScheduledJobActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone;
    private ImageView imgCancel;
    private LinearLayout jobsDatesContainer;
    private String[] datesList;
    private String dates = "";
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_job);
        initViews();
    }

    private void initViews() {
        mContext = ScheduledJobActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_scheduled_job_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_scheduled_job_title);
        txtTitle.setText("SCHEDULED JOBS");

        txtDone = (TextView) findViewById(R.id.textview_toolbar_scheduled_job_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, "7/2/2016 8:38 PM");
                setResult(CrewToolsConstants.REQUEST_JOB_PROGRESS_SCHEDULED, intent);
                finish();//finishing activity
            }
        });
        
        imgCancel = (ImageView) findViewById(R.id.imageview_scheduled_job_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jobsDatesContainer = (LinearLayout) findViewById(R.id.linearlayout_scheduled_jobs_dates_container);
        if (jobsDatesContainer.getChildCount() == 0) {
            startActivityForResult(new Intent(mContext, CalenderDatesPickerActivity.class), CrewToolsConstants.REQUEST_MULTIPLE_DATES);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_MULTIPLE_DATES:
                    dates = data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE);
                    datesList = dates.split("@@");
                    for (int i = 0; i < datesList.length; i++) {
                        addView();
                    }
                    break;

                case CrewToolsConstants.REQUEST_TIME_CREW_PICKER:
                    String[] message = (data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE)).split("@@");
                    ((TextView) jobsDatesContainer.getChildAt(position).findViewById(R.id.textview_scheduled_job_date_item_time)).setText(message[0]);
                    ((TextView) jobsDatesContainer.getChildAt(position).findViewById(R.id.textview_scheduled_job_date_item_employee)).setText(message[1]);
                    break;

//                case CrewToolsConstants.REQUEST_CAPTURE_SIGN_CLIENT:
//                    hasClientSigned = true;
//                    break;
            }
        }
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_scheduled_job_date_item, null);
        TextView txtDate = (TextView) baseView.findViewById(R.id.textview_scheduled_job_date_item_date);
        TextView txtEmployee = (TextView) baseView.findViewById(R.id.textview_scheduled_job_date_item_employee);
        TextView txtTime = (TextView) baseView.findViewById(R.id.textview_scheduled_job_date_item_time);

        baseView.setTag(jobsDatesContainer.getChildCount());
        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = Integer.parseInt(v.getTag().toString().trim());
                startActivityForResult(new Intent(mContext, TimeCrewPickerActivity.class).putExtra("TITLE", datesList[jobsDatesContainer.getChildCount() - 1]), CrewToolsConstants.REQUEST_TIME_CREW_PICKER);
            }
        });

        txtDate.setText(datesList[jobsDatesContainer.getChildCount()]);
        jobsDatesContainer.addView(baseView);
    }
}
