package com.littleheap.smartbulter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.entity.MyCourier;

import java.util.List;

/**
 * Created by wangs on 2017/7/26.
 * Description:快递查询
 */

public class CourierAdapter extends BaseAdapter {

    private Context context;
    private List<MyCourier> list;
    private MyCourier myCourier;
    //布局加载器
    private LayoutInflater inflater;

    public CourierAdapter(Context context, List<MyCourier> list) {
        this.context = context;
        this.list = list;
        //获取系统服务
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        //判断是不是第一次加载
        if (view == null) {
            //第一次加载
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_courier_item, null);
            //初始化TextView
            viewHolder.tv_remark = view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = view.findViewById(R.id.tv_zone);
            viewHolder.tv_datatime = view.findViewById(R.id.tv_datetime);
            //设置缓存
            view.setTag(viewHolder);
        } else {
            //不是第一次加载
            viewHolder = (ViewHolder) view.getTag();
        }
        //设置数据
        myCourier = list.get(i);
        viewHolder.tv_remark.setText(myCourier.getRemark());
        viewHolder.tv_zone.setText(myCourier.getZone());
        viewHolder.tv_datatime.setText(myCourier.getDatetime());
        return view;
    }

    class ViewHolder {
        private TextView tv_remark, tv_zone, tv_datatime;
    }
}
