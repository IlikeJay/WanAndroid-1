package com.example.wanandroid1.ui.konwledge;

import com.example.wanandroid1.base.BaseContract;
import com.example.wanandroid1.bean.KnowledgeSystem;

import java.util.List;

/**
 * Created by Golden on 2018/3/5.
 */

public interface KnowledgeContact {

    interface View extends BaseContract.BaseView{

        void refreshKnowledgeSystems(List<KnowledgeSystem> knowledgeSystems);

    }
    interface Presenter extends BaseContract.BasePresenter<KnowledgeContact.View>{
        void getKnowledgeData();
    }
}
