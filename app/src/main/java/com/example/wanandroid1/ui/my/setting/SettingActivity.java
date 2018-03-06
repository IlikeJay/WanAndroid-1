package com.example.wanandroid1.ui.my.setting;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseActivity;
@Route(path = "/setting/SettingActivity")
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContact.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,SettingFragment.newInstance()).commit();
    }

    @Override
    protected boolean showHomeAsUp() {
        return true;
    }
}
