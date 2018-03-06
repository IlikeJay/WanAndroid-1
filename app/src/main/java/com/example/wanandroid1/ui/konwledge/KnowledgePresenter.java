package com.example.wanandroid1.ui.konwledge;

import com.example.wanandroid1.base.BasePresenter;
import com.example.wanandroid1.bean.DataResponse;
import com.example.wanandroid1.bean.KnowledgeSystem;
import com.example.wanandroid1.net.ApiService;
import com.example.wanandroid1.net.RetrofitManager;
import com.example.wanandroid1.utils.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by Golden on 2018/3/5.
 */

class KnowledgePresenter extends BasePresenter<KnowledgeContact.View> implements KnowledgeContact.Presenter{
    @Inject
    public KnowledgePresenter() {
    }
    @Override
    public void getKnowledgeData() {
        mView.showLoading();
        RetrofitManager.create(ApiService.class).getKnowledgeSystems().compose(RxSchedulers.<DataResponse<List<KnowledgeSystem>>>applySchedulers()).compose(mView.<DataResponse<List<KnowledgeSystem>>>bindToLife()).subscribe(new Consumer<DataResponse<List<KnowledgeSystem>>>() {
            @Override
            public void accept(DataResponse<List<KnowledgeSystem>> response) throws Exception {
                    if (response.getErrorCode()==0){
                        List<KnowledgeSystem> knowledgeSystems = response.getData();
                        mView.refreshKnowledgeSystems(knowledgeSystems);
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
