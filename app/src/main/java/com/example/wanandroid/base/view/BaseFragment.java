package com.example.wanandroid.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.base.presenter.BasePresenter;
import com.example.wanandroid.base.view.inter.IMvpView;

/**
 * Created by Golden on 2018/2/26.
 */

public abstract class BaseFragment<P extends BasePresenter ,V extends IMvpView> extends Fragment implements IMvpView {

    private static final String SAVED_STATE = "saved_state";
    private Context mContext;
    private P mPresenter;
    private boolean isViewReady;
    private boolean isFragmentVisible;
    private boolean isLoaded;
    private View mRootView;
    private Bundle mSavedState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public Context getActivityContext() {
        return mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView ==null){
            mRootView = LayoutInflater.from(mContext).inflate(getFragmentLayoutId(), container, false);
        }
        mPresenter = oncreatePresenter();
        mPresenter.attachView(((V) this));
        return mRootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isFragmentVisible = isVisibleToUser;
        // //如果视图准备完毕且Fragment处于可见状态，则开始初始化操作
        if (isViewReady && isFragmentVisible){
            onFragmentVisiable();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //视图准备完毕
        isViewReady = true;
        //如果视图准备完毕且Fragment处于可见状态，则开始初始化操作
        if (isViewReady && isFragmentVisible){
            onFragmentVisiable();
        }
        //如果之前有保存数据，则恢复数据
        restoreStateFromArguments();
    }




    protected abstract P  oncreatePresenter();


    protected abstract void initView();

    public abstract int getFragmentLayoutId();



    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        if (b != null) {
            mSavedState = b.getBundle(SAVED_STATE);
            if (mSavedState != null) {
                restoreState();
                return true;
            }
        }
        return false;
    }

    private void restoreState() {
        if (mSavedState != null) {
            onRestoreState(mSavedState);
        }
    }

    //保存数据
    private void saveStateToArguments() {
        if (getView() != null) {
            mSavedState = saveState();
        }
        if (mSavedState != null) {
            Bundle b = getArguments();
            if (b != null) {
                b.putBundle(SAVED_STATE, mSavedState);
            }
        }
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    private void onSaveState(Bundle state) {

    }

    protected abstract void onRestoreState(Bundle mSavedState);

    protected void onFragmentVisiable() {
        if (!isLoaded) {
            isLoaded = true;
            initView();
            obtainData();
            ////做事件监听的初始化
            initEvent();
        }
    }

    protected abstract void initEvent();

    protected abstract void obtainData();


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
        isViewReady = false;
        //保存数据
        saveStateToArguments();
    }



}
