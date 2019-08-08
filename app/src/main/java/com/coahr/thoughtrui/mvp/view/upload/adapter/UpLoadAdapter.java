package com.coahr.thoughtrui.mvp.view.upload.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.ReviewScoreUtil;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/15
 * 描述：上传页面adapter
 */
public class UpLoadAdapter extends BaseQuickAdapter<ProjectsDB, BaseViewHolder> {
    private onSelectChangeListener selectChangeListener;
    private boolean visible;
    private List<ProjectsDB> projects_Positions = new ArrayList<>();

    public UpLoadAdapter() {
        super(R.layout.item_fragment_upload, null);
    }

    public void setCheckViewVisible(boolean b) {
        this.visible = b;
        projects_Positions.clear();
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProjectsDB item) {
        if (item != null) {
            helper.setText(R.id.up_tv_schedule, item.getProgress())
                    .setText(R.id.f_type, TextUtils.isEmpty(item.getSale_code()) ? "[" + item.getService_code() + "]" : "[" + item.getSale_code() + "]")
                    .setText(R.id.up_tv_time, item.getPname())
                    .setText(R.id.up_tv_project_code, item.getCode())
                    .setText(R.id.up_tv_project_name, item.getPname())
                    .setText(R.id.up_tv_project_company, item.getDname())
                    .setText(R.id.up_tv_project_address, item.getLocation())
                    .setText(R.id.up_tv_update_time, TimeUtils.getStingYMDHM(item.getModifyTime()));
            String itemDate = getItemDate(item.getPid());
            ((TextView) helper.getView(R.id.up_item_data)).setText(itemDate);

            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", item.getPid());
            int totalScore = 0;
            if (projectsDBS != null && projectsDBS.size() > 0) {
                List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
                totalScore = ReviewScoreUtil.getTotalScore(subjectsDBS);
            }

            helper.setText(R.id.up_tv_project_score, "检核得分：" + totalScore + "分");
        }

        if (visible) {
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

        ((ImageView) helper.getView(R.id.iv_upload_btn)).setOnClickListener(new View.OnClickListener() {
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

    /**
     * 获取题目的数据
     */
    private String getItemDate(String id) {
        String massage = BaseApplication.mContext.getResources().getString(R.string.toast_25);
        int CountAll = 0;
        int dataSize = 0;
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", id);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                for (int i = 0; i < subjectsDBS.size(); i++) {
                    CountAll++;
                    if (subjectsDBS.get(i).getIsComplete() == 1 && subjectsDBS.get(i).getsUploadStatus() == 0) {
//                        List<String> fileList = FileIOUtils.getFileList(Constants.SAVE_DIR_PROJECT_Document + id + "/" + subjectsDBS.get(i).getNumber() + "_" + subjectsDBS.get(i).getHt_id());
                        List<String> fileList = FileIOUtils.getAllFileList(Constants.SAVE_DIR_PROJECT_Document + id + "/" + subjectsDBS.get(i).getNumber() + "_" + subjectsDBS.get(i).getHt_id());
                        if (fileList != null && fileList.size() > 0) {
                            for (int j = 0; j < fileList.size(); j++) {
                                if (!fileList.get(j).endsWith("txt")) {
                                    dataSize++;
                                }
                            }
                        }
                    }
                }
                String format = BaseApplication.mContext.getResources().getString(R.string.upload_fragment_date_size);
                String format1 = String.format(format, CountAll, dataSize);
                massage = format1;
            } else {

            }
        }
        return massage;
    }
}
