package com.mk27manoj.crewtools.ParseSubClasses;

import android.graphics.pdf.PdfDocument;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.Date;

/**
 * Renovated by The Chris Love on 10-31-2016.
 */

@ParseClassName("CVJob")
public class CVJob extends ParseObject {

    public ParseRelation<CVJobItem> getItems() {
        return getRelation("items");
    }

    public void setItems(ParseRelation<CVJobItem> items) {
        put("items", items);
    }

    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
    }

    public Date getStart() {
        return getDate("start");
    }

    public void setStart(Date start) {
        put("start", start);
    }

    public int getPriority() {
        return getInt("priority");
    }

    public void setPriority(int priority) {
        put("priority", priority);
    }

    public ParseRelation<CVJobNote> getLog() {
        return getRelation("log");
    }

    public void setLog(ParseRelation<CVJobNote> log) {
        put("log", log);
    }

    public long getTotal() {
        return getLong("total");
    }

    public void setTotal(long total) {
        put("total", total);
    }

    public String getPayment() {
        return getString("payment");
    }

    public void setPayment(String payment) {
        put("payment", payment);
    }

    public int getOccurs() {
        return getInt("occurs");
    }

    public void setOccurs(int occurs) {
        put("occurs", occurs);
    }

    public long getPrice() {
        return getLong("price");
    }

    public void setPrice(long price) {
        put("price", price);
    }

    public CVJob getParent() {
        return (CVJob) get("parent");
    }

    public void setParent(CVJob parent) {
        put("parent", parent);
    }

    public Date getCompleted() {
        return getDate("completed");
    }

    public void setCompleted(Date completed) {
        put("completed", completed);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public int getTaxRate() {
        return getInt("taxRate");
    }

    public void setTaxRate(int taxRate) {
        put("taxRate", taxRate);
    }

    public int getTax() {
        return getInt("tax");
    }

    public void setTax(int tax) {
        put("tax", tax);
    }

    public CVClient getClient() {
        return (CVClient) get("client");
    }

    public void setClient(CVClient client) {
        put("client", client);
    }

    public Date getScheduled() {
        return getDate("scheduled");
    }

    public void setScheduled(Date scheduled) {
        put("scheduled", scheduled);
    }

    public String getServiceType() {
        return getString("serviceType");
    }

    public void setServiceType(String serviceType) {
        put("serviceType", serviceType);
    }

    public ParseRelation<CVJobEntry> getEntries() {
        return getRelation("entries");
    }

    public void setEntries(ParseRelation<CVJobEntry> entries) {
        put("entries", entries);
    }
    
    public CVAddress getAddress() {
        return (CVAddress) get("address");
    }

    public void setAddress(CVAddress address) {
        put("address", address);
    }
    
    public String getCity() {
        return getString("city");
    }

    public void setCity(String city) {
        put("city", city);
    }

    public String getState() {
        return getString("state");
    }

    public void setState(String state) {
        put("state", state);
    }
    
//    public String getPostalCode() {
//        return getString("postalCode");
//    }
//
//    public void setPostalCode(String postalCode) {
//        put("postalCode", postalCode);
//    }

    public ParseFile getContract() {
        return getParseFile("contract");
    }

    public void setContract(ParseFile contract) {
        put("contract", contract);
    }

    public ParseFile getProposal() {
        return getParseFile("proposal");
    }

    public void setProposal(ParseFile proposal) {
        put("proposal", proposal);
    }

    public Date getFinish() {
        return getDate("finish");
    }

    public void setFinish(Date finish) {
        put("finish", finish);
    }

    public int getAcceptWithin() {
        return getInt("acceptWithin");
    }

    public void setAcceptWithin(int acceptWithin) {
        put("acceptWithin", acceptWithin);
    }

    public String getLocation() {
        return getString("location");
    }

    public void setLocation(String location) {
        put("location", location);
    }

    public String[] getSchedule() {
        return (String[]) get("schedule");
    }

    public void setSchedule(String[] schedule) {
        put("schedule", schedule);
    }

    public Date getApprove() {
        return getDate("approve");
    }

    public void setApprove(Date approve) {
        put("approve", approve);
    }

    public Date getInvoiced() {
        return getDate("invoiced");
    }

    public void setInvoiced(Date invoiced) {
        put("invoiced", invoiced);
    }

    public Boolean getRepeats() {
        return getBoolean("repeats");
    }

    public void setRepeats(Boolean repeats) {
        put("repeats", repeats);
    }

    public Date getSent() {
        return getDate("sent");
    }

    public void setSent(Date sent) {
        put("sent", sent);
    }

    public String[] getCompletedDates() {
        return (String[]) get("completedDates");
    }

    public void setCompletedDates(String[] completedDates) {
        put("completedDates", completedDates);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public String[] getRepeat() {
        return (String[]) get("repeat");
    }

    public void setRepeat(String[] repeat) {
        put("repeat", repeat);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public ParseRelation<CVJobItem> getServices() {
        return getRelation("services");
    }

    public void setServices(ParseRelation<CVJobItem> services) {
        put("services", services);
    }
}