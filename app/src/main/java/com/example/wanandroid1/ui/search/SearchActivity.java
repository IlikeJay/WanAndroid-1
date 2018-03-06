package com.example.wanandroid1.ui.search;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.bean.KnowledgeSystem;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.db.HistoryModel;
import com.example.wanandroid1.event.LoginEvent;
import com.example.wanandroid1.ui.article.ArticleAdapter;
import com.example.wanandroid1.ui.article.ArticleContentActivity;
import com.example.wanandroid1.utils.RxBus;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route(path = "/search/SearchActivity")
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContact.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rcv)
    RecyclerView mRecycleView;
    @BindView(R.id.wrl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    ArticleAdapter mArticleAdapter;
    @Autowired
    public String hotNameKey;

    private TagFlowLayout mTagFlowLayout;
    private HistoryAdapter mHistoryAdapter;
    private List<HistoryModel> historyModels = new ArrayList<>();
    private SearchView mSearchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        //初始化recyclerview
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mArticleAdapter);
        /**设置SearchHeadView*/
        View headView = getLayoutInflater().inflate(R.layout.head_history_search, null);
        mTagFlowLayout = (TagFlowLayout) headView.findViewById(R.id.tfl);
        mArticleAdapter.addHeaderView(headView);
        initListener();
        mPresenter.loadHistory();

        /**登陆成功刷新*/
        RxBus.getInstance().toFlowable(LoginEvent.class)
                .subscribe(new Consumer<LoginEvent>() {
                    @Override
                    public void accept(LoginEvent event) throws Exception {
                        mPresenter.loadHistory();
                    }
                });

        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String name = mHistoryAdapter.getItem(position).getName();
                mSearchView.setQuery(name, false);
                mPresenter.loadResults(name);
                return false;
            }
        });


    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }


    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter.setOnItemClickListener(this);
        mArticleAdapter.setOnItemChildClickListener(this);
        mArticleAdapter.setOnLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshArticleData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Article.DatasBean datasBean = mArticleAdapter.getData().get(position);
        ArticleContentActivity.start(datasBean.getId(),datasBean.getLink(),datasBean.getTitle(),datasBean.getAuthor());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tvChapterName) {
            List<KnowledgeSystem.ChildrenBean> childrenBeans = new ArrayList<>();
            childrenBeans.add(new KnowledgeSystem.ChildrenBean(mArticleAdapter.getItem(position).getChapterId(),
                    mArticleAdapter.getItem(position).getChapterName()));
            ARouter.getInstance().build("/article/ArticleTypeActivity")
                    .withString(Constant.CONTENT_TITLE_KEY, mArticleAdapter.getItem(position).getChapterName())
                    .withObject(Constant.CONTENT_CHILDREN_DATA_KEY, childrenBeans)
                    .navigation();
        } else if (view.getId() == R.id.ivCollect) {
            mPresenter.collectArticle(position, mArticleAdapter.getItem(position));
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview,menu);
        mSearchView = (SearchView) menu.findItem(R.id.menu_searchview).getActionView();
        mSearchView.setMaxWidth(1920);
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.addHistory(query);
                mPresenter.loadResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SearchActivity.this.finish();
                return true;
            }
        });
        mSearchView.setQuery(hotNameKey,true);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void addHistorySuccess(HistoryModel historyModel) {
        historyModels.add(0,historyModel);
        mHistoryAdapter.notifyDataChanged();
    }

    @Override
    public void refreshHistory(List<HistoryModel> historyModels) {
        this.historyModels = historyModels == null? new ArrayList<HistoryModel>(0):historyModels;
        mHistoryAdapter = new HistoryAdapter(this,historyModels);
        mTagFlowLayout.setAdapter(mHistoryAdapter);
//        mHistoryAdapter.notifyDataChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshArticles(Article article, int loadType) {
        setLoadDataResult(mArticleAdapter, mSwipeRefreshLayout, article.getDatas(), loadType);
    }
}
