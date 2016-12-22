package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVJobNote")
public class CVJobNote extends ParseObject {

    CVJob job;
    String notes;

    public CVJob getJob() {
        return (CVJob) get("job");
    }

    public void setJob(CVJob job) {
        put("job", job);
    }

    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
    }
}
