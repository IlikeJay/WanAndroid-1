package com.example.wanandroid1.ui.my.collection;

import com.example.wanandroid1.base.BaseContract;
import com.example.wanandroid1.bean.Article;

import java.util.List;

/**
 * Created by Golden on 2018/3/6.
 */

public interface CollectionContact {

    interface View extends BaseContract.BaseView{

        void refreshCollections(List<Article.DatasBean> response,int type);

        void cancelSuccess(int position);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{

        void loadCollectionData();

        void loadMore();

        void loadData();

        /**
         * 收藏或者取消收藏
         * @param datasBean
         * @param position
         */
        void cancelCollection(Article.DatasBean datasBean, int position);
    }
}
