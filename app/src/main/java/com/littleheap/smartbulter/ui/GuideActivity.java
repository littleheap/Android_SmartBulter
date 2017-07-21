package com.littleheap.smartbulter.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.littleheap.smartbulter.MainActivity;
import com.littleheap.smartbulter.R;
import com.littleheap.smartbulter.utlis.L;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    //ViewPager
    private ViewPager mViewPager;
    //View列表
    private List<View> mList = new ArrayList<>();
    //View
    private View view1, view2, view3;
    //小圆点
    private ImageView point1, point2, point3;
    //跳过
    private ImageView iv_skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化View
        initView();
    }

    //初始化View
    private void initView() {

        iv_skip = (ImageView) findViewById(R.id.iv_skip);
        iv_skip.setOnClickListener(this);

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);

        //设置默认图片（第一个原点亮）
        setPointImage(true, false, false);

        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //Page切换回调
            @Override
            public void onPageSelected(int position) {
                L.i("position:" + position);
                switch (position) {
                    case 0://第一页
                        setPointImage(true, false, false);
                        iv_skip.setVisibility(View.VISIBLE);
                        break;
                    case 1://第二页
                        setPointImage(false, true, false);
                        iv_skip.setVisibility(View.VISIBLE);
                        break;
                    case 2://第三页
                        setPointImage(false, false, true);
                        iv_skip.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.iv_skip:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    //创建适配器内部类，填写四个方法，其中三个View列表从这里注入
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
        }
    }

    //设置原点选中的效果
    private void setPointImage(boolean isChecked1, boolean isChecked2, boolean isChecked3) {
        if (isChecked1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isChecked2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isChecked3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }

    }

}
