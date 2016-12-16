package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVAddress")
public class CVAddress extends ParseObject implements Serializable{



    public String getZip() {
        return getString("postalCode");
    }

    public void setZip(String zip) {
        put("postalCode", zip);
    }

    public Boolean getPrimary() {
        return getBoolean("primary");
    }

    public void setPrimary(Boolean primary) {
        put("primary", primary);
    }

    public String getCity() {
        return getString("city");
    }

    public void setCity(String city) {
        put("city", city);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public CVClient getClient() {
        return (CVClient) get("client");
    }

    public void setClient(CVClient client) {
        put("client", client);
    }

    public String getState() {
        return getString("state");
    }

    public void setState(String state) {
        put("state", state);
    }

    public String getPostalCode() {
        return getString("postalCode");
    }

    public void setPostalCode(String postalCode) {
        put("postalCode", postalCode);
    }

    public CVCompany getCompany() {
        return (CVCompany) get("company");
    }

    public void setCompany(CVCompany company) {
        put("company", company);
    }

    public String getAddress1() {
        return getString("address1");
    }

    public void setAddress1(String address1) {
        put("address1", address1);
    }

    public ParseGeoPoint getCoords() {
        return getParseGeoPoint("coords");
    }

    public void setCoords(ParseGeoPoint coords) {
        put("coords", coords);
    }

    public String getAddress2() {
        return getString("address2");
    }

    public void setAddress2(String address2) {
        put("address2", address2);
    }


    @Override
    public String toString() {
        super.toString();

        return getAddress1() + "\n" + getCity() + ", " + getState() + " "  + getZip();
    }
}
