package com.mk27manoj.crewtools.clients;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmailAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVPhoneNumber;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class AddClientActivity extends AppCompatActivity {

    private static final int EMAIL_REQUEST_CODE = 22222;
    private static final int PHONE_REQUEST_CODE = 33333;
    private ImageView imgCancel;
    private CVClient mClient;
    private ArrayList<CVAddress> addresses;
    private ArrayList<CVEmailAddress> emailAddresses;
    private ArrayList<CVPhoneNumber> phoneNumbers;
    private static final String TAG = "AddClientActivity";
    private final int ADDRESS_REQUEST_CODE = 11111;
    private CVCompany cvCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_client);

        setSupportActionBar(toolbar);

        addresses = new ArrayList<>();
        emailAddresses = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
        
        imgCancel = (ImageView) AddClientActivity.this.findViewById(R.id.imageview_add_client_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddClientActivity.this.finish();
            }
        });

        if (((TextView) findViewById(R.id.textview_toolbar_add_client_title)) != null) {
            ((TextView) findViewById(R.id.textview_toolbar_add_client_title)).setText("Add New Client");
        }

        mClient = new CVClient();

        if (savedInstanceState != null) {
            addresses = (ArrayList<CVAddress>) savedInstanceState.getSerializable("addresses");
            emailAddresses = (ArrayList<CVEmailAddress>) savedInstanceState.getSerializable("emails");
            phoneNumbers = (ArrayList<CVPhoneNumber>) savedInstanceState.getSerializable("phones");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable("emails", emailAddresses);
        outState.putSerializable("addresses", addresses);
        outState.putSerializable("phones", phoneNumbers);

        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void addAddressClick(View view) {
        Intent intent = new Intent(this, ClientAddAddressDialogActivity.class);
        startActivityForResult(intent, ADDRESS_REQUEST_CODE);
    }

    public void addEmailClick(View view) {
        Intent intent = new Intent(this, ClientAddEmailDialogActivity.class);
        startActivityForResult(intent, EMAIL_REQUEST_CODE);
    }

    public void addPhoneClick(View view) {
        Intent intent = new Intent(this, ClientAddPhoneDialogActivity.class);
        startActivityForResult(intent, PHONE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADDRESS_REQUEST_CODE) {
                CVAddress address = new CVAddress();
                address.setName(data.getStringExtra(ClientAddAddressDialogActivity.NAME_TAG));
                address.setPrimary(data.getBooleanExtra(ClientAddAddressDialogActivity.PRIMARY_TAG, false));
                address.setAddress1(data.getStringExtra(ClientAddAddressDialogActivity.ADDRESS1_TAG));
                address.setAddress2(data.getStringExtra(ClientAddAddressDialogActivity.ADDRESS2_TAG));
                address.setZip(data.getStringExtra(ClientAddAddressDialogActivity.ZIP_TAG));
                address.setClient(mClient);
                address.setCity(data.getStringExtra(ClientAddAddressDialogActivity.CITY_TAG));
                address.setState(data.getStringExtra(ClientAddAddressDialogActivity.STATE_TAG));

                addresses.add(address);
                ListView listView = (ListView) findViewById(R.id.listview_add_client_address);
                ClientViewAdapter adapter = new ClientViewAdapter(this, addresses, null, null);
                if (listView != null) {
                    listView.setAdapter(adapter);
                }

            } else if (requestCode == EMAIL_REQUEST_CODE) {
                CVEmailAddress emailAddress = new CVEmailAddress();
                emailAddress.setPrimary(data.getBooleanExtra(ClientAddEmailDialogActivity.PRIMARY_TAG, false));
                emailAddress.setName(data.getStringExtra(ClientAddEmailDialogActivity.NAME_TAG));
                emailAddress.setEmail(data.getStringExtra(ClientAddEmailDialogActivity.EMAIL_TAG));
                emailAddress.setClient(mClient);
                emailAddresses.add(emailAddress);
                ListView listView = (ListView) findViewById(R.id.listview_add_client_email);
                ClientViewAdapter adapter = new ClientViewAdapter(this, null, emailAddresses, null);
                if (listView != null) {
                    listView.setAdapter(adapter);
                }
            } else if (requestCode == PHONE_REQUEST_CODE) {
                CVPhoneNumber cvPhoneNumber = new CVPhoneNumber();
                cvPhoneNumber.setName(data.getStringExtra(ClientAddPhoneDialogActivity.NAME_TAG));
                cvPhoneNumber.setNumber(data.getStringExtra(ClientAddPhoneDialogActivity.NAME_TAG));
                cvPhoneNumber.setPrimary(data.getBooleanExtra(ClientAddPhoneDialogActivity.PRIMARY_TAG, false));
                cvPhoneNumber.setClient(mClient);

                phoneNumbers.add(cvPhoneNumber);
                ListView listView = (ListView) findViewById(R.id.listview_add_client_phone);
                ClientViewAdapter adapter = new ClientViewAdapter(this, null, null, phoneNumbers);
                if (listView != null) {
                    listView.setAdapter(adapter);
                }
            }
        }
    }

    public void saveChangesClick(View view) throws ParseException {
        ParseQuery<CVEmployee> clientParseQuery = ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", ParseUser.getCurrentUser());
        clientParseQuery.findInBackground(new FindCallback<CVEmployee>() {
            @Override
            public void done(List<CVEmployee> objects, ParseException e) {
                if (e == null) {
                    for (CVEmployee object : objects) {
                        cvCompany = object.getCompany();
                        EditText name = (EditText) findViewById(R.id.editText_add_client_name);
                        EditText company = (EditText) findViewById(R.id.editText_add_client_company);
                        EditText email = (EditText) findViewById(R.id.editText_add_client_email);
                        EditText phone = (EditText) findViewById(R.id.editText_add_client_phone);
                        Spinner contactBy = (Spinner) findViewById(R.id.spinner_add_client_contact_by);
                        EditText note = (EditText) findViewById(R.id.editText_add_client_note);

                        if(name.getText().toString().equals("")){
                            Toast.makeText(AddClientActivity.this, "Oops! Please enter client name.", Toast.LENGTH_LONG).show();
                        } else {
                            mClient.setName(name != null ? name.getText().toString() : "");
                            mClient.setBusiness(company != null ? company.getText().toString() : "");
                            mClient.setEmail(email != null ? email.getText().toString() : "");
                            mClient.setPhone(phone != null ? phone.getText().toString() : "");
                            mClient.setContactMethod(contactBy != null ? contactBy.getSelectedItem().toString() : "phone");
                            mClient.setNotes(note != null ? note.getText().toString() : "");
                            mClient.setCompany(cvCompany);
                            mClient.setACL(new ParseACL());
                            mClient.getACL().setReadAccess(CVUser.getCurrentUser(), true);
                            mClient.getACL().setWriteAccess(CVUser.getCurrentUser(), true);
                            try {
                                for (ParseRole role : ParseQuery.getQuery(ParseRole.class).whereContains("name", cvCompany.getObjectId()).find()) {
                                    mClient.getACL().setRoleWriteAccess(role, true);
                                    mClient.getACL().setRoleReadAccess(role, true);
                                }
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            mClient.saveEventually(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        for (CVAddress address : addresses) {
                                            address.setCompany(cvCompany);
                                            address.saveEventually();
                                        }

                                        for (CVEmailAddress emailAddress : emailAddresses) {
                                            emailAddress.setCompany(cvCompany);
                                            emailAddress.saveEventually();
                                        }

                                        for (CVPhoneNumber phoneNumber : phoneNumbers) {
                                            phoneNumber.setCompany(cvCompany);
                                            phoneNumber.saveEventually();
                                        }
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}