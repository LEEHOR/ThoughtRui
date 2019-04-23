package com.coahr.thoughtrui.mvp.view.home;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ProjectDetailFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.ProjectDetail;
import com.coahr.thoughtrui.mvp.presenter.ProjectDetailFragment_P;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceRootActivity;
import com.coahr.thoughtrui.mvp.view.projectAnnex.FragmentAnnexViewPager;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 8:56
 */
public class ProjectDetailFragment extends BaseFragment<ProjectDetailFragment_C.Presenter> implements ProjectDetailFragment_C.View, View.OnClickListener {

    @Inject
    ProjectDetailFragment_P p;
    @BindView(R.id.kaoqing)
    RelativeLayout kaoqing;
    @BindView(R.id.fangwen)
    RelativeLayout fangwen;
    @BindView(R.id.attachment)
    RelativeLayout attachment;
    @BindView(R.id.naviBar)
    MyTittleBar naviBar;
    @BindView(R.id.project_detail_name)
    TextView project_detail_name;   //项目名
    @BindView(R.id.project_detail_time)
    TextView project_detail_time;  //发布时间
    @BindView(R.id.project_detail_progress)
    ProgressBar project_detail_progress;  //进度
    @BindView(R.id.tv_type)
    TextView tv_type;  //检核类型
    @BindView(R.id.tv_project_manager)
    TextView tv_project_manager; //项目经理
    @BindView(R.id.tv_project_user)
    TextView tv_project_user;  //检核员
    @BindView(R.id.tv_cName)
    TextView tv_cName;  //经销商
    @BindView(R.id.tv_cCode)
    TextView tv_cCode;  //经销商代码
    @BindView(R.id.tv_cLevel)
    TextView tv_cLevel; //经销商等级
    @BindView(R.id.tv_cAddress)
    TextView tv_cAddress;  //经销商地址
    @BindView(R.id.tv_time_cycle)
    TextView tv_time_cycle;  //访问周期
    @BindView(R.id.tv_Kclass)
    TextView tv_Kclass; //考勤班次
    @BindView(R.id.tv_fstatus)
    TextView tv_fstatus; //访问状态
    @BindView(R.id.tv_upload_status)
    TextView tv_upload_status;  // 上传状态
    @BindView(R.id.tv_project_describe)
    TextView tv_project_describe;  //项目描述
    private String projectId;
    private boolean isHaveAudioPermission = false;
    private boolean isHaveLocationPermission = false;
    private String templateId;
    private String dealerId;
    private int type;
    private boolean isHaveProject;

    public static ProjectDetailFragment newInstance(String projectId, String templateId, String dealerId, int type) {
        ProjectDetailFragment projectDetailFragment = new ProjectDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", projectId);
        bundle.putString("templateId", templateId);
        bundle.putString("dealerId", dealerId);
        bundle.putInt("type", type);
        projectDetailFragment.setArguments(bundle);
        return projectDetailFragment;
    }

    @Override
    public ProjectDetailFragment_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_projectdetail;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            projectId = getArguments().getString("projectId");
            templateId = getArguments().getString("templateId");
            dealerId = getArguments().getString("dealerId");
            type = getArguments().getInt("type");
        }
        kaoqing.setOnClickListener(this);
        fangwen.setOnClickListener(this);
        attachment.setOnClickListener(this);
        naviBar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });

        naviBar.getRightText().setVisibility(View.GONE);
        naviBar.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void initData() {
        if (type == 1) {  //经销商页面
            getProDetail();
        }
        if (type == 2) {  //项目列表页
            getSubjectList(projectId);
        }
        if (type == 3) {    //搜索页
            getSubjectList(projectId);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kaoqing:
                if (getLocationPermission()) {
                    JumpToAttendance();
                }
                break;
            case R.id.fangwen:
                if (getAudioPermission()) {
                    showDialog();
                }
                break;
            case R.id.attachment:
                // ToastUtils.showLong("打不开哈哈哈");
                JumpToProjectAnnex();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * 联网得到项目详情
     */
    private void getProDetail() {
        Map map = new HashMap();
        map.put("userId", Constants.sessionId);
        map.put("templateId", templateId);
        map.put("dealerId", dealerId);
        p.getProjectDetail(map);
    }

    private void showDialog() {
        new MaterialDialog.Builder(_mActivity)
                .title(getResources().getString(R.string.dialog_tittle_1))
                .content(getResources().getString(R.string.dialog_content_1))
                .negativeText(getResources().getString(R.string.dialog_negativeText_1))
                .positiveText(getResources().getString(R.string.dialog_positiveText_1))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (getLocationPermission()) {
                            JumpToAttendance();
                        }
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                JumpToStartProject();
            }
        }).build().show();
    }

    /**
     * 跳转到考勤页面
     */
    private void JumpToAttendance() {
        if (isHaveProject) {
            if (NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext)) {
                Intent intent = new Intent(_mActivity, AttendanceRootActivity.class);
                startActivity(intent);
            } else {
                ToastUtils.showLong(getResources().getString(R.string.toast_3));
            }
        } else {
            ToastUtils.showLong(getResources().getString(R.string.toast_9));
        }


    }

    /**
     * 跳转到开始访问页面
     */
    private void JumpToStartProject() {
        if (isHaveProject) {
            if (NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext)) {
                Intent intent = new Intent(_mActivity, StartProjectActivity.class);
                startActivity(intent);
            } else {
                ToastUtils.showLong(getResources().getString(R.string.toast_3));
            }
        } else {
            ToastUtils.showLong(getResources().getString(R.string.toast_9));
        }
    }

    /**
     * 跳转到项目附件
     */
    private void JumpToProjectAnnex() {
        start(FragmentAnnexViewPager.newInstance());
       // Intent intent = new Intent(_mActivity, ConstantsActivity.class);
       // intent.putExtra("from", Constants.MainActivityCode);
       // intent.putExtra("to", Constants.fragment_AnnexViewPager);
       // startActivity(intent);
    }

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, ProjectsDB projectsDB, int totalSize) {
        tv_fstatus.setText(subjectsDBList != null && subjectsDBList.size() != 0 && subjectsDBList.size() == totalSize ? "已完成" : "未完成" + "(" + subjectsDBList.size() + "/" + totalSize + ")");
        p.getDateSize(subjectsDBList, projectsDB);
    }

    @Override
    public void getSubjectListFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getDateSizeSuccess(int subject, int files) {
        String sAgeFormat1= getResources().getString(R.string.string_1);
        String sFinal1 = String.format(sAgeFormat1, subject,files);
        tv_upload_status.setText(sFinal1);
    }

    @Override
    public void getDateSizeFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getProjectDetailSuccess(ProjectDetail projectDetail) {
        if (projectDetail.getData() != null) {
            isHaveProject = true;
            int id = 0;
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectDetail.getData().getId());
            if (projectsDBS != null && projectsDBS.size() > 0) {
                id = projectsDBS.get(0).getId();
                ProjectsDB projectsDB = new ProjectsDB();
                projectsDB.setDownloadTime(projectDetail.getData().getUploadTime());
                projectsDB.setProgress(projectDetail.getData().getProgress());
                int update = projectsDB.update(projectsDBS.get(0).getId());
                Constants.ht_ProjectId = projectDetail.getData().getId();
                Constants.name_Project = projectDetail.getData().getPname();
                //获取数据
                p.getSubjectList(projectsDBS.get(0));
            } else {
                List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
                ProjectsDB projectsDB = new ProjectsDB();
                //  projectsDB.setcName(projectDetail.getData().getCname());
                projectsDB.setProgress(projectDetail.getData().getProgress());
                projectsDB.setManager(projectDetail.getData().getManager());
                projectsDB.setLocation(projectDetail.getData().getLocation());
                projectsDB.setPid(projectDetail.getData().getId());
                projectsDB.setAddress(projectDetail.getData().getAreaAddress());
                projectsDB.setGrade(projectDetail.getData().getGrade());
                projectsDB.setPname(projectDetail.getData().getPname());
                projectsDB.setDname(projectDetail.getData().getDname());
                projectsDB.setCname(projectDetail.getData().getCname());
                projectsDB.setLongitude(projectDetail.getData().getLongitude());
                projectsDB.setLatitude(projectDetail.getData().getLatitude());
                projectsDB.setModifyTime(System.currentTimeMillis());
                projectsDB.setUploadTime(projectDetail.getData().getUploadTime());
                projectsDB.setService_code(projectDetail.getData().getService_code());
                projectsDB.setSale_code(projectDetail.getData().getSale_code());
                projectsDB.setNotice(projectDetail.getData().getNotice());
                projectsDB.setStage("1");
                if (usersDBS != null && usersDBS.size() > 0) {
                    projectsDB.setUser(usersDBS.get(0));
                }
                projectsDB.save();
                String sAgeFormat1= getResources().getString(R.string.string_1);
                String sFinal1 = String.format(sAgeFormat1, 0,0);
                tv_upload_status.setText(sFinal1);
                tv_fstatus.setText(getResources().getString(R.string.string_2));
            }
            tv_cName.setText(projectDetail.getData().getCname());
            tv_cCode.setText(TextUtils.isEmpty(projectDetail.getData().getSale_code())?projectDetail.getData().getService_code() : TextUtils.isEmpty(projectDetail.getData().getService_code())? projectDetail.getData().getSale_code():"");
            tv_cLevel.setText(projectDetail.getData().getGrade());
            tv_cAddress.setText(projectDetail.getData().getAreaAddress() + projectDetail.getData().getLocation());
            tv_time_cycle.setText(TimeUtils.getStingYMDHM(projectDetail.getData().getUploadTime()));
            tv_Kclass.setText(projectDetail.getData().getCname());
            project_detail_name.setText(projectDetail.getData().getPname());
            String progress = projectDetail.getData().getProgress();
            String[] split_p = progress.split("/");
            if (split_p.length > 0) {
                project_detail_progress.setMax(Integer.parseInt(split_p[1]));
                project_detail_progress.setProgress(Integer.parseInt(split_p[0]));
            }
            project_detail_time.setText(getResources().getString(R.string.phrases_4));

            tv_project_manager.setText(projectDetail.getData().getManager());
            tv_project_user.setText(Constants.user_name);
            tv_project_describe.setText(projectDetail.getData().getNotice());
            // List<ProjectsDB> projectsDBS1 = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectDetail.getData().getId());
            Constants.ht_ProjectId = projectDetail.getData().getId();
            Constants.name_Project = projectDetail.getData().getPname();
            String s = projectDetail.getData().getCname();
            if (s != null) {
                if (s.equals(getResources().getString(R.string.phrases_34))) {
                    Constants.zao_ka = s;
                    Constants.wan_ka = s;
                } else {
                    String[] split = s.split("-");
                    if (split.length > 1) {
                        Constants.zao_ka = split[0];
                        Constants.wan_ka = split[1];
                    }
                }

            }

        }
    }

    @Override
    public void getProjectDetailFailure(String fail) {
        isHaveProject = false;
        String sAgeFormat1= getResources().getString(R.string.string_1);
        String sFinal1 = String.format(sAgeFormat1, 0,0);
        tv_upload_status.setText(sFinal1);

        String sAgeFormat2= getResources().getString(R.string.string_3);
        String sFinal1_2= String.format(sAgeFormat2, 0,0);
        tv_fstatus.setText(sFinal1_2);
    }

    /**
     * 获取题目列表
     *
     * @param projectId
     */
    private void getSubjectList(String projectId) {
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectId);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            ProjectsDB projectsDB = projectsDBS.get(0);
            tv_cCode.setText(TextUtils.isEmpty(projectsDB.getSale_code()) ?  projectsDB.getService_code() : projectsDB.getSale_code());
            tv_cLevel.setText(projectsDB.getGrade());
            tv_cAddress.setText(projectsDB.getAddress() + projectsDB.getLocation());
            tv_time_cycle.setText(TimeUtils.getStingYMDHM(projectsDB.getUploadTime()));
            tv_Kclass.setText(projectsDB.getCname());
            tv_cName.setText(projectsDB.getDname());
            project_detail_name.setText(projectsDB.getPname());
            String progress = projectsDB.getProgress();
            String[] split_p = progress.split("/");
            if (split_p.length > 0) {
                project_detail_progress.setMax(Integer.parseInt(split_p[1]));
                project_detail_progress.setProgress(Integer.parseInt(split_p[0]));
            }
            project_detail_time.setText(getResources().getString(R.string.phrases_4) + TimeUtils.getStingYMD(projectsDB.getModifyTime()));
            tv_project_manager.setText(projectsDB.getManager());
            tv_project_user.setText(Constants.user_name);
            tv_project_describe.setText(projectsDB.getNotice());
            Constants.DbProjectId = String.valueOf(projectsDB.getId());
            Constants.ht_ProjectId = projectsDB.getPid();
            Constants.name_Project = projectsDB.getPname();
            isHaveProject = true;
            String s = projectsDB.getCname();
            if (s != null) {
                if (s.equals(getResources().getString(R.string.phrases_34))) {
                    Constants.zao_ka = s;
                    Constants.wan_ka = s;
                } else {
                    String[] split = s.split("-");
                    if (split.length > 1) {
                        Constants.zao_ka = split[0];
                        Constants.wan_ka = split[1];
                    }
                }
            }
            p.getSubjectList(projectsDB);
        } else {
            String sAgeFormat1= getResources().getString(R.string.string_1);
            String sFinal1 = String.format(sAgeFormat1, 0,0);
            tv_upload_status.setText(sFinal1);

            String sAgeFormat2= getResources().getString(R.string.string_3);
            String sFinal1_2= String.format(sAgeFormat2, 0,0);
            tv_fstatus.setText(sFinal1_2);
        }
    }

    /*    *
     * 动态获取录音权限
     */
    private boolean getAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                        @Override
                        public void PermissionSuccess(List<String> permissions) {
                            isHaveAudioPermission = false;
                            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            isHaveAudioPermission = false;
                        }

                        @Override
                        public void PermissionHave() {
                            isHaveAudioPermission = true;
                            //EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }
                    }, Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION);

        } else {
            isHaveAudioPermission = true;
            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
        }
        return isHaveAudioPermission;
    }


    /*    *
     * 动态获取录音权限
     */
    private boolean getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                        @Override
                        public void PermissionSuccess(List<String> permissions) {
                            isHaveLocationPermission = false;
                            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            isHaveLocationPermission = false;
                        }

                        @Override
                        public void PermissionHave() {
                            isHaveLocationPermission = true;
                            //EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }
                    }, Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION);

        } else {
            isHaveLocationPermission = true;
            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
        }
        return isHaveLocationPermission;
    }

}
