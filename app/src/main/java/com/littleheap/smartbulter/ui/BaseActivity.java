package com.littleheap.smartbulter.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.littleheap.smartbulter.R;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/7/19 0019.
 * Description：Activity基类
 */

/**
 * 1.统一的属性
 * 2.统一的方法
 * 3.统一的接口
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示ActionBar返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //设置点击返回按钮销毁当前Activity
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
