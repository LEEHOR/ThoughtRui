package com.coahr.thoughtrui.mvp.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Message;
import android.view.TouchDelegate;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.constract.StartProjectActivity_C;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_SubjectList_id;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.StartProjectActivity_P;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.StartProjectAdapter;
import com.coahr.thoughtrui.widgets.AltDialog.Login_DialogFragment;
import com.coahr.thoughtrui.widgets.CustomScrollViewPager;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDialogFragment;
import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/17
 * on 8:28
 * 开始访问页面
 */
public class StartProjectActivity extends BaseActivity<StartProjectActivity_C.Presenter> implements StartProjectActivity_C.View, View.OnClickListener {
    @Inject
    StartProjectActivity_P p;
    @BindView(R.id.project_viewPage)
    CustomScrollViewPager project_viewPage;
    @BindView(R.id.p_mytitle)
    MyTittleBar p_mytitle;
    private StartProjectAdapter startProjectAdapter;
    private List<String> htId_List=new ArrayList<>();
    private int subject_size; //题目个数
        //定位是否成功
    private boolean isLocationSuccess;
    //是否首次定位
    private boolean isFirstLocation;
    private double latitude;
    private double longitude;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private BaiduLocationHelper baiduLocationHelper_s;
    @Override
    public StartProjectActivity_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_startproject;
    }


    @Override
    public void initView() {
        project_viewPage.setScrollable(false);
        p_mytitle.setPadding(p_mytitle.getPaddingLeft(),
                ScreenUtils.getStatusBarHeight(BaseApplication.mContext),
                p_mytitle.getPaddingRight(), p_mytitle.getPaddingBottom());
        p_mytitle.getRightText().setVisibility(View.VISIBLE);
        p_mytitle.getRightText().setText("题目列表");
        p_mytitle.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartProjectActivity.this, ConstantsActivity.class);
                intent.putExtra("to", Constants.fragment_topics);
                startActivity(intent);
            }
        });
        p_mytitle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("提示", "退出答题");
            }
        });
        p_mytitle.getTvTittle().setText("第" + 1 + "题");
    }

    @Override
    public void initData() {
        if (getNetWork()) {
            if (haslogin()){
                getData();
               // p.getLocation(2);
            } else {
                ToastUtils.showLong("请登录后再试");
            }

        } else {
            ToastUtils.showLong("请开启网络以定位");
            //p.getOfflineDate(Constants.DbProjectId, Constants.ht_ProjectId);
        }
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
        ToastUtils.showLong(e.toString());
    }

    private boolean getNetWork() {
        boolean networkAvailable = NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext);
        return networkAvailable;
    }

    @Override
    public void getLocationSuccess(BDLocation location, BaiduLocationHelper baiduLocationHelper) {
        this.isLocationSuccess = true;
        this.baiduLocationHelper_s=baiduLocationHelper;
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        if (!isFirstLocation) {
              //  mHandler.post(run_location);
                getData();
            }

        isFirstLocation=true;

    }

    @Override
    public void getLocationFailure(int failure, BaiduLocationHelper baiduLocationHelper) {
        this.isLocationSuccess=false;
    }

    @Override
    public void getMainDataSuccess(QuestionBean questionBean) {
        final List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "id=?", Constants.DbProjectId);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            final List<QuestionBean.DataBean.QuestionListBean> questionList = questionBean.getData().getQuestionList();
            if (questionList != null && questionList.size() > 0) {
                for (int i = 0; i < questionList.size(); i++) {
                    List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectBy_Where(SubjectsDB.class, new String[]{"ht_id"}, "ht_id=?", questionList.get(i).getId());
                    if (subjectsDBS != null && subjectsDBS.size() > 0) {
                        for (int j = 0; j < subjectsDBS.size(); j++) {
                            SubjectsDB subjectsDB = new SubjectsDB();
                            subjectsDB.setType(questionList.get(i).getType());
                            subjectsDB.setOptions(questionList.get(i).getOptions());
                            subjectsDB.setNumber(questionList.get(i).getNumber());
                            subjectsDB.update(subjectsDBS.get(0).getId());
                        }
                    } else {
                        SubjectsDB subjectsDB = new SubjectsDB();
                        subjectsDB.setTitle(questionList.get(i).getTitle());
                        subjectsDB.setHt_id(questionList.get(i).getId());
                        subjectsDB.setType(questionList.get(i).getType());
                        subjectsDB.setOptions(questionList.get(i).getOptions());
                        subjectsDB.setDescription(questionList.get(i).getDescribes());
                        subjectsDB.setPhotoStatus(questionList.get(i).getPhotoStatus());
                        subjectsDB.setDescribeStatus(questionList.get(i).getDescribeStatus());
                        subjectsDB.setCensor(questionList.get(i).getCensor());
                        subjectsDB.setIsComplete(0);
                        subjectsDB.setType(questionList.get(i).getType());
                        subjectsDB.setDh("0");
                        subjectsDB.setNumber(questionList.get(i).getNumber());
                        subjectsDB.setsUploadStatus(0);
                        if (questionList.get(i).getQuota1() != null) {
                            subjectsDB.setQuota1(questionList.get(i).getQuota1());
                            if (questionList.get(i).getQuota2() != null) {
                                subjectsDB.setQuota2(questionList.get(i).getQuota2());
                                if (questionList.get(i).getQuota3() != null) {
                                    subjectsDB.setQuota3(questionList.get(i).getQuota3());
                                } else {
                                    subjectsDB.setQuota3(null);
                                }
                            } else {
                                subjectsDB.setQuota2(null);
                                subjectsDB.setQuota3(null);
                            }

                        } else {
                            subjectsDB.setQuota1(null);
                            subjectsDB.setQuota2(null);
                            subjectsDB.setQuota3(null);
                        }
                        subjectsDB.setProjectsDB(projectsDBS.get(0));
                        subjectsDB.save();
                    }
                }

            }
            p.getOfflineDate(Constants.DbProjectId, Constants.ht_ProjectId);
        } else {
            ToastUtils.showLong("当前项目不存在");
        }
    }

    @Override
    public void getMainDataFailure(String failure,int code) {
        ToastUtils.showLong(failure);
        if (code!=-1){
            p.getOfflineDate(Constants.DbProjectId, Constants.ht_ProjectId);
        } else {
            if (baiduLocationHelper_s != null) {
                baiduLocationHelper_s.stopLocation();
            }
         //   mHandler.removeCallbacks(run_location);
            loginDialog();
        }

    }

    @Override
    public void getOfflineSuccess(int size, String dbProjectId, String ht_projectId, List<String> ht_list) {

        this.subject_size = size;
        htId_List.clear();
        this.htId_List=ht_list;
        KLog.d("项目Id2",ht_projectId,dbProjectId);
        startProjectAdapter = new StartProjectAdapter(getSupportFragmentManager(), size, dbProjectId, ht_projectId, Constants.name_Project, ht_list);

        project_viewPage.setAdapter(startProjectAdapter);
        project_viewPage.setCurrentItem(0);

    }

    @Override
    public void getOfflineFailure(int failure) {
        ToastUtils.showLong("当前题目不存在");
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        map.put("sessionid",Constants.sessionId);
        map.put("token",Constants.devicestoken);
        p.getMainData(map);
    }

    @Override
    public void onBackPressedSupport() {
        showDialog("提示", "退出答题");
    }

    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("确认")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                if (baiduLocationHelper_s != null) {
                    baiduLocationHelper_s.stopLocation();
                }
                mHandler.removeCallbacks(run_location);
                finish();
            }
        }).build().show();
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mHandler.removeCallbacks(run_location);
        super.onDestroy();
    }

    /**
     * 翻页获取返回询问数据
     *
     * @param isCompleteBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event_pageChange(isCompleteBean isCompleteBean) {
        int isposition = isCompleteBean.getPosition();
        int isupOrDown = isCompleteBean.getUpOrDown();
        boolean complete = isCompleteBean.isComplete();

        if (complete) {
            if (isupOrDown == 1) {  //上翻页
                KLog.d("上翻页" + isposition);
                if (isposition > 0) {
                    project_viewPage.setCurrentItem(project_viewPage.getCurrentItem() - 1);
                    p_mytitle.getTvTittle().setText("第" + (isposition) + "题");
                }

            }
            if (isupOrDown == 2) {
                KLog.d("下翻页" + isposition);
                project_viewPage.setCurrentItem(project_viewPage.getCurrentItem() + 1);
                p_mytitle.getTvTittle().setText("第" + (isposition) + "题");

            }
        } else {
            ToastUtils.showLong("当前题目未完成");
        }
    }

    /**
     * 跳转
     * @param subjectList_id
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event_SubjectList_jump(EvenBus_SubjectList_id subjectList_id) {
        if (subjectList_id != null) {
            List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "ht_id=?", subjectList_id.getSub_id());
            if (subjectsDBS != null && subjectsDBS.size()>0) {
                p_mytitle.getTvTittle().setText("第" + (subjectsDBS.get(0).getNumber()) + "题");
                        project_viewPage.setCurrentItem(subjectsDBS.get(0).getNumber()-1);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    /**
     * 设置系统时间
     */
    private Runnable run_location = new Runnable() {
        @Override
        public void run() {
                if (isLocationSuccess){
                    KLog.d("发送数据");
                }
         //   mHandler.postDelayed(run_location, 1000*3);
        }
    };

    /**
     * 登录Dialog
     */
    private void loginDialog(){
        Login_DialogFragment login_dialogFragment=Login_DialogFragment.newInstance(Constants.MyTabFragmentCode);

        login_dialogFragment.setLoginListener(new Login_DialogFragment.loginListener() {
            @Override
            public void loginSuccess(AppCompatDialogFragment dialogFragment) {
                dialogFragment.dismiss();
                if (haslogin()) {
                    if (getNetWork()) {  //有网络
                        isFirstLocation=false;
                       p.getLocation(2);
                    } else { //无网络
                       ToastUtils.showLong("请连接网络后再试");
                    }
                } else {
                    ToastUtils.showLong("请重新登录");
                }
            }
        });
        login_dialogFragment.show(getSupportFragmentManager(),TAG);
    }
}
