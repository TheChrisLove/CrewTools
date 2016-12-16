package com.mk27manoj.crewtools.ParseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by gyasistory on 6/17/16.
 */
@ParseClassName("CVInvoicePayment")
public class CVInvoicePayment extends ParseObject {


    public CVInvoice getInvoice() {
        return (CVInvoice) get("invoice");
    }

    public void setInvoice(CVInvoice invoice) {
        put("invoice", invoice);
    }

    public CVPayment getPayment() {
        return (CVPayment) get("payment");
    }

    public void setPayment(CVPayment payment) {
        put("payment", payment);
    }
}
