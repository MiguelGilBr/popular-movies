package com.example.popularmovies.application;

import android.app.Application;

public class BaseApplication extends Application{

    private static BaseApplication mBaseApplication;

    public static BaseApplication getInstance() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }
}