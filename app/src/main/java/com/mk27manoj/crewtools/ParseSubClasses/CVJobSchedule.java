package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVJobSchedule")
public class CVJobSchedule extends ParseObject {



    public String[] getCrew() {
        return (String[]) get("crew");
    }

    public void setCrew(String[] crew) {
        put("crew", crew);
    }

    public Date getStart() {
        return getDate("start");
    }

    public void setStart(Date start) {
        put("start", start);
    }

    public Date getDate() {
        return getDate("date");
    }

    public void setDate(Date date) {
        put("date", date);
    }

    public Date getEnd() {
        return getDate("end");
    }

    public void setEnd(Date end) {
        put("end", end);
    }

    public Boolean getAllDay() {
        return getBoolean("allDay");
    }

    public void setAllDay(Boolean allDay) {
        put("allDay", allDay);
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
