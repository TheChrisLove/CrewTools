package com.mk27manoj.crewtools.crew;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.AccountInfoActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private static final String ARG_ID = "arg_employee_id";
    private Context mContext;
    private EditText usernameEditText, passwordEditText, nameEditText, titleEditText, emailEditText, phoneEditText;
    private Toolbar mToolbar, mToolbarBottom;
    private LinearLayout linearJobsLayout, linearCalenderLayout, linearAccountLayout;
    private TextView txtJobs, txtClients, txtCalender, txtInvoices, txtAccount, txtToolbarEdit;
    private ImageView imgAdminCheck, imgManagerCheck, imgMembercheck, imgAdminInfo, imgManagerInfo, imgMemberInfo, imgCancel;
    CVEmployee cvEmployee;
    private RelativeLayout relativeCrewmate, relativeAdmin, relativeManager;
    private Button btnSave;
    public static final String TAG = "AccountFragment";
    private AlertDialog alertD;
    private Boolean mIsActive = true;


    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstanceOf(String employeeId){
        AccountFragment fragment = new AccountFragment();

        Bundle args = new Bundle();
        args.putString(ARG_ID, employeeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        if (getArguments() != null){
            String id = getArguments().getString(ARG_ID);
            setEmployeeText(id);
        }

        isDisabledViews(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            List<CVEmployee> currentEmployee = ParseQuery.getQuery(CVEmployee.class)
                    .whereEqualTo("user", ParseUser.getCurrentUser()).find();
            for (CVEmployee employee : currentEmployee) {
                cvEmployee.getUser().fetch();
                employee.fetch();
                if (employee.getRole() == "admin") {
                    txtToolbarEdit.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onResume() called with: " + "admin");
                } else if (cvEmployee.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())){
                    txtToolbarEdit.setVisibility(View.VISIBLE);
                    // TODO
                    Log.d(TAG, "onResume() called with: " + employee.getRole() + " Parse User");
                }else {
                    txtToolbarEdit.setVisibility(View.GONE);
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    private void setEmployeeText(String id) {
        try {
            List<CVEmployee> employees = ParseQuery.getQuery(CVEmployee.class)
                    .whereEqualTo("objectId", id)
                    .find();

            for (CVEmployee employee : employees) {
                cvEmployee = employee;
                usernameEditText = (EditText) getActivity().findViewById(R.id.editText_my_account_username);
                cvEmployee.getUser().fetch();
                usernameEditText.setText(employee.getUser().getUsername());
                passwordEditText = (EditText) getActivity().findViewById(R.id.editText_my_account_password);
                passwordEditText.setText(employee.getUser().getString("password"));
                nameEditText = (EditText) getActivity().findViewById(R.id.editText_my_account_name);
                nameEditText.setText(employee.getUser().getString("name"));
                titleEditText = (EditText) getActivity().findViewById(R.id.editText_my_account__title);
                titleEditText.setText(employee.getTitle());
                emailEditText = (EditText) getActivity().findViewById(R.id.editText_my_account_email);
                emailEditText.setText(employee.getUser().getEmail());
                phoneEditText = (EditText) getActivity().findViewById(R.id.editText_my_account_phone);
                phoneEditText.setText(employee.getPhone());
                imgMembercheck = (ImageView) getActivity().findViewById(R.id.imageview_my_account_crewmate_selected);
                imgManagerCheck = (ImageView) getActivity().findViewById(R.id.imageview_my_account_manager_selected);
                imgAdminCheck = (ImageView) getActivity().findViewById(R.id.imageview_my_account_admin_selected);
                setRoleImage();
            }
            isDisabledViews(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setRoleImage() {
        if(cvEmployee.getRole() != null) {
            switch (cvEmployee.getRole()) {
                case "member":
                    imgMembercheck.setVisibility(View.VISIBLE);
                    imgManagerCheck.setVisibility(View.INVISIBLE);
                    imgAdminCheck.setVisibility(View.INVISIBLE);
                    break;
                case "manager":
                    imgMembercheck.setVisibility(View.INVISIBLE);
                    imgManagerCheck.setVisibility(View.VISIBLE);
                    imgAdminCheck.setVisibility(View.INVISIBLE);
                    break;
                case "admin":
                    imgMembercheck.setVisibility(View.INVISIBLE);
                    imgManagerCheck.setVisibility(View.INVISIBLE);
                    imgAdminCheck.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    private void initViews() {
        txtToolbarEdit = (TextView) getActivity().findViewById(R.id.textview_toolbar_my_account_edit);
        txtToolbarEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mIsActive){
                   mIsActive = false;
                   txtToolbarEdit.setText(R.string.edit);
                   isDisabledViews(mIsActive);
               } else {
                   //  Toast Message
                   mIsActive = true;
                   txtToolbarEdit.setText(R.string.done);
                   isDisabledViews(mIsActive);
               }
           }
        });

        imgCancel = (ImageView) getActivity().findViewById(R.id.imageview_my_account_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { 
                if(isResumed()) { getActivity().finish(); }
            }
        });

        relativeCrewmate = (RelativeLayout) getActivity().findViewById(R.id.relativelayout_my_account_crewmate);
        relativeManager = (RelativeLayout) getActivity().findViewById(R.id.relativelayout_my_account_manager);
        relativeAdmin = (RelativeLayout) getActivity().findViewById(R.id.relativelayout_my_account_admin);

        relativeCrewmate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CVEmployee currentEmployee = null;
                try {
                    for (CVEmployee employee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                        currentEmployee = employee;
                        currentEmployee.fetch();
                    }
                    if(currentEmployee != null) {
                        if (!mIsActive || !currentEmployee.getRole().equals("admin")) {
                            startActivity(new Intent(getActivity(), AccountInfoActivity.class).putExtra("ROLE", "CREW"));
                        } else {
                            cvEmployee.setRole("member");
                            setRoleImage();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        relativeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CVEmployee currentEmployee = null;
                try {
                    for (CVEmployee employee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                        currentEmployee = employee;
                        currentEmployee.fetch();
                    }
                    if(currentEmployee != null) {
                        if (!mIsActive || !currentEmployee.getRole().equals("admin")) {
                            startActivity(new Intent(getActivity(), AccountInfoActivity.class).putExtra("ROLE", "MANAGER"));
                        } else {
                            cvEmployee.setRole("manager");
                            setRoleImage();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        relativeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CVEmployee currentEmployee = null;
                try {
                    for (CVEmployee employee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                        currentEmployee = employee;
                        currentEmployee.fetch();
                    }
                    if(currentEmployee != null) {
                        if (!mIsActive || !currentEmployee.getRole().equals("admin")) {
                            startActivity(new Intent(getActivity(), AccountInfoActivity.class).putExtra("ROLE", "ADMIN"));
                        } else {
                            cvEmployee.setRole("admin");
                            setRoleImage();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSave = (Button) getActivity().findViewById(R.id.button_my_account_save);
    }

    private void isDisabledViews(Boolean isActive){
        mIsActive = isActive;
        if(usernameEditText != null) usernameEditText.setEnabled(isActive);
        if(passwordEditText != null) passwordEditText.setEnabled(isActive);
        if(nameEditText != null) nameEditText.setEnabled(isActive);
        if(titleEditText != null) titleEditText.setEnabled(isActive);
        if(emailEditText != null) emailEditText.setEnabled(isActive);
        if(phoneEditText != null) phoneEditText.setEnabled(isActive);

        if (isActive){
            btnSave.setText(R.string.save_changes);
        } else {
            btnSave.setText(R.string.call_now);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              TODO  Snackbar.make(v, "The mIsActive = " + mIsActive, Snackbar.LENGTH_LONG).show();
                if (mIsActive) {
                    try {
                        cvEmployee.fetch();
                        cvEmployee.getUser().fetch();
                        cvEmployee.getUser().setUsername(usernameEditText.getText().toString());
                        if (!passwordEditText.getText().toString().equals(null) || !passwordEditText.getText().toString().equals("")){
                            cvEmployee.getUser().setPassword(passwordEditText.getText().toString());
                        }
                        cvEmployee.getUser().put("name", nameEditText.getText().toString());
                        cvEmployee.getUser().setEmail(emailEditText.getText().toString());
                        cvEmployee.getUser().saveEventually();
                        cvEmployee.setTitle(titleEditText.getText().toString());
                        cvEmployee.setPhone(phoneEditText.getText().toString());
                        cvEmployee.saveEventually();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:4075959530"));
                    if (callIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(callIntent);
                    }
                }
            }
        });

    }
}
