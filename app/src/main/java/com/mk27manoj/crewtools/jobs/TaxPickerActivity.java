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

/**
 * Renovated by The Chris Love on 2016-06-25.
 */
public class TaxPickerActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgAdd, imgCancel;
    private LinearLayout taxContainer;
    private RelativeLayout relativeAddTax;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_picker);
        initViews();

    }

    private void initViews() {
        mContext = TaxPickerActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_tax_picker_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_tax_picker_title);

        txtTitle.setText("CHOOSE TAX RATE");

        imgAdd = (ImageView) findViewById(R.id.imageview_toolbar_tax_picker_add);
        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_tax_picker_menu_close);

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                View promptView = layoutInflater.inflate(R.layout.custom_accept_input_dialog, null);

                final AlertDialog alertD = new AlertDialog.Builder(mContext).create();
                TextView txtTitle = (TextView) promptView.findViewById(R.id.textview_custom_dialog_heading);
                txtTitle.setText("Enter Tax Rate");
                TextView txtMessage = (TextView) promptView.findViewById(R.id.textview_custom_dialog_message);
                txtMessage.setText("Type the new tax rate to add to the table of options");
                final EditText userInput = (EditText) promptView.findViewById(R.id.edittext_custom_dialog_input);
                Button btnAdd = (Button) promptView.findViewById(R.id.button_custom_dialog_add);
                Button btnCancel = (Button) promptView.findViewById(R.id.button_custom_dialog_cancel);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // TODO TOAST
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

        taxContainer = (LinearLayout) findViewById(R.id.linearlayout_tax_picker_type_container);
        taxContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "6";
                Intent intent = new Intent();
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, message);
                setResult(CrewToolsConstants.REQUEST_CREATE_JOB_PICK_TAX, intent);
                finish();//finishing activity
            }
        });
    }
}