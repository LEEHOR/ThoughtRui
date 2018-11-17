package com.coahr.thoughtrui.mvp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.MyTabFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.Event_ProjectDetail;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.presenter.MyTabFragmentP;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.home.adapter.MyTabFOffLineAdapter;
import com.coahr.thoughtrui.mvp.view.home.adapter.MyTabFOnLineAdapter;
import com.coahr.thoughtrui.widgets.SelectImageView;
import com.coahr.thoughtrui.widgets.SelectTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:48
 */
public class MyTabFragment extends BaseChildFragment<MyTabFragmentC.Presenter> implements MyTabFragmentC.View, View.OnClickListener {

    @Inject
    MyTabFragmentP p;
    @BindView(R.id.rl_start_time)
    RelativeLayout rl_start_time;
    @BindView(R.id.rl_end_time)
    RelativeLayout rl_end_time;
    @BindView(R.id.rl_distance)
    RelativeLayout rl_distance;
    //text
    @BindView(R.id.tv_start_time)
    SelectTextView tv_start_time;
    @BindView(R.id.tv_end_time)
    SelectTextView tv_end_time;
    @BindView(R.id.tv_distance)
    SelectTextView tv_distance;
    //image
    @BindView(R.id.iv_start_time)
    SelectImageView iv_start_time;
    @BindView(R.id.iv_end_time)
    SelectImageView iv_end_time;
    @BindView(R.id.iv_distance)
    SelectImageView iv_distance;
    //在线/离线
    @BindView(R.id.tab_on_recyclerView)
    RecyclerView tab_on_recyclerView;
    @BindView(R.id.tab_off_recyclerView)
    RecyclerView tab_off_recyclerView;
    //
    @BindView(R.id.myTab_swipe)
    SwipeRefreshLayout myTab_swipe;

    private int type = 0; //类型
    private MyTabFOnLineAdapter tabFAdapter;
    private LinearLayoutManager lm_on;
    private boolean isLoad; //是否定位成功
    private MyTabFOffLineAdapter myTabFOffLineAdapter;
    private LinearLayoutManager lm_off;
    private SpacesItemDecoration spacesItemDecoration;


    @Override
    public MyTabFragmentC.Presenter getPresenter() {
        return p;
    }

    public static MyTabFragment newInstance(int position) {
        MyTabFragment fragment = new MyTabFragment();
        Bundle arg = new Bundle();
        arg.putInt("type", position);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_hometab;
    }

    @Override
    public void initView() {
        rl_start_time.setOnClickListener(this);
        rl_end_time.setOnClickListener(this);
        rl_distance.setOnClickListener(this);
        //刷新
        myTab_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (isNetworkAvailable()) {
                    if (isLoad) {
                        getDate();
                    } else {
                        p.startLocation();
                    }
                } else {  //离线数据查询本地

                    p.getTypeDate(type);
                }

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spacesItemDecoration = new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 10));


    }

    @Override
    public void initData() {
        lm_on = new LinearLayoutManager(BaseApplication.mContext);
        tab_on_recyclerView.setLayoutManager(lm_on);
        lm_off = new LinearLayoutManager(BaseApplication.mContext);
        tab_off_recyclerView.setLayoutManager(lm_off);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        tabFAdapter = new MyTabFOnLineAdapter();
        tab_on_recyclerView.setAdapter(tabFAdapter);
        myTabFOffLineAdapter = new MyTabFOffLineAdapter();
        tab_off_recyclerView.setAdapter(myTabFOffLineAdapter);
        if (tab_on_recyclerView.getItemDecorationCount() == 0) {
            tab_on_recyclerView.addItemDecoration(spacesItemDecoration);
        } else {
            tab_off_recyclerView.removeItemDecoration(spacesItemDecoration);

        }

        if (tab_off_recyclerView.getItemDecorationCount() == 0) {
            tab_off_recyclerView.addItemDecoration(spacesItemDecoration);
        } else {
            tab_off_recyclerView.removeItemDecoration(spacesItemDecoration);
        }
        if (isNetworkAvailable()) {  //有网络
            p.startLocation();
            tabFAdapter.setAdapter_online(new MyTabFOnLineAdapter.adapter_online() {
                @Override
                public void newListClick(HomeDataList.DataBean.newListBean newListBean) {
                    setDate_onLine_newList(newListBean);
                    JumpToProject();
                }

                @Override
                public void newListLongClick(HomeDataList.DataBean.newListBean newListBean) {

                }

                @Override
                public void completeClick(HomeDataList.DataBean.CompleteListBean completeListBean) {
                    setDate_onLine_Complete(completeListBean);
                    JumpToProject();
                }

                @Override
                public void completeLongClick(HomeDataList.DataBean.CompleteListBean completeListBean) {
                    showDialog("已完成", "确定删除", true);
                }

                @Override
                public void unCompleteClick(HomeDataList.DataBean.UnCompleteListBean unCompleteListBean) {
                    setDate_onLine_unComplete(unCompleteListBean);
                    JumpToProject();
                }

                @Override
                public void unCompleteLongClick(HomeDataList.DataBean.UnCompleteListBean unCompleteListBean) {
                    showDialog("未完成", "确定删除", false);
                }

                @Override
                public void unDownLoadClick(HomeDataList.DataBean.newListBean newListBean) {
                    setDate_onLine_newList(newListBean);
                }

                @Override
                public void unDownLoadLongClick(HomeDataList.DataBean.newListBean newListBean) {

                }
            });
        } else { //无网络
            p.getTypeDate(type);

            myTabFOffLineAdapter.setAdapter_offline(new MyTabFOffLineAdapter.adapter_offline() {
                @Override
                public void newListClick(ProjectsDB projectsDB) {

                }

                @Override
                public void newListLongClick(ProjectsDB projectsDB) {

                }

                @Override
                public void completeClick(ProjectsDB projectsDB) {

                }

                @Override
                public void completeLongClick(ProjectsDB projectsDB) {

                }

                @Override
                public void unCompleteClick(ProjectsDB projectsDB) {

                }

                @Override
                public void unCompleteLongClick(ProjectsDB projectsDB) {

                }

                @Override
                public void unDownLoadClick(ProjectsDB projectsDB) {

                }

                @Override
                public void unDownLoadLongClick(ProjectsDB projectsDB) {

                }
            });

        }

    }

    @Override
    public void onLocationSuccess(BDLocation location) {
        isLoad = true;
        myTab_swipe.setRefreshing(false);
        Constants.Latitude = location.getLatitude();
        Constants.Longitude = location.getLongitude();
        getDate();
    }

    @Override
    public void onLocationFailure(int failure) {
        isLoad = false;
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void getHomeDataSuccess(HomeDataList homeDataList) {
        myTab_swipe.setRefreshing(false);
        tabFAdapter.setType(type);
        tabFAdapter.setHomeDataList(homeDataList, BaseApplication.mContext);
        change_online();

    }

    @Override
    public void getHomeDataFailure(String fail) {
        ToastUtils.showLong(fail);
        p.getTypeDate(type);
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void getHomeMoreSuccess(HomeDataList homeDataList) {

    }

    @Override
    public void getHomeMoreFailure(String fail) {

    }

    /**
     * 离线数据
     *
     * @param projectsDB
     */
    @Override
    public void getTypeDateSuccess(List<ProjectsDB> projectsDB) {
        myTabFOffLineAdapter.setType(type);
        myTabFOffLineAdapter.setHomeDataList(projectsDB, BaseApplication.mContext);
        change_offline();
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void getTypeDateFailure(int fail) {
        if (fail == 0) {
            ToastUtils.showLong( "没有更多本地数据");
        }
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_start_time:
                setChecked(R.id.rl_start_time);
                break;
            case R.id.rl_end_time:
                setChecked(R.id.rl_end_time);
                break;
            case R.id.rl_distance:
                setChecked(R.id.rl_distance);
                break;
        }
    }

    private void setChecked(int id) {
        if (id == R.id.rl_start_time) {
            tv_start_time.toggle(false);
            tv_end_time.toggle(true);
            tv_distance.toggle(true);

            iv_start_time.toggle(false);
            iv_end_time.toggle(true);
            iv_distance.toggle(true);
        } else if (id == R.id.rl_end_time) {
            tv_start_time.toggle(true);
            tv_end_time.toggle(false);
            tv_distance.toggle(true);

            iv_start_time.toggle(true);
            iv_end_time.toggle(false);
            iv_distance.toggle(true);
        } else if (id == R.id.rl_distance) {
            tv_start_time.toggle(true);
            tv_end_time.toggle(true);
            tv_distance.toggle(false);

            iv_start_time.toggle(true);
            iv_end_time.toggle(true);
            iv_distance.toggle(false);
        } else {
            tv_start_time.toggle(false);
            tv_end_time.toggle(true);
            tv_distance.toggle(true);

            iv_start_time.toggle(false);
            iv_end_time.toggle(true);
            iv_distance.toggle(true);
        }
    }

    private void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("latitude", Constants.Latitude);
        map.put("longitude", Constants.Longitude);
        map.put("sessionId", Constants.sessionId);
        p.getHomeData(map);
    }

    private boolean isNetworkAvailable() {

        boolean networkAvailable = NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext);
        if (networkAvailable) {
            tab_off_recyclerView.setVisibility(View.GONE);
            tab_on_recyclerView.setVisibility(View.VISIBLE);
        } else {
            tab_off_recyclerView.setVisibility(View.VISIBLE);
            tab_on_recyclerView.setVisibility(View.GONE);
        }
        return networkAvailable;
    }

    private void change_online() {
        tab_off_recyclerView.setVisibility(View.GONE);
        tab_on_recyclerView.setVisibility(View.VISIBLE);
    }

    private void change_offline() {
        tab_off_recyclerView.setVisibility(View.VISIBLE);
        tab_on_recyclerView.setVisibility(View.GONE);
    }

    private void showDialog(String title, String Content, final boolean delete) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("确认")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (delete) {
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            }
        }).build().show();
    }

    private void JumpToProject() {
        Intent intent = new Intent(getActivity(), ConstantsActivity.class);
        intent.putExtra("from", Constants.MyTabFragmentCode);
        intent.putExtra("to", Constants.ProjectDetailFragmentCode);
        startActivity(intent);
    }

    /**
     * 在线数据
     *
     * @param newListBean
     */
    //新项目或未下载
    private void setDate_onLine_newList(HomeDataList.DataBean.newListBean newListBean) {
        EventBus.getDefault().postSticky(new Event_ProjectDetail(newListBean.getCname(), newListBean.getDname(), newListBean.getPname(), newListBean.getAreaAddress(), newListBean.getCode(), newListBean.getCompleteStatus()
                , newListBean.getDownloadTime(), newListBean.getEndTime(), newListBean.getGrade(), 0, newListBean.getId(), newListBean.getInspect(), newListBean.getLatitude(), newListBean.getLocation(), newListBean.getLongitude(), newListBean.getManager()
                , newListBean.getModifyTime(), newListBean.getNotice(), newListBean.getProgress(), newListBean.getRecord(), newListBean.getStartTime(), false));
    }

    //未完成
    private void setDate_onLine_unComplete(HomeDataList.DataBean.UnCompleteListBean unComplete) {
        EventBus.getDefault().postSticky(new Event_ProjectDetail(unComplete.getCname(), unComplete.getDname(), unComplete.getPname(), unComplete.getAreaAddress(), unComplete.getCode(), unComplete.getCompleteStatus()
                , unComplete.getDownloadTime(), unComplete.getEndTime(), unComplete.getGrade(), 0, unComplete.getId(), unComplete.getInspect(), unComplete.getLatitude(), unComplete.getLocation(), unComplete.getLongitude(), unComplete.getManager()
                , unComplete.getModifyTime(), unComplete.getNotice(), unComplete.getProgress(), unComplete.getRecord(), unComplete.getStartTime(), false));
    }

    //已完成
    private void setDate_onLine_Complete(HomeDataList.DataBean.CompleteListBean complete) {
        EventBus.getDefault().postSticky(new Event_ProjectDetail(complete.getCname(), complete.getDname(), complete.getPname(), complete.getAreaAddress(), complete.getCode(), complete.getCompleteStatus()
                , complete.getDownloadTime(), complete.getEndTime(), complete.getGrade(), 0, complete.getId(), complete.getInspect(), complete.getLatitude(), complete.getLocation(), complete.getLongitude(), complete.getManager()
                , complete.getModifyTime(), complete.getNotice(), complete.getProgress(), complete.getRecord(), complete.getStartTime(), false));
    }

    /**
     * 离线数据
     */
    private void setDate_offline(ProjectsDB projectsDB) {
        EventBus.getDefault().postSticky(new Event_ProjectDetail(projectsDB.getcName(), projectsDB.getdName(), projectsDB.getPname(), projectsDB.getAddress(), projectsDB.getCode(), projectsDB.getCompleteStatus()
                , projectsDB.getDownloadTime(), projectsDB.getEndTime(), projectsDB.getGrade(), projectsDB.getId(), projectsDB.getPid(), projectsDB.getInspect(), projectsDB.getLatitude(), projectsDB.getLocation(), projectsDB.getLongitude(), projectsDB.getManager()
                , projectsDB.getModifyTime(), projectsDB.getNotice(), projectsDB.getProgress(), projectsDB.getRecord(), projectsDB.getStartTime(), true));
    }
}
