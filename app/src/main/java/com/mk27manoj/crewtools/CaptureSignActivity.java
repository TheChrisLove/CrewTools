package com.mk27manoj.crewtools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.mk27manoj.crewtools.utils.CrewToolsConstants;

public class CaptureSignActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar mToolbar;
    private TextView txtTitle;
    private ImageView imgCancel;
    private SignaturePad mSignaturePad;
    private Button btnDone;
    private int requestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_contract);
        initViews();
    }

    private void initViews() {
        mContext = CaptureSignActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar_sign_contract_top);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txtTitle = (TextView) findViewById(R.id.textview_toolbar_sign_contract_title);

        if (getIntent().getExtras().getBoolean("IsClient")) {
            txtTitle.setText("CLIENT SIGNATURE");
            requestCode = CrewToolsConstants.REQUEST_CAPTURE_SIGN_CLIENT;
        } else {
            txtTitle.setText("CONTRACTOR SIGNATURE");
            requestCode = CrewToolsConstants.REQUEST_CAPTURE_SIGN_CONTRACTOR;
        }

        btnDone = (Button) findViewById(R.id.button_sign_contract_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // TODO:  Send Signature
                mSignaturePad.getTransparentSignatureBitmap(); // Use this to pass the data
                Log.e("TAG", "This is: " + mSignaturePad.getTransparentSignatureBitmap());
                intent.putExtra(CrewToolsConstants.RESPONCE_MESSAGE, true);
                intent.putExtra("signature", mSignaturePad.getTransparentSignatureBitmap());
                setResult(requestCode, intent);
                finish();//finishing activity
            }
        });
        imgCancel = (ImageView) findViewById(R.id.imageview_toolbar_sign_contract_menu_close);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                //convert the string to byte array
//                byte[] imageAsBytes = Base64.decode(myStringImage.getBytes());
//                //get reference to the image view where you want to display the image
//                ImageView image = (ImageView)this.findViewById(R.id.ImageView);
//                //set the image by decoding the byte array to bitmap
//                image.setImageBitmap(
//                 BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
//                );
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
//                invalidate();
                mSignaturePad.clear();
            }
        });


    }
}
