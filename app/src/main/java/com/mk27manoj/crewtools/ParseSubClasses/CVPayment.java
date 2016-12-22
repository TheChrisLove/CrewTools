package com.mk27manoj.crewtools.ParseSubClasses;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.Date;

/**
 * Renovated by The Chris Love  on 12-21-2016.
 */
@ParseClassName("CVPayment")
public class CVPayment extends ParseObject {

    String method;
    String notes;
    CVClient client;
    Date date;
    Bitmap photo;
    double amount;
    CVCompany company;
    CVJob job;
    JSONObject transaction;

}
