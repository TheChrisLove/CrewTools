package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-25.
 */
public class PlacePickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgAdd, imgCancel;
    private LinearLayout placeContainer;
    private List<CVAddress> addresses;
    private static final String TAG = "PlacePickerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        initViews();

    }

    private void initViews() {
        try {
            mContext = PlacePickerActivity.this;
            mToolbar = (Toolbar) findViewById(R.id.toolbar_place_picker_top);
            txtTitle = (TextView) findViewById(R.id.textview_toolbar_place_picker_title);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            txtTitle.setText("CHOOSE ADDRESS");

            placeContainer = (LinearLayout) findViewById(R.id.linearlayout_place_picker_places_container);

            if (ParseQuery.getQuery(CVEmployee.class)
                    .whereEqualTo("user", ParseUser.getCurrentUser()).find() != null) {
                for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class)
                        .whereEqualTo("user", ParseUser.getCurrentUser()).find()) {
                    Log.i(TAG, "initViews: " + cvEmployee.toString());
                    cvEmployee.fetch();
                    addresses = ParseQuery.getQuery(CVAddress.class)
                            .whereEqualTo("company", cvEmployee.getCompany()).find();
                    Log.i(TAG, "initViews: " + addresses);
                }
            }

            //addresses.add("Home@@123 New Avenue@@NY");
            //addresses.add("Office@@2962 Richlandtown Pike Coopersburg@@DC");
            //addresses.add("Restaurant@@12B Park street@@Cal");

            if (addresses != null) {
                for (CVAddress address : addresses) {
                    addView();
                }
            }



            imgAdd = (ImageView) findViewById(R.id.imageview_toolbar_place_picker_add);
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, AddPlaceActivity.class));
                }
            });
            imgCancel = (ImageView) findViewById(R.id.imageview_place_picker_menu_close);
            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
//        placeContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = "2962 Richlandtown Pike Coopersburg, PA";
//                Intent intent = new Intent();
//                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, message);
//                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE, intent);
//                finish();//finishing activity
//            }
//        });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addView() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_place_picker, null);
        TextView txtTag = (TextView) baseView.findViewById(R.id.textview_row_place_picker_place_tag);
        TextView txtAdd1 = (TextView) baseView.findViewById(R.id.textview_row_place_picker_add1);
        TextView txtAdd2 = (TextView) baseView.findViewById(R.id.textview_row_place_picker_add2);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_place_picker_selected_place);
        if (placeContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        baseView.setTag(placeContainer.getChildCount());

        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                for (int j = 0; j < placeContainer.getChildCount(); j++) {
                    if (j == count) {
                        placeContainer.getChildAt(j).findViewById(R.id.imageview_row_place_picker_selected_place).setVisibility(View.VISIBLE);
                    } else {
                        placeContainer.getChildAt(j).findViewById(R.id.imageview_row_place_picker_selected_place).setVisibility(View.INVISIBLE);
                    }
                }

                CVAddress address = addresses.get(count);
                String message = address.getAddress1() + "\n" + address.getCity() + ", "
                        + address.getState() + " " + address.getZip();
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_ADDRESS, address.getObjectId());
                intent.putExtra(CrewToolsConstants.RESPONCE_CITY, address.getCity());
                intent.putExtra(CrewToolsConstants.RESPONCE_STATE, address.getState());
                intent.putExtra(CrewToolsConstants.RESPONCE_ZIP, address.getZip());
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, message);
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_PLACE, intent);
                finish();//finishing activity
            }
        });
        CVAddress data = addresses.get(placeContainer.getChildCount());
        txtTag.setText(data.getAddress1());
        txtAdd1.setText(data.getCity());
        txtAdd2.setText(data.getState() + ", " + data.getZip());

        placeContainer.addView(baseView);
    }

}
