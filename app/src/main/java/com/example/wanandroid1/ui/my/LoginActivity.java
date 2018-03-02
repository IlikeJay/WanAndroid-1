package com.example.wanandroid1.ui.my;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;
import com.example.wanandroid1.bean.User;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.event.LoginEvent;
import com.example.wanandroid1.utils.RxBus;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/my/LoginActivity")
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContact.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tiet_username)
    TextInputEditText tietUsername;
    @BindView(R.id.tiet_pwd)
    TextInputEditText tietPwd;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        tietUsername.setText(SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.USERNAME_KEY));
        tietPwd.setText(SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.PASSWORD_KEY));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        getMenuInflater().inflate(R.menu.);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }



    public static void start() {
        ARouter.getInstance().build("/my/LoginActivity").navigation();
    }

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        String username = tietUsername.getText().toString();
        String password = tietPwd.getText().toString();
        mPresenter.login(username, password);
    }

    @Override
    public void loginSuccess(User user) {
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.LOGIN_KEY, true);
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.USERNAME_KEY, user.getUsername());
        SPUtils.getInstance(Constant.SHARED_NAME).put(Constant.PASSWORD_KEY, user.getPassword());
        /**登陆成功通知其他界面刷新*/
        RxBus.getInstance().post(new LoginEvent());
        this.finish();
    }
}
