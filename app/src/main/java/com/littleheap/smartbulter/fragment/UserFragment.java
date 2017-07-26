package com.littleheap.smartbulter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.entity.MyUser;
import com.littleheap.smartbulter.ui.CourierActivity;
import com.littleheap.smartbulter.ui.LoginActivity;
import com.littleheap.smartbulter.utlis.L;
import com.littleheap.smartbulter.utlis.ShareUtils;
import com.littleheap.smartbulter.utlis.UtliTools;
import com.littleheap.smartbulter.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user, btn_update_ok, btn_camera, btn_picture, btn_cancel;
    private TextView edit_user, tv_courier;
    private EditText et_username, et_sex, et_age, et_desc;
    private CircleImageView profile_image;
    private CustomDialog dialog;
    private File tempFile = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);

        //初始化View
        findView(view);

        return view;

    }

    //初始化View
    private void findView(View view) {
        //退出登录
        btn_exit_user = view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);

        //编辑信息
        edit_user = view.findViewById(R.id.edit_user);
        edit_user.setOnClickListener(this);

        //用户属性
        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_desc = view.findViewById(R.id.et_desc);

        //确认更新
        btn_update_ok = view.findViewById(R.id.btn_update_ok);
        btn_update_ok.setOnClickListener(this);

        //圆形头像
        profile_image = view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);

        //设置之前圆形头像
        UtliTools.getImageFormShare(getActivity(), profile_image);

        //初始化Dialog
        dialog = new CustomDialog(getActivity(), R.layout.dialog_photo, R.style.Theme_dialog);
        //提示框外点击无效
        dialog.setCancelable(false);

        //圆形头像处理按钮
        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        //EditText默认是不可输入的
        setEnable(false);

        //设置当前用户属性值
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(user.getUsername());
        et_sex.setText(user.isSex() ? "男" : "女");
        et_age.setText(user.getAge() + "");
        et_desc.setText(user.getDesc());

        //快递
        tv_courier = view.findViewById(R.id.tv_courier);
        tv_courier.setOnClickListener(this);

    }

    //EditText是否可以输入
    private void setEnable(Boolean enable) {
        et_username.setEnabled(enable);
        et_sex.setEnabled(enable);
        et_age.setEnabled(enable);
        et_desc.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit_user:
                //退出登录
                //1.清除缓存对象
                MyUser.logOut();
                //2.通过New把用户转换成null
                BmobUser currentUser = MyUser.getCurrentUser();
                //3.跳转至登录界面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.edit_user:
                setEnable(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok:
                //1.拿到输入框的值
                String username = et_username.getText().toString().trim();
                String sex = et_sex.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(sex) & !TextUtils.isEmpty(age)) {
                    //3.获取新用户属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setSex(sex.equals("男") ? true : false);
                    user.setAge(Integer.parseInt(age));
                    if (!TextUtils.isEmpty(desc)) {
                        user.setDesc(desc);
                    } else {
                        user.setDesc("这个人很懒，什么都没有留下");
                    }
                    //4.更新服务器用户属性
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEnable(false);
                                btn_update_ok.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.profile_image:
                dialog.show();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.btn_camera:
                toCamera();
                break;
            case R.id.btn_picture:
                toPicture();
                break;
            case R.id.tv_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //可能点击舍弃
                    if (data != null) {
                        //拿到图片设置
                        setImageView(data);
                        //设置新图片，删除旧图片
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UtliTools.putImageToShare(getActivity(), profile_image);
    }
}
