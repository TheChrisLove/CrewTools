package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mk27manoj.crewtools.adapters.CrewAdapter;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

public class CrewPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtCancel, txtDone;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_picker);
        initViews();
    }

    private void initViews() {
        mContext = CrewPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_crew_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtCancel = (TextView) findViewById(R.id.textview_toolbar_crew_picker_menu_close);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDone = (TextView) findViewById(R.id.textview_toolbar_crew_picker_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListView = (ListView) findViewById(R.id.listview_crew_picker_list);
        final ArrayList<String> crewList = new ArrayList<>();
        crewList.add("Chris Cobby@@Manager@@M@@N");
        crewList.add("Jason Kichline@@Vice President@@A@@Y");
        crewList.add("Jaime Kichline@@Vice President@@N@@N");
        crewList.add("Jason Kichline@@Manager@@M@@N");
        crewList.add("Pete Logan@@Manager@@M@@N");
        crewList.add("The Boss@@Da Boss@@N@@N");

//        mListView.setAdapter(new CrewAdapter(mContext, crewList));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, crewList.get(position).split("@@")[0]);
                setResult(CrewToolsConstants.REQUEST_TIME_CREW_PICKER_CREW, intent);
                finish();//finishing activity
            }
        });
    }
}
