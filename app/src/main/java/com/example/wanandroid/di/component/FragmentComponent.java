package com.example.wanandroid.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.wanandroid.di.module.FragmentModule;
import com.example.wanandroid.di.scope.ContextLife;
import com.example.wanandroid.di.scope.PerFragment;
import com.example.wanandroid.ui.home.HomeFragment;

import dagger.Component;

/**
 * Created by lw on 2017/1/19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(HomeFragment fragment);
//
//    void inject(KnowledgeSystemFragment fragment);
//
//    void inject(MyFragment fragment);
//
//    void inject(ArticleListFragment fragment);
//
//    void inject(HotFragment fragment);
}
