package com.example.weatherreport;

import android.app.Application;

public class CampusApplication extends Application {
    private int airtempData;

    @Override
    public void onCreate() {
        airtempData = 0;
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setAirtempData(int newAirtempData) { this.airtempData = newAirtempData; }

    public int getAirtempData() { return  airtempData; }
}
