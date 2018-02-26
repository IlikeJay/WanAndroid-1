package com.example.wanandroid.base.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.wanandroid.base.presenter.BasePresenter;
import com.example.wanandroid.base.view.inter.IMvpView;

/**
 * Created by Golden on 2018/2/26.
 */

@SuppressLint("Registered")
public abstract class BaseActivity<P extends BasePresenter,V extends IMvpView> extends AppCompatActivity implements IMvpView  {
    P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mPresenter = onCreatePresenter();
        mPresenter.attachView(((V) this));
        handlerIntent(getIntent());
        initView(savedInstanceState);

    }

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void handlerIntent(Intent intent);


    public abstract P onCreatePresenter();

    public abstract int getLayoutId();

    @Override
    public Context getActivityContext() {
        return this;
    }
}
