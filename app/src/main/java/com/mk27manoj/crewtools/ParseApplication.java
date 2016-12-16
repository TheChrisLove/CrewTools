package com.mk27manoj.crewtools;

import android.app.Application;

import com.mk27manoj.crewtools.ParseSubClasses.CVAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVClient;
import com.mk27manoj.crewtools.ParseSubClasses.CVCompany;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmailAddress;
import com.mk27manoj.crewtools.ParseSubClasses.CVEmployee;
import com.mk27manoj.crewtools.ParseSubClasses.CVFile;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvitation;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvite;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvoice;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvoiceItem;
import com.mk27manoj.crewtools.ParseSubClasses.CVInvoicePayment;
import com.mk27manoj.crewtools.ParseSubClasses.CVJob;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobEntry;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobItem;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobNote;
import com.mk27manoj.crewtools.ParseSubClasses.CVJobSchedule;
import com.mk27manoj.crewtools.ParseSubClasses.CVLock;
import com.mk27manoj.crewtools.ParseSubClasses.CVPayment;
import com.mk27manoj.crewtools.ParseSubClasses.CVPhoneNumber;
import com.mk27manoj.crewtools.ParseSubClasses.CVReset;
import com.mk27manoj.crewtools.ParseSubClasses.CVService;
import com.mk27manoj.crewtools.ParseSubClasses.CVServiceUnit;
import com.mk27manoj.crewtools.ParseSubClasses.CVTask;
import com.mk27manoj.crewtools.ParseSubClasses.CVTax;
import com.mk27manoj.crewtools.ParseSubClasses.CVUser;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Renovated by The Chris Love on 11/28/2016.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // ===  All of the Parse Classes which are located in ParseSubClasses package.
        ParseObject.registerSubclass(CVAddress.class);
        ParseObject.registerSubclass(CVClient.class);
        ParseObject.registerSubclass(CVCompany.class);
        ParseObject.registerSubclass(CVEmailAddress.class);
        ParseObject.registerSubclass(CVEmployee.class);
        ParseObject.registerSubclass(CVFile.class);
        ParseObject.registerSubclass(CVInvitation.class);
        ParseObject.registerSubclass(CVInvite.class);
        ParseObject.registerSubclass(CVInvoice.class);
        ParseObject.registerSubclass(CVInvoiceItem.class);
        ParseObject.registerSubclass(CVInvoicePayment.class);
        ParseObject.registerSubclass(CVJob.class);
        ParseObject.registerSubclass(CVJobEntry.class);
        ParseObject.registerSubclass(CVJobItem.class);
        ParseObject.registerSubclass(CVJobNote.class);
        ParseObject.registerSubclass(CVJobSchedule.class);
        ParseObject.registerSubclass(CVLock.class);
        ParseObject.registerSubclass(CVPayment.class);
        ParseObject.registerSubclass(CVPhoneNumber.class);
        ParseObject.registerSubclass(CVReset.class);
        ParseObject.registerSubclass(CVService.class);
        ParseObject.registerSubclass(CVServiceUnit.class);
        ParseObject.registerSubclass(CVTask.class);
        ParseObject.registerSubclass(CVTax.class);
        ParseUser.registerSubclass(CVUser.class);
        Parse.enableLocalDatastore(this);
        
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("aemyYKDfUZ0Eu3XmNRvcL35EfsEj3UOXOzh2fPrp")
            .clientKey("X33IizlEL1PXYG4dulQAFGNorQkP0iWFiAIFnKDW")
            .server("https://crewtoolsapp.herokuapp.com/parse/")
            .build()
        );
    }
}