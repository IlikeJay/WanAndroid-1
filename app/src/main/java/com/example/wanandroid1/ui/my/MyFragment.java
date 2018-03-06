package com.example.wanandroid1.ui.my;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseFragment;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.event.LoginEvent;
import com.example.wanandroid1.net.CookiesManager;
import com.example.wanandroid1.utils.RxBus;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/5.
 */

public class MyFragment extends BaseFragment<MyPresenter> implements MyContact.View, View.OnClickListener {

    @BindView(R.id.civAvatar)
    CircleImageView civAvatar;
    @BindView(R.id.tvNick)
    TextView tvNick;
    @BindView(R.id.tvMyCollection)
    TextView tvMyCollection;
    @BindView(R.id.tvMyBookmark)
    TextView tvMyBookmark;
    @BindView(R.id.tvSetting)
    TextView tvSetting;
    @BindView(R.id.llLogout)
    LinearLayout llLogout;
    private boolean mIsLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.my_fragment;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected void initView(View view) {
        civAvatar.setOnClickListener(this);
        tvMyCollection.setOnClickListener(this);
        tvMyBookmark.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        llLogout.setOnClickListener(this);

        setUserStatusInfo();
        /**登陆成功重新设置用户新*/
        RxBus.getInstance().toFlowable(LoginEvent.class).subscribe(new Consumer<LoginEvent>() {
            @Override
            public void accept(LoginEvent event) throws Exception {
                setUserStatusInfo();
            }
        });
    }

    private void setUserStatusInfo() {
        mIsLogin = SPUtils.getInstance(Constant.SHARED_NAME).getBoolean(Constant.LOGIN_KEY);
        if (mIsLogin) {
            civAvatar.setImageResource(R.drawable.ic_head_portrait);
            tvNick.setText(SPUtils.getInstance(Constant.SHARED_NAME).getString(Constant.USERNAME_KEY));
            llLogout.setVisibility(View.VISIBLE);
        } else {
            civAvatar.setImageResource(R.drawable.ic_avatar);
            tvNick.setText(R.string.click_avatar_login);
            llLogout.setVisibility(View.GONE);
        }
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.civAvatar:
                ARouter.getInstance().build("/login/LoginActivity").navigation();
                break;
            case R.id.tvMyCollection:
                if (mIsLogin){
                    ARouter.getInstance().build("/collection/MyCollectionActivity").navigation();
                }else {
                    ARouter.getInstance().build("/login/LoginActivity").navigation();
                }
                break;
            case R.id.tvMyBookmark:
                if (mIsLogin){
                    ARouter.getInstance().build("/bookmark/BookMarkActivity").navigation();
                }else {
                    ARouter.getInstance().build("/login/LoginActivity").navigation();

                }
                break;
            case R.id.tvSetting:
                ARouter.getInstance().build("/setting/SettingActivity").navigation();
                break;
            case R.id.llLogout:
                loginout();
                break;

        }
    }

    private void loginout() {
        /**设置退出登陆*/
        SPUtils.getInstance(Constant.SHARED_NAME).clear();
        setUserStatusInfo();
        /**清除cookies*/
        CookiesManager.clearAllCookies();
        /**发送退出登陆的消息*/
        RxBus.getInstance().post(new LoginEvent());
    }
}
