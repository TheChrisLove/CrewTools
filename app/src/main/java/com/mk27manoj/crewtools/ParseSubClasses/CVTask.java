package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Date;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVTask")
public class CVTask extends ParseObject {


    public JSONArray getItems() {
        return  getJSONArray("items");
    }

    public void setItems(JSONArray items) {
        put("items",  items);
    }

    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes",  notes);
    }

    public int getPriority() {
        return getInt("priority");
    }

    public void setPriority(int priority) {
        put("priority",  priority);
    }

    public JSONArray getEmployees() {
        return  getJSONArray("employees");
    }

    public void setEmployees(JSONArray employees) {
        put("employees", employees);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name",  name);
    }

    public Date getDate() {
        return getDate("date");
    }

    public void setDate(Date date) {
        put("date",  date);
    }

    public Date getStarts() {
        return getDate("starts");
    }

    public void setStarts(Date starts) {
        put("starts", starts);
    }

    public CVAddress getLocation() {
        return (CVAddress) get("location");
    }

    public void setLocation(CVAddress location) {
        put("location",  location);
    }

    public Date getEnds() {
        return getDate("ends");
    }

    public void setEnds(Date ends) {
        put("ends", ends);
    }

    public Boolean getAllDay() {
        return getBoolean("allDay");
    }

    public void setAllDay(Boolean allDay) {
        put("allDay",  allDay);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company",  company);
    }

    public CVEmployee getCreatedBy() {
        return (CVEmployee) get("createdBy");
    }

    public void setCreatedBy(CVEmployee createdBy) {
        put("createdBy",  createdBy);
    }
}
