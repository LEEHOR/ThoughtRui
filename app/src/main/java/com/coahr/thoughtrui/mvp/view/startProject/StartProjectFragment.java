package com.coahr.thoughtrui.mvp.view.startProject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.StartProjectFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.presenter.StartProjectFragmentP;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.StartProjectAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 16:46
 */
public class StartProjectFragment extends BaseFragment<StartProjectFragmentC.Presenter> implements StartProjectFragmentC.View, View.OnClickListener {
    @Inject
    StartProjectFragmentP p;
    @BindView(R.id.project_viewPage)
    ViewPager project_viewPage;
    @BindView(R.id.left_lin)
    LinearLayout left_lin;
    @BindView(R.id.right_lin)
    LinearLayout right_lin;
/*    @BindView(R.id.iv_last)
    SelectImageView iv_last;
    @BindView(R.id.iv_next)
    SelectImageView iv_next;

    @BindView(R.id.tv_last)
    SelectTextView tv_last;

    @BindView(R.id.tv_next)
    SelectTextView tv_next;*/
    private PagerController pagerController;
    private ArrayList<BaseChildFragment> fragmentArrayList;
    private StartProjectAdapter startProjectAdapter;

    public static StartProjectFragment newInstance() {
        StartProjectFragment startProjectFragment = new StartProjectFragment();
        return startProjectFragment;
    }

    @Override
    public StartProjectFragmentC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_startproject;
    }

    @Override
    public void initView() {
        left_lin.setOnClickListener(this);
        right_lin.setOnClickListener(this);
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

    /**
     *
     * @param size
     *          题目数量
     * @param dbProjectId
     *          项目Id
     */
    @Override
    public void getOfflineSuccess(int size,String dbProjectId,String ht_ProjectId) {
        startProjectAdapter = new StartProjectAdapter(getChildFragmentManager(),size,dbProjectId,ht_ProjectId);
        project_viewPage.setAdapter(startProjectAdapter);
        project_viewPage.setCurrentItem(0);
    }

    @Override
    public void getOfflineFailure(int failure) {
        ToastUtils.showLong("没有本地题目");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_lin:
                setImage_textChange(R.id.left_lin);
                break;
            case R.id.right_lin:
                setImage_textChange(R.id.right_lin);
                break;
        }
    }

    //切换图片和文字颜色
    private void setImage_textChange(int id) {
      /*  if (id == R.id.left_lin) {
            iv_last.toggle(false);
            tv_last.toggle(false);

            iv_next.toggle(true);
            tv_next.toggle(true);
        } else if (id == R.id.right_lin) {
            iv_last.toggle(true);
            tv_last.toggle(true);

            iv_next.toggle(false);
            tv_next.toggle(false);
        } else {
            iv_last.toggle(true);
            tv_last.toggle(true);

            iv_next.toggle(false);
            tv_next.toggle(false);
        }*/
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        p.getMainData(map);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        super.showError(e);
    }

    private boolean getNetWork() {
        boolean networkAvailable = NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext);
        return networkAvailable;
    }

    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText("退出")
                .positiveText("重试")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                        _mActivity.onBackPressed();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
            }
        }).build().show();
    }
}
