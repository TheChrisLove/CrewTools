package com.mk27manoj.crewtools.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.crew.MyAccountActivity;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

/**
 * Renovated by The Chris Love on 2016-06-12.
 */
public class CrewAdapter extends BaseAdapter {
    private Context mContext;
    private List<CVEmployee> mList;
    private ViewHolder holder;

    public CrewAdapter(Context context, List<CVEmployee> list) {
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

            convertView = inflater.inflate(R.layout.row_crew_item, parent, false);
            holder.txtCrewHeading = (TextView) convertView.findViewById(R.id.textview_row_crew_heading);
            holder.txtCrewDescription = (TextView) convertView.findViewById(R.id.textview_row_crew_description);
            holder.txtA = (TextView) convertView.findViewById(R.id.textview_row_crew_a);
            holder.txtM = (TextView) convertView.findViewById(R.id.textview_row_crew_m);
            holder.imgSetting = (ImageView) convertView.findViewById(R.id.imageview_row_crew_setting);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            mList.get(position).getUser().fetch();

            String data = mList.get(position).getRole();

            if (mList.get(position).getUser().has("name")) {
                holder.txtCrewHeading.setText(mList.get(position).getUser().get("name").toString());
            }
            holder.txtCrewDescription.setText(mList.get(position).getTitle());
            if(data != null){
                if (data.equals("Admin")) {
                    holder.txtA.setVisibility(View.VISIBLE);
                    holder.txtM.setVisibility(View.GONE);
                } else if (data.equals("Member")) {
                    holder.txtM.setVisibility(View.VISIBLE);
                    holder.txtA.setVisibility(View.GONE);
                } else {
                    holder.txtA.setVisibility(View.GONE);
                    holder.txtM.setVisibility(View.GONE);
                }
                if (mList.get(position).getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                    holder.imgSetting.setVisibility(View.VISIBLE);
                    holder.imgSetting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, MyAccountActivity.class));
                        }
                    });
                } else {
                    holder.imgSetting.setVisibility(View.GONE);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtCrewHeading, txtCrewDescription, txtA, txtM;
        ImageView imgSetting;
    }
}
