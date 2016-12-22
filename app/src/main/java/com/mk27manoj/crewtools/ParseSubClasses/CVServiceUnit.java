package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVServiceUnit")
public class CVServiceUnit extends ParseObject {



    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name",  name);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public String getValue() {
        return getString("value");
    }

    public void setValue(String value) {
        put("value",  value);
    }
}
