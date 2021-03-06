package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Renovated by The Chris Love on 12-21-2016.
 */
@ParseClassName("User")
public class CVUser extends ParseUser {

    public String getName() {
        try {
            fetchIfNeeded();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    @Override
    public String toString() {
        super.toString();

        return getName();
    }
}
