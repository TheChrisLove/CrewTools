package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mk27manoj.crewtools.R;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class FridayFragment extends Fragment {
    private Context mContext;
    private View parentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_friday, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
    }

    private void setListeners() {
    }

}

