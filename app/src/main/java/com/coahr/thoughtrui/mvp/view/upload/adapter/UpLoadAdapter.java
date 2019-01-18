package com.coahr.thoughtrui.mvp.view.upload.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/15
 * 描述：上传页面adapter
 */
public class UpLoadAdapter extends BaseQuickAdapter<ProjectsDB,BaseViewHolder> {
    private onSelectChangeListener selectChangeListener;
    private boolean visible;
    private List<ProjectsDB> projects_Positions = new ArrayList<>();

    public UpLoadAdapter() {
        super(R.layout.item_fragment_upload, null);
    }
    public void setCheckViewVisible(boolean b){
        this.visible=b;
        projects_Positions.clear();
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder helper, final ProjectsDB item) {
        if (item != null) {
            helper.setText(R.id.up_tv_schedule,item.getProgress())
                    .setText(R.id.f_type,item.getInspect()==1?"飞检"
                            :item.getInspect()==2?"神秘顾客"
                            :item.getInspect()==3?"新店验收":"飞检")
                    .setText(R.id.up_tv_time,"["+TimeUtils.getStringDate_start(item.getStartTime())+","+TimeUtils.getStringDate_end(item.getEndTime())+"]")
                    .setText(R.id.up_tv_project_code,item.getCode())
                    .setText(R.id.up_tv_project_name,item.getPname())
                    .setText(R.id.up_tv_project_company,item.getcName())
                    .setText(R.id.up_tv_project_address,item.getLocation()+item.getAddress())
                    .setText(R.id.up_tv_update_time,TimeUtils.getStingYMDHM(item.getModifyTime()));
        }
        if (visible){
            helper.getView(R.id.ck_check).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ck_check).setVisibility(View.GONE);
        }
        ((CheckBox) helper.getView(R.id.ck_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!projects_Positions.contains(item)) {
                        projects_Positions.add(item);
                    }
                } else {
                    projects_Positions.remove(item);
                }
                selectChangeListener.onChange();
            }
        });

        if (projects_Positions.contains(item)) {
            ((CheckBox) helper.getView(R.id.ck_check)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.ck_check)).setChecked(false);
        }

        ((ImageView)helper.getView(R.id.iv_upload_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectChangeListener != null) {
                    selectChangeListener.OnClickItem(item);
                }
            }
        });
    }
    public interface onSelectChangeListener {
        void onChange();
        void OnClickItem(ProjectsDB projectsDB);
    }

    public List<ProjectsDB> getSelectedProject() {
        return projects_Positions;
    }

    public void checkAll() {
        projects_Positions.clear();
        projects_Positions.addAll(getData());
        notifyDataSetChanged();
        selectChangeListener.onChange();
    }

    public boolean isAllSelected() {
        return projects_Positions.size() == getItemCount();
    }

    public void unCheckAll() {
        projects_Positions.clear();
        notifyDataSetChanged();
        selectChangeListener.onChange();
    }
    public void setSelectChangeListener(onSelectChangeListener selectChangeListener) {
        this.selectChangeListener = selectChangeListener;
    }
}
