package com.example.wanandroid1.ui.article;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wanandroid1.bean.KnowledgeSystem;
import com.example.wanandroid1.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Golden on 2018/3/5.
 */

public class ArticleTypePageAdapter extends FragmentPagerAdapter {

    private  List<KnowledgeSystem.ChildrenBean> childrenBeans;
    private  ArrayList<Fragment> mArticleTypeFragments;

    public ArticleTypePageAdapter(FragmentManager fm, List<KnowledgeSystem.ChildrenBean>childrenBeans) {
        super(fm);
        this.childrenBeans = childrenBeans;
        mArticleTypeFragments = new ArrayList<>();
        if (childrenBeans == null) return;
        for (KnowledgeSystem.ChildrenBean childrenBean : childrenBeans) {
            ArticleListFragment articleListFragment = (ArticleListFragment) ARouter.getInstance()
                    .build("/article/ArticleListFragment")
                    .withInt(Constant.CONTENT_CID_KEY, childrenBean.getId())
                    .navigation();
            mArticleTypeFragments.add(articleListFragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mArticleTypeFragments.get(position);
    }

    @Override
    public int getCount() {
        return mArticleTypeFragments==null?0:mArticleTypeFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return childrenBeans.get(position).getName();
    }

}
