package com.example.wanandroid1.ui.my.login;

import com.example.wanandroid1.base.BaseContract;
import com.example.wanandroid1.bean.User;

/**
 * Created by Golden on 2018/3/2.
 */

public interface LoginContact  {

    interface View extends BaseContract.BaseView {
        void loginSuccess(User user);
    }

    interface Presenter extends BaseContract.BasePresenter<LoginContact.View> {
        void login(String username, String password);
    }
}
