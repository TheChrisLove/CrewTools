package com.mk27manoj.crewtools.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

import java.util.ArrayList;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class UnitPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private TextView txtEdit, txtCancel;
    private LinearLayout unitContainer;
    private RelativeLayout relativeAddUnit;
    private ArrayList<String> units;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_picker);
        initViews();
    }

    private void initViews() {
        mContext = UnitPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_unit_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_unit_picker_title);

        txtTitle.setText("PICK UNIT");
        unitContainer = (LinearLayout) findViewById(R.id.linearlayout_unit_picker_type_container);
        units = new ArrayList<>();
        units.add("flat");
        units.add("acre");
        units.add("hour");
        units.add("sqft");

        for (int i = 0; i < units.size(); i++) {
            addView(false);
        }

        txtEdit = (TextView) findViewById(R.id.textview_toolbar_unit_picker_edit);
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_unit_picker_menu_close);

        relativeAddUnit = (RelativeLayout) findViewById(R.id.relativelayout_unit_picker_add_new_unit);
        relativeAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.custom_accept_input_dialog, null);

                final AlertDialog alertD = new AlertDialog.Builder(mContext).create();
                TextView txtTitle = (TextView) promptView.findViewById(R.id.textview_custom_dialog_heading);
                txtTitle.setText("Enter unit");
                TextView txtMessage = (TextView) promptView.findViewById(R.id.textview_custom_dialog_message);
                txtMessage.setText("Type a unit to measure below");
                final EditText userInput = (EditText) promptView.findViewById(R.id.edittext_custom_dialog_input);
                Button btnAdd = (Button) promptView.findViewById(R.id.button_custom_dialog_add);
                Button btnCancel = (Button) promptView.findViewById(R.id.button_custom_dialog_cancel);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Add this to list : " + userInput.getText(), Toast.LENGTH_LONG).show();
                        alertD.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertD.dismiss();
                    }
                });
                alertD.setView(promptView);
                alertD.show();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
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
                    for (int i = 0; i < unitContainer.getChildCount(); i++) {
                        ImageView img = (ImageView) unitContainer.getChildAt(i).findViewById(R.id.imageview_row_unit_picker_add_remove);
                        img.setVisibility(View.VISIBLE);
                    }
                    relativeAddUnit.setVisibility(View.VISIBLE);
                } else {
                    txtEdit.setText("Edit");
                    for (int i = 0; i < unitContainer.getChildCount(); i++) {
                        ImageView img = (ImageView) unitContainer.getChildAt(i).findViewById(R.id.imageview_row_unit_picker_add_remove);
                        img.setVisibility(View.GONE);
                    }
                    relativeAddUnit.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addView(boolean edit) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View baseView = layoutInflater.inflate(R.layout.row_unit_picker, null);
        TextView txtType = (TextView) baseView.findViewById(R.id.textview_row_unit_picker_name);
        ImageView imgSelected = (ImageView) baseView.findViewById(R.id.imageview_row_unit_picker_selected);
        if (unitContainer.getChildCount() == 0) {
            imgSelected.setVisibility(View.VISIBLE);
        } else {
            imgSelected.setVisibility(View.GONE);
        }
        ImageView imgRemove = (ImageView) baseView.findViewById(R.id.imageview_row_unit_picker_add_remove);
        baseView.setTag(unitContainer.getChildCount());
        imgRemove.setTag(unitContainer.getChildCount());

        if (edit) {
            imgRemove.setVisibility(View.VISIBLE);
        }
        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(v.getTag().toString().trim());
                for (int j = 0; j < unitContainer.getChildCount(); j++) {
                    if (j == count) {
                        unitContainer.getChildAt(j).findViewById(R.id.imageview_row_unit_picker_selected).setVisibility(View.VISIBLE);
                    } else {
                        unitContainer.getChildAt(j).findViewById(R.id.imageview_row_unit_picker_selected).setVisibility(View.INVISIBLE);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, units.get(count));
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_UNIT, intent);
                finish();//finishing activity
            }
        });
        txtType.setText(units.get(unitContainer.getChildCount()));
        imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(v.getTag().toString().trim());
                ((LinearLayout) baseView.getParent()).removeView(baseView);
            }
        });
        unitContainer.addView(baseView);
    }
}