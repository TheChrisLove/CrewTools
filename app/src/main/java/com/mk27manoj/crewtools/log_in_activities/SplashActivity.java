package com.mk27manoj.crewtools.log_in_activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mk27manoj.crewtools.HomeActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.global_data.StaticData;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                String username = preferences.getString(StaticData.PREF_USERNAME, null);
                String password = preferences.getString(StaticData.PREF_PASSWORD, null);
                if (username==null || password==null) {
                    startStartUpActivity();
                } else {

                    CVUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            Log.d(TAG, "done() called with: " + "user = [" + user.getUsername() + "], e = [" + e + "]");
                            if (e == null){


                                ParseQuery<CVEmployee> employeeParseQuery = ParseQuery.getQuery(CVEmployee.class);
                                employeeParseQuery.whereEqualTo("user", user);
                                employeeParseQuery.findInBackground(new FindCallback<CVEmployee>() {
                                    @Override
                                    public void done(List<CVEmployee> objects, ParseException e) {
                                        Log.d(TAG, "done() called with: " + "objects = [" + objects.size() + "], e = [" + e + "]");
                                        if (objects.size() == 0){
                                            Intent invitationIntent = new Intent(SplashActivity.this, AcceptInvitationActivity.class);
                                            startActivity(invitationIntent);
                                        }else {

                                            String roleName = objects.get(0).getCompany().getObjectId() + "_"
                                                    + objects.get(0).getRole();
                                            ParseQuery.getQuery(ParseRole.class)
                                                    .whereEqualTo("name", roleName)
                                                    .findInBackground(new FindCallback<ParseRole>() {
                                                        @Override
                                                        public void done(List<ParseRole> objects, ParseException e) {
                                                            if (e == null){
                                                                for (ParseRole object : objects) {
                                                                    CVUser.getCurrentUser().getACL().setRoleReadAccess(object, true);
                                                                }
                                                                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });


                            } else {
                                Toast.makeText(SplashActivity.this, "Please reenter your username and password again.", Toast.LENGTH_SHORT).show();
                                startStartUpActivity();
                            }
                            finish();
                        }
                    });
                }

                // close this activity

            }
        }, SPLASH_TIME_OUT);
    }

    private void startStartUpActivity() {
        Intent intent = new Intent(SplashActivity.this, StartupActivity.class);
        startActivity(intent);
    }
}
