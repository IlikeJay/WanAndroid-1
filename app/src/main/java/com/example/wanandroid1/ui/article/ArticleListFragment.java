package com.example.wanandroid1.ui.article;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseFragment;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.event.LoginEvent;
import com.example.wanandroid1.utils.RxBus;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/5.
 */
@Route(path = "/article/ArticleListFragment")
public class ArticleListFragment extends BaseFragment<ArticleListPresenter> implements ArticleListContact.View, ArticleAdapter.OnItemClickListener, ArticleAdapter.OnItemChildClickListener,
        SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.RequestLoadMoreListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @Inject
    ArticleAdapter articleAdapter;
    @Autowired(name = "cid")
    int id;

    @Override
    protected int getLayoutId() {
        return R.layout.articletypelist_fragment;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
        articleAdapter.setChapterNameVisible(false);

        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(articleAdapter);

        articleAdapter.setOnItemChildClickListener(this);
        articleAdapter.setOnItemClickListener(this);
        swiperefreshlayout.setOnRefreshListener(this);
        articleAdapter.setOnLoadMoreListener(this);

        mPresenter.loadData(id);

        /**登陆成功刷新*/
        RxBus.getInstance().toFlowable(LoginEvent.class)
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent event) throws Exception {
                        mPresenter.refresh();
                    }
                });

    }

    @Override
    public void showLoading() {
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.ivCollect) {
            mPresenter.collectArticle(position, articleAdapter.getItem(position));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticleContentActivity.start(articleAdapter.getItem(position).getId(),
                articleAdapter.getItem(position).getLink(), articleAdapter.getItem(position).getTitle(),
                articleAdapter.getItem(position).getAuthor());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }

    @Override
    public void refresh(Article article, int loadType) {
        setLoadDataResult(articleAdapter, swiperefreshlayout, article.getDatas(), loadType);
    }

    @Override
    public void collectSuccess(int position, Article.DatasBean item) {
        articleAdapter.setData(position,item);
    }


}
