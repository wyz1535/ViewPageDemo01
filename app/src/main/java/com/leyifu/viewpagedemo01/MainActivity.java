package com.leyifu.viewpagedemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leyifu.viewpagedemo01.view.MyViewPager;


public class MainActivity extends AppCompatActivity {

    private MyViewPager mvp;

    private int[] ivs = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private String[] titles = new String[]{"穿梭幻想世界带你飞", "咪咕阅读百本神作", "神奇动物在哪里", "手机拍张美照", "吃货必备神器"};
    private String[] URL = new String[]{"https://m.baidu.com/?from=2001a",
            "https://sina.cn/?wm=4007",
            "https://sports.sina.cn/?vt=4&pos=108&wm=4007&HTTPS=1",
            "https://tech.sina.cn/?vt=4&pos=108&wm=4007&HTTPS=1",
            "http://auto.sina.cn/?vt=4&pos=108&wm=4007"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mvp = ((MyViewPager) findViewById(R.id.vp));
        mvp.setDatas(ivs, titles, URL);
    }
}
