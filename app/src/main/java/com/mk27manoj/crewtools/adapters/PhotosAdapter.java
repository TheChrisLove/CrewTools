package com.mk27manoj.crewtools.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mk27manoj.crewtools.ParseSubClasses.CVFile;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love (thechrislove@icloud.com) on 2016-07-09.
 */
public class PhotosAdapter extends BaseAdapter {
    private Context mContext;
    private ViewHolder holder;
    private List<CVFile> imageList;

    public PhotosAdapter(Context context, List<CVFile> list) {
        mContext = context;
        imageList = list;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_square_imageview_item, parent, false);
            holder.imageview = (ImageView) convertView.findViewById(R.id.imageview_photos_picture);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            CVFile file = (CVFile) getItem(position);
            file.fetch();
            Picasso.with(mContext).load(file.getFile().getUrl()).into(holder.imageview);
            //holder.imageview.setImageURI(Uri.parse(file.getFile().getUrl()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView imageview;
    }
}