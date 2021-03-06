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

import java.util.ArrayList;
import java.util.List;
/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class BillableJobsFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private ArrayList<String> jobsList;
    private static final String TAG = "BillableJobsFragment";
    List<CVJob> mJobs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_billable_jobs, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listView_billable_jobs);

        ParseQuery<CVJob> query = ParseQuery.getQuery(CVJob.class).whereNotEqualTo("invoiced", null);
//        query.whereEqualTo("user", ParseUser.getCurrentUser());
            
        query.findInBackground(new FindCallback<CVJob>() {
            @Override
            public void done(List<CVJob> objects, ParseException e) {
                Log.d(TAG, "Opened Jobs Fragment: done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                if (e == null) {
                    mListView.setAdapter(new JobsAdapter(getContext(), objects));
                }
            }
        });
    }

    private void setListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CVJob job = (CVJob) parent.getAdapter().getItem(position);

                if (job != null) {
                    Intent intent = new Intent(getActivity(), JobsActivity.class);
                    intent.putExtra("Job", job.getObjectId());
                    startActivity(intent);
                }
            }
        });
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
                                        .whereNotEqualTo("invoiced", null)
                                        .whereEqualTo("company", object.getCompany())
                                        .findInBackground(new FindCallback<CVJob>() {
                                            @Override
                                            public void done(List<CVJob> objects, ParseException e) {
                                                if (e == null){
                                                    JobsAdapter adapter = new JobsAdapter(
                                                            getContext(), objects);
                                                    ListView listView = (ListView) getActivity().findViewById(
                                                            R.id.listView_billable_jobs);
                                                    if (listView != null) {
                                                        listView.setAdapter(adapter);
                                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                startActivity(new Intent(getActivity(), JobsActivity.class));
                                                            }
                                                        });
                                                    }
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