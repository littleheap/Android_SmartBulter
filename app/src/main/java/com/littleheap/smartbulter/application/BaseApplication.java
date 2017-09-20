package com.littleheap.smartbulter.application;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.littleheap.smartbulter.utlis.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

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
        //Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        //科大讯飞
        //将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);

    }
}
