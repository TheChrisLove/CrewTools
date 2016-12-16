package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */

@ParseClassName("CVService")
public class CVService extends ParseObject {

    public String getUnit() {
        return getString("unit");
    }

    public void setUnit(String unit) {
        put("unit", unit);
    }

    public Boolean getEditable() {
        return getBoolean("editable");
    }

    public void setEditable(Boolean editable) {
        put("editable", editable);
    }

    public Boolean getTaxable() {
        return getBoolean("taxable");
    }

    public void setTaxable(Boolean taxable) {
        put("taxable",  taxable);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getServiceType() {
        return getString("serviceType");
    }

    public void setServiceType(String serviceType) {
        put("serviceType",  serviceType);
    }

    public Double getString() {
        return getDouble("string");
    }

    public void setString(Double string) {
        put("string",  string);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public double getAmount(){
        return getDouble("amount");
    }
    public void setAmount(double amount){
        put("amount", amount);
    }
}
