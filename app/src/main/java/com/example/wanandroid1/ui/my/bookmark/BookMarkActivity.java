package com.example.wanandroid1.ui.my.bookmark;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;

import butterknife.BindView;
@Route(path = "/bookmark/BookMarkActivity")
public class BookMarkActivity extends BaseActivity<BookMarkPresenter> implements BookMarkContact.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_mark;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }
}
