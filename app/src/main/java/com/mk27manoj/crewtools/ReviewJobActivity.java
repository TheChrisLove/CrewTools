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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

public class ReviewJobActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtDone;
    private ImageView imgCancel;
    private ImageView imgOutstandingCheck;
    private RelativeLayout relativeOutstandingJobs, relativePhotos;
    private LinearLayout outstandingContainer;
    private ArrayList<String> outstandingJobs;
    private TextView txtEnsureCompleteMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_job);
        initViews();
    }

    private void initViews() {
        mContext = ReviewJobActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_review_job_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_review_job_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtDone = (TextView) findViewById(R.id.textview_toolbar_review_job_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, "7/2/2016 8:38 PM");
                setResult(CrewToolsConstants.REQUEST_JOB_PROGRESS_COMPLETED, intent);
                finish();//finishing activity
            }
        });

        imgOutstandingCheck = (ImageView) findViewById(R.id.imageview_review_job_outstanding_checked);
        imgOutstandingCheck.setImageResource(R.drawable.ic_checked);

        txtEnsureCompleteMsg = (TextView) findViewById(R.id.textview_review_job_ensure_complete_msg);
        relativeOutstandingJobs = (RelativeLayout) findViewById(R.id.relativelayout_review_job_outstanding_job_layout);
        relativePhotos = (RelativeLayout) findViewById(R.id.relativelayout_review_job_photos_layout);
        relativePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, PhotosActivity.class));
            }
        });
        outstandingContainer = (LinearLayout) findViewById(R.id.linearlayout_outstanding_jobs_container);

        outstandingJobs = new ArrayList<>();
        outstandingJobs.add("13-Jul-2016@@ ");
        outstandingJobs.add("14-Jul-2016@@ ");
        outstandingJobs.add("15-Jul-2016@@ ");
        outstandingJobs.add("16-Jul-2016@@ ");
        outstandingJobs.add("17-Jul-2016@@ ");
        outstandingJobs.add("18-Jul-2016@@ ");
        outstandingJobs.add("19-Jul-2016@@ ");
        outstandingJobs.add("20-Jul-2016@@ ");
        outstandingJobs.add("21-Jul-2016@@ ");
        outstandingJobs.add("22-Jul-2016@@ ");
        outstandingJobs.add("23-Jul-2016@@ ");
        outstandingJobs.add("24-Jul-2016@@ ");
        outstandingJobs.add("25-Jul-2016@@7:00AM - 7:30AM");

        for (int i = 0; i < outstandingJobs.size(); i++) {
            addView();
        }

        if (outstandingContainer.getChildCount() != 0) {
            relativeOutstandingJobs.setVisibility(View.GONE);
            txtEnsureCompleteMsg.setVisibility(View.VISIBLE);
        } else {
            relativeOutstandingJobs.setVisibility(View.VISIBLE);
            txtEnsureCompleteMsg.setVisibility(View.GONE);
        }
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_REVIEW_JOB_OUTSTANDING:
//                    txtOpenedDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgOutstandingCheck.setImageResource(R.drawable.ic_checked);
                    break;
            }
        }
    }
*/

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_outstanding_job, null);
        TextView txtDate = (TextView) baseView.findViewById(R.id.textview_outstanding_job_date);
        TextView txtTime = (TextView) baseView.findViewById(R.id.textview_outstanding_job_time);
        final ImageView imgChecked = (ImageView) baseView.findViewById(R.id.imageview_outstanding_job_checked);
        baseView.setTag(outstandingContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgChecked.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_unchecked).getConstantState()) {
                    imgChecked.setImageResource(R.drawable.ic_checked);
                } else {
//                    Toast.makeText(_con, "Image isn't ivPic", Toast.LENGTH_LONG).show();
                    imgChecked.setImageResource(R.drawable.ic_unchecked);
                    // new RegisterAsyntask().execute();
                }
                int count = Integer.parseInt(v.getTag().toString().trim());

//                Intent intent = new Intent();
//                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, occurences.get(count));
//                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_OCCURANCE, intent);
//                finish();//finishing activity
            }
        });
        String data[] = outstandingJobs.get(outstandingContainer.getChildCount()).split("@@");
        txtDate.setText(data[0]);
        txtTime.setText(data[1]);
        outstandingContainer.addView(baseView);
    }
}