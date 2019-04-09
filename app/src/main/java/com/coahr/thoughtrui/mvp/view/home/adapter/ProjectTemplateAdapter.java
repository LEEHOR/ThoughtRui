package com.coahr.thoughtrui.mvp.view.home.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;


/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/26
 * 描述：项目模板adapter
 */
public class ProjectTemplateAdapter extends BaseQuickAdapter<Template_list.TemplateListBean,BaseViewHolder> {
    private OnClick onClick;
    public ProjectTemplateAdapter() {
        super(R.layout.item_project_template, null);
    }


    @Override
    protected void convert(BaseViewHolder helper,Template_list.TemplateListBean item) {
        helper.setText(R.id.template_name,item.getName())
                .setText(R.id.template_time, BaseApplication.mContext.getString(R.string.phrases_1) + TimeUtils.getStingYMD(item.getModify_time()));
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    onClick.getOnClick(item);
                }
            }
        });
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void getOnClick(Template_list.TemplateListBean item);
    }
}
