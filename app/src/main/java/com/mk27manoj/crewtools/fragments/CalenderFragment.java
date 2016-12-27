package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mk27manoj.crewtools.NewTaskActivity;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.calendar.add_task.AddTaskActivity;
import com.mk27manoj.crewtools.ScheduledJobActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class CalenderFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String TAG = "CalenderFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_calender, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();

        if (getActivity().findViewById(R.id.toolbar_home_top_activity) != null) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_home_top_activity);
            ImageView imgAddInvoice = (ImageView) toolbar.findViewById(R.id.imageview_toolbar_home_add);
            imgAddInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, NewTaskActivity.class));
                }
            });
        }

        viewPager = (ViewPager) parentView.findViewById(R.id.viewpager_calender_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) parentView.findViewById(R.id.tablayout_calender_tabs);
        tabLayout.setupWithViewPager(viewPager);
        
        ImageView viewCalendar = (ImageView) parentView.findViewById(R.id.calender_clickthru);
        viewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ScheduledJobActivity.class));
            }
        });
    }

    private void setListeners() {
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new TodayFragment(), "Today");
        adapter.addFragment(new TomorrowFragment(), "Tomorrow");
        adapter.addFragment(new FridayFragment(), "Friday");
        adapter.addFragment(new OtherDayFragment(), "Other");
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

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().findViewById(R.id.imageview_toolbar_home_add) != null) {
            getActivity().findViewById(R.id.imageview_toolbar_home_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
