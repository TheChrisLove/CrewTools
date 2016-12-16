package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.R;

import java.util.ArrayList;

/**
 * Renovated by The Chris Love  on 2016-07-07.
 */
public class PaymentTermsFragment extends Fragment {
    private Context mContext;
    private View parentView;
    private LinearLayout termsContainer;
    private RelativeLayout relativeAddTerms;
    private ArrayList<String> terms;
    private boolean isEnabled = false;
    //private TextView txtEditDone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_payment, container, false);
        initViews();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_company_top);
        //txtEditDone = (TextView) toolbar.findViewById(R.id.textview_toolbar_company_edit_done);

        termsContainer = (LinearLayout) parentView.findViewById(R.id.linearlayout_payment_pay_terms_container);
        relativeAddTerms = (RelativeLayout) parentView.findViewById(R.id.relativelayout_payments_add_new_term_item);

        terms = new ArrayList<>();
        terms.add("Upon Receipt");
        terms.add("Net 7");
        terms.add("Net 10");
        terms.add("Net 15");
        terms.add("Net 30");
        terms.add("Net 60");
        terms.add("Net 90");

        for (int i = 0; i < terms.size(); i++) {
            addView();
        }
/*
        txtEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEnabled) {
                    txtEditDone.setText("Done");
                    relativeAddTerms.setVisibility(View.VISIBLE);
                    for (int i = 0; i < termsContainer.getChildCount(); i++) {
                        ((ImageView) termsContainer.getChildAt(i).findViewById(R.id.imageview_payment_terms_remove)).setVisibility(View.VISIBLE);
                        ((ImageView) termsContainer.getChildAt(i).findViewById(R.id.imageview_payment_terms_edit)).setVisibility(View.VISIBLE);
                        ((ImageView) termsContainer.getChildAt(i).findViewById(R.id.imageview_payment_terms_selected)).setVisibility(View.GONE);
                    }
                } else {
                    txtEditDone.setText("Edit");
                    relativeAddTerms.setVisibility(View.GONE);
                    for (int i = 0; i < termsContainer.getChildCount(); i++) {
                        ((ImageView) termsContainer.getChildAt(i).findViewById(R.id.imageview_payment_terms_remove)).setVisibility(View.GONE);
                        ((ImageView) termsContainer.getChildAt(i).findViewById(R.id.imageview_payment_terms_edit)).setVisibility(View.GONE);
                    }
                }
                isEnabled = !isEnabled;
            }
        });*/
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_payment_terms, null);
        TextView txtName = (TextView) baseView.findViewById(R.id.textview_payment_terms_name);
        ImageView imgRemove = (ImageView) baseView.findViewById(R.id.imageview_payment_terms_remove);
        ImageView imgEdit = (ImageView) baseView.findViewById(R.id.imageview_payment_terms_edit);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_payment_terms_selected);
        if (termsContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        baseView.setTag(termsContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                for (int j = 0; j < termsContainer.getChildCount(); j++) {
                    if (j == count) {
                        termsContainer.getChildAt(j).findViewById(R.id.imageview_payment_terms_selected).setVisibility(View.VISIBLE);
                    } else {
                        termsContainer.getChildAt(j).findViewById(R.id.imageview_payment_terms_selected).setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) baseView.getParent()).removeView(baseView);
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.custom_accept_double_input_dialog, null);

                final AlertDialog alertD = new AlertDialog.Builder(mContext).create();
                TextView txtTitle = (TextView) promptView.findViewById(R.id.textview_custom_double_input_dialog_heading);
                txtTitle.setText("Add New Item");
                TextView txtMessage = (TextView) promptView.findViewById(R.id.textview_custom_double_input_dialog_message);
                txtMessage.setText("Enter the name of the new item");
                Button btnSave = (Button) promptView.findViewById(R.id.button_custom_double_input_dialog_add);
                Button btnCancel = (Button) promptView.findViewById(R.id.button_custom_double_input_dialog_cancel);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertD.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertD.dismiss();
                    }
                });
                alertD.setView(promptView);
                alertD.show();

            }
        });
        txtName.setText(terms.get(termsContainer.getChildCount()));
        termsContainer.addView(baseView);
    }

    public static PaymentTermsFragment newInstanceOf(String mCompanyId) {
        PaymentTermsFragment fragment = new PaymentTermsFragment();

        return fragment;

    }
}
