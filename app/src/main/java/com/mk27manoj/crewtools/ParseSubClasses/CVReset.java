package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVReset")
public class CVReset extends ParseObject {
    CVUser user;

    public CVUser getUser() {
        return (CVUser) get("user");
    }

    public void setUser(CVUser user) {
        put("user", user);
    }
}
