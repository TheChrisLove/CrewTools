package com.mk27manoj.crewtools.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVInvoice;
import com.mk27manoj.crewtools.ParseSubClasses.CVTask;
import com.mk27manoj.crewtools.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
public class InvoiceAdapter extends BaseAdapter {
    private Context mContext;
    private List<CVInvoice> mList;
    private ViewHolder holder;
    private int location;

    public InvoiceAdapter(Context context, List<CVInvoice> list, int location) {
        mContext = context;
        mList = list;
        this.location = location;
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
        CVInvoice data = mList.get(position);

        holder.txtEventHeading.setText("#" + data.getObjectId() + " (" + NumberFormat
                .getCurrencyInstance(new Locale("en", "US")).format(data.getTotal()) + ")");

        holder.txtEventDescription.setText(data.getClient().getName());

        if (location == 1) { //Pending
            holder.txtEventTiming.setText("Created on " + new SimpleDateFormat("MM/dd/yyyy").format(data.getDate()));
        } else  if ( location == 2){ // Sent
            holder.txtEventTiming.setText("Due on " + new SimpleDateFormat("MM/dd/yyyy").format(data.getDue()));
        } else if ( location == 3){ // Overdue
            Date today = Calendar.getInstance().getTime();
            int days = (int)((today.getTime() - data.getDue().getTime() ))/(1000 * 60 * 60 * 24);
            holder.txtEventTiming.setText("Overdue by " + days + " days" );
        } else {  // Paid
            holder.txtEventTiming.setText("Paid on " + new SimpleDateFormat("MM/dd/yyyy").format(data.getPaid()));
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView txtEventHeading, txtEventDescription, txtEventTiming;
    }
}
