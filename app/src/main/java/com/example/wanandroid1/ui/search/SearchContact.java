package com.example.wanandroid1.ui.search;

import com.example.wanandroid1.base.BaseContract;
import com.example.wanandroid1.bean.Article;
import com.example.wanandroid1.db.HistoryModel;

import java.util.List;

/**
 * Created by Golden on 2018/3/2.
 */

public class SearchContact {

    interface Presenter extends BaseContract.BasePresenter<SearchContact.View>{

        void loadHistory();


        void addHistory(String newText);

        void refreshArticleData();

        void loadResults(String newText);

        void collectArticle(int position, Article.DatasBean item);
    }
    interface View extends BaseContract.BaseView{

        void addHistorySuccess(HistoryModel historyModel);

        void refreshHistory(List<HistoryModel> historyModels);

        void refreshArticles(Article data, int loadType);
    }
}
