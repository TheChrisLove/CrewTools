package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Renovated by The Chris Love on 2016-07-07.
 */
public class CreateViewCompanyFragment extends Fragment {
    private static final String ARG_COMPANY_ID = "arg_company_id";
    private Context mContext;
    private View parentView;
    private TextView txtEditDone, txtTitle;
    private EditText edtName, edtLicense, edtMotto, edtPhone, edtWebsite, edtWorkHours, edtStreetAddress, edtSuiteApt, edtCity, edtState, edtZip;
    private boolean isEnable = false;
    private Button btnSave;
    private RelativeLayout relativeAddService;
    private LinearLayout serviceContainer;
    private JSONArray services;
    private CVCompany mCompany;
    private static final String TAG = "CreateViewCompanyFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            try {
                for (CVCompany company : ParseQuery.getQuery(CVCompany.class).whereEqualTo("objectId", getArguments().getString(ARG_COMPANY_ID)).find()) {
                    mCompany = company;
                }
            } catch (ParseException e) {
                Log.e(TAG, "onCreateView: ", e);
            }
        }
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_create_view_company, container, false);

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        setListeners();
    }

    private void initViews() {
        mContext = getActivity();
        Log.i(TAG, "initViews:  company = " + mCompany);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_company_top);
        txtEditDone = (TextView) toolbar.findViewById(R.id.textview_toolbar_company_edit_done);
        txtTitle = (TextView) toolbar.findViewById(R.id.textview_toolbar_company_title);
        serviceContainer = (LinearLayout) parentView.findViewById(R.id.textview_create_view_company_service_container);

        edtName = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_name);
        edtName.setText(mCompany.getBusiness());
        edtLicense = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_license);
        edtLicense.setText(mCompany.getLicense());
        edtMotto = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_motto);
        edtMotto.setText(mCompany.getMotto());
        edtPhone = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_phone);
        edtPhone.setText(mCompany.getPhone());
        edtWebsite = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_website);
        edtWebsite.setText(mCompany.getWebsite());
        edtWorkHours = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_work_hours);
        edtWorkHours.setText(mCompany.getWorkDayStart() + " - " + mCompany.getWorkDayEnd());
        edtStreetAddress = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_street_address);
        edtStreetAddress.setText(mCompany.getAddress1());
        edtSuiteApt = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_suite_apt);
        edtSuiteApt.setText(mCompany.getAddress2());
        edtCity = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_city);
        edtCity.setText(mCompany.getCity());
        edtState = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_state);
        edtState.setText(mCompany.getState());
        edtZip = (EditText) getActivity().findViewById(R.id.edittext_create_view_company_zip);
        edtZip.setText(mCompany.getPostalCode());

        relativeAddService = (RelativeLayout) parentView.findViewById(R.id.relativelayout_create_view_company_add_new_service);

        btnSave = (Button) getActivity().findViewById(R.id.button_create_view_company_save);


        services = mCompany.getServiceTypes();

        for (int i = 0; i < services.length(); i++) {
            try {
                addService(services.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListeners() {
        relativeAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogview = LayoutInflater.from(getActivity()).inflate(R.layout.custom_type_input_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.add_services)
                        .setView(dialogview)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                EditText editText = (EditText) dialogview.findViewById(R.id.editText_add_service_type);
                                services.put(editText.getText().toString());
                                addService(editText.getText().toString());
                                mCompany.setServiceTypes(services);

                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCompany.setBusiness(edtName.getText().toString());
                    mCompany.setLicense(edtLicense.getText().toString());
                    mCompany.setMotto(edtMotto.getText().toString());
                    mCompany.setPhone(edtPhone.getText().toString());
                    mCompany.setWebsite(edtWebsite.getText().toString());
                    String[] workdays = edtWorkHours.getText().toString().split("-");
                    mCompany.setWorkDayStart(workdays[0]);
                    mCompany.setWorkDayEnd(workdays[1]);
                    mCompany.setAddress1(edtStreetAddress.getText().toString());
                    mCompany.setAddress2(edtSuiteApt.getText().toString());
                    mCompany.setCity(edtCity.getText().toString());
                    mCompany.setState(edtState.getText().toString());
                    mCompany.setPostalCode(edtZip.getText().toString());
                    mCompany.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        txtEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEnable) {
                    txtTitle.setText("EDIT COMPANY");
                    txtEditDone.setText("Done");
                    btnSave.setVisibility(View.VISIBLE);
                    relativeAddService.setVisibility(View.VISIBLE);
                    for (int i = 0; i < serviceContainer.getChildCount(); i++) {
                        ((ImageView) serviceContainer.getChildAt(i).findViewById(R.id.imageview_create_company_add_service_remove)).setVisibility(View.VISIBLE);
                    }
                } else {
                    txtEditDone.setText("Edit");
                    txtTitle.setText("VIEW COMPANY");
                    btnSave.setVisibility(View.GONE);
                    relativeAddService.setVisibility(View.GONE);
                    for (int i = 0; i < serviceContainer.getChildCount(); i++) {
                        ((ImageView) serviceContainer.getChildAt(i).findViewById(R.id.imageview_create_company_add_service_remove)).setVisibility(View.GONE);
                    }
                }
                isEnable = !isEnable;
                edtName.setEnabled(isEnable);
                edtLicense.setEnabled(isEnable);
                edtMotto.setEnabled(isEnable);
                edtPhone.setEnabled(isEnable);
                edtWebsite.setEnabled(isEnable);
                edtWorkHours.setEnabled(isEnable);
                edtStreetAddress.setEnabled(isEnable);
                edtSuiteApt.setEnabled(isEnable);
                edtCity.setEnabled(isEnable);
                edtState.setEnabled(isEnable);
                edtZip.setEnabled(isEnable);
            }
        });
    }

    private void addService(String name) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_create_company_add_service, null);
        TextView txtService = (TextView) baseView.findViewById(R.id.textview_create_company_add_service_name);
        ImageView imgRemove = (ImageView) baseView.findViewById(R.id.imageview_create_company_add_service_remove);
        baseView.setTag(serviceContainer.getChildCount());
        imgRemove.setTag(serviceContainer.getChildCount());

        txtService.setText(name);
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) baseView.getParent()).removeView(baseView);
                services.remove(serviceContainer.indexOfChild(v));
                mCompany.setServiceTypes(services);
            }
        });
        serviceContainer.addView(baseView);
    }

    public static CreateViewCompanyFragment newInstanceOf(String mCompanyId) {
        Log.d(TAG, "newInstanceOf() called with: " + "mCompanyId = [" + mCompanyId + "]");
        CreateViewCompanyFragment fragment = new CreateViewCompanyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY_ID, mCompanyId);
        fragment.setArguments(args);
        return fragment;
    }
}
