package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

import com.mk27manoj.crewtools.ParseSubClasses.CVInvoice;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.CalenderEventsAdapter;
import com.mk27manoj.crewtools.adapters.InvoiceAdapter;
import com.mk27manoj.crewtools.ViewInvoiceActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class PendingFragment  extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private TextView openInvoice;
    private ArrayList<String> pendingList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_pending, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listview_pending_list);
        ParseQuery.getQuery(CVInvoice.class)
                .whereEqualTo("paid", null)
                .findInBackground(new FindCallback<CVInvoice>() {
                    @Override
                    public void done(List<CVInvoice> objects, ParseException e) {
                        if (e == null){
                            mListView.setAdapter(new InvoiceAdapter(mContext, objects, 1));
//                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
//                                    intent.putExtra("task_object_id", ((CVTask)parent.getAdapter().getItem(position)).getObjectId());
//                                    startActivity(intent);
//                                }
//                            });
//                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    Intent intent = new Intent(getActivity(), MyAccountActivity.class);
//                                    intent.putExtra("EMPLOYEE", employeeList.get(position).getObjectId());
//                                    startActivity(intent);
//                                }
//                            });
                        }

                    }
                });
    }

    private void setListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CVInvoice invoice = (CVInvoice) parent.getAdapter().getItem(position);

                if (invoice != null) {
                    Intent intent = new Intent(getActivity(), ViewInvoiceActivity.class);
                    Log.e("Invoice Owed", "Value: " + invoice.getOwed());
                    Log.e("Invoice Total", "Value: " + invoice.getTotal());
                    intent.putExtra("owed", invoice.getOwed());
                    intent.putExtra("total", invoice.getTotal());
                    startActivity(intent);
                }
            }
        });
    }
}