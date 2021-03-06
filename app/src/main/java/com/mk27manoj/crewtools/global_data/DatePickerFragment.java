package com.mk27manoj.crewtools.global_data;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatException;

/**
 * Created by gyasistory on 7/1/2016.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DatePickerFragment";

    DatePickerListener listener;

    public interface DatePickerListener{
        void datePicked(int Month, int Day, int Year);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof DatePickerListener){
            listener = (DatePickerListener) activity;
        } else {
            throw new IllegalArgumentException("Please Attach DatePicker Interface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Log.d(TAG, "onDateSet() called with: " + "view = [" + view + "], year = [" + year +
                "], month = [" + month + "], day = [" + day + "]");




        listener.datePicked(month, day, year);

    }
}