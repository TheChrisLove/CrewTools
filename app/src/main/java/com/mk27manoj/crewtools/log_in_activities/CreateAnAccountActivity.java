package com.mk27manoj.crewtools.log_in_activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.global_data.StaticData;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class CreateAnAccountActivity extends Activity {
    private Context mContext;
    private Button btnContinue;
    private ImageView imgBack;
    private static final String TAG = "CreateAnAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_an_account);
        initViews();

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "SaveInBackground: ", e);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtilities.hideKeyBoard(mContext);


    }

    @Override
    public void onBackPressed() {
    }

    private void initViews() {
        mContext = CreateAnAccountActivity.this;
        btnContinue = (Button) findViewById(R.id.button_create_an_account_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CVUser user = new CVUser();
                String name = ((EditText)findViewById(R.id.editTextCreateName)).getText().toString();
                String email = ((EditText)findViewById(R.id.editTextCreateEmail)).getText().toString();
                final String username = ((EditText)findViewById(R.id.editTextCreateUsername)).getText().toString();
                final String password = ((EditText)findViewById(R.id.editTextCreatePassword)).getText().toString();
                user.setName(name);
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password);


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            user.logInInBackground(username, password);
                            SharedPreferences preferences = PreferenceManager
                                    .getDefaultSharedPreferences(CreateAnAccountActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString(StaticData.PREF_USERNAME, username)
                                    .putString(StaticData.PREF_PASSWORD, password)
                                    .apply();
                            startActivity(new Intent(mContext, AcceptInvitationActivity.class));

                        } else {
                            Log.e(TAG, "SignUpInBackground: ", e);
                        }
                    }
                });

            }
        });
        imgBack = (ImageView) findViewById(R.id.imageview_create_an_account_left_arrow);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}