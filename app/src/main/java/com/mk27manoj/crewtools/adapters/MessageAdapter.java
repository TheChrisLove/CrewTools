package com.mk27manoj.crewtools.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mk27manoj.crewtools.ParseSubClasses.CVJobEntry;
import com.mk27manoj.crewtools.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Renovated by The Chris Love (thechrislove@icloud.com) on 10-31-2016.
 */
public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mList;
    private List<CVJobEntry> mJobEntryList;
    private ViewHolder holder;
    private static final String TAG = "MessageAdapter";

    public MessageAdapter(Context context, List<CVJobEntry> list) {
        mContext = context;
        mJobEntryList = list;
    }

    @Override
    public int getCount() {
        if(mJobEntryList != null){
            return mJobEntryList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mJobEntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       try {
           //String[] data = mList.get(position).split("@@");
           CVJobEntry entry = (CVJobEntry) getItem(position);
           entry.fetch();
           entry.getCreatedBy().fetch();
           entry.getCreatedBy().getUser().fetch();
           if (convertView == null) {
               holder = new ViewHolder();
               LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               if (entry.getCreatedBy().getUser() == ParseUser.getCurrentUser()) {
                   convertView = inflater.inflate(R.layout.row_my_chat_message, parent, false);
               } else {
                   convertView = inflater.inflate(R.layout.row_crew_chat_message, parent, false);
               }

               holder.txtSender = (TextView) convertView.findViewById(R.id.textview_chat_sender);
               holder.txtDate = (TextView) convertView.findViewById(R.id.textview_chat_date_time);
               holder.txtMessage = (TextView) convertView.findViewById(R.id.textview_chat_message);
               holder.imgPhoto = (ImageView) convertView.findViewById(R.id.imageview_chat_image);
               convertView.setTag(holder);
           } else {
               holder = (ViewHolder) convertView.getTag();
           }

           /*
           convertView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                   View promptView = layoutInflater.inflate(R.layout.custom_menu_items_dialog, null);

                   final AlertDialog alertD = new AlertDialog.Builder(mContext).create();
                   TextView txtTitle = (TextView) promptView.findViewById(R.id.textview_custom_menu_dialog_heading);
                   txtTitle.setVisibility(View.GONE);
                   TextView txtMessage = (TextView) promptView.findViewById(R.id.textview_custom_menu_dialog_message);
                   txtMessage.setText("Perform an action on this message");
                   ListView menuList = (ListView) promptView.findViewById(R.id.listview_custom_menu_dialog_list);
                   final String[] values = new String[]{"Copy", "Delete"};
                   menuList.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1, values));
                   menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           Toast.makeText(mContext, values[position], Toast.LENGTH_SHORT).show();
                       }
                   });
                   Button btnCancel = (Button) promptView.findViewById(R.id.button_custom_menu_dialog_cancel);

                   btnCancel.setOnClickListener(new View.OnClickListener() {
                       public void onClick(View v) {
                           alertD.dismiss();
                       }
                   });
                   alertD.setView(promptView);
                   alertD.show();
                   return false;
               }
           });
           */
           holder.txtSender.setText(entry.getCreatedBy().getUser().getString("name"));
           holder.txtDate.setText(entry.getCreatedAt().toString());
           holder.txtMessage.setText(entry.getNotes());
           //Log.i(TAG, "getView: " + entry.getFile() + " " + entry.getPhoto());
           entry.fetch();

           if (entry.getFile() == null && entry.getPhoto() == null) {
               //Log.i(TAG, "getView: Both file and photo are empty");
               holder.imgPhoto.setVisibility(View.GONE);
           } else {
               try {

                   if (entry.getPhoto()!= null) {

                       Picasso.with(convertView.getContext()).load(entry.getPhoto().getUrl()).into(holder.imgPhoto);
                       Log.i(TAG, "getView: " + entry.getPhoto().getUrl());
                   } else {
                       entry.getFile().fetch();
                       Picasso.with(convertView.getContext()).load(entry.getFile().getFile().getUrl()).into(holder.imgPhoto);
                       Log.i(TAG, "getView: " + entry.getFile().getFile().getUrl());
                   }
               } catch (Exception e) {
               }
           }
       } catch (ParseException e) {
           e.printStackTrace();
       }

        return convertView;
    }

    static class ViewHolder {
        TextView txtDate, txtMessage, txtSender;
        ImageView imgPhoto;
    }

}
