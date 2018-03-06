package com.example.wanandroid1.ui.article;

import com.example.wanandroid1.base.BaseContract;
import com.example.wanandroid1.bean.Article;

/**
 * Created by Golden on 2018/3/5.
 */

public interface ArticleListContact {
    interface View extends BaseContract.BaseView{

        void refresh(Article data, int loadType);

        void collectSuccess(int position, Article.DatasBean item);
    }
    interface Presenter extends BaseContract.BasePresenter<ArticleListContact.View>{

        void loadData(int id);

        void refresh();

        void loadMore();

        void collectArticle(int position, Article.DatasBean item);
    }
}
