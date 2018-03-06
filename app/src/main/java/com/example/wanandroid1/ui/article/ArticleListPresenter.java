package com.example.wanandroid1.ui.article;

import com.blankj.utilcode.util.SPUtils;
import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.constant.LoadType;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.ui.my.LoginActivity;
import com.example.wanandroid1.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/5.
 */

public class ArticleListPresenter extends BasePresenter<ArticleListContact.View> implements ArticleListContact.Presenter {

    private boolean mIsRefresh;
    private int mPage;
    private int mCid;

    @Inject
    public ArticleListPresenter() {
        mIsRefresh = true;
    }

    @Override
    public void loadData(int id) {
        mCid = id;
        RetrofitManager.create(ApiService.class)
                .getKnowledgeSystemArticles(mPage,id)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers())
                .compose(mView.<DataResponse<Article>>bindToLife())
                .subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> dataResponse) throws Exception {
                int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                mView.refresh(dataResponse.getData(), loadType);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                mView.refresh(new Article(), loadType);
            }
        });
    }

    @Override
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadData(mCid);
    }

    @Override
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadData(mCid);
    }

    @Override
    public void collectArticle(final int position, final Article.DatasBean item) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)){
            if (!item.isCollect()){
                RetrofitManager.create(ApiService.class)
                        .addCollectArticle(item.getId())
                        .compose(RxSchedulers.<DataResponse>applySchedulers())
                        .compose(mView.<DataResponse>bindToLife())
                        .subscribe(new Consumer<DataResponse>() {
                            @Override
                            public void accept(DataResponse response) throws Exception {
                                if (response.getErrorCode() == 0){
                                    mView.showSuccess("收藏成功!");
                                    switchState(item, position);
                                }else {
                                    mView.showFailed(response.getErrorMsg().toString());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showFailed(throwable.getMessage());
                            }
                        });
            }else {
                RetrofitManager.create(ApiService.class)
                        .removeCollectArticle(item.getId(),-1)
                        .compose(RxSchedulers.<DataResponse>applySchedulers())
                        .compose(mView.<DataResponse>bindToLife())
                        .subscribe(new Consumer<DataResponse>() {
                            @Override
                            public void accept(DataResponse response) throws Exception {
                                if (response.getErrorCode() == 0){
                                    switchState(item, position);
                                    mView.showSuccess("取消收藏成功!");
                                }else {
                                    mView.showFailed(response.getErrorMsg().toString());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showFailed(throwable.getMessage());

                            }
                        });
            }
        }else {
            LoginActivity.start();
        }


    }

    private void switchState(Article.DatasBean item, int position) {
        item.setCollect(!item.isCollect());
        mView.collectSuccess(position,item);
    }
}
