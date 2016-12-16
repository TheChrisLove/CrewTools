package com.mk27manoj.crewtools.clients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyasistory on 6/20/2016.
 */
public class ClientAdpater extends BaseAdapter {

    private Context mContext;
    private List<CVClient> mList;

    /**
     * This constructor will set the constants need for development.
     * @param context  is the activity that this adapter is attached to.
     * @param list is the List of <code>CVLient</code> that is sent to this adapter.
     */
    public ClientAdpater(Context context, List<CVClient> list) {
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

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row_job_item, parent, false);
        }
        // This is the specific Client
        CVClient client = (CVClient) getItem(position);


        // This will pull the name of the Client and his company name.
        ((TextView)convertView.findViewById(R.id.textview_row_job_heading)).setText(client.getName());
        ((TextView)convertView.findViewById(R.id.textview_row_job_description)).setText(client.getBusiness());

        return convertView;
    }


}
