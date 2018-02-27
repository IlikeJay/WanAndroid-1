package com.example.wanandroid.homepage.view;

import android.os.Bundle;

import com.example.wanandroid.base.presenter.BasePresenter;
import com.example.wanandroid.base.view.BaseFragment;

/**
 * Created by Golden on 2018/2/27.
 */

public class Fragment1 extends BaseFragment {
    @Override
    protected BasePresenter oncreatePresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    public int getFragmentLayoutId() {
        return com.qmuiteam.qmui.R.layout.abc_action_menu_item_layout;
    }

    @Override
    protected void onRestoreState(Bundle mSavedState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void obtainData() {

    }
}
