package com.example.wanandroid.base.presenter.inter;

import android.content.Context;

import com.example.wanandroid.base.view.inter.IMvpView;

/**
 * Created by Golden on 2018/2/26.
 */

public interface IPresenter<V extends IMvpView> {
    void attachView(V v);
    void dettachView();
}
