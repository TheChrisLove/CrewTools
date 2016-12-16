package com.mk27manoj.crewtools.jobs.add_jobs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class ClientDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {
    ClientDialogListener listener;
    View view;
    private static final String TAG = "ClientDialogFragment";

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    public interface ClientDialogListener{
        void ClientSelectedListener(CVClient client);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ClientDialogListener){
            listener = (ClientDialogListener) activity;
        } else {
            throw new IllegalArgumentException("Please attach ClientDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        view =inflater.inflate(R.layout.fragment_client_dialog_selector, container,false);

        String selectDate = getString(R.string.select_date);
        getDialog().setTitle(R.string.select_date);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", ParseUser.getCurrentUser())
                .findInBackground(new FindCallback<CVEmployee>() {
                    @Override
                    public void done(List<CVEmployee> objects, ParseException e) {
                        if (e == null) {
                            for (CVEmployee object : objects) {
                                ParseQuery.getQuery(CVClient.class)
                                        .whereEqualTo("company", object.getCompany())
                                        .findInBackground(new FindCallback<CVClient>() {
                                            @Override
                                            public void done(List<CVClient> objects, ParseException e) {
                                                if (objects != null) {
                                                    Log.d(TAG, "done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                                                    ListView listView = (ListView) view.findViewById(R.id.listView_add_client_dialog);

                                                    ArrayAdapter<CVClient> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                                                            objects);

                                                    listView.setAdapter(adapter);
                                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                            CVClient cvClient = (CVClient) parent.getAdapter().getItem(position);
                                                            listener.ClientSelectedListener(cvClient);
                                                            getDialog().cancel();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
