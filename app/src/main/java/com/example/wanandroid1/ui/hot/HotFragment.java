package com.example.wanandroid1.ui.hot;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseFragment;
import com.example.wanandroid1.bean.Friend;
import com.example.wanandroid1.bean.HotKey;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.ui.article.ArticleContentActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Golden on 2018/3/2.
 */
public class HotFragment extends BaseFragment<HotPresenter> implements HotContact.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    public CommonHotAdapter mCommonHotAdapter;
    @BindView(R.id.rcv)
    RecyclerView mRecycleview;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private View mHotHeadView;
    private TagFlowLayout mTtlBookMarks;
    private TagFlowLayout mTflHotKeys;
    private TagFlowLayout mTflHotFriends;
    private HotAdapter<HotKey> mHotKeyAdapter;
    private HotAdapter<Friend> mHotFriendAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.hot_fragment;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {

        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));
        mCommonHotAdapter.bindToRecyclerView(mRecycleview);

        /**设置HotHeadView*/
        mHotHeadView = LayoutInflater.from(getContext()).inflate(R.layout.layout_hot_head, null);
        mTtlBookMarks = (TagFlowLayout) mHotHeadView.findViewById(R.id.tflBookMarks);
        mTflHotKeys = (TagFlowLayout) mHotHeadView.findViewById(R.id.tflHotKeys);
        mTflHotFriends = (TagFlowLayout) mHotHeadView.findViewById(R.id.tflHotFriends);
        mCommonHotAdapter.addHeaderView(mHotHeadView);

        /**设置监听*/
        setListener();

        /**请求数据*/
        mPresenter.loadHotData();

    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTflHotKeys.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String name = mHotKeyAdapter.getItem(position).getName();
                ARouter.getInstance().build("/search/SearchActivity")
                        .withString(Constant.CONTENT_HOT_NAME_KEY, name)
                        .navigation();
                return false;
            }
        });
        mTflHotFriends.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ArticleContentActivity.start(mHotFriendAdapter.getItem(position).getId(),
                        mHotFriendAdapter.getItem(position).getLink(), mHotFriendAdapter.getItem(position).getName(),
                        null);
                return false;
            }
        });

    }

    public static HotFragment newInstance() {
        return new HotFragment();
    }




    @Override
    public void onRefresh() {
        mPresenter.loadHotData();
    }

    @Override
    public void setHotData(List<HotKey> hotKeys, List<Friend> friends) {
        mHotKeyAdapter = new HotAdapter(getContext(), hotKeys);
        mTflHotKeys.setAdapter(mHotKeyAdapter);

        mHotFriendAdapter = new HotAdapter<>(getContext(), friends);
        mTflHotFriends.setAdapter(mHotFriendAdapter);

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
