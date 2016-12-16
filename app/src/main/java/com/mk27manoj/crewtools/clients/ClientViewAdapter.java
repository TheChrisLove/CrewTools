package com.mk27manoj.crewtools.clients;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmailAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVPhoneNumber;
import com.mk27manoj.crewtools.R;

import java.util.List;

/**
 * Renovated by The Chris Love on 10-26-2016.
 */
public class ClientViewAdapter extends BaseAdapter{
    Context mContext;
    List<CVAddress> mAddressList;
    List<CVEmailAddress> mEmailList;
    List<CVPhoneNumber> mPhoneList;

    public ClientViewAdapter(Context mContext, List<CVAddress> mAddressList, List<CVEmailAddress> mEmailList, List<CVPhoneNumber> mPhoneList) {
        this.mContext = mContext;
        this.mAddressList = mAddressList;
        this.mEmailList = mEmailList;
        this.mPhoneList = mPhoneList;
    }

    @Override
    public int getCount() {
        if (mAddressList != null) {
            return mAddressList.size();
        } else if (mPhoneList != null){
            return mPhoneList.size();
        } else {
            return mEmailList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mAddressList != null) {
            return mAddressList.get(position);
        } else if (mPhoneList != null){
            return mPhoneList.get(position);
        } else {
            return mEmailList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return 1000 + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.client_row_addr_email_phone, parent, false);
        }

        if (mAddressList != null) {

            CVAddress address = (CVAddress) getItem(position);
            String completeAddress = address.getAddress1() + "\n" + address.getCity() + ", "
                    + address.getState() + " " + address.getZip();
            return getView(convertView, completeAddress);

        } else if (mPhoneList != null){

            CVPhoneNumber phoneNumber = (CVPhoneNumber) getItem(position);
            String phoneString = String.valueOf(phoneNumber.getNumber()).replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1)-$2-$3");
            return getView(convertView, phoneString);

        } else {

            CVEmailAddress emailAdress = (CVEmailAddress) getItem(position);
            String emailString = emailAdress.getEmail();
            return getView(convertView, emailString);

        }




    }

    @NonNull
    private View getView(View convertView, String inputText) {
        TextView tv = (TextView) convertView.findViewById(R.id.textview_client_input_row);
        tv.setText(inputText);
        return convertView;
    }
}
