package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mk27manoj.crewtools.jobs.JobsActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.JobsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class CompletedJobsFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private static final String TAG = "CompletedJobsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_completed_jobs, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
//        textview_my_account_menu_jobs

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
                        if (e== null){
                            for (CVEmployee object : objects) {
                                ParseQuery.getQuery(CVJob.class)
                                        .whereNotEqualTo("completed", null)
                                        .whereEqualTo("company", object.getCompany())
                                        .findInBackground(new FindCallback<CVJob>() {
                                            @Override
                                            public void done(List<CVJob> objects, ParseException e) {
                                                if (e == null){
                                                    JobsAdapter adapter = new JobsAdapter(
                                                            getContext(), objects);
                                                    ListView listView = (ListView) getActivity().findViewById(
                                                            R.id.listView_completed_jobs);
                                                    listView.setAdapter(adapter);
                                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                            startActivity(new Intent(getActivity(), JobsActivity.class));
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }
                        } else {
                            Log.e(TAG, "done: ",e );
                        }
                    }
                });
    }

}
