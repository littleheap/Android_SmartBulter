package com.littleheap.smartbulter.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.entity.MyUser;
import com.littleheap.smartbulter.utlis.UtliTools;

import cn.bmob.v3.b.From;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //手动更改密码组件
    private EditText et_now, et_new, et_new_password;
    Button btn_update_password;

    //邮箱更改密码组件
    private Button btn_forget_password;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    //初始化View
    private void initView() {
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);

        et_email = (EditText) findViewById(R.id.et_email);
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forget_password:
                //1.获取输入框邮箱
                final String email = et_email.getText().toString().trim();
                //2.判断邮箱是否为空
                if (!TextUtils.isEmpty(email)) {
                    //3.发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this, "邮件已发送至：" + email, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "邮件发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入邮箱不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_password:
                //1.获取输入框内容
                String now = et_now.getText().toString().trim();
                String new_pass = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(now) & !TextUtils.isEmpty(new_pass) & !TextUtils.isEmpty(new_password)) {
                    //3.判断两次密码是否一致
                    if (new_pass.equals(new_password)) {
                        //4.重置密码
                        MyUser.updateCurrentUserPassword(now, new_pass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框内容不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
