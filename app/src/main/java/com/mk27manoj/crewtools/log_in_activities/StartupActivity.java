package com.mk27manoj.crewtools.log_in_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mk27manoj.crewtools.R;

public class StartupActivity extends Activity {
    private Context mContext;
    private Button btnCreateAccount;
    private TextView btnSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        initViews();
    }

    private void initViews() {
        mContext = StartupActivity.this;
        btnCreateAccount = (Button) findViewById(R.id.button_startup_create_an_account);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CreateAnAccountActivity.class));
            }
        });
        
        btnSignInAccount = (TextView) findViewById(R.id.textview_startup_sign_in);
        btnSignInAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SignInActivity.class));
            }
        });
    }
    
    public void startupClick(View view) {
        Intent intent = new Intent(StartupActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}