package com.mk27manoj.crewtools.clients;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmailAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVPhoneNumber;
import com.mk27manoj.crewtools.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientContactFragment extends Fragment {

    public static final String TAG = "ClientContactFragment";
    private static final String ARG_ID = "_id";
    private CVClient mClient;

    public ClientContactFragment() {
        // Required empty public constructor
    }

    public static ClientContactFragment newInstanceOf(String id){
        ClientContactFragment fragment = new ClientContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();

        if (args != null){
            setClientData(args.getString(ARG_ID));
        }
    }

    public void setClientData(String id) {

        ParseQuery<CVClient> parseQuery = ParseQuery.getQuery(CVClient.class);
        parseQuery.whereContains("objectId", id);
        parseQuery.findInBackground(new FindCallback<CVClient>() {
            @Override
            public void done(List<CVClient> objects, ParseException e) {
                if (e == null){
                    for (final CVClient object : objects) {
                        mClient = object;
                        if(mClient != null) {
                            ((TextView) getActivity().findViewById(R.id.textView_client_name)).setText(mClient.getName());
                            ((TextView) getActivity().findViewById(R.id.textView_client_company)).setText(mClient.getBusiness());
                            ((TextView) getActivity().findViewById(R.id.textView_client_email)).setText(mClient.getEmail());
                            ((TextView) getActivity().findViewById(R.id.textView_client_phone)).setText(mClient.getPhone());
                        }
                        ParseQuery<CVAddress> addressParseQuery = ParseQuery.getQuery(CVAddress.class);
                        addressParseQuery.whereEqualTo("client", mClient);
                        addressParseQuery.findInBackground(new FindCallback<CVAddress>() {
                            @Override
                            public void done(List<CVAddress> objects, ParseException e) {
                                Log.d(TAG, "Client Contact Fragment: done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                                if (e == null){
                                    ClientViewAdapter adapter = new ClientViewAdapter(getActivity(), objects, null, null);

                                    ((ListView)getActivity().findViewById(R.id.listview_client_addresses)).setAdapter(adapter);
                                }
                            }
                        }
                        );


                        ParseQuery<CVEmailAddress> emailAddressParseQuery = ParseQuery.getQuery(CVEmailAddress.class);
                        emailAddressParseQuery.whereEqualTo("client", mClient).findInBackground(new FindCallback<CVEmailAddress>() {
                            @Override
                            public void done(List<CVEmailAddress> objects, ParseException e) {
                                if (e == null){
                                    ClientViewAdapter adapter = new ClientViewAdapter(getActivity(), null, objects, null);
                                    ListView emailListView = (ListView) getActivity().findViewById(R.id.listview_client_emails);
                                    emailListView.setAdapter(adapter);
                                }
                            }
                        });


                        ParseQuery<CVPhoneNumber> phoneNumberParseQuery = ParseQuery.getQuery(CVPhoneNumber.class);
                        phoneNumberParseQuery.whereEqualTo("client", mClient).findInBackground(new FindCallback<CVPhoneNumber>() {
                            @Override
                            public void done(List<CVPhoneNumber> objects, ParseException e) {
                                if (e == null){
                                    ClientViewAdapter adapter = new ClientViewAdapter(getActivity(), null, null, objects);
                                    ListView phoneListView = (ListView) getActivity().findViewById(R.id.listview_client_phonenumbers);
                                    phoneListView.setAdapter(adapter);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
