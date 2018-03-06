package com.example.wanandroid1.ui.konwledge;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseFragment;
import com.example.wanandroid1.bean.KnowledgeSystem;
import com.example.wanandroid1.constant.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Golden on 2018/3/5.
 */

public class KnowledgeSystemFragment extends BaseFragment<KnowledgePresenter> implements KnowledgeContact.View, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rcv)
    RecyclerView mRecycleView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;
    @Inject
    KnowledgeSystemAdapter mKnowledgeSystemAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_main;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public void showLoading() {
        mSwiperefreshlayout.setRefreshing(true);
    }

    @Override
    protected void initView(View view) {

        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mKnowledgeSystemAdapter.bindToRecyclerView(mRecycleView);

        mSwiperefreshlayout.setOnRefreshListener(this);
        mKnowledgeSystemAdapter.setOnItemClickListener(this);
//        mKnowledgeSystemAdapter.setOnItemChildClickListener(this);

        mPresenter.getKnowledgeData();
    }





    public static KnowledgeSystemFragment newInstance() {
       return new KnowledgeSystemFragment();
    }

    @Override
    public void refreshKnowledgeSystems(List<KnowledgeSystem> knowledgeSystems) {
        mKnowledgeSystemAdapter.setNewData(knowledgeSystems);
        mSwiperefreshlayout.setRefreshing(false);

    }




    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build("/article/ArticleTypeActivity")
                .withString(Constant.CONTENT_TITLE_KEY, mKnowledgeSystemAdapter.getItem(position).getName())
                .withObject(Constant.CONTENT_CHILDREN_DATA_KEY, mKnowledgeSystemAdapter.getItem(position).getChildren())
                .navigation();
    }



    @Override
    public void onRefresh() {
        mPresenter.getKnowledgeData();
    }
}
