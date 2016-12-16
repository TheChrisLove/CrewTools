package com.mk27manoj.crewtools;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewInvoiceActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private Button btnSignContract;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_estimate);
        initViews();
    }

    private void initViews() {
        /*
        mContext = ViewInvoiceActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_view_estimate_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_view_estimate_title);

        txtTitle.setText("VIEW INVOICE");

        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_view_estimate_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignContract = (Button) findViewById(R.id.button_view_estimate_sign_contract);
        btnSignContract.setText("Send Invoice");
        btnSignContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        */
    }
}