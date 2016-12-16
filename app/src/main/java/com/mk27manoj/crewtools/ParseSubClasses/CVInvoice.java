package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.Date;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVInvoice")
public class CVInvoice extends ParseObject {


    public ParseRelation<CVInvoiceItem> getItems() {
        return getRelation("items");
    }

    public void setItems(ParseRelation<CVInvoiceItem> items) {
        put("items", items);
    }

    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
    }

    public long getTotal() {
        return getLong("total");
    }

    public void setTotal(long total) {
        put("total", total);
    }

    public long getOwed() {
        return getLong("owed");
    }

    public void setOwed(long owed) {
        put("owed", owed);
    }

    public double getTaxRate() {
        return getDouble("taxRate");
    }

    public void setTaxRate(double taxRate) {
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

    public Date getPaid() {
        return getDate("paid");
    }

    public void setPaid(Date paid) {
        put("paid", paid);
    }

    public ParseRelation<CVInvoicePayment> getPayments() {
        return getRelation("payments");
    }

    public void setPayments(ParseRelation<CVInvoicePayment> payments) {
        put("payments", payments);
    }

    public Date getDate() {
        return getDate("date");
    }

    public void setDate(Date date) {
        put("date", date);
    }

    public Date getGenerated() {
        return getDate("generated");
    }

    public void setGenerated(Date generated) {
        put("generated", generated);
    }

    public Date getDue() {
        return getDate("due");
    }

    public void setDue(Date due) {
        put("due", due);
    }

    public CVAddress getAddress() {
        return (CVAddress) get("address");
    }

    public void setAddress(CVAddress address) {
        put("address", address);
    }

    public Date getSent() {
        return getDate("sent");
    }

    public void setSent(Date sent) {
        put("sent", sent);
    }

    public byte[] getFile() {
        return getBytes("file");
    }

    public void setFile(byte[] file) {
        put("file", file);
    }

    public long getBalance() {
        return getLong("balance");
    }

    public void setBalance(long balance) {
        put("balance", balance);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public int getNet() {
        return getInt("net");
    }

    public void setNet(int net) {
        put("net", net);
    }

    public CVJob getJob() {
        return (CVJob) get("job");
    }

    public void setJob(CVJob job) {
        put("job",job);
    }
}
