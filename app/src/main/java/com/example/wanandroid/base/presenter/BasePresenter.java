package com.example.wanandroid.base.presenter;

import android.content.Context;

import com.example.wanandroid.base.presenter.inter.IPresenter;
import com.example.wanandroid.base.view.inter.IMvpView;

import java.lang.ref.WeakReference;


/**
 * Created by Golden on 2018/2/26.
 */

public abstract class BasePresenter implements IPresenter {

    private IMvpView iMvpView;
    private WeakReference mWeak;

    @Override
    public void attachView(IMvpView iMvpView) {
        mWeak = new WeakReference(iMvpView);
        this.iMvpView = (IMvpView) mWeak.get();
    }

    @Override
    public void dettachView() {
        if(mWeak!=null){
            mWeak.clear();
            iMvpView =null;
        }
    }
}
