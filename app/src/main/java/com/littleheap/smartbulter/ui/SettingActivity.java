package com.littleheap.smartbulter.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.service.SmsService;
import com.littleheap.smartbulter.utlis.L;
import com.littleheap.smartbulter.utlis.ShareUtils;
import com.littleheap.smartbulter.utlis.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    //语音播报
    private Switch sw_speak;
    //短信提醒
    private Switch sw_sms;
    //检测更新
    private LinearLayout ll_update;
    private TextView tv_version;
    private String versionName;
    private int versionCode;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    //初始化View
    private void initView() {

        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        boolean isSpeak = ShareUtils.getBoolean(this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);

        sw_sms = (Switch) findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);
        boolean isSms = ShareUtils.getBoolean(this, "isSms", false);
        sw_sms.setChecked(isSms);

        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setOnClickListener(this);
        try {
            getVersionNameCode();
            tv_version.setText("检测版本" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tv_version.setText("检测失败");
        }

//        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
//        ll_scan.setOnClickListener(this);
//
//        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);
//
//        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
//        ll_qr_code.setOnClickListener(this);
//
//        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
//        ll_my_location.setOnClickListener(this);
//
//        ll_about = (LinearLayout) findViewById(R.id.ll_about);
//        ll_about.setOnClickListener(this);
//
//        ll_update = (LinearLayout) findViewById(R.id.ll_update);
//        ll_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sw_speak:
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                ShareUtils.putBoolean(this, "isSpeak", sw_speak.isChecked());
                break;
            case R.id.sw_sms:
                //切换相反
                sw_sms.setSelected(!sw_sms.isSelected());
                //保存状态
                ShareUtils.putBoolean(this, "isSms", sw_sms.isChecked());
                if (sw_sms.isChecked()) {
                    //开启短息监听服务
                    startService(new Intent(this, SmsService.class));
                } else {
                    //关闭短信监听服务
                    stopService(new Intent(this, SmsService.class));
                }
                break;
            case R.id.tv_version:
                L.i("ll_update");
                /**
                 * 步骤:
                 * 1.请求服务器的配置文件，拿到code
                 * 2.比较
                 * 3.dialog提示
                 * 4.跳转到更新界面，并且把url传递过去
                 */
                RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i(t);
                        parsingJson(t);
                    }
                });
                break;
//            case R.id.ll_scan:
//                L.i("ll_scan");
//                //打开扫描界面扫描条形码或二维码
////                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
////                startActivityForResult(openCameraIntent, 0);
//                break;
//            case R.id.ll_qr_code:
//                startActivity(new Intent(this, QrCodeActivity.class));
//                break;
//            case R.id.ll_my_location:
//                startActivity(new Intent(this,LocationActivity.class));
//                break;
//            case R.id.ll_about:
//                startActivity(new Intent(this,AboutActivity.class));
//                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int code = jsonObject.getInt("versionCode");
            if(code > versionCode){
                showUpdateDialog(jsonObject.getString("content"));
            }else {
                Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //弹出升级提示
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this)
                .setTitle("有新版本啦！")
                //content
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingActivity.this, UpdatActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //我什么都不做，也会执行dismis方法
            }
        }).show();
    }

    //获取版本号/Code
    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        versionName = info.versionName;
        versionCode = info.versionCode;
    }
}
