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

public class OpenedJobsFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private ArrayList<String> jobsList;
    private static final String TAG = "OpenedJobsFragment";
    List<CVJob> mJobs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_opened_jobs, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listView_open_jobs);

        ParseQuery<CVJob> query = ParseQuery.getQuery(CVJob.class).whereDoesNotExist("completedDates");
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
        try {
            for (CVEmployee object : ParseQuery.getQuery(CVEmployee.class)
                    .whereEqualTo("user", ParseUser.getCurrentUser())
                    .find()) {

                JobsAdapter adapter = new JobsAdapter(
                        getContext(), ParseQuery.getQuery(CVJob.class)
                        .whereDoesNotExist("completedDates")
                        .whereEqualTo("company", object.getCompany())
                        .find());
                ListView listView = (ListView) getActivity().findViewById(R.id.listView_open_jobs);
                if (listView != null) {
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            try {
                                Intent intent = new Intent(getActivity(), JobsActivity.class);
                                ((CVJob) parent.getAdapter().getItem(position)).fetch();
                                intent.putExtra("Job", ((CVJob) parent.getAdapter().getItem(position)).getObjectId());
                                startActivity(intent);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}