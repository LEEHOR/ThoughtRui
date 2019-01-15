package com.coahr.thoughtrui.mvp.view.home;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

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
                Intent intent = new Intent(_mActivity, ConstantsActivity.class);
                intent.putExtra("to", Constants.fragment_topics);
                startActivity(intent);
            }
        });
        sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, "sessionId", null);
        pageAdapter = new MainFragmentViewPageAdapter(getFragmentManager());
        viewPager.setAdapter(pageAdapter);
        home_tab.setupWithViewPager(viewPager);

    }

    @Override
    public void initData() {
        getLocationPermission();
        if (sessionId != null) {
            getDataList();
        }
    }

    @Override
    public void getHomeDataSuccess(HomeDataList homeDataList) {
        List<HomeDataList.DataBean.AllListBean> allList = homeDataList.getData().getAllList();
        if (allList != null && allList.size() > 0) {
            totalSize = allList.size();
            for (int i = 0; i < allList.size(); i++) {
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
        //当前使用用户
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionId=?", Constants.sessionId);
        if (ProjectDBList != null && !ProjectDBList.isEmpty() && ProjectDBList.size() > 0) {
            ProjectDBList.get(0).setRecord(listBean.getRecord()); //录音方式
            ProjectDBList.get(0).setInspect(listBean.getInspect()); //检验方式
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
            ProjectDBList.get(0).setNotice(listBean.getNotice());
            if (usersDBS != null && usersDBS.size() > 0) {
                ProjectDBList.get(0).setUser(usersDBS.get(0));
            }
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, "sessionId", null);
           // p.startLocation(1);
            getDataList();
            KLog.d("首页定位------2----2-------2---------------2");
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
                            KLog.d("首页定位------3------3-------3------3----3---");

                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            Toast.makeText(_mActivity, "获取权限失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void PermissionHave() {
                            KLog.d("首页定位------3------3-------3------3----3---");

                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        } else {
            KLog.d("首页定位------3------3-------3------3----3---");

        }
    }

    private void setPager(){
        viewPager.setCurrentItem(0);
    }
    private void getDataList(){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", Constants.sessionId);
        p.getHomeData(map);
    }
}
