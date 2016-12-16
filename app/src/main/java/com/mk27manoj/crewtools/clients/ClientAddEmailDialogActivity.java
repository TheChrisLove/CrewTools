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
 * Renovated by The Chris Love on 10/24/2016.
 */
public class ClientAddEmailDialogActivity extends AppCompatActivity {
    public static final String PRIMARY_TAG = "primary";
    public static final String NAME_TAG = "name";
    public static final String EMAIL_TAG = "email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_dialog_view);
    }

    public void cancelEmailClick(View view) {
        finish();
    }

    public void AddEmailClick(View view) {

        EditText name = (EditText) findViewById(R.id.editText_add_email_name);
        Switch primary = (Switch) findViewById(R.id.switchPrimary);
        EditText email = (EditText) findViewById(R.id.editText_add_email);

        Intent intent = new Intent();

        intent.putExtra(NAME_TAG, name != null ? name.getText().toString() : "")
                .putExtra(PRIMARY_TAG, primary != null && primary.isChecked())
                .putExtra(EMAIL_TAG, email != null ? email.getText().toString() : "");

        setResult(RESULT_OK, intent);
        finish();
    }
}


