package com.mk27manoj.crewtools.clients;

import android.graphics.PorterDuff;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.jobs.JobsActivity;
import com.mk27manoj.crewtools.fragments.InvoicesFragment;
import com.mk27manoj.crewtools.fragments.JobListFragment;

public class ClientActivity extends AppCompatActivity {
    private Context mContext;
    private boolean isEnable = false;
    private ImageView mContactImageView, mJobsImageView, mInvoiceImageView, mPaymentImageView, imgCancel, imgCreateJob;
    private LinearLayout clientLayout, jobLayout, invoicesLayout, paymentLayout;
    private TextView mContactTextView, mJobsTextView, mInvoiceTextView, mPaymentTextView, txtCancel, txtDone;
    private String mClientObjectId;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mContactImageView = (ImageView) findViewById(R.id.imageview_client_menu_contact);
        mJobsImageView = (ImageView) findViewById(R.id.imageview_client_menu_jobs);
        mInvoiceImageView = (ImageView) findViewById(R.id.imageview_client_menu_invoice);
        mPaymentImageView = (ImageView) findViewById(R.id.imageview_client_menu_payment);

        mContactTextView = (TextView) findViewById(R.id.textview_client_menu_contact);
        mJobsTextView = (TextView) findViewById(R.id.textview_client_menu_job);
        mInvoiceTextView = (TextView) findViewById(R.id.textview_client_menu_invoice);
        mPaymentTextView = (TextView) findViewById(R.id.textview_client_menu_payment);

        mContactTextView.setTextColor(ContextCompat.getColor(this, R.color.buttonFontColor));
        mContactImageView.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_client);
        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra("client");
        if (name != null) {
            if (toolbar != null) {
//                ((TextView) toolbar.findViewById(R.id.textview_toolbar_client_title)).setText(name);
                ((TextView) toolbar.findViewById(R.id.textview_toolbar_client_title)).setText("VIEW CLIENT");

                mClientObjectId = getIntent().getStringExtra("objectId");

                getSupportFragmentManager().beginTransaction().replace(R.id.client_container,
                        ClientContactFragment.newInstanceOf(mClientObjectId), ClientContactFragment.TAG).commit();
            }
        }

        clientLayout = (LinearLayout) findViewById(R.id.linealayout_client_contact);
        if (clientLayout != null) {
            clientLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContactTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.buttonFontColor));
                    mContactImageView.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                    mJobsTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mJobsImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mInvoiceTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mInvoiceImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mPaymentTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mPaymentImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

                    ClientContactFragment fragment = (ClientContactFragment) getSupportFragmentManager()
                            .findFragmentByTag(ClientContactFragment.TAG);
                    if (fragment == null) {
                        fragment = ClientContactFragment.newInstanceOf(mClientObjectId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.client_container, fragment,
                                ClientContactFragment.TAG).commit();
                    } else {
                        fragment.setClientData(mClientObjectId);
                    }
                }
            });
        }

        jobLayout = (LinearLayout) findViewById(R.id.linealayout_client_jobs);
        if (jobLayout != null) {
            jobLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContactTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mContactImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mJobsTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.buttonFontColor));
                    mJobsImageView.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                    mInvoiceTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mInvoiceImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mPaymentTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mPaymentImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    getSupportFragmentManager().beginTransaction().replace(R.id.client_container, new JobListFragment()).commit();
                }
            });
        }

        invoicesLayout = (LinearLayout) findViewById(R.id.linealayout_client_invoices);
        if (invoicesLayout != null) {
            invoicesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContactTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mContactImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mJobsTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mJobsImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mInvoiceTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.buttonFontColor));
                    mInvoiceImageView.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                    mPaymentTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mPaymentImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    getSupportFragmentManager().beginTransaction().replace(R.id.client_container, new InvoicesFragment()).commit();
                }
            });
        }

        paymentLayout = (LinearLayout) findViewById(R.id.linealayout_client_payments);
        if (paymentLayout != null) {
            paymentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContactTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mContactImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mJobsTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mJobsImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mInvoiceTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.fontColorWhite));
                    mInvoiceImageView.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
                    mPaymentTextView.setTextColor(ContextCompat.getColor(ClientActivity.this, R.color.buttonFontColor));
                    mPaymentImageView.setColorFilter(0xFFFF4F27, PorterDuff.Mode.SRC_IN);
                    getSupportFragmentManager().beginTransaction().replace(R.id.client_container, new ClientPaymentFragment()).commit();
                }
            });
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        mContext = this;

        txtDone = (TextView) findViewById(R.id.imageview_toolbar_client_activity);
        imgCancel = (ImageView) findViewById(R.id.imageview_client_menu_close);
        txtCancel = (TextView) findViewById(R.id.textview_toolbar_client_cancel);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtDone.setVisibility(View.VISIBLE);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEnable) {
                    txtDone.setText("Done");
                    imgCancel.setVisibility(View.GONE);
                    txtCancel.setVisibility(View.VISIBLE);
                } else {
                    txtDone.setText("Edit");
                    imgCancel.setVisibility(View.VISIBLE);
                    txtCancel.setVisibility(View.GONE);
                }
//                isEnable = !isEnable;
//                edtName.setEnabled(isEnable);
//                txtType.setEnabled(isEnable);
//                txtClient.setEnabled(isEnable);
//                txtPlace.setEnabled(isEnable);
//                txtOccurance.setEnabled(isEnable);
//                txtPriority.setEnabled(isEnable);
//                txtStartingDate.setEnabled(isEnable);
//                txtCompletionDate.setEnabled(isEnable);
//                txtPayment.setEnabled(isEnable);
//                txtAcceptWithin.setEnabled(isEnable);
//                edtDescription.setEnabled(isEnable);
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        imgCreateJob = (ImageView) findViewById(R.id.imageview_toolbar_client_activity);
//        imgCreateJob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, JobsActivity.class));
//            }
//        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Client Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}