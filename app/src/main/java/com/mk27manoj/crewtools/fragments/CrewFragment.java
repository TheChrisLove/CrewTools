package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mk27manoj.crewtools.crew.InviteCrewActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.CrewAdapter;
import com.mk27manoj.crewtools.crew.MyAccountActivity;
import com.mk27manoj.crewtools.log_in_activities.StartupActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class CrewFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private ListView mListView;
    private List<CVEmployee> employeeList;
    private ArrayList<String> crewList;
    private List<CVEmployee> mCVEmployee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_crew, container, false);
        initViews();
        setListeners();
        return parentView;

    }

    private void initViews() {
        mContext = getActivity();
        mListView = (ListView) parentView.findViewById(R.id.listview_crew_list);


        ParseQuery<CVEmployee> employeeParseQuery = ParseQuery.getQuery(CVEmployee.class);
        employeeParseQuery.findInBackground(new FindCallback<CVEmployee>() {
            @Override
            public void done(List<CVEmployee> objects, ParseException e) {
                if (e == null){
                    if (objects.size()> 0){
                        employeeList = objects;
                        mListView.setAdapter(new CrewAdapter(mContext, objects));
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getActivity(), MyAccountActivity.class);
                                intent.putExtra("EMPLOYEE", employeeList.get(position).getObjectId());
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });


    }

    private void setListeners() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.button_crew_sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), StartupActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().findViewById(R.id.imageview_toolbar_home_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO TOAST
                Toast.makeText(getActivity(), "This is working for Crew", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), InviteCrewActivity.class));
            }
        });
    }
}
