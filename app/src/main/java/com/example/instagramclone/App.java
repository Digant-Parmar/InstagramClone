package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("yjJkJB7KxqigsrA0nV8eolYYR2GsvjCSl0nlZfeV")
                // if defined
                .clientKey("BhypLYD7muZDBFDfDOsxAIoAhTTsbMS4u6N6fHeR")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
