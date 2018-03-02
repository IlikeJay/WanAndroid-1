package com.example.wanandroid1.ui.search;

import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.constant.LoadType;
import com.example.wanandroid1.db.HistoryModel;
import com.example.wanandroid1.db.HistoryModel_Table;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.utils.RxSchedulers;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/2.
 */

public class SearchPresenter extends BasePresenter<SearchContact.View> implements SearchContact.Presenter {

    private int mPage;
    private boolean mIsRefresh;
    private String results;

    @Inject
    public SearchPresenter() {
    }

    @Override
    public void loadHistory() {
        mView.showLoading();
        Observable.create(new ObservableOnSubscribe<List<HistoryModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<HistoryModel>> e) throws Exception {
                List<HistoryModel> historyModels = SQLite.select().from(HistoryModel.class)
                        .orderBy(HistoryModel_Table.date, false)
                        .limit(10).offset(0)
                        .queryList();
                e.onNext(historyModels);
            }
        }).compose(RxSchedulers.<List<HistoryModel>>applySchedulers()).compose(mView.<List<HistoryModel>>bindToLife()).subscribe(new Consumer<List<HistoryModel>>() {
            @Override
            public void accept(List<HistoryModel> historyModels) throws Exception {
//                List<HistoryModel> models = historyModels == null ? new ArrayList<HistoryModel>(0) : historyModels;
                mView.refreshHistory(historyModels);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showFailed(throwable.getMessage());
            }
        });
    }

    @Override
    public void addHistory(String name) {
        HistoryModel historyModel = new HistoryModel();
        historyModel.setName(name);
        historyModel.setDate(new Date());
        long id = historyModel.insert();
        if (id > 0){
            mView.addHistorySuccess(historyModel);
        }
    }

    @Override
    public void refreshArticleData() {
        mPage = 0;
        mIsRefresh = true;
        loadResults(results);
    }

    @Override
    public void loadResults(String content) {
        results = content;
        RetrofitManager.create(ApiService.class)
                .getSearchArticles(mPage, results)
                .compose(RxSchedulers.<DataResponse<Article>>applySchedulers())
                .compose(mView.<DataResponse<Article>>bindToLife())
                .subscribe(new Consumer<DataResponse<Article>>() {
                    @Override
                    public void accept(DataResponse<Article> dataResponse) throws Exception {
                        int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        mView.refreshArticles(dataResponse.getData(), loadType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        mView.refreshArticles(new Article(), loadType);
                    }
                });

    }

    @Override
    public void collectArticle(int position, Article.DatasBean item) {

    }


}
