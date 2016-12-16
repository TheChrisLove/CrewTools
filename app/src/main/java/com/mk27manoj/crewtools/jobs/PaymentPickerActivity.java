package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

/**
 * Renovated by The Chris Love on 2016-06-25.
 */
public class PaymentPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private LinearLayout priorityContainer;
    private RelativeLayout relativeAddPriority;
    private ArrayList<String> payments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_picker);
        initViews();

    }

    private void initViews() {
        mContext = PaymentPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_priority_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_priority_picker_title);

        txtTitle.setText("CHOOSE PAYMENTS");
        priorityContainer = (LinearLayout) findViewById(R.id.linearlayout_priority_picker_type_container);

        payments = new ArrayList<>();
        payments.add("55/55");
        payments.add("On Completion");
        payments.add("Upfront");
        payments.add("Monthly");

        for (int i = 0; i < payments.size(); i++) {
            addView();
        }

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_priority_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_priority_picker, null);
        TextView txtName = (TextView) baseView.findViewById(R.id.textview_row_priority_picker_name);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_priority_picker_selected);
        if (priorityContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        baseView.setTag(priorityContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                for (int j = 0; j < priorityContainer.getChildCount(); j++) {
                    if (j == count) {
                        priorityContainer.getChildAt(j).findViewById(R.id.imageview_row_priority_picker_selected).setVisibility(View.VISIBLE);
                    } else {
                        priorityContainer.getChildAt(j).findViewById(R.id.imageview_row_priority_picker_selected).setVisibility(View.INVISIBLE);
                    }
                }

                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, payments.get(count));
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PAYMENTS, intent);
                finish();//finishing activity
            }
        });
        txtName.setText(payments.get(priorityContainer.getChildCount()));
        priorityContainer.addView(baseView);
    }

}