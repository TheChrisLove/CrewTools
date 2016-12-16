package com.mk27manoj.crewtools.log_in_activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mk27manoj.crewtools.HomeActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.global_data.StaticData;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class SignInActivity extends AppCompatActivity {
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void signInClick(View view) {
        final ProgressDialog dialog = new ProgressDialog(SignInActivity.this);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading....");
        dialog.show();
        if (((EditText)findViewById(R.id.editTextSignInName)) != null) {
            username = ((EditText)findViewById(R.id.editTextSignInName)).getText().toString();
        }
        if (((EditText)findViewById(R.id.editTextSignInPassword)) != null) {
            password = ((EditText)findViewById(R.id.editTextSignInPassword)).getText().toString();
        }

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
               dialog.cancel();
                if (e == null){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SignInActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString(StaticData.PREF_USERNAME, username)
                            .putString(StaticData.PREF_PASSWORD, password)
                            .apply();

                    ParseQuery<CVEmployee> employeeParseQuery = ParseQuery.getQuery(CVEmployee.class);
                    employeeParseQuery.findInBackground(new FindCallback<CVEmployee>() {
                        @Override
                        public void done(List<CVEmployee> objects, ParseException e) {
                            if (objects == null){
                                Intent invitationIntent = new Intent(SignInActivity.this, AcceptInvitationActivity.class);
                                startActivity(invitationIntent);
                            }else {
                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });


                } else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SignInActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher)
                            .setTitle("Log In Error")
                            .setMessage("Please double check your username and password.  Press Ok if you would like to reenter your usernamen and password.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((EditText)findViewById(R.id.editTextSignInName)).setText("");
                                    ((EditText)findViewById(R.id.editTextSignInPassword)).setText("");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();

                }
            }
        });

    }
}
