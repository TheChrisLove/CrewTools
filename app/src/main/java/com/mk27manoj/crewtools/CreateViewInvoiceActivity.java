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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.JobPickerActivity;
import com.mk27manoj.crewtools.NewLineItemActivity;
import com.mk27manoj.crewtools.TermsPickerActivity;
import com.mk27manoj.crewtools.ViewInvoiceActivity;
import com.mk27manoj.crewtools.jobs.ClientPickerActivity;
import com.mk27manoj.crewtools.jobs.PlacePickerActivity;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

public class CreateViewInvoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone;
    private ImageView imgCancel;
    private TextView txtClient, txtAddress, txtJob, txtDate, txtTerms, txtDue;
    private RelativeLayout relativeAddLineItem;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view_invoice);
        initViews();
    }

    private void initViews() {
        mContext = CreateViewInvoiceActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_create_view_invoice_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_create_view_invoice_title);
        txtTitle.setText("NEW INVOICE");

        txtDone = (TextView) findViewById(R.id.textview_toolbar_create_view_invoice_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, "7/2/2016 8:38 PM");
//                setResult(CrewToolsConstants.REQUEST_JOB_PROGRESS_SCHEDULED, intent);
//                finish();//finishing activity
            }
        });
        imgCancel = (ImageView) findViewById(R.id.imageview_create_view_invoice_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave = (Button) findViewById(R.id.button_create_view_invoice_save);

        relativeAddLineItem = (RelativeLayout) findViewById(R.id.relativelayout_create_view_invoice_add_new_line_item);

        txtClient = (TextView) findViewById(R.id.textview_create_view_invoice_client);
        txtAddress = (TextView) findViewById(R.id.textview_create_view_invoice_address);
        txtJob = (TextView) findViewById(R.id.textview_create_view_invoice_job);
        txtDate = (TextView) findViewById(R.id.textview_create_view_invoice_date);
        txtTerms = (TextView) findViewById(R.id.textview_create_view_invoice_terms);
        txtDue = (TextView) findViewById(R.id.textview_create_view_invoice_balance_due);

        btnSave.setOnClickListener(this);
        relativeAddLineItem.setOnClickListener(this);
        txtClient.setOnClickListener(this);
        txtAddress.setOnClickListener(this);
        txtJob.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtTerms.setOnClickListener(this);
        txtDue.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_ADDRESS:
                    txtAddress.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    break;
                case CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_CLIENT:
                    txtClient.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    break;
                case CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_JOB:
                    txtJob.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    break;
                case CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_TERMS:
                    txtTerms.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_create_view_invoice_client:
                startActivityForResult(new Intent(mContext, ClientPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_CLIENT);
                break;
            case R.id.textview_create_view_invoice_address:
                startActivityForResult(new Intent(mContext, PlacePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_ADDRESS);
                break;
            case R.id.textview_create_view_invoice_job:
                startActivityForResult(new Intent(mContext, JobPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_JOB);
                break;
            case R.id.textview_create_view_invoice_date:
                CommonUtilities.setDateOnView(mContext, txtDate);
//                startActivityForResult(new Intent(mContext, DaPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_DATE);
                break;
            case R.id.textview_create_view_invoice_balance_due:
//                startActivityForResult(new Intent(mContext, PlacePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_DUE);
                break;
            case R.id.textview_create_view_invoice_terms:
                startActivityForResult(new Intent(mContext, TermsPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_TERMS);
                break;
            case R.id.relativelayout_create_view_invoice_add_new_line_item:
                startActivityForResult(new Intent(mContext, NewLineItemActivity.class), CrewToolsConstants.REQUEST_CREATE_VIEW_JOB_TERMS);
                break;
            case R.id.button_create_view_invoice_save:
                startActivity(new Intent(mContext, ViewInvoiceActivity.class));
                break;
        }
    }
}

