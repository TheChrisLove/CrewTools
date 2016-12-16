package com.mk27manoj.crewtools.ParseSubClasses;

import android.graphics.Bitmap;
import android.media.Image;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVFile")
public class CVFile extends ParseObject{



    public CVJob[] getItems() {
        return (CVJob[]) get("items");
    }

    public void setItems(CVJob[] items) {
        put("items", items);
    }

    public String getCaption() {
        return getString("caption");
    }

    public void setCaption(String caption) {
        put("caption", caption);
    }

    public ParseFile getThumbnail() {
        return getParseFile("thumbnail");
    }

    public void setThumbnail(ParseFile thumbnail) {
        put("thumbnail", thumbnail);
    }

    public String getItemObjectId() {
        return getString("itemObjectId");
    }

    public void setItemObjectId(String itemObjectId) {
        put("itemObjectId", itemObjectId);
    }

    public String getAlbum() {
        return getString("album");
    }

    public void setAlbum(String album) {
        put("album", album);
    }

    public Object getMetadata() {
        return get("metadata");
    }

    public void setMetadata(Object metadata) {
        put("metadata", metadata);
    }

    public String getContentType() {
        return getString("contentType");
    }

    public void setContentType(String contentType) {
        put("contentType", contentType);
    }

    public String getItemParseClass() {
        return getString("itemParseClass");
    }

    public void setItemParseClass(String itemParseClass) {
        put("itemParseClass", itemParseClass);
    }

    public ParseFile getFile() {
        return getParseFile("file");
    }

    public void setFile(ParseFile file) {
        put("file", file);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public long getFilesize() {
        return getLong("filesize");
    }

    public void setFilesize(long filesize) {
        put("filesize", filesize);
    }
}
