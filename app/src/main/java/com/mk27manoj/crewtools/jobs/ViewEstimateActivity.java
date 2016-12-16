package com.mk27manoj.crewtools.jobs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk27manoj.crewtools.CaptureSignActivity;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

/**
 * Renovated by The Chris Love on 2016-06-26.
 */
public class ViewEstimateActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private Button btnSignContract;
    private boolean hasContractorSigned = false, hasClientSigned = false;
    private boolean isApprovedRequest = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_estimate);
        initViews();
    }

    private void initViews() {
        /*
        mContext = ViewEstimateActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_view_estimate_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_view_estimate_title);

        txtTitle.setText("VIEW ESTIMATE");

        isApprovedRequest = getIntent().getExtras().getBoolean("Approved");

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_view_estimate_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignContract = (Button) findViewById(R.id.button_view_estimate_sign_contract);
        btnSignContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isApprovedRequest) {
                    Intent intent = new Intent();
                    intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, "7/2/2016 8:38 PM");
                    setResult(CrewToolsConstants.REQUEST_JOB_PROGRESS_APPROVED, intent);
                    finish();//finishing activity
                } else {
                    if (hasClientSigned && hasContractorSigned) {
                        Intent intent = new Intent();
                        intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, "7/2/2016 8:38 PM");
                        setResult(CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT, intent);
                        finish();//finishing activity
                    } else {
                        Dialog d = new AlertDialog.Builder(mContext)
                                .setTitle("Sign Contract")
                                .setNegativeButton("Cancel", null)
                                .setItems(new String[]{"Contractor Signature", "Client Signature"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlg, int position) {
                                        if (position == 0) {
                                            Intent clientIntent = new Intent(mContext, CaptureSignActivity.class);
                                            clientIntent.putExtra("IsClient", false);
                                            startActivityForResult(clientIntent, CrewToolsConstants.REQUEST_CAPTURE_SIGN_CONTRACTOR);
                                        } else if (position == 1) {
                                            Intent clientIntent = new Intent(mContext, CaptureSignActivity.class);
                                            clientIntent.putExtra("IsClient", true);
                                            startActivityForResult(clientIntent, CrewToolsConstants.REQUEST_CAPTURE_SIGN_CLIENT);
                                        }
                                    }
                                })
                                .create();
                        d.show();
                    }
                }
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_CAPTURE_SIGN_CONTRACTOR:
                    hasContractorSigned = true;
                    break;
                case CrewToolsConstants.REQUEST_CAPTURE_SIGN_CLIENT:
                    hasClientSigned = true;
                    break;
            }
        }
    }
}
