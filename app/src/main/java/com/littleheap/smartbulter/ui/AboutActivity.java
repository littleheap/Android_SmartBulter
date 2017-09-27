package com.littleheap.smartbulter.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.utlis.UtliTools;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends BaseActivity {

    private ListView mListView;
    private List<String>mList = new ArrayList<>();
    private ArrayAdapter<String>mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去除阴影
        getSupportActionBar().setElevation(0);

        initView();
    }

    //初始化View
    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);

        mList.add("应用名：" + getString(R.string.app_name));
        mList.add("版本号："+ UtliTools.getVersion(this));
        mList.add("官网：http://www.LittleHeap.com");

        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        //设置适配器
        mListView.setAdapter(mAdapter);
    }
}
