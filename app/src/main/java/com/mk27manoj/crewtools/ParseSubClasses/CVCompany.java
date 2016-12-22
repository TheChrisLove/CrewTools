package com.mk27manoj.crewtools.ParseSubClasses;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

/**
 * Renovated by The Chris Love on 12-21-2016.
 */

@ParseClassName("CVCompany")
public class CVCompany extends ParseObject{

    public String getBusiness() {
        return getString("business");
    }

    public void setBusiness(String business) {
        put("business", business);
    }

    public String getLicense() {
        return getString("license");
    }

    public void setLicense(String license) {
        put("license", license);
    }

    public int getBillingClientId() {
        return getInt("billingClientId");
    }

    public void setBillingClientId(int billingClientId) {
        put("billingClientId", billingClientId);
    }

    public String getPrimaryColor() {
        return getString("primaryColor");
    }

    public void setPrimaryColor(String primaryColor) {
        put("primaryColor", primaryColor);
    }

    public Date getLogoUpdatedAt() {
        return getDate("logoUpdatedAt");
    }

    public void setLogoUpdatedAt(Date logoUpdatedAt) {
        put("logoUpdatedAt", logoUpdatedAt);
    }

    public ParseFile getWatermark() {
        return getParseFile("watermark");
    }

    public void setWatermark(ParseFile watermark) {
        put("watermark", watermark);
    }

    public String getWatermarkTintColor() {
        return getString("watermarkTintColor");
    }

    public void setWatermarkTintColor(String watermarkTintColor) {
        put("watermarkTintColor", watermarkTintColor);
    }

    public JSONArray getServiceTypes() {
        return getJSONArray("serviceTypes");
    }

    public void setServiceTypes(JSONArray serviceTypes) {
        put("serviceTypes", serviceTypes);
    }

    public String getPayment() {
        return getString("payment");
    }

    public void setPayment(String payment) {
        put("payment", payment);
    }

    public String[] getTerms() {
        return (String[]) get("terms");
    }

    public void setTerms(String[] terms) {
        put("terms", terms);
    }

    public String getPaypalEmail() {
        return getString("paypalEmail");
    }

    public void setPaypalEmail(String paypalEmail) {
        put("paypalEmail", paypalEmail);
    }

    public Date getWatermarkUpdateAt() {
        return getDate("watermarkUpdateAt");
    }

    public void setWatermarkUpdateAt(Date watermarkUpdateAt) {
        put("watermarkUpdateAt", watermarkUpdateAt);
    }

    public String getCity() {
        return getString("city");
    }

    public void setCity(String city) {
        put("city", city);
    }

    public ParseFile getLogo() {
        return getParseFile("logo");
    }

    public void setLogo(ParseFile logo) {
        put("logo", logo);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public Double getTaxRate() {
        return getDouble("taxRate");
    }

    public void setTaxRate(Double taxRate) {
        put("taxRate", taxRate);
    }

    public Double getTax() {
        return getDouble("tax");
    }

    public void setTax(Double tax) {
        put("tax", tax);
    }

    public String getMotto() {
        return getString("motto");
    }

    public void setMotto(String motto) {
        put("motto", motto);
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public String getPaymentIdentifier() {
        return getString("paymentIdentifier");
    }

    public void setPaymentIdentifier(String paymentIdentifier) {
        put("paymentIdentifier", paymentIdentifier);
    }

    public String getWorkDayEnd() {
        return getString("workDayEnd");
    }

    public void setWorkDayEnd(String workDayEnd) {
        put("workDayEnd", workDayEnd);
    }

    public String getState() {
        return getString("state");
    }

    public void setState(String state) {
        put("state", state);
    }

    public int getAcceptWithin() {
        return getInt("acceptWithin");
    }

    public void setAcceptWithin(int acceptWithin) {
        put("acceptWithin", acceptWithin);
    }

    public String getWorkDayStart() {
        return getString("workDayStart");
    }

    public void setWorkDayStart(String workDayStart) {
        put("workDayStart", workDayStart);
    }

    public String getPaymentProvider() {
        return getString("paymentProvider");
    }

    public void setPaymentProvider(String paymentProvider) {
        put("paymentProvider",paymentProvider);
    }

    public String getPostalCode() {
        return getString("postalCode");
    }

    public void setPostalCode(String postalCode) {
        put("postalCode", postalCode);
    }

    public String getWebsite() {
        return getString("website");
    }

    public void setWebsite(String website) {
        put("website", website);
    }

    public int getNet() {
        return getInt("net");
    }

    public void setNet(int net) {
        put("net", net);
    }

    public String getAddress1() {
        return getString("address1");
    }

    public void setAddress1(String address1) {
        put("address1", address1);
    }

    public String[] getServices() {
        return (String[]) get("services");
    }

    public void setServices(String[] services) {
        put("services", services);
    }

    public String getAddress2() {
        return getString("address2");
    }

    public void setAddress2(String address2) {
        put("address2", address2);
    }
}