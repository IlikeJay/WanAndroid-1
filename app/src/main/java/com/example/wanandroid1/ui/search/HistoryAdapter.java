package com.example.wanandroid1.ui.search;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid1.R;
import com.example.wanandroid1.db.HistoryModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by Golden on 2018/3/2.
 */

public class HistoryAdapter extends TagAdapter<HistoryModel> {

    private final Context context;
    private  List<HistoryModel> datas;

    public HistoryAdapter(Context context, List<HistoryModel> datas) {
        super(datas);
        this.datas = datas;
        this.context = context;

    }





    @Override
    public View getView(FlowLayout parent, int position, HistoryModel model) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        int parseColor = 0;
        try {
            tvTitle.setText(model.getName());
            String str = Integer.toHexString((int) (Math.random() * 16777215));
            parseColor = Color.parseColor("#".concat(str));
            tvTitle.setTextColor(parseColor);
        } catch (Exception e) {
            e.printStackTrace();
            parseColor = context.getResources().getColor(R.color.colorAccent);
        }
        tvTitle.setTextColor(parseColor);

        return view;
    }
}
