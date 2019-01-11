package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
public class ReviewInfoListAdapter extends BaseQuickAdapter<CensorInfoList.DataBean.ListBean,BaseViewHolder> {
    public ReviewInfoListAdapter() {
        super(R.layout.item_fragment_censor_infolist, null);
    }
private  OnClickListener listener;
    @Override
    protected void convert(final BaseViewHolder helper, final CensorInfoList.DataBean.ListBean item) {
        if (item != null) {
            helper.setText(R.id.tv_tittle,item.getTitle());
        }
        helper.getView(R.id.info_list_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnClick(item,helper.getAdapterPosition());
                }
            }
        });
    }
    public  interface OnClickListener{

        void  OnClick(CensorInfoList.DataBean.ListBean bean,int position);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
