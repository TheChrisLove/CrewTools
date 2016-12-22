package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.Date;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVLock")
public class CVLock extends ParseObject {



    public String[] getItems() {
        return (String[]) get("items");
    }

    public void setItems(String[] items) {
        put("items",items);
    }

    public CVEmployee getOwner() {
        return (CVEmployee) get("owner");
    }

    public void setOwner(CVEmployee owner) {
        put("owner", owner);
    }

    public ParseRelation<CVEmployee> getFollowers() {
        return getRelation("followers");
    }

    public void setFollowers(ParseRelation<CVEmployee> followers) {
        put("followers", followers);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public Date getReleased() {
        return getDate("released");
    }

    public void setReleased(Date released) {
        put("released", released);
    }
}
