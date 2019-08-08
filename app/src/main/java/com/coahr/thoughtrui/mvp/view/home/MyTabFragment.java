package com.coahr.thoughtrui.mvp.view.home;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.AlertDialogUtil;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.MyTabFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.Bean.UnDownLoad;
import com.coahr.thoughtrui.mvp.presenter.MyTabFragmentP;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.home.adapter.MyTabFOffLineAdapter;
import com.coahr.thoughtrui.mvp.view.home.adapter.MyTabFOnLineAdapter;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.SelectImageView;
import com.coahr.thoughtrui.widgets.SelectTextView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

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
    private String DownLoadProject_Id;
    private List<HomeDataList.DataBean.AllListBean> allListBeanList = new ArrayList<>();
    private List<String> db_list = new ArrayList<>();

    private List<ProjectsDB> mProjectsDBS;
    private int selectedPosition = -1;

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
                if (!isLoad) {

                    if (isNetworkAvailable()) {
                        isLoad = true;
                        getDate();
                    } else {
                        isLoad = true;
                        p.getTypeDate(type);
                    }

                } else {
                    myTab_swipe.setRefreshing(false);
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
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        if (PreferenceUtils.contains(BaseApplication.mContext, Constants.sessionId_key)) {
            String sessionId = PreferenceUtils.getPrefString(BaseApplication.mContext, Constants.sessionId_key, null);
            Constants.sessionId = sessionId;
            List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
            if (usersDBS != null && usersDBS.size() > 0) {
                Constants.user_id = String.valueOf(usersDBS.get(0).getId());
            }
        }
        lm_on = new LinearLayoutManager(BaseApplication.mContext);
        tab_on_recyclerView.setLayoutManager(lm_on);
        lm_off = new LinearLayoutManager(BaseApplication.mContext);
        tab_off_recyclerView.setLayoutManager(lm_off);

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

        if (haslogin()) {
            if (isNetworkAvailable()) {  //有网络
                getDate();
            } else { //无网络
                p.getTypeDate(type);
            }
        }

        //有网络adapter监听
        tabFAdapter.setAdapter_online(new MyTabFOnLineAdapter.adapter_online() {
            @Override
            public void newListClick(HomeDataList.DataBean.AllListBean newListBean) {
                JumpToProject(newListBean.getId());
            }

            @Override
            public void newListLongClick(HomeDataList.DataBean.AllListBean newListBean) {
//                showDialog(getResources().getString(R.string.dialog_tittle_2), getResources().getString(R.string.dialog_content_2), false);
            }

            @Override
            public void completeClick(HomeDataList.DataBean.AllListBean completeListBean) {
                JumpToProject(completeListBean.getId());
            }

            @Override
            public void completeLongClick(HomeDataList.DataBean.AllListBean completeListBean, int position) {
//                showDialog(getResources().getString(R.string.dialog_tittle_3), getResources().getString(R.string.dialog_content_3), true);
                showBottomDialog(completeListBean.getId(), position);
            }

            @Override
            public void unCompleteClick(HomeDataList.DataBean.AllListBean unCompleteListBean) {
                JumpToProject(unCompleteListBean.getId());
            }

            @Override
            public void unCompleteLongClick(HomeDataList.DataBean.AllListBean unCompleteListBean, int position) {
//                showDialog(getResources().getString(R.string.dialog_tittle_4), getResources().getString(R.string.dialog_content_2), false);
                showBottomDialog(unCompleteListBean.getId(), position);
            }

            @Override
            public void unDownLoadClick(HomeDataList.DataBean.AllListBean newListBean) {
                DownLoadProject_Id = newListBean.getId();
                getUnDownLoad(newListBean.getId());
            }

            @Override
            public void unDownLoadLongClick(HomeDataList.DataBean.AllListBean newListBean) {

            }
        });

        //无网络adapter监听
        myTabFOffLineAdapter.setAdapter_offline(new MyTabFOffLineAdapter.adapter_offline() {
            @Override
            public void newListClick(ProjectsDB projectsDB) {
                JumpToProject(projectsDB.getPid());
            }

            @Override
            public void newListLongClick(ProjectsDB projectsDB) {
//                showDialog(getResources().getString(R.string.dialog_tittle_2), getResources().getString(R.string.dialog_content_2), false);
            }

            @Override
            public void completeClick(ProjectsDB projectsDB) {
                JumpToProject(projectsDB.getPid());
            }

            @Override
            public void completeLongClick(ProjectsDB projectsDB, int position) {
//                showDialog(getResources().getString(R.string.dialog_tittle_3), getResources().getString(R.string.dialog_content_3), true);
                showBottomDialog(projectsDB.getPid(), position);
            }

            @Override
            public void unCompleteClick(ProjectsDB projectsDB) {
                JumpToProject(projectsDB.getPid());
            }

            @Override
            public void unCompleteLongClick(ProjectsDB projectsDB, int position) {
//                showDialog(getResources().getString(R.string.dialog_tittle_4), getResources().getString(R.string.dialog_content_2), false);
                showBottomDialog(projectsDB.getPid(), position);
            }

            @Override
            public void unDownLoadClick(ProjectsDB projectsDB) {
                DownLoadProject_Id = projectsDB.getPid();
                getUnDownLoad(projectsDB.getPid());
            }

            @Override
            public void unDownLoadLongClick(ProjectsDB projectsDB) {
            }
        });
    }

    /**
     * 底部警告Dialog
     */
    private void showBottomDialog(String projectId, int position) {
        AlertDialogUtil.showProjectDeleteDialog(_mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(/*getResources().getString(R.string.dialog_tittle_4), getResources().getString(R.string.dialog_content_2), */false, projectId, position);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除所有
                showDialog(/*getResources().getString(R.string.dialog_tittle_4), getResources().getString(R.string.dialog_content_2), */true, projectId, position);
            }
        });
    }

    @Override
    public void getHomeDataSuccess(HomeDataList homeDataList) {
        allListBeanList.clear();
        if (homeDataList != null) {

            if (homeDataList.getData().getAllList() != null && homeDataList.getData().getAllList().size() > 0) {
                for (int i = 0; i < homeDataList.getData().getAllList().size(); i++) {
                  /*  if (type == 0) {  //新项目
                        if (homeDataList.getData().getAllList().get(i).getCompleteStatus() == 1) {
                            allListBeanList.add(homeDataList.getData().getAllList().get(i));
                        }
                    }*/

                    if (type == 0) {  //全部
                        allListBeanList.add(homeDataList.getData().getAllList().get(i));

                    } else if (type == 1) { //未完成
                        if (homeDataList.getData().getAllList().get(i).getCompleteStatus() == 2) {
                            allListBeanList.add(homeDataList.getData().getAllList().get(i));
                        }
                    } else if (type == 2) { //已完成
                        if (homeDataList.getData().getAllList().get(i).getCompleteStatus() == 3) {
                            allListBeanList.add(homeDataList.getData().getAllList().get(i));
                        }
                    } else {
                        allListBeanList.add(homeDataList.getData().getAllList().get(i));
                    }

                }
            }
        }
        p.getSaveDb(homeDataList);
        tabFAdapter.setType(type);
        tabFAdapter.setHomeDataList(allListBeanList, BaseApplication.mContext);
        change_online();
        isLoad = false;
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void getHomeDataFailure(String fail, int code) {
        ToastUtils.showLong(fail);
        isLoad = false;
        myTab_swipe.setRefreshing(false);
        p.getTypeDate(type);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        ToastUtils.showLong(e.toString());
        isLoad = false;
        myTab_swipe.setRefreshing(false);
        p.getTypeDate(type);
    }

    /**
     * 离线数据
     *
     * @param projectsDB
     */
    @Override
    public void getTypeDateSuccess(List<ProjectsDB> projectsDB) {
        mProjectsDBS = projectsDB;
        myTabFOffLineAdapter.setType(type);
        myTabFOffLineAdapter.setHomeDataList(mProjectsDBS, BaseApplication.mContext);
        change_offline();
        isLoad = false;
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void getTypeDateFailure(int fail) {
        if (fail == 0) {
            ToastUtils.showLong(getResources().getString(R.string.toast_11));
        }
        isLoad = false;
        myTab_swipe.setRefreshing(false);
    }

    @Override
    public void getUnDownLoadSuccess(UnDownLoad unDownLoad) {
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", DownLoadProject_Id);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            ProjectsDB projectsDB = new ProjectsDB();
            projectsDB.setDownloadTime(System.currentTimeMillis());
            projectsDB.setCname(projectsDBS.get(0).getCname());
        }
        if (isNetworkAvailable()) {
            getDate();
        } else {
            p.getTypeDate(type);
        }
    }

    @Override
    public void getUnDownLoadFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getSaveDbSuccess(List<String> dbList) {
        getDifferenceSet(dbList);
    }

    @Override
    public void getSaveDbFailure() {

    }

    @Override
    public void deleteProjectSuccess(String success) {
        ToastUtils.showShort("删除成功");
        //移除当前数据
        if (selectedPosition != -1) {
            allListBeanList.remove(selectedPosition);
        }

        if (tabFAdapter != null) {
            tabFAdapter.setHomeDataList(allListBeanList, BaseApplication.mContext);
        }
    }

    @Override
    public void deleteProjectFailure(String failure) {
        ToastUtils.showShort(failure);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_start_time:
                if (isNetworkAvailable()) {

                } else {

                }
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
        if (haslogin()) {
            Map<String, Object> map = new HashMap<>();
            map.put("sessionId", Constants.sessionId);
            KLog.e("测试代码", "sessionId == " + Constants.sessionId);
            p.getHomeData(map);
        } else {
            isLoad = false;
            myTab_swipe.setRefreshing(false);
        }

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

    private void showDialog(/*String title, String Content, */final boolean isDeleteAll, String projectId, int position) {
        selectedPosition = position;
        KLog.e("测试代码", "projectId == " + projectId);

        new MaterialDialog.Builder(_mActivity)
                .title("警告：如未上传，删除后数据会永久丢失！")
//                .content(Content)
                .positiveText(getResources().getString(R.string.confirm_delete))
                .negativeText(getResources().getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //更新本地数据库数据
                        updateLocalData(projectId, isDeleteAll);

                        //删除项目
                        if (isDeleteAll) {
                            if (isNetworkAvailable()) {
                                Map map = new HashMap();
                                map.put("projectId", projectId);
                                p.deleteProject(map);
                            } else {
                                if (mProjectsDBS != null && mProjectsDBS.size() > 0 && selectedPosition != -1) {
                                    mProjectsDBS.remove(selectedPosition);
                                }
                                //离线数据删除后，更新UI
                                if (myTabFOffLineAdapter != null) {
                                    myTabFOffLineAdapter.setHomeDataList(mProjectsDBS, BaseApplication.mContext);
                                }
                            }
                        }

                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 更新本地数据库数据
     */
    private void updateLocalData(String projectId, boolean isDeleteAll) {
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectId);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            //获取该项目的所属题目集合
            List<SubjectsDB> subjectsDBS = projectsDBS.get(0).getSubjectsDBList();

            if (isDeleteAll) {
                //删除工程
                int deleteProject = DataBaseWork.DBDeleteById(ProjectsDB.class, projectsDBS.get(0).getId());
                KLog.e("测试代码", "deleteProject == " + deleteProject);

                if (subjectsDBS != null && subjectsDBS.size() > 0) {

                    //删除本地文件
                    FileIOUtils.deleteFile(Constants.SAVE_DIR_PROJECT_Document + projectId);

                    //更新每个题目数据
                    for (SubjectsDB subjectsDB : subjectsDBS) {
                        int deleteSubjectsDB = DataBaseWork.DBDeleteById(SubjectsDB.class, subjectsDB.getId());
                        KLog.e("测试代码", "deleteSubjectsDB == " + deleteSubjectsDB);
                    }
                }
            } else {
                if (subjectsDBS != null && subjectsDBS.size() > 0) {
                    //删除每个题目的图片
                    for (SubjectsDB subjectsDB : subjectsDBS) {
                        FileIOUtils.deleteFile(Constants.SAVE_DIR_PROJECT_Document + projectId + "/" + subjectsDB.getNumber() + "_" + subjectsDB.getHt_id() + "/" + "picture");
                        FileIOUtils.deleteFile(Constants.SAVE_DIR_PROJECT_Document + projectId + "/" + subjectsDB.getNumber() + "_" + subjectsDB.getHt_id() + "/" + "audio");
                    }
                }
            }
        }
    }

    /**
     * 跳转到详情页
     */
    private void JumpToProject(String projectId) {
        if (haslogin()) {
            //((SupportFragment)getParentFragment())
            /*((SupportFragment)getParentFragment()).*/
            ((SupportFragment) getParentFragment()).start(ProjectDetailFragment.newInstance(
                    projectId, "", "", 2));
           /* Intent intent = new Intent(getActivity(), ConstantsActivity.class);
            intent.putExtra("from", Constants.MyTabFragmentCode);
            intent.putExtra("templateId", "");
            intent.putExtra("dealerId", "");
            intent.putExtra("type", 2);
            intent.putExtra("projectId", projectId);
            intent.putExtra("to", Constants.ProjectDetailFragmentCode);
            startActivity(intent);*/
        } else {
            ToastUtils.showLong(getResources().getString(R.string.toast_10));
        }
    }

    /**
     * 假下载
     */
    private void getUnDownLoad(String projectId) {
        if (haslogin()) {
            Map map = new HashMap();
            map.put("projectId", projectId);
            map.put("sessionId", Constants.sessionId);
            p.getUnDownLoadProject(map);
        } else {
            ToastUtils.showLong(getResources().getString(R.string.toast_10));
        }

    }

    /**
     * 登录Dialog
     */
    private void loginDialog() {
        Login_DialogFragment login_dialogFragment = Login_DialogFragment.newInstance(Constants.MyTabFragmentCode);

        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                if (haslogin()) {
                    if (isNetworkAvailable()) {  //有网络
                        getDate();
                    } else { //无网络
                        p.getTypeDate(type);
                    }
                }
            }
        });
        login_dialogFragment.show(getFragmentManager(), TAG);
    }

    /**
     * 删除后台数据与数据库中不相同的项目
     */
    private void deleteByDb(final String pid) {
        List<ProjectsDB> ProjectDBList = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", pid);
        if (ProjectDBList != null && ProjectDBList.size() > 0) {
            //删除
            ProjectsDB projectsDB = new ProjectsDB();
            projectsDB.setIsDeletes(1);
            projectsDB.update(ProjectDBList.get(0).getId());
        }

    }

    /**
     * 获取差集
     */
    private void getDifferenceSet(List<String> ht_List) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);

        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
            if (projectsDBSList != null && projectsDBSList.size() > 0) {
                db_list.clear();
                for (int i = 0; i < projectsDBSList.size(); i++) {
                    db_list.add(projectsDBSList.get(i).getPid());
                }
            }
        }

        if (db_list != null && ht_List != null && ht_List.size() > 0) {
            if (db_list.removeAll(ht_List)) {
                if (db_list != null && db_list.size() > 0) {
                    for (int i = 0; i < db_list.size(); i++) {
                        //删除操作
                        deleteByDb(db_list.get(i));
                    }
                }
            }
        }
    }

}