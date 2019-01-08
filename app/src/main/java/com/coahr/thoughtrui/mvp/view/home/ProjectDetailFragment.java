package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.model.Bean.Event_ProjectDetail;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;
import com.coahr.thoughtrui.mvp.view.attence.AttendanceRootActivity;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 8:56
 */
public class   ProjectDetailFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.kaoqing)
    RelativeLayout kaoqing;
    @BindView(R.id.fangwen)
    RelativeLayout fangwen;
    @BindView(R.id.attachment)
    RelativeLayout attachment;
    @BindView(R.id.naviBar)
    MyTittleBar naviBar;

    public static ProjectDetailFragment newInstance() {
        ProjectDetailFragment projectDetailFragment=new ProjectDetailFragment();
        return  projectDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * Evenbus首页或搜索页数据
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(Event_ProjectDetail messageEvent) {
        if (messageEvent != null) {
            Constants.ht_ProjectId=messageEvent.getP_Id();
            KLog.d("项目idht",Constants.ht_ProjectId);
            if (messageEvent.isOffline()){  //离线
              Constants.DbProjectId=String.valueOf(messageEvent.getDb_Id());
              Constants.name_Project=messageEvent.getPname();
              KLog.d("1项目id",Constants.DbProjectId);
          } else {                     //在线
              List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectBy_Where(ProjectsDB.class, new String[]{"id"}, "pid=?", messageEvent.getP_Id());
              if (projectsDBS != null && projectsDBS.size()>0) {
                  Constants.DbProjectId=String.valueOf(projectsDBS.get(0).getId());
                  Constants.name_Project=projectsDBS.get(0).getPname();
              }
              KLog.d("2项目id",Constants.DbProjectId);
          }
        }
    }

    @Override
    public BaseContract.Presenter getPresenter() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_projectdetail;
    }

    @Override
    public void initView() {
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

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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
        Intent intent=new Intent(_mActivity,AttendanceRootActivity.class);
        startActivity(intent);
       // start(StartProjectFragment.newInstance());
    }
    /**
     * 跳转到开始访问页面
     */
    private void JumpToStartProject(){
        Intent intent=new Intent(_mActivity,StartProjectActivity.class);
        startActivity(intent);
    }

}
