package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.fragments.CreateViewJobFragment;
import com.mk27manoj.crewtools.fragments.InvoicesFragment;
import com.mk27manoj.crewtools.fragments.MessageFragment;
import com.mk27manoj.crewtools.fragments.PhotosFragment;
import com.mk27manoj.crewtools.utils.CommonUtilities;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class JobsActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private LinearLayout linearJob, linearEstimate, linearMessage, linearPhoto, linearInvoices;
    private TextView txtJob, txtEstimate, txtMessage, txtPhoto, txtInvoices;
    private ImageView imgJob, imgEstimate, imgMessage, imgPhoto, imgInvoices;
    private String mJobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecetd_job);
        if (getIntent() != null) {
            mJobId = getIntent().getStringExtra("Job");
        }
        initViews();
        setListeners();
    }

    private void initViews() {
        mContext = JobsActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_create_job_top);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_create_job_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle.setText("VIEW JOB");

        imgCancel = (ImageView) findViewById(R.id.imageview_create_job_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearJob = (LinearLayout) findViewById(R.id.linealayout_create_job_menu_job);
        linearEstimate = (LinearLayout) findViewById(R.id.linealayout_create_job_menu_estimate);
        linearMessage = (LinearLayout) findViewById(R.id.linealayout_create_job_menu_message);
        linearPhoto = (LinearLayout) findViewById(R.id.linealayout_create_job_menu_photos);
        linearInvoices = (LinearLayout) findViewById(R.id.linealayout_create_job_menu_invoices);

        txtJob = (TextView) findViewById(R.id.textview_create_job_menu_job);
        txtEstimate = (TextView) findViewById(R.id.textview_create_job_menu_estimate);
        txtMessage = (TextView) findViewById(R.id.textview_create_job_menu_message);
        txtPhoto = (TextView) findViewById(R.id.textview_create_job_menu_photos);
        txtInvoices = (TextView) findViewById(R.id.textview_create_job_menu_invoices);

        imgJob = (ImageView) findViewById(R.id.imageview_create_job_menu_job);
        imgEstimate = (ImageView) findViewById(R.id.imageview_create_job_menu_estimate);
        imgMessage = (ImageView) findViewById(R.id.imageview_create_job_menu_message);
        imgPhoto = (ImageView) findViewById(R.id.imageview_create_job_menu_photos);
        imgInvoices = (ImageView) findViewById(R.id.imageview_create_job_menu_invoices);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateViewJobFragment createViewJobFragment = CreateViewJobFragment.newInstanceOf(mJobId);
        fragmentTransaction.replace(R.id.framelayout_create_view_job_container, createViewJobFragment);
        fragmentTransaction.commit();
        txtJob.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
        imgJob.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
    }

    private void setListeners() {
        linearJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("VIEW JOB");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateViewJobFragment createViewJobFragment = CreateViewJobFragment.newInstanceOf(mJobId);
                fragmentTransaction.replace(R.id.framelayout_create_view_job_container, createViewJobFragment);
                fragmentTransaction.commit();

                txtJob.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgJob.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtMessage.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgMessage.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtEstimate.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgEstimate.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtPhoto.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgPhoto.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

        linearEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("VIEW ESTIMATE");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                JobEstimateViewFragment paymentTermsFragment = JobEstimateViewFragment.newInstanceOf(mJobId);
                fragmentTransaction.replace(R.id.framelayout_create_view_job_container, paymentTermsFragment);
                fragmentTransaction.commit();

                txtEstimate.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgEstimate.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtMessage.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgMessage.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtPhoto.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgPhoto.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtJob.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJob.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

        linearMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("MESSAGE");
                if (getIntent() != null) {
                    mJobId = getIntent().getStringExtra("Job");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    MessageFragment messageFragment =  MessageFragment.newInstanceOf(mJobId);
                    fragmentTransaction.replace(R.id.framelayout_create_view_job_container, messageFragment);
                    fragmentTransaction.commit();

                    txtMessage.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                    imgMessage.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                    txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                    imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    txtEstimate.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                    imgEstimate.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    txtPhoto.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                    imgPhoto.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    txtJob.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                    imgJob.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                }
            }
        });

        linearPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("PHOTOS");
                if (getIntent() != null) {
                    mJobId = getIntent().getStringExtra("Job");
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PhotosFragment photosFragment = PhotosFragment.newInstanceOf(mJobId);
                fragmentTransaction.replace(R.id.framelayout_create_view_job_container, photosFragment);
                fragmentTransaction.commit();


                txtPhoto.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgPhoto.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtMessage.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgMessage.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtEstimate.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgEstimate.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtJob.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJob.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

        linearInvoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("INVOICE");


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                InvoicesFragment designFragment = new InvoicesFragment();
                fragmentTransaction.replace(R.id.framelayout_create_view_job_container, designFragment);
                fragmentTransaction.commit();


                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgInvoices.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtMessage.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgMessage.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtEstimate.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgEstimate.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtPhoto.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgPhoto.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtJob.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJob.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });
    }
}