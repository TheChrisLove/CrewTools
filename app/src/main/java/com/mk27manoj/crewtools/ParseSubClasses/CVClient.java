package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVClient")
public class CVClient extends ParseObject {

    public String getBusiness() {
        return getString("business");
    }

    public void setBusiness(String business) {
        put("business", business);
    }

    public String getContactMethod() {
        return getString("contactMethod");
    }

    public void setContactMethod(String contactMethod) {
        put("contactMethod", contactMethod);
    }

    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
    }

    public String getName() {
        try {
            fetchIfNeeded();
            return getString("name");
        } catch (ParseException e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public CVAddress getBillingAddress() {
        return (CVAddress) get("billingAddress");
    }

    public void setBillingAddress(CVAddress billingAddress) {
        put("billingAddress", billingAddress);
    }

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    @Override
    public String toString() {
        super.toString();
        return getName();
    }
}