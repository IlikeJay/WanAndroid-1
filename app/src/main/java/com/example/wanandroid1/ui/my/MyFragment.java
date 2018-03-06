package com.example.wanandroid1.ui.my;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.wanandroid1.R;
import com.example.wanandroid1.base.BaseFragment;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

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
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.civAvatar:
                ARouter.getInstance().build("/my/LoginActivity").navigation();
                break;
            case R.id.tvMyCollection:
                break;
            case R.id.tvMyBookmark:
                break;
            case R.id.tvSetting:
                break;

        }
    }
}
