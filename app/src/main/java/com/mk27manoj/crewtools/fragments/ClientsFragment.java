package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.JobsAdapter;
import com.mk27manoj.crewtools.clients.AddClientActivity;
import com.mk27manoj.crewtools.clients.ClientActivity;
import com.mk27manoj.crewtools.clients.ClientAdpater;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class ClientsFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private static final String TAG = "ClientsFragment";
    List<CVClient> mClients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_clients, container, false);

        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listview_clients_list);


        ParseQuery<CVClient> query = ParseQuery.getQuery(CVClient.class);
        query.whereMatchesKeyInQuery("company", "company", ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", CVUser.getCurrentUser()));
        query.findInBackground(new FindCallback<CVClient>() {
            @Override
            public void done(List<CVClient> objects, ParseException e) {
                Log.d(TAG, "Clients Fragment: done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                if (e == null) {
                    mClients = objects;
                    mListView.setAdapter(new ClientAdpater(getContext(), objects));
                }
            }
        });

        //mListView.setAdapter(new JobsAdapter(mContext, clientsList));

    }

    private void setListeners() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CVClient client = (CVClient) parent.getAdapter().getItem(position);

                if (client != null) {
                    Intent intent = new Intent(getActivity(), ClientActivity.class);
                    intent.putExtra("client", client.getName());
                    intent.putExtra("objectId", client.getObjectId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        setListeners();


    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().findViewById(R.id.imageview_toolbar_home_add) != null) {
            getActivity().findViewById(R.id.imageview_toolbar_home_add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), AddClientActivity.class));
                }
            });
        }

        ((EditText) getActivity().findViewById(R.id.edittext_clients_search)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TAG, "onEditorAction() called with: " + "v = [" + v + "], actionId = [" + actionId + "], event = [" + event + "]");
                return false;
            }
        });
        ((EditText) getActivity().findViewById(R.id.edittext_clients_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged() called with: " + "s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
                ParseQuery<CVClient> query = ParseQuery.getQuery(CVClient.class);
                query.whereContains("name", s.toString());

                query.findInBackground(new FindCallback<CVClient>() {
                    @Override
                    public void done(List<CVClient> objects, ParseException e) {
                        Log.d(TAG, "done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                        if (e == null) {
                            if (mClients == null) {
                                mClients = objects;
                            } else {
                                for (CVClient object : objects) {
                                    if (!mClients.contains(object)) {
                                        mClients.add(object);
                                    }
                                }
                            }

                        }
                        mListView.setAdapter(new ClientAdpater(getContext(), objects));
                    }
                });


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
