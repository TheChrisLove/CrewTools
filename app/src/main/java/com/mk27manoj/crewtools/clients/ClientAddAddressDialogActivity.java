package com.mk27manoj.crewtools.clients;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.mk27manoj.crewtools.R;

/**
 * Created by gyasistory on 6/30/2016.
 */
public class ClientAddAddressDialogActivity extends AppCompatActivity {

    public static final String NAME_TAG = "name";
    public static final String PRIMARY_TAG = "primary";
    public static final String ADDRESS1_TAG = "address1";
    public static final String ADDRESS2_TAG = "address2";
    public static final String CITY_TAG = "city";
    public static final String STATE_TAG = "state";
    public static final String ZIP_TAG = "zip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_dialog_view);

        setTitle(R.string.add_address);


    }

    public void cancelAddAddressClick(View view) {
        finish();
    }

    public void okAddAddressClick(View view) {
        EditText name = (EditText) findViewById(R.id.editText_add_address_name);
        Switch primary = (Switch) findViewById(R.id.switchPrimary);
        EditText address1 = (EditText) findViewById(R.id.editText_add_address1);
        EditText address2 = (EditText) findViewById(R.id.editText_add_address2);
        EditText city = (EditText) findViewById(R.id.editText_add_address_city);
        EditText state = (EditText) findViewById(R.id.editText_add_address_state);
        EditText zip = (EditText) findViewById(R.id.editText_add_address_zip);

        Intent intent = new Intent();

        intent.putExtra(NAME_TAG, name != null ? name.getText().toString() : "")
                .putExtra(PRIMARY_TAG, primary != null ? primary.isChecked() : false)
                .putExtra(ADDRESS1_TAG, address1 != null ? address1.getText().toString() : "")
                .putExtra(ADDRESS2_TAG, address2 != null ? address2.getText().toString() : "")
                .putExtra(CITY_TAG, city != null ? city.getText().toString() : "")
                .putExtra(STATE_TAG, state != null ? state.getText().toString() : "")
        .putExtra(ZIP_TAG, zip != null ? zip.getText().toString() : "");


        setResult(RESULT_OK, intent);
        finish();
    }
}
