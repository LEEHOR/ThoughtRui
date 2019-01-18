package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ProjectDetailFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.Event_ProjectDetail;
import com.coahr.thoughtrui.mvp.presenter.ProjectDetailFragment_P;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceRootActivity;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 8:56
 */
public class  ProjectDetailFragment extends BaseFragment<ProjectDetailFragment_C.Presenter> implements ProjectDetailFragment_C.View, View.OnClickListener {

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

    public static ProjectDetailFragment newInstance(String projectId) {
        ProjectDetailFragment projectDetailFragment=new ProjectDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("projectId",projectId);
        projectDetailFragment.setArguments(bundle);
        return  projectDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  EventBus.getDefault().register(this);
    }

/*    *//**
     * Evenbus首页或搜索页数据
     *//*
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(Event_ProjectDetail messageEvent) {
        if (messageEvent != null) {
            Constants.ht_ProjectId=messageEvent.getP_Id();
            KLog.d("项目idht",Constants.ht_ProjectId);
            if (messageEvent.isOffline()){  //离线
              Constants.DbProjectId=String.valueOf(messageEvent.getDb_Id());
              Constants.name_Project=messageEvent.getPname();
                String cname = messageEvent.getCname();
                if (cname.equals("自由班次")) {
                    Constants.zao_ka=cname;
                    Constants.wan_ka=cname;
                } else {
                    String[] split = cname.split("-");
                    if (split.length > 1) {
                        Constants.zao_ka=split[0];
                        Constants.wan_ka=split[1];
                    }
                }

              KLog.d("1项目id",Constants.DbProjectId);
          } else {                     //在线
              List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class,"pid=?", messageEvent.getP_Id());
              if (projectsDBS != null && projectsDBS.size()>0) {
                  Constants.DbProjectId=String.valueOf(projectsDBS.get(0).getId());
                  Constants.name_Project=projectsDBS.get(0).getPname();
                  String s = projectsDBS.get(0).getcName();
                  if (s.equals("自由班次")) {
                      Constants.zao_ka=s;
                      Constants.wan_ka=s;
                  } else {
                      String[] split = s.split("-");
                      if (split.length > 1) {
                          Constants.zao_ka=split[0];
                          Constants.wan_ka=split[1];
                      }
                  }
              }
              KLog.d("2项目id",Constants.DbProjectId);
          }
        }
    }*/


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

        naviBar.getRightText().setVisibility(View.VISIBLE);
        naviBar.getRightText().setText("浏览");
        naviBar.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void initData() {
        getSubjectList(projectId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kaoqing:
                JumpToAttendance();
                break;
            case R.id.fangwen:
                showDialog();
                break;
            case R.id.attachment:
                JumpToProjectAnnex();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }*/
    }

    private void showDialog(){
        new MaterialDialog.Builder(_mActivity)
                .title("提示")
                .content("您是否打卡")
                .negativeText("去打卡")
                .positiveText("已经打卡")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        JumpToAttendance();
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
    private void JumpToAttendance(){
        if (NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext)) {
            Intent intent=new Intent(_mActivity,AttendanceRootActivity.class);
            startActivity(intent);
        } else {
            ToastUtils.showLong("没有网络，无法打卡");
        }

    }
    /**
     * 跳转到开始访问页面
     */
    private void JumpToStartProject(){
        if (NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext)) {
            Intent intent = new Intent(_mActivity, StartProjectActivity.class);
            startActivity(intent);
        }else {
            ToastUtils.showLong("没有网络，无法访问");
        }
    }

    /**
     * 跳转到项目附件
     */
    private void JumpToProjectAnnex(){
        Intent intent=new Intent(_mActivity,ConstantsActivity.class);
        intent.putExtra("from", Constants.MainActivityCode);
        intent.putExtra("to", Constants.fragment_AnnexViewPager);
        startActivity(intent);
    }

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, ProjectsDB projectsDB, int totalSize) {
        tv_fstatus.setText(subjectsDBList.size()==totalSize?"已完成":"未完成"+"("+subjectsDBList.size()+"/"+totalSize+")");
        p.getDateSize(subjectsDBList,projectsDB);
    }

    @Override
    public void getSubjectListFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getDateSizeSuccess(int subject, int files) {
        tv_upload_status.setText("数据"+subject+"条，"+"数据"+files+"个未上传");
    }

    @Override
    public void getDateSizeFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    /**
     * 获取题目列表
     * @param projectId
     */
    private void getSubjectList(String projectId){
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectId);
        if (projectsDBS != null && projectsDBS.size()>0) {
            ProjectsDB projectsDB = projectsDBS.get(0);
            tv_cName.setText(projectsDB.getcName());
            tv_cCode.setText(projectsDB.getCode());
            tv_cLevel.setText(projectsDB.getGrade());
            tv_cAddress.setText(projectsDB.getAddress()+projectsDB.getLocation());
            tv_time_cycle.setText(TimeUtils.getStingYMD(projectsDB.getStartTime())+"/"+TimeUtils.getStringDate_end(projectsDB.getEndTime()));
            tv_Kclass.setText(projectsDB.getcName());
            project_detail_name.setText(projectsDB.getPname());
            String progress = projectsDB.getProgress();
            String[] split_p = progress.split("/");
            if (split_p.length>0) {
                project_detail_progress.setMax(Integer.parseInt(split_p[1]));
                project_detail_progress.setProgress(Integer.parseInt(split_p[0]));
            }
            project_detail_time.setText("发布于"+TimeUtils.getStingYMD(projectsDB.getModifyTime()));
            tv_type.setText(projectsDB.getInspect()==1?"飞检"
                    :projectsDB.getInspect()==2?"神秘顾客"
                    :projectsDB.getInspect()==3?"新店验收"
                    :"飞检");
            tv_project_manager.setText(projectsDB.getManager());
            tv_project_user.setText(Constants.user_name);
            tv_project_describe.setText(projectsDB.getNotice());
            Constants.DbProjectId= String.valueOf(projectsDB.getId());
            Constants.ht_ProjectId=projectsDB.getPid();
            Constants.name_Project=projectsDB.getPname();
            String s =projectsDB.getcName();
            if (s.equals("自由班次")) {
                Constants.zao_ka=s;
                Constants.wan_ka=s;
            } else {
                String[] split = s.split("-");
                if (split.length > 1) {
                    Constants.zao_ka=split[0];
                    Constants.wan_ka=split[1];
                }
            }
            p.getSubjectList(projectsDB);
        }
    }
}
