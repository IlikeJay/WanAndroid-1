package com.example.wanandroid1.ui.my;

import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.bean.User;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.utils.RxSchedulers;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/2.
 */

class LoginPresenter extends BasePresenter<LoginContact.View> implements LoginContact.Presenter{
    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(String username, String password) {
        RetrofitManager.create(ApiService.class).login(username,password).compose(RxSchedulers.<DataResponse<User>>applySchedulers()).compose(mView.<DataResponse<User>>bindToLife()).subscribe(new Consumer<DataResponse<User>>() {
            @Override
            public void accept(DataResponse<User> response) throws Exception {
                if (response.getErrorCode() == 0){
                    mView.loginSuccess(response.getData());
                }else {
                    mView.showFailed(response.getErrorMsg().toString());

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showFailed(throwable.getMessage());
            }
        });
    }
}
