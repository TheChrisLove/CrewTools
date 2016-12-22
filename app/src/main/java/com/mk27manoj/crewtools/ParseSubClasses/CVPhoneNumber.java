package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVPhoneNumber")
public class CVPhoneNumber extends ParseObject implements Serializable {



    public Boolean getPrimary() {
        return getBoolean("primary");
    }

    public void setPrimary(Boolean primary) {
        put("primary",  primary);
    }

    public String getNumber() {
        return getString("number");
    }

    public void setNumber(String number) {
        put("number",  number);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name",  name);
    }

    public CVClient getClient() {
        return (CVClient) get("client");
    }

    public void setClient(CVClient client) {
        put("client", client);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }
}
