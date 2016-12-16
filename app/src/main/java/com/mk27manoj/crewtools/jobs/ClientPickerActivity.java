package com.mk27manoj.crewtools.jobs;

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

import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.clients.AddClientActivity;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-25.
 */
public class ClientPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgAdd, imgCancel;
    private LinearLayout clientContainer;
    private List<CVClient> clients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_picker);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        mContext = ClientPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_client_picker_top);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_client_picker_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle.setText("CHOOSE CLIENT");

        imgAdd = (ImageView) findViewById(R.id.imageview_toolbar_client_picker_add);
        imgCancel = (ImageView) findViewById(R.id.imageview_client_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AddClientActivity.class));
            }
        });
        clientContainer = (LinearLayout) findViewById(R.id.linearlayout_client_picker_clients_container);

        try {
            for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                clients = ParseQuery.getQuery(CVClient.class).whereEqualTo("company", cvEmployee.getCompany()).find();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < clients.size(); i++) {
            addView();
        }

//        clientContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = "Norman Kichline";
//                Intent intent = new Intent();
//                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, message);
//                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_CLIENT, intent);
//                finish();//finishing activity
//            }
//        });
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_client_picker, null);
        TextView txtName = (TextView) baseView.findViewById(R.id.textview_row_client_picker_name);
        TextView txtOrganizationName = (TextView) baseView.findViewById(R.id.textview_row_client_picker_organization_name);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_client_picker_selected_client);
        if (clientContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        baseView.setTag(clientContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                for (int j = 0; j < clientContainer.getChildCount(); j++) {
                    if (j == count) {
                        clientContainer.getChildAt(j).findViewById(R.id.imageview_row_client_picker_selected_client).setVisibility(View.VISIBLE);
                    } else {
                        clientContainer.getChildAt(j).findViewById(R.id.imageview_row_client_picker_selected_client).setVisibility(View.INVISIBLE);
                    }
                }
                CVClient data = clients.get(count);

                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_CLIENT_ID, data.getObjectId());
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, data.getName());
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_CLIENT, intent);
                finish();//finishing activity
            }
        });

            CVClient data = clients.get(clientContainer.getChildCount());
            txtName.setText(data.getName());
            txtOrganizationName.setText(data.getBusiness());
            clientContainer.addView(baseView);

    }
}