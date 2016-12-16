package com.mk27manoj.crewtools.calendar.add_task;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Renovated by The Chris Love on 10-26-2016.
 */
public class LocationDialogPicker extends DialogFragment {

    private static final String TAG = "LocationDialogPicker";
    private LocationDialogListener listener;
    View mView;

    public interface LocationDialogListener{
        void locationSelected(CVAddress address);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof LocationDialogListener){
            listener = (LocationDialogListener) activity;
        } else {
            throw  new IllegalArgumentException("Please attach Location interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mView = inflater.inflate(R.layout.fragment_location_dialog, container, false);
        
        String selectLoc = getString(R.string.select_location);
        getDialog().setTitle(selectLoc);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", CVUser.getCurrentUser())
                .findInBackground(new FindCallback<CVEmployee>() {
                    @Override
                    public void done(List<CVEmployee> objects, ParseException e) {
                        Log.d(TAG, "1: done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                        if (e == null){
                            for (CVEmployee object : objects) {
                                ParseQuery.getQuery(CVAddress.class)
                                        .whereEqualTo("company",object.getCompany())
                                        .findInBackground(new FindCallback<CVAddress>() {
                                            @Override
                                            public void done(List<CVAddress> objects, ParseException e) {
                                                if (e == null){
                                                    Log.d(TAG, "2: done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                                                    ArrayAdapter<CVAddress> adapter = new
                                                            ArrayAdapter<>(getDialog().getContext(),
                                                            android.R.layout.simple_list_item_1, objects);
                                                    ListView listView = (ListView) mView.findViewById(R.id.listView_add_address_dialog);
                                                    if(listView != null) {
                                                        listView.setAdapter(adapter);
                                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                listener.locationSelected((CVAddress) parent.getAdapter().getItem(position));
                                                                getDialog().cancel();
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });

    }
}
