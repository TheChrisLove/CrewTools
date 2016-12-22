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
import android.widget.TextView;


import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

public class AccountInfoActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private LinearLayout infoContainer;
    private ArrayList<String> infoList;
    private String role;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        initViews();
    }

    private void initViews() {
        mContext = AccountInfoActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_account_info_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_account_info_title);

        infoContainer = (LinearLayout) findViewById(R.id.linearlayout_account_info_container);

        role = getIntent().getExtras().getString("ROLE");
        if (role.equals("ADMIN")) {
            txtTitle.setText("ADMINISTRATOR INFO");
            infoList = new ArrayList<>();
            infoList.add("View Assigned Jobs");
            infoList.add("Approve Assigned Jobs");
            infoList.add("Complete Assigned Jobs");
            infoList.add("View All Jobs");
            infoList.add("Create New Jobs");
            infoList.add("Edit Jobs");
            infoList.add("Assign Jobs");
            infoList.add("Approve Jobs");
            infoList.add("Complete Jobs");
            infoList.add("Invoice Jobs");
            infoList.add("Delete Jobs");
            infoList.add("View Clients");
            infoList.add("Create Clients");
            infoList.add("Edit Clients");
            infoList.add("Delete Clients");
            infoList.add("View Employees");
            infoList.add("Invite New Employees");
            infoList.add("Edit Employees");
            infoList.add("Change Employee Roles");
            infoList.add("Delete Employees");
            infoList.add("Edit Company Details");
            infoList.add("Delete Company");

        } else if (role.equals("MANAGER")) {
            txtTitle.setText("MANAGER INFO");
            infoList = new ArrayList<>();
            infoList.add("View All Jobs");
            infoList.add("Create New Jobs");
            infoList.add("Assign Jobs");
            infoList.add("Approve Jobs");
            infoList.add("Complete Jobs");
            infoList.add("View Clients");
            infoList.add("View Employees");
        } else {
            txtTitle.setText("CREWMATE INFO");
            infoList = new ArrayList<>();
            infoList.add("View Assigned Jobs");
            infoList.add("Approve Assigned Jobs");
            infoList.add("Complete Assigned Jobs");
        }

        for (int i = 0; i < infoList.size(); i++) {
            addView();
        }

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_account_info_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_account_info_item, null);
        TextView txtName = (TextView) baseView.findViewById(R.id.textview_account_info_name);
        baseView.setTag(infoContainer.getChildCount());
        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, infoList.get(count));
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_OCCURANCE, intent);
                finish();//finishing activity
            }
        });
        txtName.setText(infoList.get(infoContainer.getChildCount()));
        infoContainer.addView(baseView);
    }
}