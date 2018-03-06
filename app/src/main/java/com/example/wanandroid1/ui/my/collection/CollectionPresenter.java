package com.example.wanandroid1.ui.my.collection;

import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.constant.LoadType;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.utils.RxSchedulers;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/6.
 */

public class CollectionPresenter extends BasePresenter<CollectionContact.View> implements CollectionContact.Presenter {
    private int mPage;
    private boolean mIsRefresh;

    @Inject
    public CollectionPresenter() {
    }

    @Override
    public void loadCollectionData() {
        mIsRefresh = true;
        mPage = 0;
        loadData();
    }

    @Override
    public void loadMore() {
        mIsRefresh =false;
        mPage++;
        loadData();
    }

    @Override
    public void loadData() {
        if (mIsRefresh){
            mView.showLoading();
        }
        RetrofitManager.create(ApiService.class).getCollectArticles(mPage).compose(RxSchedulers.<DataResponse<Article>>applySchedulers()).compose(mView.<DataResponse<Article>>bindToLife()).subscribe(new Consumer<DataResponse<Article>>() {
            @Override
            public void accept(DataResponse<Article> response) throws Exception {
                mView.refreshCollections(response.getData().getDatas(),mIsRefresh? LoadType.TYPE_REFRESH_SUCCESS:LoadType.TYPE_LOAD_MORE_SUCCESS);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.refreshCollections(new ArrayList<Article.DatasBean>(0),mIsRefresh?LoadType.TYPE_REFRESH_ERROR:LoadType.TYPE_LOAD_MORE_ERROR);
                mView.showFailed(throwable.getMessage());
            }
        });
    }

    @Override
    public void cancelCollection(final Article.DatasBean bean, final int position) {
        RetrofitManager.create(ApiService.class).removeCollectArticle(bean.getId(),-1)
                .compose(RxSchedulers.<DataResponse>applySchedulers())
                .compose(mView.<DataResponse>bindToLife())
                .subscribe(new Consumer<DataResponse>() {
                    @Override
                    public void accept(DataResponse response) throws Exception {
                        if (response.getErrorCode() == 0) {
//                            loadCollectionData();
                            mView.showSuccess("取消收藏成功!");
                            bean.setCollect(!bean.isCollect());
                            mView.cancelSuccess(position);
                        } else {
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
}
