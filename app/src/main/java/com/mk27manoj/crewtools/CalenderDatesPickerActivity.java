package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.CalendarCellDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CalenderDatesPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtDone;
    private ImageView imgCancel;
    private CalendarPickerView calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view_multiple_date_picker);
        initViews();
    }

    private void initViews() {
        mContext = CalenderDatesPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_calender_view_multiple_date_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_calender_view_multiple_date_picker_title);

        txtTitle.setText("CHOOSE DATE TO VIEW");

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_calender_view_multiple_date_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtDone = (TextView) findViewById(R.id.textview_toolbar_calender_view_multiple_date_picker_done);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Date> selectedDates = (ArrayList<Date>) calendar.getSelectedDates();
                String dates = "";
                for (int i = 0; i < selectedDates.size(); i++) {
                    if (i != selectedDates.size() - 1) {
                        dates += CommonUtilities.getDDMMYYYYFromDate(selectedDates.get(i)) + "@@";
                    } else {
                        dates += CommonUtilities.getDDMMYYYYFromDate(selectedDates.get(i));
                    }
                }
                
                Log.e("TAG", 'W' + dates);
                
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, dates);
                setResult(CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT, intent);
                finish();//finishing activity
                
//                intent.putExtra(DATE_EXTRA, t.getDate());
    //                this.date = new Date(intent.getExtras().getString(DATE_EXTRA));
//                this.date = (Date)intent.getSerializableExtra(DATE_EXTRA);

            }
        });

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        
//        List<CalendarCellDecorator> decorators = new ArrayList<>();
//                
//        decorators.add(new PickupCellDecorator(dates));
//        calendar.setDecorators(decorators);
        
//        calendar.init(lastYear.getTime(), nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(new Date());
        calendar.init(lastYear.getTime(), nextYear.getTime()).withSelectedDate(new Date());
    }
}