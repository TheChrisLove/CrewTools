package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.jobs.JobsActivity;

/**
 * Renovated by The Chris Love  on 2016-07-07.
 */
public class LegalFragment extends Fragment {
    private Context mContext;
    private View parentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_legal, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
    }

    private void setListeners() {
    }

    public static LegalFragment newInstanceOf(String mCompanyId) {

        LegalFragment fragment = new LegalFragment();

        return fragment;
    }
}