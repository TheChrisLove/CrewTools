package com.mk27manoj.crewtools.log_in_activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mk27manoj.crewtools.HomeActivity;
import com.mk27manoj.crewtools.log_in_activities.SignInActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvitation;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Renovated by The Chris Love on 11-30-2016.
 */
public class AcceptInvitationActivity extends Activity {
    private Context mContext;
    private Button btnContinue;
    private ImageView imgBack;
    CVInvitation mInvitation;
    private static final String TAG = "AcceptInvitationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_invitation);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtilities.hideKeyBoard(mContext);
    }

    private void initViews() {
        mContext = AcceptInvitationActivity.this;
        btnContinue = (Button) findViewById(R.id.button_accept_invitation_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInvitation();
            }
        });
        imgBack = (ImageView) findViewById(R.id.imageview_accept_invitation_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    @SuppressLint("LongLogTag")
    private void checkInvitation() {

        final String code = ((EditText) findViewById(R.id.edittext_accept_invite_password_1)).getText().toString() +
                ((EditText) findViewById(R.id.edittext_accept_invite_password_2)).getText().toString() +
                ((EditText) findViewById(R.id.edittext_accept_invite_password_3)).getText().toString() +
                ((EditText) findViewById(R.id.edittext_accept_invite_password_4)).getText().toString() +
                ((EditText) findViewById(R.id.edittext_accept_invite_password_5)).getText().toString() +
                ((EditText) findViewById(R.id.edittext_accept_invite_password_6)).getText().toString();
        Log.i(TAG, "checkInvitation: " + code);
                
        ParseQuery<CVInvitation> invitationParseQuery = ParseQuery.getQuery(CVInvitation.class);
        invitationParseQuery.whereEqualTo("code", code);
        invitationParseQuery.findInBackground(new FindCallback<CVInvitation>() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(List<CVInvitation> objects, ParseException e) {
                Log.d(TAG, "done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                if (objects != null & objects.size() > 0) {
                    // You have a successful Invitation and we will add you to the account.

                    ParseQuery<ParseRole> roleParseQuery = ParseQuery.getQuery("Role");
                    mInvitation = objects.get(0);
                    if(mInvitation.getCompany() != null){
                        roleParseQuery.whereEqualTo("name", mInvitation.getCompany().getObjectId() + "_"
                                + mInvitation.getRole());
                        roleParseQuery.findInBackground(new FindCallback<ParseRole>() {
                            @Override
                            public void done(List<ParseRole> objects, ParseException e) {
                                if (objects == null || objects.size() == 0) {
                                    //  The role doesn't exist and needs to be created.
                                    //  Will Create all three roles for the company
                                    ParseACL acl = new ParseACL();
                                    ParseRole member = new ParseRole(mInvitation.getCompany().getObjectId() + "_member");
                                    member.setACL(acl);
                                    ParseRole admin = new ParseRole(mInvitation.getCompany().getObjectId() + "_admin");
                                    admin.setACL(acl);
                                    ParseRole manager = new ParseRole(mInvitation.getCompany().getObjectId() + "_manager");
                                    manager.setACL(acl);
                                    ParseUser.getCurrentUser().setACL(acl);

                                    //  Setting the member ACL
                                    member.setACL(member.getACL());
                                    member.setACL(admin.getACL());
                                    member.setACL(manager.getACL());
                                    member.setACL(ParseUser.getCurrentUser().getACL());
                                    member.saveInBackground();

                                    //  Setting the admin ACL
                                    admin.setACL(member.getACL());
                                    admin.setACL(admin.getACL());
                                    admin.setACL(manager.getACL());
                                    admin.setACL(ParseUser.getCurrentUser().getACL());
                                    admin.saveInBackground();

                                    //  Setting the member ACL
                                    manager.setACL(member.getACL());
                                    manager.setACL(admin.getACL());
                                    manager.setACL(manager.getACL());
                                    manager.setACL(ParseUser.getCurrentUser().getACL());
                                    manager.saveInBackground();

                                }
                                CVEmployee employee = new CVEmployee();
                                employee.setUser(CVUser.getCurrentUser());
                                employee.setCompany(mInvitation.getCompany());
                                employee.setRole(mInvitation.getRole());
                                if (CVUser.getCurrentUser().getACL() != null) {
                                    employee.setACL(CVUser.getCurrentUser().getACL());
                                } else {
                                    ParseACL acl = new ParseACL();
                                    CVUser.getCurrentUser().setACL(acl);
                                    employee.setACL(CVUser.getCurrentUser().getACL());
                                }
                                if (objects != null) {
                                    if (objects.size()> 0) {
                                        employee.setACL(objects.get(0).getACL());
                                    }
                                } else {
                                    employee.setACL(new ParseRole(mInvitation.getCompany().getObjectId()
                                            + "_" + mInvitation.getRole()).getACL());
                                }
                                employee.saveEventually(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            //  You have created a new employee and we will delete the invitation.
                                            if (!mInvitation.getKeep()) {
                                                mInvitation.deleteInBackground();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                        startActivity(new Intent(AcceptInvitationActivity.this, HomeActivity.class));
                    }
                } else {
                    //  Your invitation is invalid please reenter.
                    AlertDialog.Builder dialog = new AlertDialog.Builder(AcceptInvitationActivity.this);
                    dialog.setIcon(R.mipmap.ic_launcher)
                            .setTitle("Invalid Invitation Code")
                            .setMessage("Oops! The invitation you entered was incorrect, please re-enter or try logging in another account.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((EditText) findViewById(R.id.edittext_accept_invite_password_1)).setText("");
                                    ((EditText) findViewById(R.id.edittext_accept_invite_password_2)).setText("");
                                    ((EditText) findViewById(R.id.edittext_accept_invite_password_3)).setText("");
                                    ((EditText) findViewById(R.id.edittext_accept_invite_password_4)).setText("");
                                    ((EditText) findViewById(R.id.edittext_accept_invite_password_5)).setText("");
                                    ((EditText) findViewById(R.id.edittext_accept_invite_password_6)).setText("");
                                }
                            })
                            .setNegativeButton("Log In", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AcceptInvitationActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }

            }
        });


    }
}