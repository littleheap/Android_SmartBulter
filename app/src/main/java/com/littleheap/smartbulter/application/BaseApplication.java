package com.littleheap.smartbulter.application;

import android.app.Application;

import com.littleheap.smartbulter.utlis.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class BaseApplication extends Application {

    //创建Application
    @Override
    public void onCreate() {
        super.onCreate();

        //Bugly异常检测SDK
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
    }
}
