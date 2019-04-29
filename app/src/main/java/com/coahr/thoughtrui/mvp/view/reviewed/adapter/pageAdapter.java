package com.coahr.thoughtrui.mvp.view.reviewed.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;


/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class pageAdapter extends BaseQuickAdapter<CensorBean.DataBean.ListBean, BaseViewHolder> {
    private OnItemClick onItemClick;
    private int type;

    public pageAdapter() {
        super(R.layout.item_fragmentpager_censorlist, null);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CensorBean.DataBean.ListBean item) {
        if (item != null) {
            helper.setText(R.id.tv_schedule, item.getProgress())
                    .setText(R.id.tv_explain, "[" + item.getService_code() + "]")
                    .setText(R.id.tv_project_times, item.getPname())
                    //.setText(R.id.tv_project_name,item.getPname())
                    .setText(R.id.tv_project_company, item.getDname())
                    .setText(R.id.tv_project_address, item.getLocation())
                    .setText(R.id.tv_update_time, TimeUtils.getStingYMDHM(item.getModifyTime()))
                    .setText(R.id.item_data, String.format(BaseApplication.mContext.getResources().getString(R.string.review_fragment_3), type == 0 ? item.getRepulseCount() : type == 1 ? item.getPassCount() : item.getRepulseCount()));

            View view = helper.getView(R.id.pager_cardView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.OnClick(item, helper.getAdapterPosition());
                    }
                }
            });
        }

    }

    public interface OnItemClick {
        void OnClick(CensorBean.DataBean.ListBean bean, int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
