package com.mk27manoj.crewtools.calendar.add_task;

import android.content.DialogInterface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVTask;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.global_data.DatePickerFragment;
import com.mk27manoj.crewtools.global_data.TimePickerFragment;
import com.mk27manoj.crewtools.jobs.add_jobs.ClientDialogFragment;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.SaveCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DatePickerFragment.DatePickerListener,
        TimePickerFragment.TimePickerListener, ClientDialogFragment.ClientDialogListener,
        LocationDialogPicker.LocationDialogListener {


    private static final String TAG = "AddTaskActivity";
    private Boolean AllDay = true;
    private JSONArray mParseObjects = new JSONArray();
    private CVAddress mAddress = new CVAddress();
    private JSONArray mCVEmployeesJSONArray = new JSONArray();
    private List<CVEmployee> mQueryEmployeeList;
    private ArrayList<CVEmployee> mEmployeeList = new ArrayList<>();
    ArrayAdapter<CVEmployee> adapter;

    Date mDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_task);

        setSupportActionBar(toolbar);

        TextView toolbarTextview = (TextView) findViewById(R.id.textview_toolbar_add_task);
        if (toolbarTextview != null) {
            toolbarTextview.setText(getResources().getString(R.string.add_task));
        }
            
        ParseQuery.getQuery(CVEmployee.class)
                .findInBackground(new FindCallback<CVEmployee>() {
                    @Override
                    public void done(List<CVEmployee> objects, ParseException e) {
                        if (e == null) {
                            mQueryEmployeeList = objects;
                            LinearLayout employeeLayout = (LinearLayout) findViewById(R.id.linearLayout_add_task_employee);

                            for (int i = 0; i < mQueryEmployeeList.size(); i++) {
                                CheckedTextView employeeTextView = (CheckedTextView) LayoutInflater.from(AddTaskActivity.this).inflate(R.layout.layout_checked_text, employeeLayout, false);
                                employeeTextView.setId(i);
                                employeeTextView.setText(mQueryEmployeeList.get(i).toString());
                                if (employeeLayout != null) {
                                    employeeLayout.addView(employeeTextView, i);
                                }
                                employeeTextView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d(TAG, "onClick() called with: " + "v = [" + v.getId() + "]" + ((CheckedTextView) v).isChecked());
                                        if (((CheckedTextView) v).isChecked()) {
                                            mEmployeeList.remove(mQueryEmployeeList.get(v.getId()));
                                            ((CheckedTextView) v).setChecked(false);
                                        } else {
                                            mEmployeeList.add(mQueryEmployeeList.get(v.getId()));
                                            ((CheckedTextView) v).setChecked(true);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });

    }

    public void addTaskLocationClick(View view) {
        Log.d(TAG, "addTaskLocationClick() called with: " + "view = [" + view + "]");
        
        LocationDialogPicker locationDialogPicker = new LocationDialogPicker();
        locationDialogPicker.show(getSupportFragmentManager(), TAG);
    }

    public void addTaskObjectClick(View view) {
        Log.d(TAG, "addTaskObjectClick() called with: " + "view = [" + view + "]");
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String taskType = getString(R.string.select_type_of_task);
        dialog.setTitle(taskType)
                .setIcon(getDrawable(android.R.drawable.ic_menu_info_details))
                .setItems(R.array.task_types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                // Client Selected
                                ClientDialogFragment clientDialogFragment = new ClientDialogFragment();
                                clientDialogFragment.show(getSupportFragmentManager(), TAG);
                                break;
                            case 1:
                                // Job Selected

                                break;
                            case 2:
                                // Invoice Selected

                                break;
                        }
                    }
                }).show();
    }

    public void addTaskDateClick(View view) {
        Log.d(TAG, "addTaskDateClick() called with: " + "view = [" + view + "]");
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), TAG + "_Date");
    }

    public void addTaskTimeClick(View view) {
        Log.d(TAG, "addTaskTimeClick() called with: " + "view = [" + view + "]");
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getFragmentManager(), TAG);
    }


    @Override
    public void timePicked(int hour, int Minute) {
        Log.d(TAG, "timePicked() called with: " + "hour = [" + hour + "], Minute = [" + Minute + "]");
        mDate.setHours(hour);
        mDate.setMinutes(Minute);

        AllDay = false;

        TextView timeTask = (TextView) findViewById(R.id.textView_add_task_time);
        if (timeTask != null) {
            timeTask.setText(hour + ":" + Minute);
        }
    }

    @Override
    public void datePicked(int Month, int Day, int Year) {

        mDate.setDate(Day);
        mDate.setMonth(Month);
        mDate.setYear(Year);

        TextView dateTask = (TextView) findViewById(R.id.textView_add_task_date);
        if (dateTask != null) {
            dateTask.setText(Month + "/" + Day + "/" + Year);
        }
    }

    @Override
    public void ClientSelectedListener(CVClient client) {
        if(mParseObjects != null){
            mParseObjects.put(client);
        }

        TextView clientTextView = (TextView) findViewById(R.id.textView_add_task_for);
        if (clientTextView != null) {
            clientTextView.setText(client.getName());
        }

    }

    @Override
    public void locationSelected(CVAddress address) {

        mAddress = address;

        TextView addTaskPlaceTextView = (TextView) findViewById(R.id.TextView_add_task_place);

        addTaskPlaceTextView.setText(address.toString());

    }

    public void saveChangesClick(View view) {
        if (mParseObjects != null) {
            if (mEmployeeList.size() > 0) {
                ParseQuery.getQuery(ParseRole.class)
                        .whereContains("name", mEmployeeList.get(0).getCompany().getObjectId())
                        .findInBackground(new FindCallback<ParseRole>() {
                            @Override
                            public void done(List<ParseRole> objects, ParseException e) {
                                if (e == null) {
                                    CVTask task = new CVTask();
                                    ParseACL acl = new ParseACL();
                                    task.setACL(acl);
                                    task.getACL().setPublicReadAccess(false);
                                    task.getACL().setPublicWriteAccess(false);
                                    CVUser.getCurrentUser().getACL().setPublicReadAccess(true);
                                    CVUser.getCurrentUser().getACL().setPublicWriteAccess(true);
                                    task.setACL(CVUser.getCurrentUser().getACL());
                                    for (ParseRole object : objects) {
                                        object.getACL().setPublicWriteAccess(true);
                                        object.getACL().setPublicReadAccess(true);
                                        task.setACL(object.getACL());
                                    }

                                    task.setCompany(mEmployeeList.get(0).getCompany());
                                    TextView etNote = (TextView) findViewById(R.id.editText_add_task_note);
                                    EditText etName = (EditText) findViewById(R.id.editText_add_task_name);
                                    if (etNote != null) {
                                        task.setNotes(etNote.getText().toString());
                                    }
                                    if (etName != null) {
                                        task.setName(etName.getText().toString());
                                    }
                                    task.setItems(mParseObjects);
                                    task.setLocation(mAddress);
                                    Spinner prioritySpinner = (Spinner) findViewById(R.id.spinner_add_task_priority);
                                    if (prioritySpinner != null) {
                                        task.setPriority(prioritySpinner.getSelectedItemPosition() + 1);
                                    }
                                    task.setDate(mDate);
                                    task.setAllDay(AllDay);
                                    for (CVEmployee cvEmployee : mEmployeeList) {
                                        mCVEmployeesJSONArray.put(cvEmployee);
                                    }
                                    task.setEmployees(mCVEmployeesJSONArray);
                                    task.saveEventually(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Log.d(TAG, "saveChangesClick:  ");
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        });
            } else {
                // No Employee
                Toast.makeText(AddTaskActivity.this, "No Employee Selected", Toast.LENGTH_LONG).show();
            }
        } else {
            // No Parse Object
            Toast.makeText(AddTaskActivity.this, "Nothing is set for the Task", Toast.LENGTH_SHORT).show();
        }
    }
}

