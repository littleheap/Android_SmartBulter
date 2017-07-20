package com.littleheap.smartbulter.utlis;

import android.util.Log;

/**
 * Created by Administrator on 2017/7/20 0020.
 * Description:Log封装类
 */

public class L {
    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "Smartbutler";

    //五个等级(DIWE)
    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
