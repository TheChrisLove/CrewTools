package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVService;
import com.mk27manoj.crewtools.R;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class JobTypeEditorActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone, txtCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_type_editor);
        initViews();
    }

    private void initViews() {
        mContext = JobTypeEditorActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_job_type_editor_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_job_type_editor_title);

        txtTitle.setText("EDIT JOB TYPE");

        txtDone = (TextView) findViewById(R.id.textview_toolbar_job_type_editor_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_job_type_editor_menu_close);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextServiceName = (EditText) findViewById(R.id.editText_service_editor_name);
                EditText editTextServiceAmount = (EditText) findViewById(R.id.editText_service_amount);
                Switch switchEditable = (Switch) findViewById(R.id.switch_service_editor_is_amount_editable);
                Switch switchTaxable = (Switch) findViewById(R.id.switch_service_editor_istaxable);

                finish();
            }
        });
    }
}
