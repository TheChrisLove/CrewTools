package com.mk27manoj.crewtools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class JobsAdapter extends BaseAdapter {
    private Context mContext;
    private List<CVJob> mList;
    private ViewHolder holder;

    public JobsAdapter(Context context, List<CVJob> list) {
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
        try {
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.row_job_item, parent, false);
                holder.txtJobsHeading = (TextView) convertView.findViewById(R.id.textview_row_job_heading);
                holder.txtJobsDescription = (TextView) convertView.findViewById(R.id.textview_row_job_description);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CVJob cvJob = (CVJob) getItem(position);

            cvJob.fetch();

            holder.txtJobsHeading.setText(cvJob.getName());
            if (cvJob.has("client")) {
                if (cvJob.getClient().has("name")) {
                    cvJob.getClient().fetch();
                    holder.txtJobsDescription.setText(cvJob.getClient().getName());
                }
            } else {
                holder.txtJobsDescription.setText(R.string.na);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtJobsHeading, txtJobsDescription;
    }
}
