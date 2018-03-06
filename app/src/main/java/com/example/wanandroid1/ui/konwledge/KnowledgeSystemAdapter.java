package com.example.wanandroid1.ui.konwledge;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid1.R;
import com.example.wanandroid1.bean.KnowledgeSystem;

import javax.inject.Inject;

/**
 * Created by lw on 2018/1/22.
 */

public class KnowledgeSystemAdapter extends BaseQuickAdapter<KnowledgeSystem, BaseViewHolder> {
    @Inject
    public KnowledgeSystemAdapter() {
        super(R.layout.item_knowledge_system, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeSystem item) {
        helper.setText(R.id.typeItemFirst, item.getName());
        StringBuffer sb = new StringBuffer();
        for (KnowledgeSystem.ChildrenBean childrenBean : item.getChildren()) {
            sb.append(childrenBean.getName() + "     ");
        }
        helper.setText(R.id.typeItemSecond, sb.toString());
    }
}
