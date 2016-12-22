package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVTax")
public class CVTax extends ParseObject {
    int rate;
    CVCompany company;

    public Double getRate() {
        return getDouble("rate");
    }

    public void setRate(int rate) {
        put("rate",  rate);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }
}
