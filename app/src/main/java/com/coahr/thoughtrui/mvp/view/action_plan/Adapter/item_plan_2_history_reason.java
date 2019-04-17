package com.coahr.thoughtrui.mvp.view.action_plan.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/17
 * 描述：
 */
public class item_plan_2_history_reason extends BaseQuickAdapter<ReportList.DataBean.AllListBean.ReasonListBeanX, BaseViewHolder> {
    public item_plan_2_history_reason() {
        super(R.layout.item_fragment_action_plan_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportList.DataBean.AllListBean.ReasonListBeanX item) {
        if (item != null) {
            String format = String.format(BaseApplication.mContext.getResources().getString(R.string.plan_2_9), TimeUtils.getStingYMDHM(item.getSubmitTime()), item.getIncompleteReason());
            helper.setText(R.id.tv_plan_history_reason, format);
        }
    }
}
