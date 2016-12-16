package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.ReviewJobActivity;
import com.mk27manoj.crewtools.ScheduledJobActivity;
import com.mk27manoj.crewtools.jobs.ViewEstimateActivity;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JobProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private ImageView imgOpenedDate, imgSentDate, imgApprovedDate, imgScheduledDate, imgInvoicedDate, imgCompletedDate;
    private TextView txtOpenedDate, txtSentDate, txtApprovedDate, txtScheduledDate, txtInvoicedDate, txtCompletedDate;
    private RelativeLayout relativeOpened, relativeSent, relativeApproved, relativeScheduled, relativeInvoiced, relativeCompleted;
    private static final String TAG = "JobProgressActivity";
    private CVJob mJob;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_progress);

        if (getIntent() != null){
            try {
                for (CVJob cvJob : ParseQuery.getQuery(CVJob.class).whereEqualTo("objectId", getIntent()
                        .getStringExtra(CrewToolsConstants.RESPONSE_JOB_ID)).find()) {
                    mJob = cvJob;

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setActiveJobProgress();
    }

    private void initViews() {
        mContext = JobProgressActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_job_progress_top);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_job_progress_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle.setText("JOB PROGRESS");

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_job_progress_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgOpenedDate = (ImageView) findViewById(R.id.imageview_job_progress_opened_selection);
        imgSentDate = (ImageView) findViewById(R.id.imageview_job_progress_sent_selection);
        imgApprovedDate = (ImageView) findViewById(R.id.imageview_job_progress_approved_selection);
        imgScheduledDate = (ImageView) findViewById(R.id.imageview_job_progress_scheduled_selection);
        imgInvoicedDate = (ImageView) findViewById(R.id.imageview_job_progress_invoiced_selection);
        imgCompletedDate = (ImageView) findViewById(R.id.imageview_job_progress_completed_selection);

        txtOpenedDate = (TextView) findViewById(R.id.textview_job_progress_opened_date);
        txtOpenedDate.setText(DateFormat.getDateInstance().format(mJob.getCreatedAt()));
        txtSentDate = (TextView) findViewById(R.id.textview_job_progress_sent_date);
        txtApprovedDate = (TextView) findViewById(R.id.textview_job_progress_approved_date);
        txtScheduledDate = (TextView) findViewById(R.id.textview_job_progress_scheduled_date);
        txtInvoicedDate = (TextView) findViewById(R.id.textview_job_progress_invoiced_date);
        txtCompletedDate = (TextView) findViewById(R.id.textview_job_progress_completed_date);

        relativeOpened = (RelativeLayout) findViewById(R.id.relativelayout_job_progress_opened);
        relativeSent = (RelativeLayout) findViewById(R.id.relativelayout_job_progress_sent);
        relativeApproved = (RelativeLayout) findViewById(R.id.relativelayout_job_progress_approved);
        relativeScheduled = (RelativeLayout) findViewById(R.id.relativelayout_job_progress_scheduled);
        relativeInvoiced = (RelativeLayout) findViewById(R.id.relativelayout_job_progress_invoiced);
        relativeCompleted = (RelativeLayout) findViewById(R.id.relativelayout_job_progress_completed);

        relativeOpened.setOnClickListener(this);
        relativeSent.setOnClickListener(this);
        relativeApproved.setOnClickListener(this);
        relativeScheduled.setOnClickListener(this);
        relativeInvoiced.setOnClickListener(this);
        relativeCompleted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativelayout_job_progress_opened:
                break;
            case R.id.relativelayout_job_progress_sent:
                startActivityForResult(new Intent(mContext, ViewEstimateActivity.class).putExtra("Approved", false), CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT);
                break;
            case R.id.relativelayout_job_progress_approved:
                startActivityForResult(new Intent(mContext, ViewEstimateActivity.class).putExtra("Approved", true), CrewToolsConstants.REQUEST_JOB_PROGRESS_APPROVED);
                break;
            case R.id.relativelayout_job_progress_scheduled:
                startActivityForResult(new Intent(mContext, ScheduledJobActivity.class), CrewToolsConstants.REQUEST_JOB_PROGRESS_SCHEDULED);
                break;
            case R.id.relativelayout_job_progress_invoiced:
                break;
            case R.id.relativelayout_job_progress_completed:
                startActivityForResult(new Intent(mContext, ReviewJobActivity.class), CrewToolsConstants.REQUEST_JOB_PROGRESS_COMPLETED);
                break;
        }
    }

    private void setActiveJobProgress(){
        if (mJob.getSent() != null){
            txtSentDate.setText(DateFormat.getDateInstance().format(mJob.getSent()));
            imgSentDate.setImageResource(R.drawable.ic_checked);
        }

        if (mJob.getApprove() != null){
            txtApprovedDate.setText(DateFormat.getDateInstance().format(mJob.getApprove()));
            imgApprovedDate.setImageResource(R.drawable.ic_checked);
        }

        if (mJob.getInvoiced() != null){
            txtInvoicedDate.setText(DateFormat.getDateInstance().format(mJob.getInvoiced()));
            imgInvoicedDate.setImageResource(R.drawable.ic_checked);
        }

        if (mJob.getScheduled() != null){
            txtScheduledDate.setText(DateFormat.getDateInstance().format(mJob.getScheduled()));
            imgScheduledDate.setImageResource(R.drawable.ic_checked);
        }

        if (mJob.getCompleted() != null){
            txtCompletedDate.setText(DateFormat.getDateInstance().format(mJob.getCompleted()));
            imgCompletedDate.setImageResource(R.drawable.ic_checked);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_JOB_PROGRESS_OPENED:
                    txtOpenedDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgOpenedDate.setImageResource(R.drawable.ic_checked);
                    break;

                case CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT:
                    txtSentDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgSentDate.setImageResource(R.drawable.ic_checked);
                    break;

                case CrewToolsConstants.REQUEST_JOB_PROGRESS_APPROVED:
                    txtApprovedDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgApprovedDate.setImageResource(R.drawable.ic_checked);
                    break;

                case CrewToolsConstants.REQUEST_JOB_PROGRESS_SCHEDULED:
                    txtScheduledDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgScheduledDate.setImageResource(R.drawable.ic_checked);
                    break;

                case CrewToolsConstants.REQUEST_JOB_PROGRESS_INVOICED:
                    txtInvoicedDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgInvoicedDate.setImageResource(R.drawable.ic_checked);
                    break;

                case CrewToolsConstants.REQUEST_JOB_PROGRESS_COMPLETED:
                    txtCompletedDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    imgCompletedDate.setImageResource(R.drawable.ic_checked);
                    break;

            }
        }
    }
}