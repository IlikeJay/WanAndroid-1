package com.example.wanandroid1.ui.article;

import com.example.wanandroid1.base.BaseContract;

/**
 * Created by Golden on 2018/3/1.
 */

public interface ArticleContentContact {
    interface View extends BaseContract.BaseView{

    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void collectArticle(int id);

        void collectOutsideArticle(String title, String author, String link);
    }
}
