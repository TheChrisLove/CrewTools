package com.mk27manoj.crewtools;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.fragments.CreateViewCompanyFragment;
import com.mk27manoj.crewtools.fragments.DesignFragment;
import com.mk27manoj.crewtools.fragments.LegalFragment;
import com.mk27manoj.crewtools.fragments.PaymentTermsFragment;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

/**
 * Renovated by The Chris Love on 2016-07-07.
 */
public class CompanyActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle, txtCancel, txtEditDone;
    private LinearLayout linearCompany, linearPayment, linearLegal, linearDesign;
    private TextView txtCompany, txtPayment, txtLegal, txtDesign;
    private ImageView imgCompany, imgPayment, imgLegal, imgDesign;
    private String mCompanyId;
    private static final String TAG = "CompanyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtilities.hideKeyBoard(mContext);
    }

    private void initViews() {
        if (getIntent() != null){
            Log.i(TAG, "onCreate:  intent data = " + getIntent().getStringExtra(CrewToolsConstants.RESPONSE_COMPANY_ID));
            mCompanyId = getIntent().getStringExtra(CrewToolsConstants.RESPONSE_COMPANY_ID);
        }
        mContext = CompanyActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_company_top);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_company_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle.setText("VIEW COMPANY");
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_company_cancel);
        txtEditDone = (TextView) findViewById(R.id.textview_toolbar_company_edit_done);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearCompany = (LinearLayout) findViewById(R.id.linearlayout_company_menu_company);
        linearPayment = (LinearLayout) findViewById(R.id.linearlayout_company_menu_payment_terms);
        linearLegal = (LinearLayout) findViewById(R.id.linearlayout_company_menu_legal);
        linearDesign = (LinearLayout) findViewById(R.id.linearlayout_company_menu_design);

        txtCompany = (TextView) findViewById(R.id.textview_company_menu_company);
        txtPayment = (TextView) findViewById(R.id.textview_company_menu_payment_terms);
        txtLegal = (TextView) findViewById(R.id.textview_company_menu_legal);
        txtDesign = (TextView) findViewById(R.id.textview_company_menu_design);

        imgCompany = (ImageView) findViewById(R.id.imageview_company_menu_company);
        imgPayment = (ImageView) findViewById(R.id.imageview_company_menu_payment_terms);
        imgLegal = (ImageView) findViewById(R.id.imageview_company_menu_legal);
        imgDesign = (ImageView) findViewById(R.id.imageview_company_menu_design);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CreateViewCompanyFragment createViewCompanyFragment = CreateViewCompanyFragment.newInstanceOf(mCompanyId);
        fragmentTransaction.replace(R.id.framelayout_company_container, createViewCompanyFragment);
        fragmentTransaction.commit();
        txtCompany.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
        imgCompany.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);

        linearCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("VIEW COMPANY");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateViewCompanyFragment createViewCompanyFragment = CreateViewCompanyFragment.newInstanceOf(mCompanyId);
                fragmentTransaction.replace(R.id.framelayout_company_container, createViewCompanyFragment);
                fragmentTransaction.commit();

                txtCompany.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgCompany.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtLegal.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgLegal.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtPayment.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgPayment.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtDesign.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgDesign.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

        linearPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("VIEW COMPANY");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PaymentTermsFragment paymentTermsFragment =  PaymentTermsFragment.newInstanceOf(mCompanyId);
                fragmentTransaction.replace(R.id.framelayout_company_container, paymentTermsFragment);
                fragmentTransaction.commit();

                txtPayment.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgPayment.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtLegal.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgLegal.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCompany.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCompany.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtDesign.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgDesign.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

        linearLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("LEGAL");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LegalFragment legalFragment = LegalFragment.newInstanceOf(mCompanyId);
                fragmentTransaction.replace(R.id.framelayout_company_container, legalFragment);
                fragmentTransaction.commit();

                txtLegal.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgLegal.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtCompany.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCompany.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtPayment.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgPayment.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtDesign.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgDesign.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

        linearDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("TEMPLATE DESIGN");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DesignFragment designFragment = DesignFragment.newInstanceOf(mCompanyId);
                fragmentTransaction.replace(R.id.framelayout_company_container, designFragment);
                fragmentTransaction.commit();

                txtDesign.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgDesign.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtLegal.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgLegal.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtPayment.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgPayment.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCompany.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCompany.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });

    }

    //    @Override
//    public void onBackPressed() {
//    }

}
