package com.littleheap.smartbulter.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.adapter.CourierAdapter;
import com.littleheap.smartbulter.entity.MyCourier;
import com.littleheap.smartbulter.utlis.L;
import com.littleheap.smartbulter.utlis.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangs on 2017/7/26.
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name, et_number;
    private Button btn_get_courier;
    private ListView mListView;
    private List<MyCourier> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();

    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        mListView = (ListView) findViewById(R.id.mListView);
        btn_get_courier = (Button) findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_courier:
                /**
                 * 1.获取输入框内容
                 * 2.判断是否为空
                 * 3.请求JSON
                 * 4.解析JSON
                 * 5.适配ListView
                 * 6.适配器实体类
                 * 7.显示数据
                 */
                //1.获取输入框内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                //拼接URL
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY + "&com=" + name + "&no=" + number;
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //3.请求JSON
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
//                            Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
//                            L.i("JSON:" + t);
                            //4.解析JSON数据
                            try {
                                parsingJSON(t);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //解析JSON数据
    private void parsingJSON(String t) throws JSONException {
        JSONObject jsonObject = new JSONObject(t);
        JSONObject jsonResult = jsonObject.getJSONObject("result");
        JSONArray jsonArray = jsonResult.getJSONArray("list");
        for (int i = 0; i<jsonArray.length(); i++){
            JSONObject json = (JSONObject) jsonArray.get(i);
            //数据整合
            MyCourier data = new MyCourier();
            data.setRemark(json.getString("remark"));
            data.setZone(json.getString("zone"));
            data.setZone(json.getString("datetime"));
            list.add(data);
        }
        CourierAdapter adapter = new CourierAdapter(this,list);
        mListView.setAdapter(adapter);
    }
}
