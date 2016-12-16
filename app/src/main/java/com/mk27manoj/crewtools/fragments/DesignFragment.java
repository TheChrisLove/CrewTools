package com.mk27manoj.crewtools.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.utils.ColorPickerDialog;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

/**
 * Renovated by The Chris Love  on 2016-07-07.
 */
public class DesignFragment extends Fragment {
    private static final String ARG_COMPANY_ID = "arg_company_id";
    private Context mContext;
    private Activity activity;
    private View parentView;
    private TextView txtColor;
    private ImageView imgLogo;
    private CVCompany mCompany;
    private static final String TAG = "DesignFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            try {
                for (CVCompany company : ParseQuery.getQuery(CVCompany.class).whereEqualTo("objectId", getArguments().getString(ARG_COMPANY_ID)).find()) {
                    mCompany = company;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_design, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();
        activity = getActivity();
        txtColor = (TextView) parentView.findViewById(R.id.textview_design_color);
        imgLogo = (ImageView) parentView.findViewById(R.id.imageview_design_logo);
    }

    private void setListeners() {
        txtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog(mContext, new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(String key, int color) {

                        Log.d(TAG, "colorChanged() called with: " + "key = [" + key + "], color = [ #" + Integer.toHexString(color) + "]");
                        mCompany.setPrimaryColor(Integer.toHexString(color));
                        txtColor.setBackgroundColor(color);
                        mCompany.saveEventually();
                    }
                }, "", Color.BLACK, Color.WHITE).show();
            }
        });

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mCompany.getPrimaryColor() != null) {
            txtColor.setBackgroundColor(Color.parseColor("#" +mCompany.getPrimaryColor()));
        }

        if (mCompany.getLogo() != null){
            Picasso.with(getActivity()).load(mCompany.getLogo().getUrl()).into(imgLogo);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = mContext.getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("Design Fragment", "path of image from gallery......******************........." + picturePath + "");
                imgLogo.setImageBitmap(thumbnail);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                ParseFile parseFile = new ParseFile(imageBytes);
                //parseFile.saveInBackground();
                mCompany.setLogo(parseFile);
                try {
                    mCompany.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DesignFragment newInstanceOf(String mCompanyId) {

        DesignFragment fragment = new DesignFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY_ID, mCompanyId);
        fragment.setArguments(args);
        return fragment;
    }
}
