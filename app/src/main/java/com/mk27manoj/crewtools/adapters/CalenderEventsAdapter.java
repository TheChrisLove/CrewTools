package com.mk27manoj.crewtools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVTask;
import com.mk27manoj.crewtools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class CalenderEventsAdapter extends BaseAdapter {
    private Context mContext;
    private List<CVTask> mList;
    private ViewHolder holder;

    public CalenderEventsAdapter(Context context, List<CVTask> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row_calender_event, parent, false);
            holder.txtEventHeading = (TextView) convertView.findViewById(R.id.textview_row_calender_heading);
            holder.txtEventDescription = (TextView) convertView.findViewById(R.id.textview_row_calender_description);
            holder.txtEventTiming = (TextView) convertView.findViewById(R.id.textview_row_calender_event_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CVTask data = mList.get(position);

        holder.txtEventHeading.setText(data.getName());
        if (data.has("location")) {
            if (data.getLocation().has("city")  && data.getLocation().has("state"))
            holder.txtEventDescription.setText(data.getLocation().getCity() + ", " + data.getLocation().getState());
        }
       if (data.getAllDay()) {
           holder.txtEventTiming.setText(R.string.all_day);
       }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtEventHeading, txtEventDescription, txtEventTiming;
    }
}