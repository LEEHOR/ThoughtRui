package com.coahr.thoughtrui.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.constract.StartProjectActivity_C;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.StartProjectActivity_P;
import com.coahr.thoughtrui.mvp.view.startProject.PagerController;
import com.coahr.thoughtrui.mvp.view.startProject.StartProjectFragment;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.StartProjectAdapter;
import com.coahr.thoughtrui.widgets.CustomScrollViewPager;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/17
 * on 8:28
 * 开始访问页面
 */
public class StartProjectActivity extends BaseActivity<StartProjectActivity_C.Presenter> implements StartProjectActivity_C.View {
    @Inject
    StartProjectActivity_P p;
    @BindView(R.id.project_viewPage)
    CustomScrollViewPager project_viewPage;
    @BindView(R.id.p_mytitle)
    MyTittleBar p_mytitle;
    @BindView(R.id.recorder_model)
    View recorder_model;
    /*    @BindView(R.id.iv_last)
        SelectImageView iv_last;
        @BindView(R.id.iv_next)
        SelectImageView iv_next;

        @BindView(R.id.tv_last)
        SelectTextView tv_last;

        @BindView(R.id.tv_next)
        SelectTextView tv_next;*/
    private PagerController pagerController;
    private StartProjectAdapter startProjectAdapter;
    private int subject_size; //题目个数
    private View recorder_time;
    private View tv_start_recorder;
    private View tv_stop_recorder;

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
        recorder_model.setVisibility(View.GONE);
        recorder_time = recorder_model.findViewById(R.id.tv_recorderTime);
        tv_start_recorder = recorder_model.findViewById(R.id.tv_start_recorder);
        tv_stop_recorder = recorder_model.findViewById(R.id.tv_stop_recorder);
        project_viewPage.setScrollable(false);
        p_mytitle.setPadding(p_mytitle.getPaddingLeft(),
                ScreenUtils.getStatusBarHeight(BaseApplication.mContext),
                p_mytitle.getPaddingRight(), p_mytitle.getPaddingBottom());
        p_mytitle.getRightText().setVisibility(View.VISIBLE);
        p_mytitle.getRightText().setText("题目列表");
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
            getData();
        } else {
            p.getOfflineDate(Constants.DbProjectId,Constants.ht_ProjectId);
        }

    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
    }

    private boolean getNetWork() {
        boolean networkAvailable = NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext);
        return networkAvailable;
    }
    @Override
    public void getMainDataSuccess(QuestionBean questionBean) {
        final List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "id=?", Constants.DbProjectId);
        final List<QuestionBean.DataBean.QuestionListBean> questionList = questionBean.getData().getQuestionList();
        if (questionList != null && questionList.size() > 0) {
            for (int i = 0; i < questionList.size(); i++) {
                List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectBy_Where(SubjectsDB.class, new String[]{"ht_id"}, "ht_id=?", questionList.get(i).getId());
                if (subjectsDBS !=null && subjectsDBS.size()>0){
                    for (int j = 0; j <subjectsDBS.size() ; j++) {
                    }
                } else {
                    SubjectsDB subjectsDB = new SubjectsDB();
                    subjectsDB.setTitle(questionList.get(i).getTitle());
                    subjectsDB.setHt_id(questionList.get(i).getId());
                    subjectsDB.setType(questionList.get(i).getType());
                    subjectsDB.setOptions(questionList.get(i).getOptions());
                    subjectsDB.setDescription(questionList.get(i).getDescription());
                    subjectsDB.setPhotoStatus(questionList.get(i).getPhotoStatus());
                    subjectsDB.setRecordStatus(questionList.get(i).getRecordStatus());
                    subjectsDB.setDescribeStatus(questionList.get(i).getDescribeStatus());
                    subjectsDB.setCensor(questionList.get(i).getCensor());
                    subjectsDB.setIsComplete(0);
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

          /*fragmentArrayList = pagerController.getFragmentArrayList();
          startProjectAdapter.setFragmentArrayList(fragmentArrayList);
          startProjectAdapter.notifyDataSetChanged();*/
        p.getOfflineDate(Constants.DbProjectId,Constants.ht_ProjectId);
    }

    @Override
    public void getMainDataFailure(String failure) {
        ToastUtils.showLong( failure);
        p.getOfflineDate(Constants.DbProjectId,Constants.ht_ProjectId);
    }

    @Override
    public void getOfflineSuccess(int size, String dbProjectId, String ht_projectId) {
        this.subject_size = size;
        startProjectAdapter = new StartProjectAdapter(getSupportFragmentManager(),size,dbProjectId,ht_projectId);
        project_viewPage.setAdapter(startProjectAdapter);
        project_viewPage.setCurrentItem(0);
    }

    @Override
    public void getOfflineFailure(int failure) {
        ToastUtils.showLong("没有本地题目");
    }
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        p.getMainData(map);
    }
    @Override
    public void onBackPressedSupport() {
        showDialog("提示","退出答题");
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
                finish();
            }
        }).build().show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取返回询问数据
     *
     * @param isCompleteBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(isCompleteBean isCompleteBean) {
        int isposition = isCompleteBean.getPosition();
        int isupOrDown = isCompleteBean.getUpOrDown();
        boolean complete = isCompleteBean.isComplete();

        if (complete){
            if (isupOrDown == 1){  //上翻页
                KLog.d("上翻页"+isposition);
                project_viewPage.setCurrentItem(isposition-=1,true);
                if (isposition==0){
                    p_mytitle.getTvTittle().setText("第"+(1)+"题");
                } else {
                    p_mytitle.getTvTittle().setText("第"+(isposition)+"题");
                }

            }
            if (isupOrDown == 2){
                KLog.d("下翻页"+isposition);
                project_viewPage.setCurrentItem(isposition+=1);
                if (isposition==1){
                    p_mytitle.getTvTittle().setText("第"+(2)+"题");
                } else {
                    p_mytitle.getTvTittle().setText("第"+(isposition+1)+"题");
                }

            }
        } else {
            ToastUtils.showLong("当前题目未完成");
        }
    }
}
