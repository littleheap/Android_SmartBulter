package com.littleheap.smartbulter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.adapter.ChatListAdapter;
import com.littleheap.smartbulter.entity.MyChatList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class BulterFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private Button btn_left, btn_right;

    private List<MyChatList> mList = new ArrayList<>();
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bulter, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mChatListView = view.findViewById(R.id.mChatListView);
        btn_left = view.findViewById(R.id.btn_send1);
        btn_right = view.findViewById(R.id.btn_send2);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        //设置适配器
        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);
        //测试小管家先说话
        addLeftItem("I am Bulter");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send1:
                addLeftItem("left");
                break;
            case R.id.btn_send2:
                addRightItem("right");
                break;
        }

    }

    //添加左边文本
    private void addLeftItem(String text) {
        MyChatList date = new MyChatList();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边文本
    private void addRightItem(String text) {
        MyChatList date = new MyChatList();
        date.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
