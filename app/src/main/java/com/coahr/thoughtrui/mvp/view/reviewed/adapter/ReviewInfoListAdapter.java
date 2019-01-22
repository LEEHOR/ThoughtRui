package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
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
    //1：审核通过。2：审核未通过
    private int tag_s;
    public void setTag(int tag){
        this.tag_s=tag;
    }
private  OnClickListener listener;
    @Override
    protected void convert(final BaseViewHolder helper, final CensorInfoList.DataBean.ListBean item) {
        if (item != null) {
            helper.setText(R.id.tv_tittle,item.getTitle())
                    .setText(R.id.tv_review_suggestion,"评审意见："+item.getSuggestion())
                    .setText(R.id.tv_review_name,item.getName());
        TextView tv_review_tag = helper.getView(R.id.tv_review_tag);  //审核标签

            if (tag_s==0){
                tv_review_tag.setText(item.getStage()==1?"初审通过":item.getStage()==2?"复审通过":item.getStage()==3?"终审通过":"初审通过");
                tv_review_tag.setTextColor(Color.rgb(45,125,255));
                tv_review_tag.setBackgroundResource(R.drawable.bg_white_blue_frame_background);
            } else {
                tv_review_tag.setText(item.getStage()==1?"初审驳回":item.getStage()==2?"复审驳回":item.getStage()==3?"终审驳回":"初审驳回");
                tv_review_tag.setTextColor(Color.rgb(255,129,3));
                tv_review_tag.setBackgroundResource(R.drawable.bg_white_pink_frame_background);
            }
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
