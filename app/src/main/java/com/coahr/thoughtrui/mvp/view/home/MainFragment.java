package com.coahr.thoughtrui.mvp.view.home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.MyMainFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.Event_Main;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.presenter.MyMainFragmentP;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.home.adapter.MainFragmentViewPageAdapter;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;
import org.litepal.crud.async.FindMultiExecutor;
import org.litepal.crud.callback.FindMultiCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.coahr.thoughtrui.commom.Constants.ht_ProjectId;
import static com.coahr.thoughtrui.commom.Constants.sessionId;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:42
 */
public class MainFragment extends BaseFragment<MyMainFragmentC.Presenter> implements MyMainFragmentC.View {
    @Inject
    MyMainFragmentP p;
    @BindView(R.id.home_tab)
    TabLayout home_tab;
    @BindView(R.id.viewpage)
    ViewPager viewPager;
    @BindView(R.id.ed_search)
    EditText tv_search;

    private int totalSize; //项目总个数

    private int updateSize; //更新的项目个数

    private int saveSize;  //新增的项目个数
    private MainFragmentViewPageAdapter pageAdapter;
    private String sessionId;
    private List<UsersDB> usersDBS;
    private List<ProjectsDB> projectsDBSList;
    private List<String> ht_List;
    private List<String> db_list;
    private static final int MSG_1 = 1;
    private static final int MSG_2 = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_1:
                    getDifferenceSet();
                    break;
                case MSG_2:
                    String obj = (String) msg.obj;
                    deleteByDb(obj);
                    break;
            }
        }
    };


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public MyMainFragmentC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_mainfragment;
    }

    @Override
    public void initView() {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(_mActivity, ConstantsActivity.class);
                intent.putExtra("to", Constants.fragment_topics);
                startActivity(intent);*/
            }
        });
        sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.sessionId_key, null);
        pageAdapter = new MainFragmentViewPageAdapter(getFragmentManager());
        viewPager.setAdapter(pageAdapter);
        home_tab.setupWithViewPager(viewPager);

    }

    @Override
    public void initData() {
        getLocationPermission();
        //当前使用用户
        usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
        KLog.d("sessionId", Constants.sessionId, sessionId);
        if (haslogin()) {
            getDataList();
        }
    }

    @Override
    public void getHomeDataSuccess(HomeDataList homeDataList) {
        List<HomeDataList.DataBean.AllListBean> allList = homeDataList.getData().getAllList();
        if (allList != null && allList.size() > 0) {
            totalSize = allList.size();
            ht_List = new ArrayList<>();
            for (int i = 0; i < allList.size(); i++) {
                ht_List.add(allList.get(i).getId());
                SaveProject(allList.get(i).getId(), allList.get(i));

            }
        }


    }

    @Override
    public void getHomeDataFailure(String fail) {
        Map<String, Object> map = new HashMap<>();
        map.put("latitude", Constants.Latitude);
        map.put("longitude", Constants.Longitude);
        map.put("sessionId", sessionId);
        // p.getHomeData(map);
    }

    @Override
    public void getHomeMoreSuccess(HomeDataList homeDataList) {

    }

    @Override
    public void getHomeMoreFailure(String fail) {

    }

    private void SaveProject(String Pid, HomeDataList.DataBean.AllListBean listBean) {
        //查询数据库有没有当前项目
        List<ProjectsDB> ProjectDBList = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", Pid);
        if (ProjectDBList != null && !ProjectDBList.isEmpty() && ProjectDBList.size() > 0) {
            ProjectDBList.get(0).setRecord(listBean.getRecord()); //录音方式
            ProjectDBList.get(0).setInspect(listBean.getInspect()); //检验方式i
            ProjectDBList.get(0).setPname(listBean.getPname());  //项目名
            ProjectDBList.get(0).setAddress(listBean.getAreaAddress());
            ProjectDBList.get(0).setDownloadTime(listBean.getDownloadTime());
            ProjectDBList.get(0).setcName(listBean.getCname());
            ProjectDBList.get(0).setCode(listBean.getCode());
            ProjectDBList.get(0).setGrade(listBean.getGrade());
            ProjectDBList.get(0).setManager(listBean.getManager());
            ProjectDBList.get(0).setStartTime(listBean.getStartTime());
            ProjectDBList.get(0).setCompleteStatus(listBean.getCompleteStatus());

            ProjectDBList.get(0).setEndTime(listBean.getEndTime());
            ProjectDBList.get(0).setdName(listBean.getDname());
            ProjectDBList.get(0).setLatitude(listBean.getLatitude());
            ProjectDBList.get(0).setLocation(listBean.getLocation());
            ProjectDBList.get(0).setLongitude(listBean.getLongitude());
            ProjectDBList.get(0).setProgress(listBean.getProgress());
            ProjectDBList.get(0).setManager(listBean.getManager());
            ProjectDBList.get(0).setGrade(listBean.getGrade());
            ProjectDBList.get(0).setModifyTime(listBean.getModifyTime());
            ProjectDBList.get(0).setNotice("xixixixixixixixixixiixixx");
           /* if (usersDBS != null && usersDBS.size() > 0) {
                ProjectDBList.get(0).setUser(usersDBS.get(0));
            }*/
            //   List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
            int update = ProjectDBList.get(0).update(ProjectDBList.get(0).getId());
            if (update > 0) {
                updateSize++;
            }
        } else {
            ProjectsDB projectsDB = new ProjectsDB();
            KLog.d("缓存", listBean.getId());
            projectsDB.setPid(listBean.getId());
            projectsDB.setRecord(listBean.getRecord()); //录音方式
            projectsDB.setInspect(listBean.getInspect()); //检验方式
            projectsDB.setPname(listBean.getPname());  //项目名
            projectsDB.setAddress(listBean.getAreaAddress());
            projectsDB.setStartTime(listBean.getStartTime());
            projectsDB.setcName(listBean.getCname());
            projectsDB.setCode(listBean.getCode());
            projectsDB.setDownloadTime(listBean.getDownloadTime());
            projectsDB.setCompleteStatus(listBean.getCompleteStatus());
            projectsDB.setProgress(listBean.getProgress());
            projectsDB.setdName(listBean.getDname());
            projectsDB.setLatitude(listBean.getLatitude());
            projectsDB.setLocation(listBean.getLocation());
            projectsDB.setLongitude(listBean.getLongitude());
            projectsDB.setModifyTime(listBean.getModifyTime());
            projectsDB.setNotice(listBean.getNotice());
            projectsDB.setEndTime(listBean.getEndTime());
            projectsDB.setManager(listBean.getManager());
            projectsDB.setIsComplete(0);
            projectsDB.setStage("1");
            if (usersDBS != null && usersDBS.size() > 0) {
                projectsDB.setUser(usersDBS.get(0));
            }
            boolean save = projectsDB.save();
            if (save) {
                saveSize++;
            }
        }

        if (totalSize == (updateSize + saveSize)) {
            KLog.d("缓存", (updateSize + saveSize));
            ToastUtils.showLong("项目数据缓存完成");
            setPager();
            //开启handler
            mHandler.sendEmptyMessage(MSG_1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(MSG_1);
            mHandler.removeMessages(MSG_2);
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取登陆状态
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(Event_Main messageEvent) {
        if (messageEvent.getIsLoad() == 1 && messageEvent.getPage() == 0) {
            sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.sessionId_key, null);
            usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
            if (haslogin()) {
                getDataList();
            }
        }
    }

    /**
     * 动态获取定位权限
     */
    private void getLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                @Override
                public void PermissionSuccess(List<String> permissions) {

                }

                @Override
                public void PermissionFail(List<String> permissions) {
                    Toast.makeText(_mActivity, "获取权限失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void PermissionHave() {

                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        } else {

        }
    }

    private void setPager() {
        viewPager.setCurrentItem(0);
    }

    private void getDataList() {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", Constants.sessionId);
        p.getHomeData(map);
    }

    /**
     * 删除后台数据与数据库中不相同的项目
     */
    private void deleteByDb(final String pid) {
        List<ProjectsDB> ProjectDBList = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", pid);
        if (ProjectDBList != null && ProjectDBList.size() > 0) {
            List<SubjectsDB> subjectsDBList = ProjectDBList.get(0).getSubjectsDBList();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileIOUtils.deleteFile(new File(Constants.SAVE_DIR_PROJECT_Document + pid));
                }
            }).start();
            if (subjectsDBList != null && subjectsDBList.size() > 0) {
                for (int i = 0; i < subjectsDBList.size(); i++) {
                    int d = DataBaseWork.DBDeleteById(SubjectsDB.class, subjectsDBList.get(i).getId());
                    KLog.d("删除题目", subjectsDBList.get(i).getId());

                }
            }
            int i = DataBaseWork.DBDeleteById(ProjectsDB.class, ProjectDBList.get(0).getId());

        }

    }

    /**
     * 获取差集
     */
    private void getDifferenceSet() {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
            if (projectsDBSList != null && projectsDBSList.size() > 0) {
                db_list = new ArrayList<>();
                for (int i = 0; i < projectsDBSList.size(); i++) {
                    db_list.add(projectsDBSList.get(i).getPid());
                }
            }
        }
        if (db_list != null && ht_List != null) {
            if (db_list.removeAll(ht_List)) {
                if (db_list != null && db_list.size() > 0) {
                    for (int i = 0; i < db_list.size(); i++) {
                        Message message = new Message();
                        message.what = MSG_2;
                        message.obj = db_list.get(i);
                        mHandler.sendMessage(message);
                    }
                }
            }
        }
    }
}
