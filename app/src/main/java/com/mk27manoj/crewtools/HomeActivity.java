package com.mk27manoj.crewtools;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.fragments.CalenderFragment;
import com.mk27manoj.crewtools.fragments.ClientsFragment;
import com.mk27manoj.crewtools.fragments.CrewFragment;
import com.mk27manoj.crewtools.fragments.InvoicesFragment;
import com.mk27manoj.crewtools.fragments.JobListFragment;
import com.mk27manoj.crewtools.utils.CommonUtilities;

/**
 * Renovated by The Chris Love on 11-30-2016.
 **/
public class HomeActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar, mToolbarBottom;
    private TextView txtTitle;
    private LinearLayout linearJobsLayout, linearClientsLayout, linearCalenderLayout, linearInvoicesLayout, linearCrewLayout;
    private TextView txtJobs, txtClients, txtCalender, txtInvoices, txtCrew;
    private ImageView imgJobs, imgClients, imgCalender, imgInvoices, imgCrew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        CommonUtilities.hideKeyBoard(mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews() {
        mContext = HomeActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_home_top_activity);
        mToolbarBottom = (Toolbar) findViewById(R.id.toolbar_home_bottom);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_home_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        txtTitle.setText("JOBS");

        linearJobsLayout = (LinearLayout) findViewById(R.id.linealayout_home_menu_jobs);
        linearClientsLayout = (LinearLayout) findViewById(R.id.linealayout_home_menu_clients);
        linearCalenderLayout = (LinearLayout) findViewById(R.id.linealayout_home_menu_calender);
        linearInvoicesLayout = (LinearLayout) findViewById(R.id.linealayout_home_menu_invoices);
        linearCrewLayout = (LinearLayout) findViewById(R.id.linealayout_home_menu_crew);

        txtJobs = (TextView) findViewById(R.id.textview_home_menu_jobs);
        txtClients = (TextView) findViewById(R.id.textview_home_menu_client);
        txtCalender = (TextView) findViewById(R.id.textview_home_menu_calender);
        txtInvoices = (TextView) findViewById(R.id.textview_home_menu_invoices);
        txtCrew = (TextView) findViewById(R.id.textview_home_menu_crew);

        imgJobs = (ImageView) findViewById(R.id.imageview_home_menu_jobs);
        imgClients = (ImageView) findViewById(R.id.imageview_home_menu_client);
        imgCalender = (ImageView) findViewById(R.id.imageview_home_menu_calender);
        imgInvoices = (ImageView) findViewById(R.id.imageview_home_menu_invoices);
        imgCrew = (ImageView) findViewById(R.id.imageview_home_menu_crew);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        JobListFragment jobListFragment = new JobListFragment();
        fragmentTransaction.replace(R.id.framelayout_home_container, jobListFragment);
        fragmentTransaction.commit();

        txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
        imgJobs.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);

        linearJobsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("JOBS");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                JobListFragment jobListFragment = new JobListFragment();
                fragmentTransaction.replace(R.id.framelayout_home_container, jobListFragment);
                fragmentTransaction.commit();
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgJobs.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtClients.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgClients.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCalender.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCrew.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCrew.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });
        linearClientsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("CLIENTS");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClientsFragment clientsFragment = new ClientsFragment();
                fragmentTransaction.replace(R.id.framelayout_home_container, clientsFragment);
                fragmentTransaction.commit();

                txtClients.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgClients.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJobs.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCalender.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCrew.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCrew.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

            }
        });
        linearCalenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("CALENDER");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CalenderFragment calenderFragment = new CalenderFragment();
                fragmentTransaction.replace(R.id.framelayout_home_container, calenderFragment);
                fragmentTransaction.commit();

                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgCalender.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJobs.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtClients.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgClients.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCrew.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCrew.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });
        linearInvoicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("INVOICES");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                InvoicesFragment invoicesFragment = new InvoicesFragment();
                fragmentTransaction.replace(R.id.framelayout_home_container, invoicesFragment);
                fragmentTransaction.commit();

                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgInvoices.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJobs.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtClients.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgClients.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCalender.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCrew.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCrew.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });
        linearCrewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText("CREW");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CrewFragment crewFragment = new CrewFragment();
                fragmentTransaction.replace(R.id.framelayout_home_container, crewFragment);
                fragmentTransaction.commit();

                txtCrew.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgCrew.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJobs.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtClients.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgClients.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtInvoices.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgInvoices.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCalender.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
