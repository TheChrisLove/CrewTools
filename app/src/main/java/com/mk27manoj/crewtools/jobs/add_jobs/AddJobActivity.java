package com.mk27manoj.crewtools.jobs.add_jobs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.clients.ClientContactFragment;
import com.mk27manoj.crewtools.global_data.DatePickerFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddJobActivity extends AppCompatActivity
implements DatePickerFragment.DatePickerListener, ClientDialogFragment.ClientDialogListener{
    private static final String TAG = "AddJobActivity";

    private Boolean isStartDate = false;
    private Boolean isCompleteDate = false;
    private Date mStartDate = new Date();
    private Date mCompleteDate = new Date();
    private CVClient cvClient;
    private CVJob cvJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
    }

    public void addJobClientClick(View view) {
        ClientDialogFragment clientDialogFragment = new ClientDialogFragment();
        clientDialogFragment.show(getSupportFragmentManager(), "Add Fragment");
    }

    public void addJobDateStartClick(View view) {
        isStartDate = true;
        isCompleteDate = false;

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void addJobDateCompleteClick(View view) {
        isStartDate = false;
        isCompleteDate = true;

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void datePicked(int Month, int Day, int Year) {
        TextView startTextview = (TextView) findViewById(R.id.textView_add_job_start_date);
        TextView completeTextview = (TextView) findViewById(R.id.textView_add_job_completion_date);
        if (isStartDate) {
            mStartDate.setMonth(Month);
            mStartDate.setDate(Day);
            mStartDate.setYear(Year);

            if (startTextview != null) {
                startTextview.setText(mStartDate.toString());
            }

        } else if (isCompleteDate){
            mCompleteDate.setMonth(Month);
            mCompleteDate.setDate(Day);
            mCompleteDate.setYear(Year);
            if (completeTextview != null) {
                completeTextview.setText(mCompleteDate.toString());
            }
        }
    }

    public void saveChangesClick(View view) {
        ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", ParseUser.getCurrentUser())
                .findInBackground(new FindCallback<CVEmployee>() {
                    @Override
                    public void done(List<CVEmployee> objects, ParseException e) {
                        for (final CVEmployee object : objects) {
                            if (cvClient != null) {
                                CVCompany cvCompany = object.getCompany();
                                EditText jobName = (EditText) findViewById(R.id.editText_add_job_name);
                                EditText jobType = (EditText) findViewById(R.id.editText_add_job_type);
                                // Client should be saved
                                EditText jobPlace = (EditText) findViewById(R.id.editText_add_job_place);
                                Spinner jobPerformed = (Spinner) findViewById(R.id.spinner_add_perform);
                                //  Start date is saved
                                // complete Date is saved
                                Spinner jobPayment = (Spinner) findViewById(R.id.spinner_add_job_payment);
                                Spinner jobAcceptWithin = (Spinner) findViewById(R.id.spinner_add_job_accept);
                                EditText jobNotes = (EditText) findViewById(R.id.editText_add_job_description);

                                cvJob = new CVJob();
                                cvJob.setCompany(cvCompany);
                                if (jobName != null) {
                                    cvJob.setName(jobName.getText().toString());
                                }
                                if (jobType != null) {
                                    cvJob.setType(jobType.getText().toString());
                                }
                                cvJob.setClient(cvClient);
                                if (jobPlace != null) {
                                    cvJob.setLocation(jobPlace.getText().toString());
                                }
                                if (jobPerformed != null) {
                                    cvJob.setOccurs(jobPerformed.getSelectedItemPosition() + 1);
                                }
                                if (mStartDate != null) cvJob.setStart(mStartDate);
                                if (mCompleteDate != null) cvJob.setCompleted(mCompleteDate);
                                if (jobPayment != null) {
                                    cvJob.setPayment(getResources().getStringArray(R.array.payment_entries)[jobPayment.getSelectedItemPosition()]);
                                }
                                if (jobAcceptWithin != null) {
                                    cvJob.setAcceptWithin(getResources().getIntArray(R.array.accept_with_entries)[jobAcceptWithin.getSelectedItemPosition()]);
                                }
                                if (jobNotes != null) {
                                    cvJob.setNotes(jobNotes.getText().toString());
                                }

                                ParseUser.getCurrentUser().getACL().setPublicReadAccess(true);
                                ParseUser.getCurrentUser().getACL().setPublicWriteAccess(true);
                                cvJob.setACL(ParseUser.getCurrentUser().getACL());

                                ParseQuery.getQuery(ParseRole.class)
                                        .whereContains("name", cvCompany.getObjectId())
                                        .findInBackground(new FindCallback<ParseRole>() {
                                            @Override
                                            public void done(List<ParseRole> objects, ParseException e) {
                                                if (e == null){
                                                    for (ParseRole parseRole : objects) {
                                                        parseRole.getACL().setPublicWriteAccess(true);
                                                        parseRole.getACL().setPublicReadAccess(true);
                                                        cvJob.setACL(parseRole.getACL());
                                                        ParseUser.getCurrentUser().setACL(parseRole.getACL());
                                                    }

                                                    cvJob.saveEventually(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {
                                                            if (e == null){
                                                                finish();
                                                            } else {
                                                                AlertDialog.Builder dialog = new AlertDialog.Builder(AddJobActivity.this);
                                                                dialog.setTitle(R.string.job_not_saved)
                                                                        .setIcon(R.drawable.ic_jobs)
                                                                        .setMessage(R.string.message_job_not_saved)
                                                                        .setPositiveButton(R.string.ok, null)
                                                                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                finish();
                                                                            }
                                                                        })
                                                                        .show();


                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        });
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddJobActivity.this);
                                builder.setTitle(R.string.job_no_client)
                                        .setMessage(R.string.job_no_client_message)
                                        .setIcon(R.drawable.ic_clients)
                                        .setPositiveButton(R.string.ok, null)
                                        .show();
                            }
                        }
                    }
                });


    }

    @Override
    public void ClientSelectedListener(CVClient client) {
        Toast.makeText(AddJobActivity.this, "Working " + client, Toast.LENGTH_SHORT).show();

        Log.d(TAG, "ClientSelectedListener() called with: " + "client = [" + client + "]");

        cvClient = client;

        TextView clientTextView = (TextView) findViewById(R.id.Textview_add_job_client);
        EditText clientAddressEditText = (EditText) findViewById(R.id.editText_add_job_place);

        if (clientTextView != null) {
            clientTextView.setText(client.getName());
        }


        if (clientAddressEditText != null && client.has("billingAddress")) {
            clientAddressEditText.setText(client.getBillingAddress().getAddress1());
        }
    }

    public void addJobServiceClick(View view) {
    }
}
