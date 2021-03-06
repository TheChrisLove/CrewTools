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
import android.widget.AdapterView;

import com.mk27manoj.crewtools.ParseSubClasses.CVInvoice;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.InvoiceAdapter;
import com.mk27manoj.crewtools.ViewInvoiceActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class OverdueFragment  extends Fragment {
    private Context mContext;
    private View parentView;
    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_overdue, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listView_overdue_invoices);

        ParseQuery.getQuery(CVInvoice.class)
                .whereNotEqualTo("sent", null)
                .findInBackground(new FindCallback<CVInvoice>() {
                    @Override
                    public void done(List<CVInvoice> objects, ParseException e) {
                        if (e == null){
                            Date today = Calendar.getInstance().getTime();
                            List<CVInvoice> overdueList = objects;
                            for (int i = 0; i < objects.size(); i++) {
                                if ((today.getTime() - objects.get(i).getDate().getTime()) < 0){
                                    overdueList.remove(objects.get(i));
                                }
                            }
                            if (mListView != null) {
                                mListView.setAdapter(new InvoiceAdapter(mContext, overdueList, 3));
                            }
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