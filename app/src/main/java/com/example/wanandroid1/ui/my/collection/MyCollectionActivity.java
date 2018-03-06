package com.example.wanandroid1.ui.my.collection;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.bean.KnowledgeSystem;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.ui.article.ArticleAdapter;
import com.example.wanandroid1.ui.article.ArticleContentActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Golden on 2018/3/6.
 */
@Route(path = "/collection/MyCollectionActivity")
public class MyCollectionActivity extends BaseActivity<CollectionPresenter> implements CollectionContact.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    @Inject
    ArticleAdapter mArticleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.collection_activity;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }

    @Override
    protected void initView() {
        setToolbarTitle(getString(R.string.my_collections));

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mArticleAdapter.bindToRecyclerView(recyclerview);

        mSwiperefreshlayout.setOnRefreshListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);
        mArticleAdapter.setOnItemClickListener(this);
        mArticleAdapter.setOnItemChildClickListener(this);

        mPresenter.loadCollectionData();
    }


    @Override
    public void onRefresh() {
        mPresenter.loadCollectionData();

    }

    @Override
    public void showLoading() {
        mSwiperefreshlayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwiperefreshlayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Article.DatasBean item = mArticleAdapter.getData().get(position);
        ArticleContentActivity.start(item.getId(), item.getLink(), item.getTitle(), item.getAuthor());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int id = view.getId();
        if (id == R.id.ivCollect){
            mPresenter.cancelCollection(mArticleAdapter.getData().get(position), position);
        }else if (id == R.id.tvChapterName){
            List<KnowledgeSystem.ChildrenBean> childrenBeans = new ArrayList<>();
            childrenBeans.add(new KnowledgeSystem.ChildrenBean(mArticleAdapter.getItem(position).getChapterId(),
                    mArticleAdapter.getItem(position).getChapterName()));
            ARouter.getInstance().build("/article/ArticleTypeActivity")
                    .withString(Constant.CONTENT_TITLE_KEY, mArticleAdapter.getItem(position).getChapterName())
                    .withObject(Constant.CONTENT_CHILDREN_DATA_KEY, childrenBeans)
                    .navigation();
        }
    }

    @Override
    public void refreshCollections(List<Article.DatasBean> response,int type) {
        setLoadDataResult(mArticleAdapter,mSwiperefreshlayout,response,type);
    }

    @Override
    public void cancelSuccess(int position) {
        mArticleAdapter.remove(position);
    }


}
