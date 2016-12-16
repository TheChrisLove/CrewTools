package com.mk27manoj.crewtools.crew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvitation;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CommonUtilities;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class InviteCrewActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtChangeRole, txtNewUser, firsttextView, secondTextView, thirdTextView,
            fourthTextView, fifthTextView, sixthTextView;
    private ImageView imgCancel, imgUpload;
    private CVInvitation invitation = new CVInvitation();
    private String accessCode;
    private static final String TAG = "InviteCrewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_crew);
        initViews();

    }

    private void initViews() {
        //  Create Random User
        int firstNumber = new Random().nextInt(10);
        int secondNumber = new Random().nextInt(10);
        int thirdNumber = new Random().nextInt(10);
        int fourthNumber = new Random().nextInt(10);
        int fifthNumber = new Random().nextInt(10);
        int sixthNumber = new Random().nextInt(10);
        try {
            accessCode = "" + firstNumber + secondNumber + thirdNumber + fourthNumber + fifthNumber + sixthNumber;

            firsttextView = (TextView) findViewById(R.id.text_accept_invite_password_1);
            if (firsttextView != null) {
                firsttextView.setText("" +firstNumber + "");
            }

            secondTextView = (TextView) findViewById(R.id.text_accept_invite_password_2);
            if (secondTextView != null) {
                secondTextView.setText("" +secondNumber+ "");
            }

            thirdTextView = (TextView) findViewById(R.id.text_accept_invite_password_3);
            if (thirdTextView != null) {
                thirdTextView.setText("" +thirdNumber+ "");
            }

            fourthTextView = (TextView) findViewById(R.id.text_accept_invite_password_4);
            if (fourthTextView != null) {
                fourthTextView.setText("" +fourthNumber+ "");
            }

            fifthTextView = (TextView) findViewById(R.id.text_accept_invite_password_5);
            if (fifthTextView != null) {
                fifthTextView.setText("" +fifthNumber+ "");
            }

            sixthTextView = (TextView) findViewById(R.id.text_accept_invite_password_6);
            if (sixthTextView != null) {
                sixthTextView.setText("" +sixthNumber);
            }


            invitation.setCode(accessCode);
            for (CVEmployee employee : ParseQuery.getQuery(CVEmployee.class)
                    .whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                employee.getCompany().fetch();

                invitation.setCompany(employee.getCompany());

            }
           Date newDate = new Date( Calendar.getInstance().getTime().getTime() + (1000 *60 * 60*48 ));
            invitation.setExpires(newDate);
            invitation.setKeep(false);
            invitation.setRole("member");
            invitation.save();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mContext = InviteCrewActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_invite_crew_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_invite_crew_menu_close);
        imgUpload = (ImageView) findViewById(R.id.imageview_toolbar_invite_crew_upload);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent sharingIntent = new Intent(
                            android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    // String shareBody = Data.machinesbean.get(position)
                    // .getMachine_desc().toString();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Crew Tools Account Invitation");
                    invitation.fetch();
                    invitation.getCompany().fetch();
                    String message = "You have been invited to join " + invitation.getCompany()
                            .getName() + " on the Crew Tools app.  Please use the access code below " +
                            "to create your account.\n" + invitation.getCode();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));

                } catch (Exception e) {
                    Toast.makeText(InviteCrewActivity.this, "Application not Installed",
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: ",e );
                }
            }
        });

        txtNewUser = (TextView) findViewById(R.id.textview_invite_crew_new_users);
        txtChangeRole = (TextView) findViewById(R.id.textview_invite_crew_change_role);
        txtNewUser.setText(Html.fromHtml("New users will be enrolled as <b>member</b>"));
        txtChangeRole.setText(Html.fromHtml("<b><u>Change Roles</u></b>"));
        txtChangeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.custom_menu_items_dialog, null);

                final AlertDialog alertD = new AlertDialog.Builder(mContext).create();
                TextView txtTitle = (TextView) promptView.findViewById(R.id.textview_custom_menu_dialog_heading);
                txtTitle.setText("Change Role");
                TextView txtMessage = (TextView) promptView.findViewById(R.id.textview_custom_menu_dialog_message);
                txtMessage.setText("Enter the name of the new item");
                ListView menuList = (ListView) promptView.findViewById(R.id.listview_custom_menu_dialog_list);
                final String[] values = new String[]{"admin", "manger", "member"};
                menuList.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, values));
                menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(mContext, values[position], Toast.LENGTH_SHORT).show();
                        txtNewUser.setText(Html.fromHtml("New users will be enrolled as <b>" +
                                values[position] +"</b>"));
                        invitation.setRole(values[position]);

                        try {
                            invitation.save();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        alertD.cancel();
                    }
                });
                Button btnCancel = (Button) promptView.findViewById(R.id.button_custom_menu_dialog_cancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertD.dismiss();
                    }
                });
                alertD.setView(promptView);
                alertD.show();
            }
        });
    }




}