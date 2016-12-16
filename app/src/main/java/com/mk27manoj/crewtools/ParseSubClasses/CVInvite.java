package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVInvite")
public class CVInvite extends ParseObject {


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

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name",name);
    }

    public String getRole() {
        return getString("role");
    }

    public void setRole(String role) {
        put("role", role);
    }
}
