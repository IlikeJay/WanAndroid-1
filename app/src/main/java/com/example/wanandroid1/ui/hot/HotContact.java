package com.example.wanandroid1.ui.hot;

import com.example.wanandroid1.base.BaseContract;
import com.example.wanandroid1.bean.Friend;
import com.example.wanandroid1.bean.HotKey;

import java.util.List;

/**
 * Created by Golden on 2018/3/2.
 */

public class HotContact {

    interface View extends BaseContract.BaseView{

        void setHotData(List<HotKey> hotKeys, List<Friend> friends);

    }

    interface Presenter extends BaseContract.BasePresenter<View>{

        void loadHotData();

    }
}
