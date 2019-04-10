package com.coahr.thoughtrui.widgets.AltDialog;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseDialogFragment;
import com.coahr.thoughtrui.mvp.constract.ProjectSuccessDialog_C;
import com.coahr.thoughtrui.mvp.presenter.ProjectSuccessDialog_P;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/17
 * 描述：答题完成弹框
 */
public class ProjectSuccessDialog extends BaseDialogFragment<ProjectSuccessDialog_C.Presenter> implements ProjectSuccessDialog_C.View,View.OnClickListener {
    @Inject
    ProjectSuccessDialog_P p;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_project_prograss)
    TextView tv_project_prograss;
    @BindView(R.id.tv_upload_status)
    TextView tv_upload_status;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    private String projectId;
    private List<ProjectsDB> projectsDBS;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                dismiss();
                break;
        }
    }

    public static ProjectSuccessDialog newInstance(String projectId) {
        ProjectSuccessDialog projectSuccessDialog = new ProjectSuccessDialog();
        Bundle bundle=new Bundle();
        bundle.putString("projectId",projectId);
        projectSuccessDialog.setArguments(bundle);
        return  projectSuccessDialog;
    }

    @Override
    public ProjectSuccessDialog_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_project_success;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            projectId = getArguments().getString("projectId");
            projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectId);

        }
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_user_name.setText(Constants.user_name);
        getSubjectList();
    }

    @Override
    public void initAnimate() {

    }

    @Override
    public void iniWidow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setWindowAnimations(R.style.Photo_See_Animation);
        }
    }

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList,ProjectsDB projectsDB,int totalSize) {
        String format_checking = getResources().getString(R.string.success_dialog_checking);
        String format = String.format(format_checking, subjectsDBList.size(), totalSize);
        tv_project_prograss.setText(format);
            p.getDateSize(subjectsDBList,projectsDB);
    }


    @Override
    public void getSubjectListFailure(String failure) {
         tv_project_prograss.setText(getResources().getString(R.string.success_dialog_date));
        tv_upload_status.setText(getResources().getString(R.string.success_dialog_date));
    }

    @Override
    public void getDateSizeSuccess(int subject, int files) {
        String format_checking = getResources().getString(R.string.success_dialog_date_size);
        String format = String.format(format_checking, subject, files);
        tv_upload_status.setText(format);
    }

    @Override
    public void getDateSizeFailure(String failure) {
        tv_upload_status.setText(getString(R.string.success_dialog_date));
    }

    private  void getSubjectList(){
        if (projectsDBS != null && projectsDBS.size()>0) {
          //  long startTime = projectsDBS.get(0).getStartTime();
          //  String stingYMDHM = TimeUtils.getStingYMDHM(startTime);
          //  tv_start_time.setText("始于"+stingYMDHM);

            String stingYMDHM1 = TimeUtils.getStingYMDHM(System.currentTimeMillis());
            String format = getResources().getString(R.string.success_dialog_end);
            String format1 = String.format(format, stingYMDHM1);
            tv_end_time.setText(format1);
        }
            p.getSubjectList(projectsDBS.get(0));

    }
}
