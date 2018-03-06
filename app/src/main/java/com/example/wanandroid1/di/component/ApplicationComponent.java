package com.example.wanandroid1.di.component;

import android.content.Context;

import com.example.wanandroid1.di.module.ApplicationModule;
import com.example.wanandroid1.di.scope.ContextLife;
import com.example.wanandroid1.di.scope.PerApp;

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