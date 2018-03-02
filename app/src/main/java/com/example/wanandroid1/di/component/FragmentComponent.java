package com.example.wanandroid1.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.wanandroid1.di.module.FragmentModule;
import com.example.wanandroid1.di.scope.ContextLife;
import com.example.wanandroid1.di.scope.PerFragment;
import com.example.wanandroid1.ui.home.HomeFragment;
import com.example.wanandroid1.ui.hot.HotFragment;

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
    void inject(HotFragment fragment);
}
