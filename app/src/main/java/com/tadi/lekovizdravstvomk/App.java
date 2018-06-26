package com.tadi.lekovizdravstvomk;

import android.app.Application;

import com.tadi.lekovizdravstvomk.model.MonitoringDatabase;

public class App extends Application {

    private static MonitoringDatabase database;


    @Override
    public void onCreate() {
        super.onCreate();


        database = MonitoringDatabase.getDatabase(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        MonitoringDatabase.destroyInstance();
        super.onTerminate();
    }


    public static MonitoringDatabase getDatabase() {
        return database;
    }
}
