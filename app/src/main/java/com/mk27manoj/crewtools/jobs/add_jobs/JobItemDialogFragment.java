package com.mk27manoj.crewtools.jobs.add_jobs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.util.Log;

import com.mk27manoj.crewtools.ParseSubClasses.CVJobItem;
import com.mk27manoj.crewtools.ParseSubClasses.CVService;
import com.mk27manoj.crewtools.R;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

/**
 * Renovated by The Chris Love 10-24-2016.
 */

public class JobItemDialogFragment extends DialogFragment {

    private static final String TAG = "JobItemDialogFragment";
    private static final String ARG_SERVICE_ID = "arg_service_id";
    private JobItemDialogListener listener;
    private View mView;
    private CVService mService;
    private EditText editTextQuantity, editTextPer, editTextNotes;
    private TextView textViewTotal, textViewPerText;
    private CVJobItem mJobItem;
    private Switch switchTaxable;

    public interface JobItemDialogListener{
        void JobItemClick(CVJobItem jobItem);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof  JobItemDialogListener){
            listener = (JobItemDialogListener) activity;
        } else {
            throw new IllegalArgumentException("Please attach new ");
        }
    }

    public static JobItemDialogFragment newInstanceOf(String serviceId){
        JobItemDialogFragment frag = new JobItemDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SERVICE_ID, serviceId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.job_item_dialog_view, null);
        editTextQuantity = (EditText) mView.findViewById(R.id.editText_job_item_quantity);
        editTextPer = (EditText) mView.findViewById(R.id.editText_job_item_per);
        editTextNotes = (EditText) mView.findViewById(R.id.editText_job_item_notes);
        textViewTotal = (TextView) mView.findViewById(R.id.editText_job_item_total);
        textViewPerText = (TextView) mView.findViewById(R.id.textView_job_item_per_text);
        switchTaxable = (Switch) mView.findViewById(R.id.switch_job_item_taxable);
        builder.setTitle(R.string.add_services)
                .setView(mView);
        if (getArguments() != null){
            try {
                for (CVService service : ParseQuery.getQuery(CVService.class).whereEqualTo("objectId", getArguments()
                        .getString(ARG_SERVICE_ID)).find()) {
                    mService = service;
                    mJobItem = new CVJobItem();
                    textViewPerText.setText("Per " + mService.getUnit() + " @");
                    editTextQuantity.setText(1 + "");
                    editTextPer.setText(mService.getAmount() + "");
                    switchTaxable.setChecked(mService.getTaxable());
                    if (!mService.getEditable()){
                        editTextPer.setEnabled(false);
                        switchTaxable.setEnabled(false);
                    }
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                mJobItem.setNotes(editTextNotes.getText().toString());
                                mJobItem.setName(mService.getName());
                                mJobItem.setTaxable(switchTaxable.isChecked());
                                mJobItem.setQuantity(Integer.parseInt(editTextQuantity.getText().toString()));
                                mJobItem.setAmount(Double.parseDouble(editTextPer.getText().toString()));
                                Double total = Integer.parseInt(editTextQuantity.getText().toString())
                                        * Double.parseDouble(editTextPer.getText().toString());
                                mJobItem.setTotal(total);
                                if (mService.getUnit() != null) {
                                    mJobItem.setUnit(mService.getUnit());
                                }
                                mJobItem.setEditable(mService.getEditable());
                                if (mService.getServiceType() != null) {
                                    mJobItem.setServiceType(mService.getServiceType());
                                }
                                mJobItem.setCompany(mService.getCompany());
                                mJobItem.setACL(new ParseACL());
                                mJobItem.getACL().setWriteAccess(ParseUser.getCurrentUser(), true);
                                mJobItem.getACL().setReadAccess(ParseUser.getCurrentUser(), true);
                                mService.getCompany().fetch();
                                for (ParseRole role : ParseQuery.getQuery(ParseRole.class).whereContains("name",
                                        mService.getCompany().getObjectId()).find()) {
                                    mJobItem.getACL().setRoleReadAccess(role, true);
                                    mJobItem.getACL().setRoleWriteAccess(role, true);
                                }
                                mJobItem.save();
                                listener.JobItemClick(mJobItem);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getDialog().cancel();
                        }
                    });
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return builder.create();
    }
}
