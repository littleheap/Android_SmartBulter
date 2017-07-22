package com.littleheap.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.littleheap.smartbulter.MainActivity;
import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.utlis.ShareUtils;
import com.littleheap.smartbulter.utlis.StaticClass;
import com.littleheap.smartbulter.utlis.UtliTools;

/**
 * Created by Administrator on 2017/7/20 0020.
 * Description:闪屏页
 */

/**
 * 1.延时2000ms
 * 2.判断程序是否第一次运行
 * 3.自定义字体
 * 4.Activity全屏主题
 */
public class SplashActivity extends AppCompatActivity {
    private TextView tv_splash;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    //初始化View
    private void initView() {
        //延时2秒（第一个参数为一个数值1001，第二个参数是延时时间长短）
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);
        //设置字体
        UtliTools.setFont(this, tv_splash);

    }

    //判断程序是否是第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    //禁止返回键

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
