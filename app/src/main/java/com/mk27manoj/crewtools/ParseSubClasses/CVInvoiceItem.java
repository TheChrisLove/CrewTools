package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVInvoiceItem")
public class CVInvoiceItem extends ParseObject {


    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
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

    public int getQuantity() {
        return getInt("quantity");
    }

    public void setQuantity(int quantity) {
        put("quantity", quantity);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public long getAmount() {
        return getLong("amount");
    }

    public void setAmount(long amount) {
        put("amount", amount);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public CVJob getJob() {
        return (CVJob) get("job");
    }

    public void setJob(CVJob job) {
        put("job", job);
    }
}
