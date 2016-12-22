package com.mk27manoj.crewtools.ParseSubClasses;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVJobEntry")
public class CVJobEntry extends ParseObject {



    public String getNotes() {
        return getString("notes");
    }

    public void setNotes(String notes) {
        put("notes", notes);
    }

    public ParseFile getPhoto() {
        return (ParseFile) get("photo");
    }

    public void setPhoto(ParseFile photo) {
        put("photo", photo);
    }

    public CVFile getFile() {
        return (CVFile) get("file");
    }

    public void setFile(CVFile file) {
        put("file", file);
    }

    public CVJob getJob() {
        return (CVJob) get("job");
    }

    public void setJob(CVJob job) {
        put("job", job);
    }

    public void setCreatedBy(CVEmployee user){
        put("createdBy", user);
    }

    public CVEmployee getCreatedBy(){
        return (CVEmployee) get("createdBy");
    }
}
