package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobItem;
import com.mk27manoj.crewtools.ParseSubClasses.CVService;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.jobs.add_jobs.JobItemDialogFragment;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */

public class ServicePickerActivity extends AppCompatActivity implements JobItemDialogFragment.JobItemDialogListener{
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private TextView txtEdit;
    private ImageView imgCancel;
    private LinearLayout serviceContainer;
    private RelativeLayout relativeAddService;
    private List<CVService> services = null;
    private boolean isEditable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_picker);
        try {
            initViews();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onRestart(){
        super.onRestart();
        
        startActivity(new Intent(ServicePickerActivity.this, ServicePickerActivity.class));
        Intent intent = new Intent();
        if(serviceContainer.getChildCount() > 0){
            intent.putExtra(CrewToolsConstants.RESPONCE_SERVICE_ID, services.get(serviceContainer.getChildCount()).getObjectId());
            intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, services.get(serviceContainer.getChildCount()).getName());
            setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_SERVICE, intent);
        }
        finish();
   }

    private void initViews() throws ParseException {
        mContext = ServicePickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_service_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_service_picker_title);

        txtTitle.setText("CHOOSE SERVICE");

        serviceContainer = (LinearLayout) findViewById(R.id.linearlayout_service_picker_type_container);

        services = new ArrayList<>();

        for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class).whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
            services = ParseQuery.getQuery(CVService.class).whereEqualTo("company", cvEmployee.getCompany()).find();
        }

        for (int i = 0; i < services.size(); i++) {
            addView();
        }

        txtEdit = (TextView) findViewById(R.id.textview_toolbar_service_picker_edit);
        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_service_picker_menu_close);

        relativeAddService = (RelativeLayout) findViewById(R.id.relativelayout_service_picker_add_new_service);
        relativeAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.invalidate();
                startActivity(new Intent(mContext, ServiceEditorActivity.class));
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtEdit.getText().toString().equalsIgnoreCase("Edit")) {
                    txtEdit.setText("Done");
                    for (int i = 0; i < serviceContainer.getChildCount(); i++) {
                        ImageView img = (ImageView) serviceContainer.getChildAt(i).findViewById(R.id.imageview_service_picker_add_remove);
                        img.setVisibility(View.VISIBLE);
                    }
                    relativeAddService.setVisibility(View.VISIBLE);
                } else {
                    txtEdit.setText("Edit");
                    for (int i = 0; i < serviceContainer.getChildCount(); i++) {
                        ImageView img = (ImageView) serviceContainer.getChildAt(i).findViewById(R.id.imageview_service_picker_add_remove);
                        img.setVisibility(View.GONE);
                    }
                    relativeAddService.setVisibility(View.GONE);
                }
            }
        });

//        serviceContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = "Landscaping";
//                Intent intent = new Intent();
//                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, message);
//                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_SERVICE, intent);
//                finish();
//            }
//        });
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_service_picker, null);
        TextView txtService = (TextView) baseView.findViewById(R.id.textview_service_picker_name);
        ImageView imgRemove = (ImageView) baseView.findViewById(R.id.imageview_service_picker_add_remove);
        baseView.setTag(serviceContainer.getChildCount());
        imgRemove.setTag(serviceContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.invalidate();
                int count = Integer.parseInt(v.getTag().toString().trim());

                JobItemDialogFragment fragment = JobItemDialogFragment
                        .newInstanceOf(services.get(count).getObjectId());
                fragment.show(getSupportFragmentManager(), "jobItems");

            }
        });
        txtService.setText(services.get(serviceContainer.getChildCount()).getName());
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int index = Integer.parseInt(v.getTag().toString().trim());
                    ((LinearLayout) baseView.getParent()).removeView(baseView);
                    if(index == services.size()){
                        Log.e("Here", "Index is: " + index);
                        services.get(index).delete();
                        services.remove(index);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        serviceContainer.addView(baseView);
    }

    @Override
    public void JobItemClick(CVJobItem jobItem) {
        Intent intent = new Intent();
        intent.putExtra(CrewToolsConstants.RESPONCE_SERVICE_ID, jobItem.getObjectId());
        intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, jobItem.getName());
        setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_SERVICE, intent);
        finish();//finishing activity
    }
}