package com.littleheap.smartbulter.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
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
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
