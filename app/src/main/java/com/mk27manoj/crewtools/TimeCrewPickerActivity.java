package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.TimePickerActivity;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

public class TimeCrewPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone;
    private ImageView imgCancel;
    private LinearLayout jobsDatesContainer;
    private String[] datesList;
    private String dates = "";
    private TextView txtCrew, txtTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_crew_picker);
        initViews();
    }

    private void initViews() {
        mContext = TimeCrewPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_time_crew_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_time_crew_picker_title);

        txtTitle.setText(getIntent().getExtras().getString("TITLE"));

        imgCancel = (ImageView) findViewById(R.id.imageview_time_crew_picker_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDone = (TextView) findViewById(R.id.textview_toolbar_time_crew_picker_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, txtTime.getText().toString() + "@@" + txtCrew.getText().toString());
                setResult(CrewToolsConstants.REQUEST_TIME_CREW_PICKER, intent);
                finish();//finishing activity
            }
        });

        txtCrew = (TextView) findViewById(R.id.textview_time_crew_picker_crew);
        txtCrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, CrewPickerActivity.class), CrewToolsConstants.REQUEST_TIME_CREW_PICKER_CREW);
            }
        });
        txtTime = (TextView) findViewById(R.id.textview_time_crew_picker_time);
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, TimePickerActivity.class), CrewToolsConstants.REQUEST_TIME_CREW_PICKER_TIME);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_TIME_CREW_PICKER_CREW:
                    txtCrew.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    break;
                case CrewToolsConstants.REQUEST_TIME_CREW_PICKER_TIME:
                    txtTime.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                    break;
            }
        }
    }
}
