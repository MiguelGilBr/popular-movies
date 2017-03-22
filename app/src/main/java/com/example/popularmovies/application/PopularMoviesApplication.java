package com.example.popularmovies.application;

import android.database.sqlite.SQLiteDatabase;
import com.example.popularmovies.datamodel.DaoMaster;
import com.example.popularmovies.datamodel.DaoSession;

public class PopularMoviesApplication extends BaseApplication{

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    //GREENDAO
    private void initGreenDao() {
        helper = new DaoMaster.DevOpenHelper(this, "popular-movies-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }
}