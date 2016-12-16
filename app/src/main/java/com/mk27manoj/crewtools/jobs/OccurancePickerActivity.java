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

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */

public class OccurancePickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private LinearLayout occuranceContainer;
    private ArrayList<String> occurences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurance_picker);
        initViews();
    }

    private void initViews() {
        mContext = OccurancePickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_occurance_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_occurance_picker_title);

        txtTitle.setText("PICK OCCURANCE");
        occuranceContainer = (LinearLayout) findViewById(R.id.linearlayout_occurance_picker_type_container);

        occurences= new ArrayList<>();
        occurences.add("Once");
        occurences.add("Twice");
        occurences.add("Thrice");
        occurences.add("4 Times");
        occurences.add("5 Times");
        occurences.add("6 Times");
        occurences.add("7 Times");
        occurences.add("8 Times");
        occurences.add("9 Times");
        occurences.add("10 Times");
        occurences.add("11 Times");
        occurences.add("12 Times");

        for (int i=0;i<occurences.size();i++){
            addView();
        }
        
        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_occurance_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_occurance_picker, null);
        TextView txtName = (TextView) baseView.findViewById(R.id.textview_row_occurance_picker_name);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_occurance_picker_selected);
        if (occuranceContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        baseView.setTag(occuranceContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                for (int j = 0; j < occuranceContainer.getChildCount(); j++) {
                    if (j == count) {
                    occuranceContainer.getChildAt(j).findViewById(R.id.imageview_row_occurance_picker_selected).setVisibility(View.VISIBLE);
                    } else {
                    occuranceContainer.getChildAt(j).findViewById(R.id.imageview_row_occurance_picker_selected).setVisibility(View.INVISIBLE);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONSE_OCCURANCE_NUMBER, count +1);
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, occurences.get(count));
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_OCCURANCE, intent);
                finish();//finishing activity
            }
        });
        txtName.setText(occurences.get(occuranceContainer.getChildCount()));
        occuranceContainer.addView(baseView);
    }
}