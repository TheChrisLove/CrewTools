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

import com.mk27manoj.crewtools.CreateViewInvoiceActivity;
import com.mk27manoj.crewtools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class InvoicesFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_invoices, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_home_top_activity);
        if (toolbar != null) {
            ImageView imgAddInvoice = (ImageView) toolbar.findViewById(R.id.imageview_toolbar_home_add);

            imgAddInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, CreateViewInvoiceActivity.class));
                }
            });
        }

        viewPager = (ViewPager) parentView.findViewById(R.id.viewpager_invoices_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) parentView.findViewById(R.id.tablayout_invoices_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setListeners() {

        if (getActivity().findViewById(R.id.imageview_toolbar_home_add) != null) {
            getActivity().findViewById(R.id.imageview_toolbar_home_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "This is working for Invoices", Toast.LENGTH_SHORT).show();
                }
            });
        }
        
        if (getActivity().findViewById(R.id.textview_row_calender_heading) != null) {
            getActivity().findViewById(R.id.textview_row_calender_heading).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "This is working for Invoices", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new PendingFragment(), "Pending");
        adapter.addFragment(new SentFragment(), "Sent");
        adapter.addFragment(new OverdueFragment(), "Overdue");
        adapter.addFragment(new PaidFragment(), "Paid");
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


    }
}
