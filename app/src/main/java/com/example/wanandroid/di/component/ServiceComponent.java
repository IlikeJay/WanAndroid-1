package com.example.wanandroid.di.component;

import android.content.Context;

import com.example.wanandroid.di.module.ServiceModule;
import com.example.wanandroid.di.scope.ContextLife;
import com.example.wanandroid.di.scope.PerService;

import dagger.Component;


/**
 * Created by lw on 2017/1/19.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
