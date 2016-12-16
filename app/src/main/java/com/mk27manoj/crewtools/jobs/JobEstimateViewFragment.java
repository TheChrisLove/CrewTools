package com.mk27manoj.crewtools.jobs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.CaptureSignActivity;
import com.mk27manoj.crewtools.CompanyActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */
public class JobEstimateViewFragment extends Fragment {

    private static final String ARG_JOB_ID = "arg_job_id";
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel, imgCompanyEdit, imgEstimateShare;
    private TextView btnSignContract;
    private boolean hasContractorSigned = false, hasClientSigned = false;
    private boolean isApprovedRequest = false;
    private CVJob mJob;
    RelativeLayout content;
    private static final String TAG = "JobEstimateViewFragment";

    public static JobEstimateViewFragment newInstanceOf(String jobId) {
        JobEstimateViewFragment frag = new JobEstimateViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JOB_ID, jobId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() != null) {
            try {
                for (CVJob cvJob : ParseQuery.getQuery(CVJob.class).whereEqualTo("objectId", getArguments().getString(ARG_JOB_ID)).find()) {
                    mJob = cvJob;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return inflater.inflate(R.layout.activity_view_estimate, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();

        try {
            // TODO:  Populate the PDF View
            //LinearLayout mainView = new LinearLayout(getActivity());

            content = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout_estimate_pdf_view);

            TextView title = (TextView) content.findViewById(R.id.textView_pdf_company_title);
            if(mJob != null) {
                mJob.getCompany().fetch();
                CVCompany company = mJob.getCompany();
                Spanned titleString = Html.fromHtml("<b>" + company.getBusiness() +
                        "</b> <br><small>" + company.getAddress1() + "<br>" + company.getCity() +
                        ", " + company.getState() + " " + company.getPostalCode() + "</small>");

                Log.i(TAG, "onActivityCreated: " + titleString);

                title.setText(titleString);

                // Set Client Text
                TextView clientNameTextView = (TextView) content.findViewById(R.id.textView_job_estimate_client_name);
                mJob.getClient().fetch();
                CVClient client = mJob.getClient();
                clientNameTextView.setText(client.getName());

                LinearLayout jobNameTextView = (LinearLayout) content.findViewById(R.id.linearLayout_job_estimate_items);
//                jobNameTextView.setText(mJob.getName());


                TextView clientAddressTextView = (TextView) content.findViewById(R.id.textView_job_estimate_address1);

                if (client.getBillingAddress() != null) {
                    client.getBillingAddress().fetchIfNeeded();
                    clientAddressTextView.setText(client.getBillingAddress().getAddress1());
                } else {
                    String PrimaryAddress = null, PrimaryCity = null, PrimaryState = null,
                            PrimaryZip = null, SecondaryAddress = null, SecondaryCity = null,
                            SecondaryState = null, SecondaryZip = null;
                    for (CVAddress address : ParseQuery.getQuery(CVAddress.class).whereEqualTo("client", client).find()) {
                        if (address.getPrimary()) {
                            PrimaryAddress = address.getAddress1();
                            PrimaryCity = address.getCity();
                            PrimaryState = address.getState();
                            PrimaryZip = address.getPostalCode();
                        } else {
                            SecondaryAddress = address.getAddress1();
                            SecondaryCity = address.getCity();
                            SecondaryState = address.getState();
                            SecondaryZip = address.getPostalCode();
                        }
                    }
                    if (PrimaryAddress == null) {
                        clientAddressTextView.setText(SecondaryAddress);
                    } else {
                        clientAddressTextView.setText(PrimaryAddress);
                    }
                    Log.i(TAG, "onActivityCreated: primaryAddress = " + PrimaryAddress + " SecondaryAddress = " + SecondaryAddress);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "onActivityCreated: ", e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            content = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout_estimate_pdf_view);
            // TODO: Move to a click event it should work.
            PdfDocument proposalPDF = new PdfDocument();
            //  5,100 x 6,600 pixels.

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1654, 2339, 1).create();

            Page page = proposalPDF.startPage(pageInfo);

//            content.measure(1654, 2339);
//            content.layout(0,0,1654, 2339);

            content.draw(page.getCanvas());
            proposalPDF.finishPage(page);

            String pdfName = "proposal_" + Calendar.getInstance().getTime() + ".pdf";
            FileOutputStream fos = getActivity().openFileOutput(pdfName, Context.MODE_PRIVATE);

            proposalPDF.writeTo(fos);
            proposalPDF.close();
            fos.close();

            FileInputStream fis = getActivity().openFileInput(pdfName);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int data;
            while ((data = fis.read()) >= 0) {

                outputStream.write(data);
            }
            Log.i(TAG, "onActivityCreated: toByteArray = " + outputStream.toByteArray());
            if (outputStream.toByteArray() != null) {

                ParseFile proposalFile = new ParseFile(outputStream.toByteArray(), pdfName);

                Log.i(TAG, "onActivityCreated: parseFile = " + proposalFile.getName());
                if(mJob != null){
                    mJob.setProposal(proposalFile);
                    mJob.save();
                }
            }
            fis.close();
        } catch (ParseException | IOException e) {
            Log.e(TAG, "onResume: ",e );
        }
    }

    private void initViews() {
        mContext = getActivity();

        if(mJob != null){
            if (mJob.getApprove() == null) {
                isApprovedRequest = false;
            } else {
                isApprovedRequest = true;
            }
        }

        imgCompanyEdit = (ImageView) getActivity().findViewById(R.id.imageView_estimate_company_edit);
        imgCompanyEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), CompanyActivity.class);
                    mJob.getCompany().fetch();
                    intent.putExtra(CrewToolsConstants.RESPONSE_COMPANY_ID, mJob.getCompany().getObjectId());
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        imgEstimateShare = (ImageView) getActivity().findViewById(R.id.imageView_estimate_share);
        imgEstimateShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                if (mJob.getContract() != null) {
                    Uri uri = Uri.parse(mJob.getContract().getUrl());
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.setType("image/");
                    startActivity(Intent.createChooser(intent, mContext.getString(R.string.share_contract_with)));
                } else if (mJob.getProposal() != null) {
                    try {
                        Uri uri = Uri.parse(mJob.getProposal().getUrl());
                        Log.i(TAG, "onClick:  uri " + uri);
                        intent.putExtra(Intent.EXTRA_STREAM, mJob.getProposal().getData());
                        intent.setType("image/*");
                        startActivity(Intent.createChooser(intent, mContext.getString(R.string.share_contract_with)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast toast = Toast.makeText(mContext, "Your Estimate has not been created yet!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }
            }
        });

        btnSignContract = (TextView) getActivity().findViewById(R.id.button_view_estimate_sign_contract);
        btnSignContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isApprovedRequest) {
                    if (mJob.getApprove() == null) {
                        mJob.setApprove(Calendar.getInstance().getTime());
                    }
                } else {
                    if (hasClientSigned && hasContractorSigned) {
                        Toast.makeText(getActivity(), "Yay! Estimate has been signed by both parties.", Toast.LENGTH_LONG).show();
                    } else {
                        Dialog d = new AlertDialog.Builder(mContext)
                                .setTitle("Sign Contract")
                                .setNegativeButton("Cancel", null)
                                .setIcon(ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_dialog_info, null))
                                .setItems(new String[]{"Contractor Signature", "Client Signature"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dlg, int position) {
                                        if (position == 0) {
                                            Intent clientIntent = new Intent(mContext, CaptureSignActivity.class);
                                            clientIntent.putExtra("IsClient", false);
                                            Log.e("TAG", "myIntent is: " + clientIntent);
                                            startActivityForResult(clientIntent, CrewToolsConstants.REQUEST_CAPTURE_SIGN_CONTRACTOR);
                                        } else if (position == 1) {
                                            Intent clientIntent = new Intent(mContext, CaptureSignActivity.class);
                                            clientIntent.putExtra("IsClient", true);
                                            Log.e("TAG", "myIntent is: " + clientIntent);
                                            startActivityForResult(clientIntent, CrewToolsConstants.REQUEST_CAPTURE_SIGN_CLIENT);
                                        }
                                    }
                                })
                                .create();
                        d.show();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d(TAG, "onActivityResult() called with: " + "requestCode = ["
                + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
            
            switch (requestCode) {
                case CrewToolsConstants.REQUEST_CAPTURE_SIGN_CONTRACTOR:
                    hasContractorSigned = true;
                    break;
                case CrewToolsConstants.REQUEST_CAPTURE_SIGN_CLIENT:
                    hasClientSigned = true;
                    break;
            }
        }
    }

}
