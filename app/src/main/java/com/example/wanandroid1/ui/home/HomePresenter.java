package com.example.wanandroid1.ui.home;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.SPUtils;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.App;
import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.bean.Banner;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.bean.User;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.constant.LoadType;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.ui.my.LoginActivity;
import com.example.wanandroid1.utils.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * Created by lw on 2018/1/18.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {
    private int mPage;
    private boolean mIsRefresh;

    @Inject
    public HomePresenter() {
        this.mIsRefresh = true;
    }

    @Override
    public void loadHomeBanners() {
        RetrofitManager.create(ApiService.class)
                .getHomeBanners()
                .compose(RxSchedulers.<DataResponse<List<Banner>>>applySchedulers())
                .compose(mView.<DataResponse<List<Banner>>>bindToLife())
                .subscribe(new Consumer<DataResponse<List<Banner>>>() {
                    @Override
                    public void accept(DataResponse<List<Banner>> dataResponse) throws Exception {
                        mView.setHomeBanners(dataResponse.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed(throwable.getMessage());
                    }
                });
    }

    @Override
    public void loadHomeArticles() {
        RetrofitManager.create(ApiService.class)
                .getHomeArticles(mPage)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers())
                .compose(mView.<DataResponse<Article>>bindToLife())
                .subscribe(new Consumer<DataResponse<Article>>() {
                    @Override
                    public void accept(DataResponse<Article> dataResponse) throws Exception {
                        int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.setHomeArticles(dataResponse.getData(), loadType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.setHomeArticles(new Article(), loadType);
                    }
                });
    }

    @Override
    public void refresh() {
        mPage = 0;
        mIsRefresh = true;
        loadHomeBanners();
        loadHomeArticles();
    }

    @Override
    public void loadMore() {
        mPage++;
        mIsRefresh = false;
        loadHomeArticles();
    }

    @Override
    public void collectArticle(final int position, final Article.DatasBean bean) {
        if (SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY)) {
            if (bean.isCollect()) {
                RetrofitManager.create(ApiService.class).removeCollectArticle(bean.getId(), -1)
                        .compose(RxSchedulers.<DataResponse>applySchedulers())
                        .compose(mView.<DataResponse>bindToLife())
                        .subscribe(new Consumer<DataResponse>() {
                            @SuppressLint("StringFormatInvalid")
                            @Override
                            public void accept(DataResponse response) throws Exception {
                                if (response.getErrorCode() == 0) {
                                    bean.setCollect(!bean.isCollect());
                                    mView.collectArticleSuccess(position, bean);
                                    mView.showSuccess(App.getAppContext().getString(R.string.collection_cancel_success));
                                } else {
                                    mView.showFailed(App.getAppContext().getString(R.string.collection_cancel_failed, response.getData()));
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showFailed(throwable.getMessage());
                            }
                        });
            } else {
                RetrofitManager.create(ApiService.class).addCollectArticle(bean.getId())
                        .compose(RxSchedulers.<DataResponse>applySchedulers())
                        .compose(mView.<DataResponse>bindToLife())
                        .subscribe(new Consumer<DataResponse>() {
                            @SuppressLint("StringFormatInvalid")
                            @Override
                            public void accept(DataResponse response) throws Exception {
                                if (response.getErrorCode() == 0) {
                                    bean.setCollect(!bean.isCollect());
                                    mView.collectArticleSuccess(position, bean);
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
            }
        } else {
            LoginActivity.start();
        }
    }

    @Override
    public void loadHomeData() {
        mView.showLoading();
        String username = SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.USERNAME_KEY);
        String password = SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.PASSWORD_KEY);
        Observable<DataResponse<User>> observableUser = RetrofitManager.create(ApiService.class).login(username, password);
        Observable<DataResponse<List<Banner>>> observableBanner = RetrofitManager.create(ApiService.class).getHomeBanners();
        Observable<DataResponse<Article>> observableArticle = RetrofitManager.create(ApiService.class).getHomeArticles(mPage);
        Observable.zip(observableUser, observableBanner, observableArticle, new Function3<DataResponse<User>, DataResponse<List<Banner>>, DataResponse<Article>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(DataResponse<User> response, DataResponse<List<Banner>> dataResponse, DataResponse<Article> dataResponse2) throws Exception {
                Map<String, Object> objMap = new HashMap<>();
                objMap.put(Constant.USER_KEY, response);
                objMap.put(Constant.BANNER_KEY, dataResponse.getData());
                objMap.put(Constant.ARTICLE_KEY, dataResponse2.getData());
                return objMap;
            }
        }).compose(RxSchedulers.<Map<String, Object>>applySchedulers()).compose(mView.<Map<String, Object>>bindToLife()).subscribe(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) throws Exception {
                DataResponse<User> dataResponse = (DataResponse<User>) map.get(Constant.USER_KEY);
                if (dataResponse.getErrorCode() == 0) {
                    mView.showSuccess(App.getAppContext().getString(R.string.auto_login_success));
                } else {
                    mView.showFailed(String.valueOf(dataResponse.getErrorMsg()));
                }
                List<Banner> banners = (List<Banner>) map.get(Constant.BANNER_KEY);
                Article article = (Article) map.get(Constant.ARTICLE_KEY);
                mView.setHomeBanners(banners);
                mView.setHomeArticles(article, LoadType.TYPE_REFRESH_SUCCESS);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showFailed(throwable.getMessage());
            }
        });
    }


}
