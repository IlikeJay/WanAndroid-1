package com.example.wanandroid1.ui.article;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.App;
import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.ui.my.LoginActivity;
import com.example.wanandroid1.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/1.
 */

class ArticleContentPresenter extends BasePresenter<ArticleContentContact.View> implements ArticleContentContact.Presenter{
    @Inject
    public ArticleContentPresenter() {
    }

    @Override
    public void collectOutsideArticle(String title, String author, String url) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)){
            RetrofitManager.create(ApiService.class).addCollectOutsideArticle(title,author,url).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife()).subscribe(new Consumer<DataResponse>() {
                @SuppressLint("StringFormatInvalid")
                @Override
                public void accept(DataResponse response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        mView.showSuccess(App.getAppContext().getString(R.string.collection_success));
                    } else {
                        mView.showFailed(App.getAppContext().getString(R.string.collection_failed, response.getErrorMsg()));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.showFailed(throwable.getMessage());
                }
            });
        }else {
            LoginActivity.start();

        }
    }

    @Override
    public void collectArticle(int id) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)){
            RetrofitManager.create(ApiService.class).addCollectArticle(id).compose(RxSchedulers.<DataResponse>applySchedulers()).compose(mView.<DataResponse>bindToLife()).subscribe(new Consumer<DataResponse>() {
                @SuppressLint("StringFormatInvalid")
                @Override
                public void accept(DataResponse response) throws Exception {
                    if (response.getErrorCode() == 0) {
                        mView.showSuccess(App.getAppContext().getString(R.string.collection_success));
                    } else {
                        mView.showFailed(App.getAppContext().getString(R.string.collection_failed, response.getErrorMsg()));
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.showFailed(throwable.getMessage());
                }
            });
        }else {
            LoginActivity.start();
        }
    }
}
