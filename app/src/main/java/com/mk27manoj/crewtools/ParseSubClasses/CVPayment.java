package com.mk27manoj.crewtools.ParseSubClasses;

import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by gyasistory on 6/17/16.
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
