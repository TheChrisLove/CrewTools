package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Renovated by The Chris Love on 10-24-2016.
 */
@ParseClassName("CVEmployee")
public class  CVEmployee extends ParseObject {


    public int getBillingContactId() {
        return getInt("billingContactId");
    }

    public void setBillingContactId(int billingContactId) {
        put("billingContactId", billingContactId);
    }

    public ParseUser getUser() {
        try {
            fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getParseUser("user");
    }

    public void setUser(CVUser user) {
        put("user", user);
    }
    
    public String getName() {
        String name = "";
        try {
            name = getUser().fetchIfNeeded().get("name").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return name;
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public String getRole() {
        return getString("role");
    }

    public void setRole(String role) {
        put("role", role);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    @Override
    public String toString() {
        return getName();
    }
}
