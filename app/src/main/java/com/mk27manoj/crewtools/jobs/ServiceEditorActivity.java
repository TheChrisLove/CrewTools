package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.content.Intent;
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
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

/**
 * Renovated by The Chris Love on 2016-06-26.
 */
public class ServiceEditorActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone, txtCancel;
    private TextView txtUnit;
    private CVService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    private void initViews() {
        mContext = ServiceEditorActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_service_editor_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_service_editor_title);

        txtTitle.setText("ADD SERVICE");

        txtUnit = (TextView) findViewById(R.id.textview_service_editor_unit);
        txtUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, UnitPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_UNIT);
            }
        });
        txtDone = (TextView) findViewById(R.id.textview_toolbar_service_editor_done);
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_service_editor_menu_close);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    public void saveChangesClick(View view) {
        try {
            EditText editTextServiceName = (EditText) findViewById(R.id.editText_service_editor_name);
            EditText editTextServiceAmount = (EditText) findViewById(R.id.editText_service_amount);
            Switch switchEditable = (Switch) findViewById(R.id.switch_service_editor_is_amount_editable);
            Switch switchTaxable = (Switch) findViewById(R.id.switch_service_editor_istaxable);

            if (mService == null) mService = new CVService();

            mService.setName(editTextServiceName.getText().toString());
            String serviceAmt = editTextServiceAmount.getText().toString();
            serviceAmt = serviceAmt.startsWith("$") ? serviceAmt.substring(1) : serviceAmt;
            mService.setAmount(Double.parseDouble(serviceAmt));
            mService.setEditable(switchEditable.isChecked());
            mService.setTaxable(switchTaxable.isChecked());
            mService.setUnit(txtUnit.getText().toString());
            mService.setACL(new ParseACL());
            mService.getACL().setReadAccess(ParseUser.getCurrentUser(), true);
            mService.getACL().setWriteAccess(ParseUser.getCurrentUser(), true);
            CVCompany company = new CVCompany();
            for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                cvEmployee.getCompany().fetch();
                company = cvEmployee.getCompany();
                mService.setCompany(cvEmployee.getCompany());
            }

            if(company.getObjectId() != null) {
                for (ParseRole role : ParseQuery.getQuery(ParseRole.class).whereContains("name", company.getObjectId()).find()) {
                    mService.getACL().setRoleWriteAccess(role, true);
                    mService.getACL().setRoleReadAccess(role, true);
                }
            }

            mService.save();
            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == CrewToolsConstants.REQUEST_CREATE_JOB_PICK_UNIT) {
                txtUnit.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
            }
        }
    }
}