package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.mk27manoj.crewtools.JobProgressActivity;
import com.mk27manoj.crewtools.CalenderDatesPickerActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobItem;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.jobs.AcceptancePickerActivity;
import com.mk27manoj.crewtools.jobs.ClientPickerActivity;
import com.mk27manoj.crewtools.jobs.JobTypePickerActivity;
import com.mk27manoj.crewtools.jobs.OccurancePickerActivity;
import com.mk27manoj.crewtools.jobs.PaymentPickerActivity;
import com.mk27manoj.crewtools.jobs.PlacePickerActivity;
import com.mk27manoj.crewtools.jobs.PriorityPickerActivity;
import com.mk27manoj.crewtools.jobs.ServicePickerActivity;
import com.mk27manoj.crewtools.jobs.TaxPickerActivity;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseRole;
import com.parse.ParseUser;

public class CreateViewJobFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_JOB_ID = "arg_job_id";
    private Context mContext;
    private View parentView, imgContainer;
    private TextView txtEditDone;
    private TextView txtTax, calcTax, calcTotal, txtPlace, txtType, txtClient, txtOccurance, txtPriority, txtStartingDate, txtCompletionDate, txtPayment, txtAcceptWithin;
    private boolean isEnable = false;
    private EditText edtName, edtDescription;
    private Button btnSave;
    private RelativeLayout relativeAddService;
    private LinearLayout serviceContainer, taxContainer, totalContainer;
    private CVJob mJob;
    private String[] datesList;
    private String dates = "";
    private static final String TAG = "CreateViewJobFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_create_view_job, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_create_job_top);
        txtEditDone = (TextView) toolbar.findViewById(R.id.textview_toolbar_create_job_edit_done);
        txtEditDone.setVisibility(View.VISIBLE);

        txtEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEnable) {
                    txtEditDone.setText("Done");
//                    imgCancel.setVisibility(View.GONE);
//                    txtCancel.setVisibility(View.VISIBLE);
                } else {
                    txtEditDone.setText("Edit");
//                    imgCancel.setVisibility(View.VISIBLE);
//                    txtCancel.setVisibility(View.GONE);
                }
                isEnable = !isEnable;
                edtName.setEnabled(isEnable);
                txtType.setEnabled(isEnable);
                txtClient.setEnabled(isEnable);
                txtPlace.setEnabled(isEnable);
                txtOccurance.setEnabled(isEnable);
                txtPriority.setEnabled(isEnable);
                txtStartingDate.setEnabled(isEnable);
                txtCompletionDate.setEnabled(isEnable);
                txtPayment.setEnabled(isEnable);
                txtAcceptWithin.setEnabled(isEnable);
                edtDescription.setEnabled(isEnable);

            }
        });

        serviceContainer = (LinearLayout) parentView.findViewById(R.id.textview_create_view_job_service_container);
        taxContainer = (LinearLayout) parentView.findViewById(R.id.linearlayout_tax);
        imgContainer = (View) parentView.findViewById(R.id.linearlayout_img_tax_total);
        totalContainer = (LinearLayout) parentView.findViewById(R.id.linearlayout_total);

        txtTax = (TextView) parentView.findViewById(R.id.textview_create_view_job_price_tax);
        calcTax = (EditText) parentView.findViewById(R.id.edittext_create_view_job_price_tax);
        calcTotal = (EditText) parentView.findViewById(R.id.edittext_create_view_job_price_total);
        edtName = (EditText) parentView.findViewById(R.id.edittext_create_view_job_project_name);
        txtType = (TextView) parentView.findViewById(R.id.textview_create_view_job_project_type);
        txtClient = (TextView) parentView.findViewById(R.id.textview_create_view_job_project_client);
        txtPlace = (TextView) parentView.findViewById(R.id.textview_create_view_job_place);
        txtOccurance = (TextView) parentView.findViewById(R.id.textview_create_view_job_timespace_occurance);
        txtPriority = (TextView) parentView.findViewById(R.id.textview_create_view_job_timespace_priority);
        txtStartingDate = (TextView) parentView.findViewById(R.id.textview_create_view_job_timespace_starting_date);
        txtCompletionDate = (TextView) parentView.findViewById(R.id.textview_create_view_job_timespace_completion_date);
        txtPayment = (TextView) parentView.findViewById(R.id.textview_create_view_job_payment);
        txtAcceptWithin = (TextView) parentView.findViewById(R.id.textview_create_view_job_accept_within);
        edtDescription = (EditText) parentView.findViewById(R.id.edittext_create_view_job_description);

        relativeAddService = (RelativeLayout) parentView.findViewById(R.id.relativelayout_create_view_job_add_new_service);

        btnSave = (Button) parentView.findViewById(R.id.button_create_view_job_save);
        
        taxContainer.setVisibility(LinearLayout.GONE);
        imgContainer.setVisibility(View.GONE);
        totalContainer.setVisibility(LinearLayout.GONE);
    }

    private void setListeners() {
        txtPlace.setOnClickListener(this);
        txtType.setOnClickListener(this);
        txtClient.setOnClickListener(this);
        relativeAddService.setOnClickListener(this);
        txtTax.setOnClickListener(this);
        txtOccurance.setOnClickListener(this);
        txtPriority.setOnClickListener(this);
        txtStartingDate.setOnClickListener(this);
        txtCompletionDate.setOnClickListener(this);
        txtPayment.setOnClickListener(this);
        txtAcceptWithin.setOnClickListener(this);

        btnSave.setOnClickListener(this);
//        txtEditDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick() called with: " + "v = [" + v + "]");
        switch (v.getId()) {
            case R.id.textview_create_view_job_place:
                startActivityForResult(new Intent(mContext, PlacePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE);
                break;

            case R.id.textview_create_view_job_project_type:
                startActivityForResult(new Intent(mContext, JobTypePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_TYPE);
                break;

            case R.id.textview_create_view_job_project_client:
                startActivityForResult(new Intent(mContext, ClientPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_CLIENT);
                break;

            case R.id.relativelayout_create_view_job_add_new_service:
                startActivityForResult(new Intent(mContext, ServicePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_SERVICE);
                break;

            case R.id.textview_create_view_job_price_tax:
                startActivityForResult(new Intent(mContext, TaxPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_TAX);
                break;

            case R.id.textview_create_view_job_timespace_occurance:
                startActivityForResult(new Intent(mContext, OccurancePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_OCCURANCE);
                break;

            case R.id.textview_create_view_job_timespace_priority:
                startActivityForResult(new Intent(mContext, PriorityPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PRIORITY);
                break;

            case R.id.textview_create_view_job_payment:
                startActivityForResult(new Intent(mContext, PaymentPickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PAYMENTS);
                break;

            case R.id.textview_create_view_job_accept_within:
                startActivityForResult(new Intent(mContext, AcceptancePickerActivity.class), CrewToolsConstants.REQUEST_CREATE_JOB_PICK_ACCEPTANCE);
                break;

            case R.id.textview_create_view_job_timespace_starting_date:
//                CommonUtilities.setDateOnView(mContext, txtStartingDate);
                startActivityForResult(new Intent(mContext, CalenderDatesPickerActivity.class), 
                CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT);
                break;

            case R.id.textview_create_view_job_timespace_completion_date:
//                CommonUtilities.setDateOnView(mContext, txtCompletionDate);
                startActivityForResult(new Intent(mContext, CalenderDatesPickerActivity.class), 
                CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT);
                break;

            case R.id.button_create_view_job_save:
                try {
                    mJob.setNotes(edtDescription.getText().toString());
                    mJob.setName(edtName.getText().toString());
                    mJob.setACL(new ParseACL());
                    mJob.getACL().setReadAccess(ParseUser.getCurrentUser(), true);
                    mJob.getACL().setWriteAccess(ParseUser.getCurrentUser(), true);
                    CVCompany company = new CVCompany();
                    for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                        cvEmployee.getCompany().fetch();
                        company = cvEmployee.getCompany();
                        mJob.setCompany(cvEmployee.getCompany());
                    }

                    for (ParseRole role : ParseQuery.getQuery(ParseRole.class).whereContains("name", company.getObjectId()).find()) {
                        mJob.getACL().setRoleReadAccess(role, true);
                        mJob.getACL().setRoleWriteAccess(role, true);
                    }
                    mJob.save();
                    /*
                    Intent intent = new Intent(mContext, JobProgressActivity.class);
                    intent.putExtra(CrewToolsConstants.RESPONSE_JOB_ID, mJob.getObjectId());
                    startActivity(intent);
                    */
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                dsad
                break;
//            case R.id.edittext_create_job_place:break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult() called with: " + "requestCode = [" + requestCode +
//                "], resultCode = [" + resultCode + "], data = [" + data.getExtras() + "]");
        Log.d(TAG, "onActivityResult() called with: " + "requestCode = [" + requestCode +
                "], resultCode = [" + resultCode + "], data = [" + data + "]");
        try {
            if (mJob == null) mJob = new CVJob();
            if (data != null) {
                switch (requestCode) {
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE:
                        txtPlace.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));

                        for (CVAddress address : ParseQuery.getQuery(CVAddress.class)
                                .whereEqualTo("objectId", data.getStringExtra(CrewToolsConstants.RESPONCE_ADDRESS)).find()) {
                            mJob.setAddress(address);
                        }

                        mJob.setCity(data.getStringExtra(CrewToolsConstants.RESPONCE_CITY));
                        mJob.setState(data.getStringExtra(CrewToolsConstants.RESPONCE_STATE));
                        mJob.save();
                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_CLIENT:
                        for (CVClient cvClient : ParseQuery.getQuery(CVClient.class).whereEqualTo("objectId",
                                data.getStringExtra(CrewToolsConstants.RESPONCE_CLIENT_ID))
                                .find()) {

                            mJob.setClient(cvClient);
                            mJob.save();
                        }
                        txtClient.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_TYPE:

                        mJob.setType(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        txtType.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        mJob.saveEventually();
                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_SERVICE:

                        Log.d(TAG, "onActivityResult: jobItemId= " +data.getStringExtra(CrewToolsConstants.RESPONCE_SERVICE_ID));
                        for (CVJobItem jobItem : ParseQuery.getQuery(CVJobItem.class).whereEqualTo("objectId",
                                data.getStringExtra(CrewToolsConstants.RESPONCE_SERVICE_ID)).find()) {
                            Log.i(TAG, "onActivityResult: " + jobItem);
                            ParseRelation<CVJobItem> relation = mJob.getItems();
                            relation.add(jobItem);
                            addService(jobItem);

                        }
                        mJob.save();

                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PRIORITY:

                        txtPriority.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        switch (data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE)){
                            case "7 Days":
                                mJob.setAcceptWithin(7);
                                break;
                            case "15 Days":
                                mJob.setAcceptWithin(15);
                                break;
                            case "30 Days":
                                mJob.setAcceptWithin(30);
                                break;
                            case "45 Days":
                                mJob.setAcceptWithin(45);
                                break;
                            default:
                                mJob.setAcceptWithin(60);
                                break;
                        }
                        mJob.save();
                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_OCCURANCE:
                        mJob.setOccurs(data.getIntExtra(CrewToolsConstants.RESPONSE_OCCURANCE_NUMBER, 1));
                        txtOccurance.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        mJob.save();
                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PAYMENTS:
                        txtPayment.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        break;
                    case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_ACCEPTANCE:
                        mJob.setPayment(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        txtAcceptWithin.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
                        mJob.save();
                        break;
                    case CrewToolsConstants.REQUEST_MULTIPLE_DATES:
                        dates = data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE);
                        datesList = dates.split("@@");
                        for (int i = 0; i < datesList.length; i++) {
//                            addView();
                            Log.d(TAG, "dates are: " + datesList);
                        }
                        break;
                    case CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT:
                                Log.d(TAG, "onActivityResult() called with: " + "requestCode = [" + requestCode +
                "], resultCode = [" + resultCode + "], data = [" + data.getExtras() + "]");
//                        mJob.setStart(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
//                        txtCompletionDate.setText(data.getStringExtra(CrewToolsConstants.RESPONCE_MESSAGE));
//                        mJob.setStart(data.getExtras(CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT));
//                        txtCompletionDate.setText(data.getExtras(CrewToolsConstants.REQUEST_JOB_PROGRESS_SENT));
//                        setCompletedDates
//                        mJob.save();
                        break;

//            case CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE:break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addService(CVJobItem item) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_create_job_add_service, null);
        TextView txtService = (TextView) baseView.findViewById(R.id.textview_create_job_add_service_name);
        TextView txtAmount = (TextView) baseView.findViewById(R.id.textview_create_job_add_service_amount);
        ImageView imgRemove = (ImageView) baseView.findViewById(R.id.imageview_create_job_add_service_remove);
        ImageView imgEdit = (ImageView) baseView.findViewById(R.id.imageview_create_job_add_service_edit);
        baseView.setTag(serviceContainer.getChildCount());
        imgRemove.setTag(serviceContainer.getChildCount());
        imgRemove.setVisibility(View.VISIBLE);

        txtAmount.setText(item.getAmount() + "");
        txtService.setText(item.getName());
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) baseView.getParent()).removeView(baseView);
            }
        });
        serviceContainer.addView(baseView);
    }

    public static CreateViewJobFragment newInstanceOf(String mJobId) {
        CreateViewJobFragment frag = new CreateViewJobFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JOB_ID, mJobId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null && getArguments().getString(ARG_JOB_ID) != null) {
            String jobIdString = getArguments().getString(ARG_JOB_ID);
            try {
                for (CVJob cvJob : ParseQuery.getQuery(CVJob.class).whereEqualTo("objectId", jobIdString).find()) {
                    mJob = cvJob;
                }

                edtName.setText(mJob.getName());
                txtType.setText(mJob.getType());
                if (mJob.getClient() != null) {
                    mJob.getClient().fetch();
                    txtClient.setText(mJob.getClient().getName());
                }
                if (mJob.getAddress() != null) {
                    mJob.getAddress().fetchIfNeeded();
                    txtPlace.setText(mJob.getAddress().getAddress1() + "\n" + mJob.getCity() + ", " + mJob.getState() + " " + mJob.getAddress().getZip());
                }
                
                String finalCount = "";
                for (int i = 0; i < serviceContainer.getChildCount(); i++) {
                    Log.e(TAG, "My loop is: " + serviceContainer.getChildAt(i));
                    View childView = serviceContainer.getChildAt(i);
                    
//                    TextView childTextView = (TextView)(childView.findViewById(R.id.textout));
//                    String childTextViewText = (String)(childView.getText());
//                    EditText tot = (EditText)(childView.findViewById(R.id.textout));
//                    EditText tot = (EditText) serviceContainer.getChildAt(i).getValue();
//                    finalCount += tot;
                }
//                    calcTotal.setText(finalCount);
                    
                txtPriority.setText(getResources().getStringArray(R.array.priority_picker)[mJob.getPriority()]);
                if (mJob.getStart() != null) {
                    txtStartingDate.setText(mJob.getStart().toString());
                }
                if (mJob.getCompleted() != null) {
                    txtCompletionDate.setText(mJob.getCompleted().toString());
                }
                txtPayment.setText(mJob.getPayment());

                txtAcceptWithin.setText(mJob.getAcceptWithin() + " days");
                if (mJob.getNotes() != null) {
                    edtDescription.setText(mJob.getNotes());
                }
                for (CVJobItem cvJobItem : mJob.getItems().getQuery().find()) {
                    addService(cvJobItem);
                }
                taxContainer.setVisibility(LinearLayout.VISIBLE);
                imgContainer.setVisibility(View.VISIBLE);
                totalContainer.setVisibility(LinearLayout.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}