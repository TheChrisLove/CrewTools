package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVInvitation")
public class CVInvitation extends ParseObject {


    public Boolean getKeep() {
        return getBoolean("keep");
    }

    public void setKeep(Boolean keep) {
        put("keep", keep);
    }

    public String getCode() {
        return getString("code");
    }

    public void setCode(String code) {
        put("code", code);
    }

    public Date getExpires() {
        return getDate("expires");
    }

    public void setExpires(Date expires) {
        put("expires", expires);
    }

    public String getRole() {
        return getString("role");
    }

    public void setRole(String role) {
        put("role", role);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }
}
