package com.coahr.thoughtrui.mvp.view.search.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.model.Bean.SearchBeans;
/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/7
 * 描述：
 */
public class SearchAdapter extends BaseQuickAdapter<SearchBeans.DataBean.SearchListBean, BaseViewHolder> {
    private OnClick onClick;
    public SearchAdapter() {
        super(R.layout.item_fragmentpager_censorlist, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchBeans.DataBean.SearchListBean item) {
        if (item != null) {
            helper.setText(R.id.tv_explain, Constants.user_type == 1 ? "["+item.getSale_code()+"]"
                            : Constants.user_type == 2 ? "["+item.getSale_code()+"]"
                            : "["+item.getSale_code()+"]")
                    .setText(R.id.tv_project_times,item.getPname())
                    .setText(R.id.tv_project_code,item.getCode())
                    //.setText(R.id.tv_project_name,item.getPname())
                    .setText(R.id.tv_project_company,item.getDname())
                    .setText(R.id.tv_project_address,item.getAreaAddress())
                    .setText(R.id.tv_update_time, TimeUtils.getStingYMDHM(item.getModifyTime()));
                   helper .getView(R.id.item_data).setVisibility(View.INVISIBLE);
                   helper .getView(R.id.tv_schedule).setVisibility(View.INVISIBLE);
                   helper.getView(R.id.iv_tag).setVisibility(View.INVISIBLE);
                   helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if (onClick != null) {
                               onClick.getOnClick(item);
                           }
                       }
                   });
        }
    }
    public interface OnClick{
        void getOnClick(SearchBeans.DataBean.SearchListBean item);
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
}
