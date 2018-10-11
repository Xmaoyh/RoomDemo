package com.example.administrator.demoroom;

import android.app.Application;

import com.example.administrator.demoroom.db.AppExecutors;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 应用程序实体
 * @author MaoYiHan
 * @date 2018/8/1
 */
public class MyApplication extends Application {
    private static MyApplication app;
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mAppExecutors = new AppExecutors();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static MyApplication getInstance() {
        return app;
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
