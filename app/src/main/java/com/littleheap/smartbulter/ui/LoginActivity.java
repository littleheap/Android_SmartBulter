package com.littleheap.smartbulter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.littleheap.smartbulter.MainActivity;
import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.entity.MyUser;
import com.littleheap.smartbulter.utlis.ShareUtils;
import com.littleheap.smartbulter.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register,btn_login;
    private EditText et_name,et_code;
    private CheckBox keep_password;
    private TextView forget_password;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();
    }

    private void initView() {
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        et_name = (EditText) findViewById(R.id.et_name);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        forget_password = (TextView) findViewById(R.id.tv_forget);
        forget_password.setOnClickListener(this);

        dialog = new CustomDialog(this,200,200,R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);


        //设置选中状态
        Boolean isCheck = ShareUtils.getBoolean(this,"keep_password",false);
        keep_password.setChecked(isCheck);
        if (isCheck){
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_code.setText(ShareUtils.getString(this,"code",""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                //1.获取输入框内的用户名和密码
                String name = et_name.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                //2.判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(code)){
                    //显示正在登陆
                    dialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(code);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            //消失正在登陆
                            dialog.dismiss();
                            //判断是否异常
                            if(e == null){
                                //判断邮箱是否验证成功
                                if (user.getEmailVerified()){
                                    //登录成功跳转进入主类
                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
        }
    }

    //登录成功之后，销毁时才保存用户名密码
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtils.putBoolean(this,"keep_password",keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()){
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"code",et_code.getText().toString().trim());
        }else {
            ShareUtils.deleShare(this,"name");
            ShareUtils.deleShare(this,"code");
        }
    }
}
