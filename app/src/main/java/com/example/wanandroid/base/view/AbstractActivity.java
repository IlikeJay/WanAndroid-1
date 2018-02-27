package com.example.wanandroid.base.view;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Golden on 2018/2/26.
 */

public abstract class AbstractActivity extends AppCompatActivity {
    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();
    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化监听
     */
    protected abstract void initEvent();
}