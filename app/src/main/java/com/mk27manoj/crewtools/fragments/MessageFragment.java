package com.mk27manoj.crewtools.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVFile;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobEntry;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.MessageAdapter;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love (thechrislove@icloud.com) on 10-24-2016.
 */
public class MessageFragment extends Fragment {
    private static final String ARG_JOB_ENTRY_ID = "arg_job_entry_id";
    private static final int CAMERA_REQUEST = 11111;
    private static final int SELECT_PICTURE = 22222;
    private Context mContext;
    private View parentView;
    private ListView mMessageListView;
    private ImageView imgCameraGallery, mImgCapture;
    private ArrayList<String> messages;
    private TextView txtEdit;
    private String mJobEntryId;
    private EditText mEditTextEntryMessage;
    List<CVJobEntry> jobEntryList;

    private CVJobEntry mNewJobEntry;
    private CVFile mMessageImage;
    private CVJob mJob;
    private MessageAdapter adapter;
    private static final String TAG = "MessageFragment";

    public static MessageFragment newInstanceOf(String jobEntryId) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JOB_ENTRY_ID, jobEntryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_message, container, false);

        return parentView;
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_create_job_top);
        TextView txtEdit = (TextView) toolbar.findViewById(R.id.textview_toolbar_create_job_edit_done);
        txtEdit.setVisibility(View.GONE);
        mEditTextEntryMessage = (EditText) parentView.findViewById(R.id.editText_add_job_entry_message);
        mImgCapture = (ImageView) parentView.findViewById(R.id.image_view_message_capture_send);


        try {
            if (getArguments() != null) {
                mJobEntryId = getArguments().getString(ARG_JOB_ENTRY_ID);
                for (CVJob cvJob : ParseQuery.getQuery(CVJob.class).whereEqualTo("objectId", mJobEntryId).find()) {
                    cvJob.fetch();
                    mJob = cvJob;
                    jobEntryList = ParseQuery.getQuery(CVJobEntry.class).whereEqualTo("job", cvJob).find();
                }

                mContext = getActivity();
                mMessageListView = (ListView) parentView.findViewById(R.id.listview_message_chat_list);
                imgCameraGallery = (ImageView) parentView.findViewById(R.id.imageview_message_capture_image);

                adapter = new MessageAdapter(mContext, jobEntryList);

                mMessageListView.setAdapter(adapter);

                mImgCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            if (!mEditTextEntryMessage.getText().toString().equals("") ||
                                    !mEditTextEntryMessage.getText().toString().equals(null)) {
                                CVJobEntry jobEntry = new CVJobEntry();
                                CVEmployee currentEmployees = ParseQuery.getQuery(CVEmployee.class)
                                        .whereEqualTo("user", ParseUser.getCurrentUser()).find().get(0);
                                jobEntry.setNotes(mEditTextEntryMessage.getText().toString());
                                jobEntry.setCreatedBy(currentEmployees);
                                jobEntry.setJob(mJob);
                                if (mMessageImage != null) {
                                    jobEntry.setFile(mMessageImage);
                                }
                                jobEntry.saveEventually(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.d(TAG, "done() called with: " + "e = [" + e + "]");
                                            mEditTextEntryMessage.setText("");
                                            mMessageImage = null;
                                        } else {
                                            Log.e(TAG, "done: ", e);

                                            Toast.makeText(getActivity(), "The Message was not sent", Toast.LENGTH_LONG).show();
                                            mEditTextEntryMessage.setText("");
                                        }
                                    }
                                });
                                jobEntryList.add(jobEntry);
                                adapter.notifyDataSetChanged();

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setListeners() {
        imgCameraGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new AlertDialog.Builder(mContext)
                        .setTitle("Add Photo")
                        .setNegativeButton("Cancel", null)
                        .setItems(new String[]{"Take Picture", "Existing Picture"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dlg, int position) {
                                if (position == 0) {
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                } else if (position == 1) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent,
                                            "Select Picture"), SELECT_PICTURE);
                                }
                            }
                        })
                        .create();
                d.show();

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: " + "requestCode = ["
                + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");

                savePhotoFile(photo);


            }else if (requestCode == SELECT_PICTURE && resultCode == getActivity().RESULT_OK) {

               Uri photoUri = data.getData();
                Bitmap photo = getBitmapFromUri(photoUri);
                savePhotoFile(photo);
            }
        } catch (Exception e) {
            Log.e(TAG, "onActivityResult: ",e );
            Toast toast = Toast.makeText(getActivity(), "The image is NOT saved\n"  + e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }

    }

    private void savePhotoFile(Bitmap photo) throws ParseException {
        Log.d(TAG, "savePhotoFile() called with: " + "photo = [" + photo + "]");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        setBytestoParseFile(imageBytes);
    }

    private void setBytestoParseFile(byte[] imageBytes) throws ParseException {
        Log.d(TAG, "setBytestoParseFile() called with: " + "imageBytes = [" + imageBytes + "]");
        mMessageImage = new CVFile();
        mMessageImage.setFile(new ParseFile(imageBytes));
        for (CVEmployee cvEmployee : ParseQuery.getQuery(CVEmployee.class)
                .whereEqualTo("user", CVUser.getCurrentUser()).find()) {
            cvEmployee.getCompany().fetch();
            mMessageImage.put("createdBy", cvEmployee);
            mMessageImage.setCompany(cvEmployee.getCompany());
        }


        mMessageImage.setFilesize(imageBytes.length);
        mMessageImage.pinInBackground();
        Log.i(TAG, "setBytestoParseFile: " + mMessageImage);
        mMessageImage.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast toast = Toast.makeText(getActivity(), "The image is saved", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    Log.e(TAG, "done: ", e);
                    Toast toast = Toast.makeText(getActivity(), "The image is NOT saved\n" + e.getMessage(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 0);
                    toast.show();
                    mMessageImage = null;
                }
            }
        });
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        setListeners();
    }
}
