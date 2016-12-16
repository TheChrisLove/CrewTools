package com.mk27manoj.crewtools.clients;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.mk27manoj.crewtools.R;

/**
 * Created by gyasistory on 6/30/2016.
 */
public class ClientAddPhoneDialogActivity extends AppCompatActivity{

     static final String NAME_TAG = "name";
     static final String PRIMARY_TAG = "primary";
     static final String NUMBER_TAG = "number";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_dialog_view);
    }


    public void cancelAddPhoneClick(View view) {
        finish();
    }

    public void okAddPhoneClick(View view) {

        EditText name = (EditText) findViewById(R.id.editText_add_phone_name);
        Switch primary = (Switch) findViewById(R.id.switchPrimary);
        EditText  number = (EditText) findViewById(R.id.editText_add_phone_number);
        Intent intent = new Intent();
        intent.putExtra(ClientAddPhoneDialogActivity.NAME_TAG, name != null ? name.getText().toString() : "")
                .putExtra(ClientAddPhoneDialogActivity.PRIMARY_TAG, primary != null && primary.isChecked())
                .putExtra(ClientAddPhoneDialogActivity.NUMBER_TAG, number != null ? number.getText().toString() : "");

        setResult(RESULT_OK, intent);
        finish();



    }
}
