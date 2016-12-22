package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.jobs.JobsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class JobListFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imgCreateJob;
    public static final String TAG = "JobListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_jobs, container, false);
        
        viewPager = (ViewPager) parentView.findViewById(R.id.viewpager_jobs_viewpager);
        setupViewPager(viewPager);
        
        tabLayout = (TabLayout) parentView.findViewById(R.id.tablayout_jobs_tabs);
        tabLayout.setupWithViewPager(viewPager, true);
        
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_home_top_activity);

        if (getActivity().findViewById(R.id.imageview_toolbar_home_add) != null) {
            imgCreateJob = (ImageView) toolbar.findViewById(R.id.imageview_toolbar_home_add);
            if (imgCreateJob != null) {
                imgCreateJob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, JobsActivity.class));
                    }
                });
            }
        }
    }

    private void setListeners() {
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OpenedJobsFragment(), "Opened");
        adapter.addFragment(new ApprovedJobsFragment(), "Approved");
        adapter.addFragment(new ScheduledJobsFragment(), "Scheduled");
        adapter.addFragment(new BillableJobsFragment(), "Billable");
        adapter.addFragment(new CompletedJobsFragment(), "Completed");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
