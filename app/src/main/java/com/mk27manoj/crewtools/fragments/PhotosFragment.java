package com.mk27manoj.crewtools.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk27manoj.crewtools.FullScreenPhotoActivity;
import com.mk27manoj.crewtools.ParseSubClasses.CVFile;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.PhotoEditorActivity;
import com.mk27manoj.crewtools.R;
import com.mk27manoj.crewtools.adapters.PhotosAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love  on 2016-07-07.
 */
public class PhotosFragment extends Fragment {
    private static final String ARG_JOB_ID = "job_id";
    private Context mContext;
    private View parentView;
    private GridView gridBefore, gridInMessage;
    private ArrayList<String> photosBefore, photosInMessage;
    private ImageView imgAddPhotos;
    private List<CVFile> mBeforeList, mInMessageList;
    private String mJobId;
    private CVJob mMainJob;
    private static final String TAG = "PhotosFragment";

    public static PhotosFragment newInstanceOf(String jobId){
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JOB_ID, jobId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: " + "inflater = [" + inflater + "], container = ["
                + container + "], savedInstanceState = [" + savedInstanceState + "]");
        // Inflating view layout
        parentView = inflater.inflate(R.layout.fragment_photos, container, false);
        initViews();
        setListeners();
        return parentView;
    }

    private void initViews() {
        mContext = getActivity();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_create_job_top);
        TextView txtEditDone = (TextView) toolbar.findViewById(R.id.textview_toolbar_create_job_edit_done);
        imgAddPhotos = (ImageView) toolbar.findViewById(R.id.imageview_toolbar_create_job_add);
        imgAddPhotos.setVisibility(View.VISIBLE);
        imgAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, PhotoEditorActivity.class));
            }
        });
        txtEditDone.setVisibility(View.GONE);

        photosBefore = new ArrayList<>();
        photosBefore.add("uri-1");

        photosInMessage = new ArrayList<>();
        photosInMessage.add("uri-1");
        photosInMessage.add("uri-2");

        gridBefore = (GridView) parentView.findViewById(R.id.gridview_photos_before_grid);
        //gridInMessage = (GridView) parentView.findViewById(R.id.gridview_photos_in_message_grid);





    }

    private void setListeners() {

        gridBefore.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView image = (ImageView) view.findViewById(R.id.imageview_photos_checked);
                if (image.getVisibility() == View.VISIBLE) {
                    image.setVisibility(View.GONE);
                } else {
                    image.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        gridBefore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, FullScreenPhotoActivity.class);
                intent.putExtra("FILE_URL", mBeforeList.get(position).getFile().getUrl());
                startActivity(intent);
            }
        });
 /*
        gridInMessage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView image = (ImageView) view.findViewById(R.id.imageview_photos_checked);
                if (image.getVisibility() == View.VISIBLE) {
                    image.setVisibility(View.GONE);
                } else {
                    image.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        gridInMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, FullScreenPhotoActivity.class));
            }
        });*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            try {
                mJobId = getArguments().getString(ARG_JOB_ID);
                for (CVJob cvJob : ParseQuery.getQuery(CVJob.class).whereEqualTo("objectId", mJobId).find()) {

                    mMainJob = cvJob;
                }
                ParseQuery.getQuery(CVFile.class).whereEqualTo("company", mMainJob.getCompany()).findInBackground(new FindCallback<CVFile>() {
                    @Override
                    public void done(List<CVFile> objects, ParseException e) {
                        Log.d(TAG, "done() called with: " + "objects = [" + objects + "], e = [" + e + "]");
                        if (e == null){

                            mBeforeList=objects;
                            gridBefore.setAdapter(new PhotosAdapter(mContext, mBeforeList));
                            //gridInMessage.setAdapter(new PhotosAdapter(mContext, fileList));
                        }else {
                            Log.e(TAG, "No list available: ", e);
                        }
                    }
                });

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}