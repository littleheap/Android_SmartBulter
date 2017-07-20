package com.littleheap.smartbulter.utlis;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/19 0019.
 * Description：工具描述类
 */

public class UtliTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/Audiowide-Regular.TTF");
        textView.setTypeface(fontType);
    }
}
