package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mk27manoj.crewtools.adapters.JobsAdapter;
import com.mk27manoj.crewtools.jobs.JobsActivity;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

public class JobPickerActivity extends AppCompatActivity {
    private Context mContext;
    private ListView mListView;
    private ArrayList<String> jobsList;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel, imgAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_picker);
        initViews();
    }

    private void initViews() {
        mContext = JobPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_job_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_job_picker_title);
        txtTitle.setText("JOBS");

        imgAdd = (ImageView) findViewById(R.id.imageview_toolbar_job_picker_add);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, JobsActivity.class));
            }
        });
        imgCancel = (ImageView) findViewById(R.id.imageview_job_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (ListView) findViewById(R.id.listview_job_picker_job_list);
        jobsList = new ArrayList<>();
        jobsList.add("Sample Job@@Norman Kichline");
        jobsList.add("Mulching for Pete Logan@@Pete Logan");
        jobsList.add("Church Renovation@@Pete Logan");
        jobsList.add("Sample Job@@Norman Kichline");

//        mListView.setAdapter(new JobsAdapter(mContext, jobsList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, jobsList.get(position).split("@@")[0]);
                setResult(CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_JOB, intent);
                finish();//finishing activity
            }
        });

    }
}