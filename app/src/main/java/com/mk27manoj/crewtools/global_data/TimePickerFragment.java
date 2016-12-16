package com.mk27manoj.crewtools.global_data;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by gyasistory on 7/4/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    public interface TimePickerListener {
        void timePicked(int hour, int Minute);
    }

    private TimePickerListener listener;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (activity instanceof TimePickerListener) {
            listener = (TimePickerListener) activity;
        } else {
            throw new IllegalArgumentException("Please Attach timePicker Interface");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(calendar.HOUR);
        int minute = calendar.get(calendar.MINUTE);


        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        listener.timePicked(hourOfDay, minute);

    }
}
