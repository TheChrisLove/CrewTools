package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVEmailAddress")
public class CVEmailAddress extends ParseObject implements Serializable{


    public Boolean getPrimary() {
        return getBoolean("primary");
    }

    public void setPrimary(Boolean primary) {
        put("primary", primary);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public CVClient getClient() {
        return (CVClient) get("client");
    }

    public void setClient(CVClient client) {
        put("client", client);
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
}
