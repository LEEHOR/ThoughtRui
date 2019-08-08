package com.coahr.thoughtrui.mvp.view.action_plan.Adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/16
 * 描述：
 */
public class item_plan_viewpager_adapter extends BaseMultiItemQuickAdapter<ReportList.DataBean.AllListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public item_plan_viewpager_adapter(List<ReportList.DataBean.AllListBean> data) {
        super(data);
        addItemType(ReportList.DataBean.AllListBean.FINISH, R.layout.item_fragment_plan_viewpager_finish);
        addItemType(ReportList.DataBean.AllListBean.UN_FINISH, R.layout.item_fragment_plan_viewpager_unfinish);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportList.DataBean.AllListBean item) {
        if (helper.getItemViewType() == -1) {  //未完成
            helper.setText(R.id.plan_viewpager_un_tv_time, item.getTargetDate())
                    .setText(R.id.plan_viewpager_un_dealer_name, item.getDname())
                    .setText(R.id.plan_viewpager_un_address, String.format(BaseApplication.mContext.getResources().getString(R.string.plan_viewpager_1_4), !TextUtils.isEmpty(item.getQuota1()) ? item.getQuota1() : BaseApplication.mContext.getResources().getString(R.string.phrases_35)
                            , !TextUtils.isEmpty(item.getQuota2()) ? item.getQuota2() : BaseApplication.mContext.getResources().getString(R.string.phrases_35)))
                    .setText(R.id.plan_viewpager_un_action_time, TimeUtils.getStingYMDHM(item.getNewestTime()))
                    .setText(R.id.plan_viewpager_un_tips, item.getCount() > 1 ? BaseApplication.mContext.getResources().getString(R.string.plan_viewpager_1_3) : "")
                    .addOnClickListener(R.id.item_plan_unfinish);

        } else {
            helper.setText(R.id.plan_viewpager_fi_tv_time, item.getTargetDate())
                    .setText(R.id.plan_viewpager_fi_dealer_name, item.getDname())
                    .setText(R.id.plan_viewpager_fi_address, String.format(BaseApplication.mContext.getResources().getString(R.string.plan_viewpager_1_4), !TextUtils.isEmpty(item.getQuota1()) ? item.getQuota1() : BaseApplication.mContext.getResources().getString(R.string.phrases_35)
                            , !TextUtils.isEmpty(item.getQuota2()) ? item.getQuota2() : BaseApplication.mContext.getResources().getString(R.string.phrases_35)))
                    .setText(R.id.plan_viewpager_fi_action_time, TimeUtils.getStingYMDHM(item.getNewestTime()))
                    .addOnClickListener(R.id.item_plan_finish);
        }
    }
}
