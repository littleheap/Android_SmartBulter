package com.littleheap.smartbulter.utlis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

    //保存图片到ShareUtils下
    public static void putImageToShare(Context context, ImageView profile_image) {
        //保存头像
        BitmapDrawable bd = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = bd.getBitmap();
        //将图片压缩成字节输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //利用Base64将字节输出流转化成String
        byte[] bytes = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //将String保存
        ShareUtils.putString(context, "image_title", imgString);
    }

    //从ShareUtils中获取图片
    public static void getImageFormShare(Context context, ImageView profile_image) {
        //拿到String数据流
        String imgString = ShareUtils.getString(context, "image_title", "");
        if (!imgString.equals("")) {
            //Base64将String转化为流
            byte[] bytes = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(bytes);
            //生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            profile_image.setImageBitmap(bitmap);
        }
    }

    //获取版本号
    public static String getVersion(Context mContext){
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "未知";
        }
    }
}
