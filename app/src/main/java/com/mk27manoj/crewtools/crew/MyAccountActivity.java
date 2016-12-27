package com.mk27manoj.crewtools.crew;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.fragments.CalenderFragment;
import com.mk27manoj.crewtools.fragments.JobListFragment;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class MyAccountActivity extends AppCompatActivity {
    private Context mContext;
    //private EditText
    private Toolbar mToolbar, mToolbarBottom;
    private LinearLayout linearJobsLayout, linearCalenderLayout, linearAccountLayout;
    private TextView txtJobs, txtClients, txtCalender, txtInvoices, txtAccount,  mTextViewEditOrDone, mTextViewToolbarText;
    private ImageView imgJobs, imgClients, imgCalender, imgInvoices, imgAccount;
    CVEmployee cvEmployee;
    private RelativeLayout relativeCrewmate, relativeAdmin, relativeManager;
    private Button btnSave;
    private String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        initViews();

        if (getIntent() != null) {
            employeeId = getIntent().getStringExtra("EMPLOYEE");
            getSupportFragmentManager().beginTransaction().replace(R.id.myaccount_container,
                    AccountFragment.newInstanceOf(employeeId), AccountFragment.TAG).commit();

            try {
                List<CVEmployee> employeeList = ParseQuery.getQuery(CVEmployee.class)
                        .whereEqualTo("objectId", employeeId)
                        .find();

                for (CVEmployee employee : employeeList) {
                    cvEmployee = employee;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_my_account_top);
        mToolbarBottom = (Toolbar) findViewById(R.id.toolbar_my_account_bottom);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTextViewEditOrDone = (TextView) findViewById(R.id.textview_toolbar_my_account_edit);
        mContext = MyAccountActivity.this;
        mTextViewToolbarText = (TextView) findViewById(R.id.textView_my_account_toolbar);
        //try {
            //cvEmployee.getUser().fetch();
            mTextViewToolbarText.setText(getResources().getString(R.string.account));
        //} catch (ParseException e) {
            //e.printStackTrace();
        //}


        linearJobsLayout = (LinearLayout) findViewById(R.id.linearlayout_my_account_menu_jobs);
        linearJobsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set Toolbar text
                mTextViewToolbarText.setText(R.string.jobs);

                // set Visibility
                mTextViewEditOrDone.setVisibility(View.GONE);

                //  Change Colors Orange
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgJobs.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);

                //  Change color to white
                txtAccount.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgAccount.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCalender.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

                // Load Fragment
                JobListFragment fragment = (JobListFragment) getSupportFragmentManager().findFragmentByTag(JobListFragment.TAG);
                if (fragment == null){
                    fragment = new JobListFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.myaccount_container, fragment, JobListFragment.TAG).commit();
            }
        });
        linearCalenderLayout = (LinearLayout) findViewById(R.id.linearlayout_my_account_menu_calendar);
        linearCalenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set Toolbar text
                mTextViewToolbarText.setText(R.string.calendar);

                // set Visibility
                mTextViewEditOrDone.setVisibility(View.GONE);

                //  Change Colors Orange
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgCalender.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);

                //  Change color to white
                txtAccount.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgAccount.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJobs.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

                // Load Fragment
                CalenderFragment fragment = (CalenderFragment) getSupportFragmentManager().findFragmentByTag(CalenderFragment.TAG);
                if (fragment == null){
                    fragment = new CalenderFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.myaccount_container, fragment, CalenderFragment.TAG).commit();


            }
        });
        linearAccountLayout = (LinearLayout) findViewById(R.id.linealayout_my_account_menu_account);
        linearAccountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewToolbarText.setText(getResources().getString(R.string.account));

                // set Visibility
                mTextViewEditOrDone.setVisibility(View.VISIBLE);

                //  Change Colors Orange
                txtAccount.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
                imgAccount.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);

                //  Change color to white
                txtJobs.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgJobs.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                txtCalender.setTextColor(ContextCompat.getColor(mContext, R.color.fontColorWhite));
                imgCalender.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

                // Load Fragment
                AccountFragment fragment = (AccountFragment) getSupportFragmentManager().findFragmentByTag(AccountFragment.TAG);
                if (fragment == null){
                    fragment = AccountFragment.newInstanceOf(employeeId);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.myaccount_container, fragment, AccountFragment.TAG).commit();

            }
        });

        txtJobs = (TextView) findViewById(R.id.textview_my_account_menu_jobs);
        txtCalender = (TextView) findViewById(R.id.textview_my_account_menu_calender);
        txtAccount = (TextView) findViewById(R.id.textview_my_account_menu_account);

        imgJobs = (ImageView) findViewById(R.id.imageview_my_account_menu_jobs);
        imgCalender = (ImageView) findViewById(R.id.imageview_my_account_menu_calender);
        imgAccount = (ImageView) findViewById(R.id.imageview_my_account_menu_account);

        txtAccount.setTextColor(ContextCompat.getColor(mContext, R.color.buttonFontColor));
        imgAccount.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
    }


    @Override
    protected void onResume() {
        super.onResume();
        CommonUtilities.hideKeyBoard(mContext);
        if (getIntent() != null){
            //cvEmployee = (CVEmployee) getIntent().getSerializableExtra("EMPLOYEE");
/*
            try {
                editUserName.setText(cvEmployee.getUser().fetch().getUsername());
                editPassword.setText(cvEmployee.getUser().fetch().getString("password"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            */


        }
    }


}