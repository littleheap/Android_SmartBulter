package com.littleheap.smartbulter.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.littleheap.smartbulter.R;

/**
 * Created by Administrator on 2017/7/23 0023.
 * Description:自定义Dialog
 */

public class CustomDialog extends Dialog{

    //定义模板
    public CustomDialog(Context context, int layout, int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, layout, style, Gravity.CENTER);
    }

    //定义属性
    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity, int anim){
        super(context, style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams windowManager = window.getAttributes();
        windowManager.width = width;
        windowManager.height = height;
        windowManager.gravity = gravity;
        window.setAttributes(windowManager);
        window.setWindowAnimations(anim);
    }

    //实例
    public CustomDialog(Context context, int width, int height, int layout, int style, int gravity) {
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);
    }
}
