package com.example.wanandroid.homepage;

import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.view.BaseActivity;
import com.example.wanandroid.homepage.model.SimpleViewPagerAdapter;
import com.example.wanandroid.homepage.presenter.MainPresenter;
import com.example.wanandroid.homepage.view.Fragment1;
import com.example.wanandroid.homepage.view.inter.IMain;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter, IMain> {

    @BindView(R.id.topbar)
    QMUITopBar topbar;
    @BindView(R.id.vp)
    QMUIViewPager vp;
    @BindView(R.id.tabseg)
    QMUITabSegment tabseg;
    private List<Fragment> list =new ArrayList<>();


    @Override
    public MainPresenter onCreatePresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        topbar.addLeftBackImageButton();
        topbar.setTitle("玩Android");
        topbar.setBackgroundAlpha(50);
        topbar.setSubTitle("测试版");
        SearchView searchView =new SearchView(this);
        searchView.setQueryHint("输入查询内容");
        topbar.addRightView(searchView,R.id.searchview);
        QMUITabSegment.Tab tab =new QMUITabSegment.Tab(getString(R.string.title_home));
        QMUITabSegment.Tab tab2 =new QMUITabSegment.Tab(getString(R.string.title_knowledgesystem));
        QMUITabSegment.Tab tab3 =new QMUITabSegment.Tab(getString(R.string.title_my));
        tabseg.addTab(tab).addTab(tab2).addTab(tab3);
        tabseg.setupWithViewPager(vp);
        SimpleViewPagerAdapter adapter =new SimpleViewPagerAdapter(getSupportFragmentManager(),list);
        list.add(new Fragment1());
        list.add(new Fragment1());
        list.add(new Fragment1());
        vp.setAdapter(adapter);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initEvent() {

    }
}
