package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class TodayFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private ArrayList<String> todayEventList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_today, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listview_today_list);


    }

    private void setListeners() {
    }


    @Override
    public void onResume() {
        super.onResume();

        ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", ParseUser.getCurrentUser())
                .findInBackground(new FindCallback<CVEmployee>() {
                    @Override
                    public void done(List<CVEmployee> objects, ParseException e) {
                        if (e == null && objects.size() > 0){
                            CVCompany company = objects.get(0).getCompany();
                            ParseQuery.getQuery(CVTask.class)
                                    .whereEqualTo("company", company)
                                    .findInBackground(new FindCallback<CVTask>() {
                                        @Override
                                        public void done(List<CVTask> objects, ParseException e) {
                                            mListView.setAdapter(new CalenderEventsAdapter(getContext(), objects));
                                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
                                                    intent.putExtra("task_object_id", ((CVTask)parent.getAdapter().getItem(position)).getObjectId());
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
                        }
                    }
                });
    }
}

