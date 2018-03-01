package com.example.wanandroid.di.component;

import android.content.Context;

import com.example.wanandroid.di.module.ApplicationModule;
import com.example.wanandroid.di.scope.ContextLife;
import com.example.wanandroid.di.scope.PerApp;

import dagger.Component;


/**
 * Created by lw on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}