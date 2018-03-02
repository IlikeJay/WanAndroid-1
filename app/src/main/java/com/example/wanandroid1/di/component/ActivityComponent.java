package com.example.wanandroid1.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.wanandroid1.di.module.ActivityModule;
import com.example.wanandroid1.di.scope.ContextLife;
import com.example.wanandroid1.di.scope.PerActivity;
import com.example.wanandroid1.ui.article.ArticleContentActivity;
import com.example.wanandroid1.ui.my.LoginActivity;
import com.example.wanandroid1.ui.search.SearchActivity;

import dagger.Component;

/**
 * Created by lw on 2017/1/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(SearchActivity activity);

    void inject(LoginActivity activity);
//
    void inject(ArticleContentActivity activity);
//
//    void inject(MyCollectionActivity activity);
//
//    void inject(MyBookmarkActivity activity);
}
