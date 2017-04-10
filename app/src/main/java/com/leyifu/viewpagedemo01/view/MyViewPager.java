package com.leyifu.viewpagedemo01.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyifu.viewpagedemo01.R;
import com.leyifu.viewpagedemo01.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hahaha on 2017/4/7 0007.
 */
public class MyViewPager extends LinearLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = "MyViewPager";
    private ViewPager view_pager;
    private TextView tv_title;
    private View view;
    private ImageView imageView;

    private int[] ivs;
    private String[] titles;
    private List<ImageView> imageViews;
    private LinearLayout ll_point;

    private int modifyPosition;
    private int position;
    private int prePosition = 1;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        view = View.inflate(getContext(), R.layout.view_pager, this);
        initUI(view);
    }

    private void initUI(View view) {
        view_pager = ((ViewPager) view.findViewById(R.id.view_pager));
        tv_title = ((TextView) view.findViewById(R.id.tv_title));
        ll_point = ((LinearLayout) view.findViewById(R.id.ll_point));
    }

    public void setDatas(int[] ivs, String[] titles) {
        this.titles = titles;
        imageViews = new ArrayList<>();
        for (int i = 0; i < ivs.length + 2; i++) {
            ImageView imageView = new ImageView(getContext());
            if (i == 0) {
                imageView.setImageResource(ivs[ivs.length - 1]);
            } else if (i == ivs.length + 1) {
                imageView.setImageResource(ivs[0]);
            } else {
                imageView.setImageResource(ivs[i - 1]);

                //添加点
                View child = new View(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
                params.rightMargin = 10;
                child.setLayoutParams(params);
                child.setBackgroundResource(R.drawable.point_selector);

                child.setOnClickListener(this);
                child.setTag(i);

                ll_point.addView(child);

            }
            imageViews.add(imageView);
        }

        view_pager.setAdapter(new MyAdapter(getContext(), imageViews, titles));

        view_pager.setCurrentItem(1);
        tv_title.setText(titles[0]);
        ll_point.getChildAt(0).setEnabled(false);
    }

    private Handler handler = new Handler();
    private boolean isScroll = true;
    //与自定义窗体绑定的时候开始监听
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isScroll = true;
        view_pager.addOnPageChangeListener(this);
        //自动播放第一张图片
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isScroll){
                    Log.e(TAG, "run: 我正在自动滚动" );
                    view_pager.setCurrentItem(view_pager.getCurrentItem()+1);

                    //自动播放后面的图片
                    handler.postDelayed(this, 3000);
                }
            }
        },3000);
    }

    //与自定义窗体解除绑定的时候移除监听
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        view_pager.removeOnPageChangeListener(this);
        isScroll=false;
    }

    //页面滚动的时候
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e(TAG, "onPageScrolled: 1111");
    }

    //页面被选择的时候
    @Override
    public void onPageSelected(int position) {
        this.modifyPosition = position;
        Log.e(TAG, "onPageSelected:22222  " + position + "modifyPosition" + modifyPosition);
        this.position = position;
        if (position == 0) {
            modifyPosition = imageViews.size() - 2;
        } else if (position == imageViews.size() - 1) {
            modifyPosition = 1;
        }

//        让当前点变红 上一次点变白
        if (prePosition != modifyPosition) {
            tv_title.setText(titles[modifyPosition - 1]);
            ll_point.getChildAt(modifyPosition - 1).setEnabled(false);
            ll_point.getChildAt(prePosition - 1).setEnabled(true);
            prePosition = modifyPosition;
        }
    }

    //页面滚动状态发生改变的时候
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            Log.e(TAG, "onPageScrollStateChanged:3333");
            if (this.position == 0 || this.position == imageViews.size() - 1) {
                //当为flase时 从第一张跳到最后一张不会有弹动的页面  为true时 会显示弹动的页面
                view_pager.setCurrentItem(modifyPosition, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        view_pager.setCurrentItem(tag);
    }
}
