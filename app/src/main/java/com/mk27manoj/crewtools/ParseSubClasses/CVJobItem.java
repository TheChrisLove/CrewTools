package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVJobItem")
public class CVJobItem  extends ParseObject{



    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
    }

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

    public double getTotal() {
        return getDouble("total");
    }

    public void setTotal(double total) {
        put("total", total);
    }

    public Boolean getTaxable() {
        return getBoolean("taxable");
    }

    public void setTaxable(Boolean taxable) {
        put("taxable", taxable);
    }

    public int getNumber() {
        return getInt("number");
    }

    public void setNumber(int number) {
        put("number", number);
    }

    public long getQuantity() {
        return getLong("quantity");
    }

    public void setQuantity(long quantity) {
        put("quantity", quantity);
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
        put("serviceType", serviceType);
    }

    public double getAmount() {
        return getDouble("amount");
    }

    public void setAmount(double amount) {
        put("amount", amount);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }
}
