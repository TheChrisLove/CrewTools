package com.mk27manoj.crewtools.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class CommonUtilities {
    public static void hideKeyBoard(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) ((Activity) context).getSystemService(((Activity) context).INPUT_METHOD_SERVICE);
            if(((Activity) context).getCurrentFocus() != null){
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareApp(Context parent) {
        try {

            Intent sharingIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            // String shareBody = Data.machinesbean.get(position)
            // .getMachine_desc().toString();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Crew Tools");
            String message = "Crew Tools share message";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            parent.startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } catch (Exception e) {
            Toast.makeText(parent, "Application not Installed",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public static void setDateOnView(Context parent, final View view) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(parent,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        ((TextView) view).setText(dayOfMonth + "-"
                                + String.format("%02d", (monthOfYear + 1))
                                + "-" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public static void setTimeOnView(Context mContext, final View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                int strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? 12 : datetime.get(Calendar.HOUR);
                ((TextView) view).setText(String.format("%02d:%02d %s", strHrsToShow, datetime.get(Calendar.MINUTE), am_pm));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public static String getDDMMYYYYFromDate(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }
}
