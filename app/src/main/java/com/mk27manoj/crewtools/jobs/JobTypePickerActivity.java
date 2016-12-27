package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Renovated by The Chris Love on 2016-06-25.
 */
public class JobTypePickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private TextView txtEdit, txtCancel;
    private LinearLayout jobTypeContainer;
    private RelativeLayout relativeAddJobType;
    private boolean isEditable = false;
    private CVCompany cvCompany;
    JSONArray jsonArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_type_picker);
        initViews();
    }

    private void initViews() {
        mContext = JobTypePickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_job_type_picker_top);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_job_type_picker_title);

        txtTitle.setText("CHOOSE JOB TYPE");

        try {
//            for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
            for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class).find()) {
                cvEmployee.getCompany().fetch();
                cvCompany = cvEmployee.getCompany();
                jsonArray = cvEmployee.getCompany().getServiceTypes();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        jobTypeContainer = (LinearLayout) findViewById(R.id.linearlayout_job_type_picker_type_container);
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.length(); i++) {
                addView(false);
            }
        }

        txtEdit = (TextView) findViewById(R.id.textview_toolbar_job_type_picker_edit);
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_job_type_picker_menu_close);

        relativeAddJobType = (RelativeLayout) findViewById(R.id.relativelayout_job_type_picker_add_new_job_type);
        relativeAddJobType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.custom_accept_input_dialog, null);

                final AlertDialog alertD = new AlertDialog.Builder(mContext).create();
                TextView txtTitle = (TextView) promptView.findViewById(R.id.textview_custom_dialog_heading);
                txtTitle.setText("Add New Item");
                TextView txtMessage = (TextView) promptView.findViewById(R.id.textview_custom_dialog_message);
                txtMessage.setText("Enter the name of the new item");
                final EditText userInput = (EditText) promptView.findViewById(R.id.edittext_custom_dialog_input);
                Button btnAdd = (Button) promptView.findViewById(R.id.button_custom_dialog_add);
                Button btnCancel = (Button) promptView.findViewById(R.id.button_custom_dialog_cancel);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!userInput.getText().toString().trim().equals("")) {
                            jsonArray.put(userInput.getText().toString());
                            cvCompany.setServiceTypes(jsonArray);
                            cvCompany.saveEventually();
                            addView(true);
                        }
                        alertD.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertD.dismiss();
                    }
                });
                alertD.setView(promptView);
                alertD.show();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEdit.getText().toString().equalsIgnoreCase("Edit")) {
                    isEditable = true;
                    txtEdit.setText("Done");
                    for (int i = 0; i < jobTypeContainer.getChildCount(); i++) {
                        ImageView img = (ImageView) jobTypeContainer.getChildAt(i).findViewById(R.id.imageview_job_type_picker_add_remove);
                        img.setVisibility(View.VISIBLE);
                        ImageView imgEdit = (ImageView) jobTypeContainer.getChildAt(i).findViewById(R.id.imageview_row_job_type_picker_edit);
                        imgEdit.setVisibility(View.VISIBLE);
                        ImageView imgSelected = (ImageView) jobTypeContainer.getChildAt(i).findViewById(R.id.imageview_row_job_type_picker_selected);
                        imgSelected.setVisibility(View.INVISIBLE);
                    }
                    relativeAddJobType.setVisibility(View.VISIBLE);
                } else {
                    txtEdit.setText("Edit");
                    isEditable = false;
                    for (int i = 0; i < jobTypeContainer.getChildCount(); i++) {
                        ImageView img = (ImageView) jobTypeContainer.getChildAt(i).findViewById(R.id.imageview_job_type_picker_add_remove);
                        img.setVisibility(View.GONE);
                        ImageView imgEdit = (ImageView) jobTypeContainer.getChildAt(i).findViewById(R.id.imageview_row_job_type_picker_edit);
                        imgEdit.setVisibility(View.INVISIBLE);
                    }
                    relativeAddJobType.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addView(boolean edit) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_job_type_picker, null);
        TextView txtType = (TextView) baseView.findViewById(R.id.textview_job_type_picker_name);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_job_type_picker_selected);
        if (jobTypeContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        ImageView imgEdit = (ImageView) baseView.findViewById(R.id.imageview_row_job_type_picker_edit);
        ImageView imgRemove = (ImageView) baseView.findViewById(R.id.imageview_job_type_picker_add_remove);
        baseView.setTag(jobTypeContainer.getChildCount());
        imgRemove.setTag(jobTypeContainer.getChildCount());

        if (edit) {
            imgRemove.setVisibility(View.VISIBLE);
            imgEdit.setVisibility(View.VISIBLE);
        }
        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditable) {
                    int count = Integer.parseInt(v.getTag().toString().trim());
                    for (int j = 0; j < jobTypeContainer.getChildCount(); j++) {
                        if (j == count) {
                            jobTypeContainer.getChildAt(j).findViewById(R.id.imageview_row_job_type_picker_selected).setVisibility(View.VISIBLE);
                        } else {
                            jobTypeContainer.getChildAt(j).findViewById(R.id.imageview_row_job_type_picker_selected).setVisibility(View.INVISIBLE);
                        }
                    }
                    Intent intent = new Intent();
                    try {
                        intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, jsonArray.getString(count));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_TYPE, intent);
                    finish();//finishing activity
                }
            }
        });
        try {
            txtType.setText(jsonArray.getString(jobTypeContainer.getChildCount()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(v.getTag().toString().trim());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonArray.remove(index);
                    cvCompany.setServiceTypes(jsonArray);
                    cvCompany.saveEventually();
                }
                ((LinearLayout) baseView.getParent()).removeView(baseView);
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, JobTypeEditorActivity.class));
            }
        });
        jobTypeContainer.addView(baseView);
    }
}