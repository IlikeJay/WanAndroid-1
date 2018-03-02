package com.example.wanandroid1.ui.hot;

import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.bean.Friend;
import com.example.wanandroid1.bean.HotKey;
import com.example.wanandroid1.constant.Constant;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.utils.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/2.
 */

public class HotPresenter extends BasePresenter<HotContact.View> implements HotContact.Presenter {
    @Inject
    public HotPresenter() {
    }

    @Override
    public void loadHotData() {
        mView.showLoading();
        Observable<DataResponse<List<Friend>>> observableFriend = RetrofitManager.create(ApiService.class).getHotFriends();
        Observable<DataResponse<List<HotKey>>> observableHotKey = RetrofitManager.create(ApiService.class).getHotKeys();
        Observable.zip(observableFriend, observableHotKey, new BiFunction<DataResponse<List<Friend>>, DataResponse<List<HotKey>>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(DataResponse<List<Friend>> response, DataResponse<List<HotKey>> response2) throws Exception {
                Map<String, Object> objMap = new HashMap<>();
                objMap.put(Constant.CONTENT_HOT_KEY, response2.getData());
                objMap.put(Constant.CONTENT_HOT_FRIEND_KEY, response.getData());
                return objMap;
            }
        }).compose(RxSchedulers.<Map<String, Object>>applySchedulers()).compose(mView.<Map<String, Object>>bindToLife()).subscribe(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> map) throws Exception {
                List<HotKey> hotKeys = (List<HotKey>) map.get(Constant.CONTENT_HOT_KEY);
                List<Friend> friends = (List<Friend>) map.get(Constant.CONTENT_HOT_FRIEND_KEY);
                mView.setHotData(hotKeys, friends);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showFailed(throwable.getMessage());
            }
        });
    }


}
