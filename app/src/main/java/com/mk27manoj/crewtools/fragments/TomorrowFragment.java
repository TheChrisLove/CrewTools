package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVTask;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.CalenderEventsAdapter;
import com.mk27manoj.crewtools.calendar.ViewTaskActivity;
import com.mk27manoj.crewtools.global_data.StaticData;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class TomorrowFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private ArrayList<String> tmrwEventList;
    private static final String TAG = "TomorrowFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listview_tmrw_list);
        
        ParseQuery<CVTask> query = ParseQuery.getQuery(CVTask.class);
//        query.whereEqualTo("user", ParseUser.getCurrentUser());
            
        query.findInBackground(new FindCallback<CVTask>() {
            @Override
            public void done(List<CVTask> objects, ParseException e) {
                Log.d(TAG, "Tomorrow's tasks: done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                if (e == null) {
                    mListView.setAdapter(new CalenderEventsAdapter(getContext(), objects));
                }
            }
        });
    }

    private void setListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CVTask task = (CVTask) parent.getAdapter().getItem(position);

                if (task != null) {
                    Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                    intent.putExtra("task_object_id", task.getObjectId());
                    startActivity(intent);
                }
            }
        });
    }
}