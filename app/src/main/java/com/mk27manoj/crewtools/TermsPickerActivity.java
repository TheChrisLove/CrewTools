package com.mk27manoj.crewtools;

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
import android.widget.TextView;

import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

public class TermsPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private LinearLayout termsContainer;
    private ArrayList<String> terms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurance_picker);
        initViews();
    }

    private void initViews() {
        mContext = TermsPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_occurance_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_occurance_picker_title);

        txtTitle.setText("PAYMENT TERMS");
        termsContainer = (LinearLayout) findViewById(R.id.linearlayout_occurance_picker_type_container);

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

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_occurance_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_occurance_picker, null);
        TextView txtName = (TextView) baseView.findViewById(R.id.textview_row_occurance_picker_name);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_occurance_picker_selected);
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
                        termsContainer.getChildAt(j).findViewById(R.id.imageview_row_occurance_picker_selected).setVisibility(View.VISIBLE);
                    } else {
                        termsContainer.getChildAt(j).findViewById(R.id.imageview_row_occurance_picker_selected).setVisibility(View.INVISIBLE);
                    }
                }

                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, terms.get(count));
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_OCCURANCE, intent);
                finish();//finishing activity
            }
        });
        txtName.setText(terms.get(termsContainer.getChildCount()));
        termsContainer.addView(baseView);
    }
}