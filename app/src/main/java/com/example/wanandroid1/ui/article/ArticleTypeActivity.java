package com.example.wanandroid1.ui.article;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;
import com.example.wanandroid1.bean.KnowledgeSystem;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Golden on 2018/3/5.
 */
@Route(path = "/article/ArticleTypeActivity")
public class ArticleTypeActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Autowired
    public String title;
    @Autowired
    public List<KnowledgeSystem.ChildrenBean> childrenData;

    @Override
    protected int getLayoutId() {
        return R.layout.articletype_activity;
    }

    @Override
    protected void initInjector() {
//        mActivityComponent.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_type, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void initView() {
        setToolbarTitle(title);
        ArticleTypePageAdapter typePageAdapter =new ArticleTypePageAdapter(getSupportFragmentManager(),childrenData);
        viewpager.setAdapter(typePageAdapter);
        tablayout.setupWithViewPager(viewpager);
    }

    /**
     * 是否显示返回键
     * @return
     */
    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menuShare) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_type_url, getString(R.string.app_name),
                    childrenData.get(tablayout.getSelectedTabPosition()).getName(), childrenData.get(tablayout.getSelectedTabPosition()).getId()));
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, getString(R.string.share_title)));
        } else if (itemId == R.id.menuShare) {
            ARouter.getInstance().build("/search/SearchActivity").navigation();
        }
        return super.onOptionsItemSelected(item);

    }




}
