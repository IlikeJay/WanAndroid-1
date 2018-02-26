package com.example.wanandroid.base.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.base.presenter.BasePresenter;
import com.example.wanandroid.base.util.ActivityStackManager;
import com.example.wanandroid.base.view.inter.IMvpView;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.hongfans.common.log.LogUtil;

/**
 * Created by Golden on 2018/2/26.
 */

@SuppressLint("Registered")
public abstract class BaseActivity<P extends BasePresenter,V extends IMvpView> extends AbstractActivity implements IMvpView  {
    P mPresenter;
    private ActivityStackManager mStackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(getLayoutId());
        mPresenter = onCreatePresenter();
        mPresenter.attachView(((V) this));
        initBarColor();//初始化状态栏/导航栏颜色，需在设置了布局后再调用
        initView();//由具体的activity实现，做视图相关的初始化
        obtainData();//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化

    }

    private void initBarColor() {
        int color = getResources().getColor(R.color.colorPrimary);
        setBarColor(color, 0, color, 0);
    }

    private void setBarColor(int statusColor, int statusAlpha, int navColor, int navAlpha) {
        UltimateBar.newColorBuilder()
                .statusColor(statusColor)       // 状态栏颜色
                .statusDepth(statusAlpha)                // 状态栏颜色深度
                .applyNav(true)                 // 是否应用到导航栏
                .navColor(navColor)             // 导航栏颜色
                .navDepth(navAlpha)                   // 导航栏颜色深度
                .build(this)
                .apply();
    }


    //单独设置状态栏的颜色，第二个参数控制透明度，布局内容不占据状态栏空间
    public void setStatusBarColor(int color, int alpha) {
        UltimateBar.newColorBuilder().statusColor(color).statusDepth(alpha);
    }

    //设置状态栏、导航栏颜色（有DrawerLayout时可使用这种），第二个参数控制透明度，布局内容不占据状态栏空间
    public void setBarColorForDrawer(int statusColor, int statusAlpha, int navColor, int navAlpha) {
        UltimateBar.newColorBuilder().statusColor(statusColor).statusDepth(statusAlpha).applyNav(true).navColor(navColor).navDepth(navAlpha).build(this).apply();
    }

    //单独设置状态栏的颜色（有DrawerLayout时可使用这种），第二个参数控制透明度，布局内容不占据状态栏空间
    public void setStatusBarColorForDrawer(int color, int alpha) {
        UltimateBar.newColorBuilder().statusColor(color).statusDepth(alpha).build(this).apply();
    }

    //设置半透明的状态栏、导航栏颜色，第二个参数控制透明度，布局内容占据状态栏空间
    public void setBarTranslucent(int statusColor, int statusAlpha, int navColor, int navAlpha) {
        UltimateBar.newColorBuilder().statusColor(statusColor).statusDepth(statusAlpha).applyNav(true).navColor(navColor).navDepth(navAlpha).build(this).apply();

    }

    //单独设置半透明的状态栏颜色，第二个参数控制透明度，布局内容不占据状态栏空间
    public void setStatusBarTranslucent(int color, int alpha) {
        UltimateBar.newColorBuilder().statusColor(color).statusDepth(alpha).build(this).apply();
    }

    //设置全透明的状态栏、导航栏颜色，布局内容占据状态栏空间，参数为是否也应用到
    public void setTransparentBar(boolean applyNav) {
        UltimateBar.newColorBuilder().applyNav(applyNav).build(this).apply();

    }

    //隐藏状态栏、导航栏，布局内容占据状态栏导航栏空间，参数为是否也应用到导航栏上
    public void hideBar(boolean applyNav) {
        UltimateBar.newColorBuilder().applyNav(applyNav).build(this).apply();
    }

    /**
     * 获取页面的堆栈管理
     */
    public ActivityStackManager getActivityStackManager() {
        return mStackManager;
    }

    @Override
    public void onLowMemory() {
        LogUtil.e("内存不足");
        //清空图片内存缓存（包括Bitmap缓存和未解码图片的缓存）
        Glide.get(this).clearMemory();
//        Glide.get(this).clearDiskCache();
        super.onLowMemory();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mOnKeyClickListener != null) {//如果没有设置返回事件的监听，则默认finish页面。
                    mOnKeyClickListener.clickBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    OnKeyClickListener mOnKeyClickListener;

    public void setOnKeyListener(OnKeyClickListener onKeyClickListener) {
        this.mOnKeyClickListener = onKeyClickListener;
    }

    /**
     * 按键的监听，供页面设置自定义的按键行为
     */
    public interface OnKeyClickListener {
        /**
         * 点击了返回键
         */
        void clickBack();

        //可加入其它按键事件
    }


//    // 只有魅族（Flyme4+），小米（MIUI6+），android（6.0+）可以设置状态栏中图标、字体的颜色模式（深色模式/亮色模式）
//    public boolean setStatusBarMode(boolean isDark) {
//        Window window = getWindow();
//        return SystemTypeUtil.setStatusBarLightMode(window, isDark);
//    }


    private void init() {
        mStackManager = ActivityStackManager.getInstance();
        mStackManager.addActivity(this);
    }


    public abstract P onCreatePresenter();

    public abstract int getLayoutId();

    @Override
    public Context getActivityContext() {
        return this;
    }
}
