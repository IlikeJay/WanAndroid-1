package com.example.wanandroid.di.module;

import android.content.Context;

import com.example.wanandroid.base.App;
import com.example.wanandroid.di.scope.ContextLife;
import com.example.wanandroid.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;


/**
 * Created by lw on 2017/1/19.
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
