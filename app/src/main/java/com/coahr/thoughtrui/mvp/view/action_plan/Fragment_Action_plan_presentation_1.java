package com.coahr.thoughtrui.mvp.view.action_plan;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.DBbean.TemplateDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.LoadCity.JsonBean;
import com.coahr.thoughtrui.Utils.LoadCity.JsonFileReader;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectQuotaLevel1;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectQuotaLevel2;
import com.coahr.thoughtrui.mvp.presenter.Fragment_action_plan_pre_1_P;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/12
 * 描述：行动计划提报1
 */
public class Fragment_Action_plan_presentation_1 extends BaseFragment<Fragment_action_plan_pre_1_c.Presenter> implements Fragment_action_plan_pre_1_c.View {
    @Inject
    Fragment_action_plan_pre_1_P p;
    @BindView(R.id.plan_1_tittle)
    MyTittleBar plan1Tittle;
    @BindView(R.id.plan_plan_time)
    TextView planPlanTime;
    @BindView(R.id.plan_planer)
    TextView planPlaner;
    @BindView(R.id.plan_province)
    TextView planProvince;
    @BindView(R.id.plan_dealer_name)
    TextView planDealerName;
    @BindView(R.id.plan_templet)
    TextView planTemplet;
    @BindView(R.id.plan_quota_1)
    TextView planQuota1;
    @BindView(R.id.plan_quota_2)
    TextView planQuota2;
    @BindView(R.id.plan_take_photo)
    TextView planTakePhoto;
    @BindView(R.id.plan_photo_recyclerView)
    RecyclerView planRecyclerView;
    @BindView(R.id.plan_1_next)
    TextView plan1Next;
    @BindView(R.id.plan_photo_view)
    LinearLayout planPhotoView;
    @BindView(R.id.plan_tv_6)
    TextView planTv6;
    @BindView(R.id.et_plan_status)
    TextView etPlanStatus;
    /**
     * 经销商名称
     */
    private ArrayList<String> project_name = new ArrayList<>();
    /**
     * 指标联动
     */
    private ArrayList<SubjectListBean.DataBean.QuestionListRoot> quotaList_1 = new ArrayList<>();
    private ArrayList<ArrayList<SubjectListBean.DataBean.QuestionListRoot.valueBean>> quotaList_2 = new ArrayList<>();
    /*
     省市区三级列表联动数据结构
      */
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String select_city;

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static final int MSG_LOAD_OSS = 0x0004;
    private static final int MSG_LOAD_PIC = 0x0005;
    private boolean isLoaded;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (!isLoaded) {
                        if (thread == null) {//如果已创建就不再重新创建子线程了
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getJson();
                                }
                            });
                            thread.start();
                        }
                    } else {
                        showCityPickerView();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    showCityPickerView();
                    break;
                case MSG_LOAD_FAILED:
                    isLoaded = false;
                    break;
                case MSG_LOAD_OSS:
                    p.getBeforePic(ossClient, projectId, levelId);
                    break;
            }
        }
    };
    /**
     * 图片
     */
    private GridLayoutManager gridLayoutManager;
    private PagerFragmentPhotoAdapter adapter;
    private ArrayList<String> Before_imageList_oss = new ArrayList<>(); //OssUrl
    private ArrayList<String> select_pic = new ArrayList<>();  //相册选择
    private ArrayList<String> resultList = new ArrayList<>();  //Osskey
    private ArrayList<String> before_up = new ArrayList<>();  //改善前上传图片
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    private ReportList.DataBean.AllListBean reportList;
    private OSSClient ossClient;
    private OSSAuthCredentialsProvider credentialProvider;
    private String projectId;  //项目Id
    private String projectIds;  //当前店铺的所有项目Id（可能不同模板）
    private String levelId;
    private String beforeDesc;
    private int type;  //判断是否为更新操作 1.首次 2.更新
    private String executor;  //监督人
    private String address; //省份
    private String name;   //模板
    private String dname;  //经销商名称
    private String quota1;
    private String quota2;
    private String targetDate;
    //某经销商下所有模板id的拼接字符串
    private String templateId;
    //某经销商下所有模板id的集合
    private List<String> templateIds;

    //未得满分的题目集合
    private List<SubjectsDB> noStandardSubjects = new ArrayList<>();
    //一级指标数据集合
    private List<SubjectQuotaLevel1> quotaList1 = new ArrayList<>();
    //二级指标数据集合
    List<SubjectQuotaLevel2> quotaList2 = new ArrayList<>();
    //被选中的一级指标
    private SubjectQuotaLevel1 selectedQuotaLevel1;

    @Override
    public Fragment_action_plan_pre_1_c.Presenter getPresenter() {
        return p;
    }

    public static Fragment_Action_plan_presentation_1 newInstance(ReportList.DataBean.AllListBean allListBean, int type) {
        Fragment_Action_plan_presentation_1 fragmentActionPlanPresentation1 = new Fragment_Action_plan_presentation_1();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ReportList", allListBean);
        bundle.putInt("type", type);
        fragmentActionPlanPresentation1.setArguments(bundle);
        return fragmentActionPlanPresentation1;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_action_plan_presentation_1;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        planTv6.setText(getResources().getString(R.string.plan_1_8));
        planPhotoView.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            reportList = (ReportList.DataBean.AllListBean) getArguments().getParcelable("ReportList");
            type = getArguments().getInt("type");
        }
        if (reportList != null) {
            targetDate = reportList.getTargetDate();
            executor = reportList.getExecutor();
            address = reportList.getAddress();
            select_city = address;
            projectId = reportList.getProjectId();
            levelId = reportList.getLevelId();
            KLog.e("测试代码", "levelId == " + levelId);
            beforeDesc = reportList.getBeforeDesc();

            name = reportList.getName();
            dname = reportList.getDname();
            quota1 = reportList.getQuota1();
            quota2 = reportList.getQuota2();


            planPlanTime.setText(targetDate);
            planPlaner.setText(executor);
            planProvince.setText(address);
            planDealerName.setText(dname);
            planTemplet.setText(name);
            planQuota1.setText(quota1);
            planQuota2.setText(quota2 != null ? quota2 : "");
            etPlanStatus.setText(beforeDesc == null ? "" : beforeDesc);
        } else {
            planPlanTime.setText(TimeUtils.getStingYMD(System.currentTimeMillis()));
            planPlaner.setText(Constants.user_name);
        }

        plan1Tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
        adapter = new PagerFragmentPhotoAdapter();
        gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
        planRecyclerView.setLayoutManager(gridLayoutManager);
        planRecyclerView.setAdapter(adapter);
        planRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 5), DensityUtils.dp2px(BaseApplication.mContext, 5)), getResources().getColor(R.color.colorWhite));
        adapter.setIsDel(true);
        adapter.setListener(new PagerFragmentPhotoListener() {
            @Override
            public void onClick(List<String> imagePathList, int position) {
                photoAlbumDialogFragment = PhotoAlbumDialogFragment.newInstance();
                photoAlbumDialogFragment.setImgList(adapter.getData());
                photoAlbumDialogFragment.setFirstSeePosition(position);
                FragmentManager fragmentManager = getFragmentManager();
                photoAlbumDialogFragment.show(fragmentManager, TAG);
            }

            @Override
            public void onLongClick(List<String> imagePathList, int position) {
            }

            @Override
            public void onDel(List<String> imagePathList, int position) {
                imagePathList.remove(position);
                adapter.setImageList(imagePathList);
                adapter.notifyItemRemoved(position);
            }
        });
    }

    /**
     * Evenbus,提交后，自动关闭
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void EvenBus(Boolean isFinish) {
        if (isFinish) {
            _mActivity.onBackPressed();
        }
    }

    @Override
    public void initData() {
        if (reportList != null) {
            planPlanTime.setText(reportList.getTargetDate());
            planPlaner.setText(reportList.getUname());
            planProvince.setText(reportList.getAddress());
            planDealerName.setText(reportList.getDname());
            planTemplet.setText(reportList.getName());
            planQuota1.setText(reportList.getQuota1());
            planQuota2.setText(reportList.getQuota2() != null ? reportList.getQuota2() : "");
            projectId = reportList.getProjectId();
            levelId = reportList.getLevelId();
            KLog.e("测试代码", "levelId == " + levelId);
            etPlanStatus.setText(beforeDesc == null ? "" : beforeDesc);
        }

        //判断令牌有无过期
        if (Constants.Expiration > System.currentTimeMillis()) {
            getSTS_OSS(Constants.AK, Constants.SK, Constants.STOKEN, Constants.ENDPOINT);
        } else {
            getAliyunOss();
        }

        if (type == 2) {
            planProvince.setEnabled(false);
            planDealerName.setEnabled(false);
            planTemplet.setEnabled(false);
            planQuota1.setEnabled(false);
            etPlanStatus.setEnabled(false);
        }
    }

    @OnClick({R.id.plan_province, R.id.plan_dealer_name, R.id.plan_templet, R.id.plan_quota_1, R.id.plan_quota_2, R.id.plan_take_photo, R.id.plan_1_next, R.id.et_plan_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_province:  //城市选择
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                break;
            case R.id.plan_dealer_name:
                p.getProjectName(Constants.sessionId);
                break;
            case R.id.plan_templet:
//                getTemplateList();'
                if (TextUtils.isEmpty(templateId)) {
                    ToastUtils.showShort("选择项目模块失败！"/*请重新开始检核该项目！*/);
                    return;
                }

                templateIds = Arrays.asList(templateId.split(","));

                //初始化模板数据
                initTemplateDataAndShow();
                break;
            case R.id.plan_quota_1:
                if (TextUtils.isEmpty(projectId)) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_37));
                    return;
                }
                if (TextUtils.isEmpty(planTemplet.getText().toString())) {
                    ToastUtils.showLong("请选择所属检核模板！");
                    return;
                }

                //获取后台指标数据
                getQuota();

                //获取本项目指标数据
                initLocalQuotaDataAndShow();
                break;
            case R.id.plan_quota_2:
                quotaList2.clear();
                quotaList2 = getNoStandardQuota2List();

                if (quotaList2.size() > 0) {
                    showLocalNoStandardQuota2();
                }
                break;
            case R.id.plan_take_photo:
                openMulti(10 - adapter.getData().size());
                break;
            case R.id.plan_1_next:
                before_up.addAll(adapter.getData());

                //指标Id
                if (TextUtils.isEmpty(levelId)) {
                    levelId = initLevelId();
                }
                KLog.e("测试代码", "levelId == " + levelId);

                if (select_city == null) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_7));
                } else if (TextUtils.isEmpty(projectIds) && TextUtils.isEmpty(projectId)) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_37));
                } else if (TextUtils.isEmpty(planTemplet.getText().toString())) {
                    ToastUtils.showLong("请选择所属检核模板");
                    return;
                } else if (TextUtils.isEmpty(levelId)) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_38));
                } /*else if (before_up.size() <= 0) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                } */ else if (TextUtils.isEmpty(etPlanStatus.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "请输入改善前状态描述", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Iterator<String> before_it = before_up.iterator();
                    while (before_it.hasNext()) {
                        String x = before_it.next();
                        if (x.startsWith("http")) {
                            before_it.remove();
                        }
                    }

//                    if (before_up.size() > 0) {
                    start(Fragment_Action_plan_presentation_2.newInstance(reportList, projectId, levelId, before_up, type, etPlanStatus.getText().toString().trim()));
//                    } else {
//                        ToastUtils.showLong(getResources().getString(R.string.toast_28));
//                    }
                }
                break;
            case R.id.et_plan_status:
                EvaluateInputDialogFragment desc_Input = EvaluateInputDialogFragment.newInstance(300);
                desc_Input.show(_mActivity.getSupportFragmentManager(), TAG);
                //未完成原因
                desc_Input.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
                    @Override
                    public void onInputSend(String input, AppCompatDialogFragment dialog) {
                        if (input != null && !input.equals("")) {
                            etPlanStatus.setText(input);
                            dialog.dismiss();
                        }
                    }
                });
                break;
        }
    }

    private String initLevelId() {
        String tempLevelId = "";
        if (subjectListBean != null) {
            List<SubjectListBean.DataBean.QuestionListRoot> questionList = subjectListBean.getData().getQuestionList();
            if (questionList != null && questionList.size() > 0) {
                for (int i = 0; i < questionList.size(); i++) {
                    SubjectListBean.DataBean.QuestionListRoot questionListRoot = questionList.get(i);
                    if (questionList != null && questionListRoot.getName().equals(planQuota1.getText().toString().trim())) {
                        tempLevelId = questionListRoot.getId();
                    }
                    List<SubjectListBean.DataBean.QuestionListRoot.valueBean> questionListItem = questionListRoot.getValue();
                    if (questionListItem != null && questionListItem.size() > 0) {
                        for (int j = 0; j < questionListItem.size(); j++) {
                            SubjectListBean.DataBean.QuestionListRoot.valueBean valueBean = questionListItem.get(j);
                            if (valueBean != null && valueBean.getName().equals(planQuota2.getText().toString().trim())) {
                                tempLevelId = valueBean.getId();
                            }
                        }
                    }
                }
            }
        }
        return tempLevelId;
    }

    /**
     * 获取本项目本地存储指标
     */
    private void initLocalQuotaDataAndShow() {
        //根据projectId获取题目指标
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", projectId);
        if (projectsDBS != null && projectsDBS.size() > 0) {
            //获取未达标的题目集合
            noStandardSubjects = getNoStandardSubjects(projectsDBS);

            quotaList1.clear();

            if (noStandardSubjects.size() > 0) {
                quotaList1 = getNoStandardQuota1List();
            } else {
                ToastUtils.showShort("请开始检核该项目");
                return;
            }

            KLog.e("测试代码", "quotaList1 == " + quotaList1.toString());
            KLog.e("测试代码", "quotaList2 == " + quotaList2.toString());
            if (quotaList1.size() > 0) {
                showLocalNoStandardQuota1();
            }
        }
    }

    /**
     * 获取二级指标数据集合
     */
    private List<SubjectQuotaLevel2> getNoStandardQuota2List() {
        //一级指标数据
        if (selectedQuotaLevel1 != null) {
            //3,获取二级指标数据
            for (int i = 0; i < noStandardSubjects.size(); i++) {
                SubjectsDB subjectsDB = noStandardSubjects.get(i);
                boolean isQuota2Added = false;

                //保存后的一级指标与每一题的指标名称比较
                if (selectedQuotaLevel1.getName().equals(subjectsDB.getQuota2())) {
                    //同一个一级指标，处理二级指标是否已添加
                    for (int k = 0; k < quotaList2.size(); k++) {
                        SubjectQuotaLevel2 subjectQuotaLevel2 = quotaList2.get(k);

                        //如果二级指标与该题目二级指标相同
//                                    if (subjectQuotaLevel2.getName().equals(subjectsDB.getQuota2())) {
                        if (subjectQuotaLevel2.getName().equals(subjectsDB.getQuota3())) {
                            isQuota2Added = true;
                            break;
                        }
                    }

                    //没有添加过,添加过不用再添加
                    if (isQuota2Added == false) {
                        SubjectQuotaLevel2 tempSubjectQuota = new SubjectQuotaLevel2();
                        tempSubjectQuota.setId("");
//                                    tempSubjectQuota.setName(subjectsDB.getQuota2());
                        tempSubjectQuota.setName(subjectsDB.getQuota3());
                        quotaList2.add(tempSubjectQuota);
                    }
                }
            }
        }

        return quotaList2;
    }

    /**
     * 获取未达标一级指标集合
     */
    private List<SubjectQuotaLevel1> getNoStandardQuota1List() {
        //2,获取一级指标数据
        for (int i = 0; i < noStandardSubjects.size(); i++) {
            //每一题
            SubjectsDB filterSubjectsDB = noStandardSubjects.get(i);
            boolean isQuota1Added = false;

            for (int j = 0; j < quotaList1.size(); j++) {
                SubjectQuotaLevel1 subjectQuota1 = quotaList1.get(j);
                //每一题的指标名称与保存后的指标比较
                if (subjectQuota1.getName().equals(filterSubjectsDB.getQuota2())) {
                    isQuota1Added = true;
                    break;
                }
            }

            //没有添加过,添加过不用再添加
            if (isQuota1Added == false) {
                SubjectQuotaLevel1 tempSubjectQuota = new SubjectQuotaLevel1();
                tempSubjectQuota.setId("");
//                            tempSubjectQuota.setName(filterSubjectsDB.getQuota1());
                tempSubjectQuota.setName(filterSubjectsDB.getQuota2());
                quotaList1.add(tempSubjectQuota);
            }
        }
        return quotaList1;
    }

    /**
     * 获取未达标的题目集合
     */
    private List<SubjectsDB> getNoStandardSubjects(List<ProjectsDB> projectsDBS) {
        ProjectsDB projectsDB = projectsDBS.get(0);

        //数据库所有题目
        List<SubjectsDB> subjectsDBList = projectsDB.getSubjectsDBList();
//        List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectByTogether_order(SubjectsDB.class, new String[]{"projectsdb_id=?", String.valueOf(projectsDBS.get(0).getId())}, "number");
//        List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=? ",projectsDB.getPid());

        noStandardSubjects.clear();

        //1,获取非满分的题目，筛选
        if (subjectsDBList != null && subjectsDBList.size() > 0) {
            for (int i = 0; i < subjectsDBList.size(); i++) {
                //每一题
                SubjectsDB subjectsDB = subjectsDBList.get(i);
                if (subjectsDB.getType() == 0) {  //选择题
                    String options = subjectsDB.getOptions();//标准
                    String[] split = options.split("&");
                    if (split != null && split.length > 0) {
                        if(subjectsDB.getAnswer() == null){
                            continue;
                        }
                        int score = Integer.parseInt(subjectsDB.getAnswer());
                        int standard = Integer.parseInt(split[1]);
                        if (score < standard) {
                            noStandardSubjects.add(subjectsDB);
                        }
                    }
                } else if (subjectsDB.getType() == 1) { //填空题
                    if (subjectsDB.getAnswer() == null){
                        continue;
                    }
                    int score = Integer.parseInt(subjectsDB.getAnswer());
                    int standard = Integer.parseInt(subjectsDB.getOptions());
                    if (score < standard) {
                        noStandardSubjects.add(subjectsDB);
                    }
                }
            }
        }

        KLog.e("测试代码", "filterSubjects == " + noStandardSubjects.toString());
        return noStandardSubjects;
    }

    /**
     * 展示本地未达标的二级指标
     */
    private void showLocalNoStandardQuota2() {
        OptionsPickerView pvOption_quota = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                planQuota2.setText(quotaList2.get(options1).getPickerViewText());
//                levelId = quotaList2.get(options1).getId();
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_28))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();

        if (quotaList2.size() > 0) {
            pvOption_quota.setPicker(quotaList2);
        }
        pvOption_quota.show();
    }

    /**
     * 展示本地未达标的一级指标
     */
    private void showLocalNoStandardQuota1() {

        OptionsPickerView pvOption_quota = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                selectedQuotaLevel1 = quotaList1.get(options1);
                planQuota1.setText(selectedQuotaLevel1.getPickerViewText());
//                levelId = selectedQuotaLevel1.getId();

                //一级指标重选后，重置二级指标
                planQuota2.setText("");
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_28))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();

        pvOption_quota.setPicker(quotaList1);
        pvOption_quota.show();
    }

    /**
     * 初始化模板数据和展示
     */
    private void initTemplateDataAndShow() {
        List<TemplateDB> templateDBS = new ArrayList<>();
        for (int i = 0; i < templateIds.size(); i++) {
            List<TemplateDB> templateDbs = DataBaseWork.DBSelectByTogether_Where(TemplateDB.class, "templateId=?", templateIds.get(i));
            if (templateDbs != null && templateDbs.size() > 0) {
                templateDBS.add(templateDbs.get(0));
            }
        }

        if (templateDBS.size() > 0) {
            List<Template_list.TemplateListBean> template_list = new ArrayList<>();
            for (int i = 0; i < templateDBS.size(); i++) {
                TemplateDB templateDB = templateDBS.get(i);

                Template_list.TemplateListBean templateListBean = new Template_list.TemplateListBean();
                templateListBean.setId(templateDB.getTemplateId());
                templateListBean.setModify_time(templateDB.getModify_time());
                templateListBean.setName(templateDB.getName());

                template_list.add(templateListBean);
            }

            showTemplateView(template_list);
        } else {
            KLog.e("提报中", "没有数据");
        }
    }

    @Override
    public void getProjectNameSuccess(List<ProjectsDB> projectsDBList) {
        List<ProjectsDB> selectCityProjectsDBList = new ArrayList<>();

        if (projectsDBList != null && projectsDBList.size() > 0) {
            for (ProjectsDB projectsDB : projectsDBList) {
                String city = projectsDB.getCity();
                if (city != null && city.equals(select_city)) {
                    //初始化经销商数据
                    initProjectDelearName(selectCityProjectsDBList, projectsDB);
                }
            }

            if (selectCityProjectsDBList.size() > 0) {
                //展示筛选后的项目列表
                showProjectName(selectCityProjectsDBList);
            } else {
                ToastUtils.showLong("未搜索到" + (select_city == null?"":select_city) + "经销商检核数据");
            }
        } else {
            ToastUtils.showLong("未搜索到" + (select_city == null?"":select_city) + "经销商检核数据");
        }
    }

    /**
     * 初始化经销商数据
     */
    private void initProjectDelearName(List<ProjectsDB> selectCityProjectsDBList, ProjectsDB projectsDB) {
        ProjectsDB tempProjectsDB = null;

        ListIterator<ProjectsDB> projectsDBListIterator = selectCityProjectsDBList.listIterator();
        while (projectsDBListIterator.hasNext()) {
            ProjectsDB next = projectsDBListIterator.next();
            if (next.getDname().equals(projectsDB.getDname())) {
                tempProjectsDB = next;
                break;
            }
        }

        if (tempProjectsDB == null) {
            //同经销商只添加一次
            selectCityProjectsDBList.add(projectsDB);
        } else {
            //之前没有该模板id
            if (!tempProjectsDB.getTemplateId().contains(projectsDB.getTemplateId()) /*&& projectsDB.getCompleteStatus() != 3*/) {
                tempProjectsDB.setTemplateId(tempProjectsDB.getTemplateId().concat("," + projectsDB.getTemplateId()));
            }
            //之前没有该项目id
            if (!tempProjectsDB.getPid().contains(projectsDB.getPid()) /*&& projectsDB.getCompleteStatus() != 3*/) {
                //不是已完成
                tempProjectsDB.setPid(tempProjectsDB.getPid().concat("," + projectsDB.getPid()));
            }
        }
    }

    @Override
    public void getProjectNameFailure(String fail) {
        ToastUtils.showLong(fail);
    }

    private SubjectListBean subjectListBean;

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (subjectListBean != null
                && subjectListBean.getData().getQuestionList() != null
                && subjectListBean.getData().getQuestionList().size() > 0) {
            this.subjectListBean = subjectListBean;
//            showQuota(subjectListBean.getData().getQuestionList());
        }
    }

    @Override
    public void getSubjectListFailure(String failure, int code) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getBeforePicSuccess(List<OSSObjectSummary> ossObjectSummaries) {
        if (ossObjectSummaries != null && ossObjectSummaries.size() > 0) {
            resultList.clear();
            Collections.sort(ossObjectSummaries, new Comparator<OSSObjectSummary>() {
                @Override
                public int compare(OSSObjectSummary ossObjectSummary, OSSObjectSummary t1) {
                    int i = ossObjectSummary.getLastModified().compareTo(t1.getLastModified());
                    if (i > 0) {
                        return -1;
                    } else if (i < 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            if (ossObjectSummaries.size() >= 10) {
                for (int i = 0; i < 10; i++) {
                    if (ossObjectSummaries.get(i).getSize() > 0) {
                        resultList.add(ossObjectSummaries.get(i).getKey());
                    }
                }
            } else {
                for (int i = 0; i < ossObjectSummaries.size(); i++) {
                    if (ossObjectSummaries.get(i).getSize() > 0) {
                        resultList.add(ossObjectSummaries.get(i).getKey());
                    }
                }
            }
            for (int i = 0; i < resultList.size(); i++) {
                KLog.d("图片排序", resultList.get(i));
            }
            p.getBeforePicUrl(ossClient, resultList);
        }
    }

    @Override
    public void getBeforePicFailure(String failure) {

    }

    @Override
    public void getBeforePicUrlSuccess(List<String> picUrlList) {
        Before_imageList_oss.clear();
        Before_imageList_oss.addAll(picUrlList);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adapter.setNewData(picUrlList);
                adapter.setImageList(picUrlList);
            }
        });

    }

    @Override
    public void getBeforePicUrlFailure(String failure) {

    }

    @Override
    public void getProjectTemplatesSuccess(Template_list template_list) {
        if (template_list != null && template_list.getTemplate_list() != null) {
            showTemplateView(template_list.getTemplate_list());
        }

    }

    @Override
    public void getProjectTemplateFailure(String fail) {

    }

    @Override
    public void getOssSuccess(AliyunOss aliyunOss) {
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.AK_KEY, aliyunOss.getAccessKeyId());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SK_KEY, aliyunOss.getAccessKeySecret());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.STOKEN_KEY, aliyunOss.getSecurityToken());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.BUCKET_KEY, aliyunOss.getBucket());
        PreferenceUtils.setPrefLong(BaseApplication.mContext, Constants.Expiration_KEY, aliyunOss.getExpiration());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.ENDPOINT_KEY, "http://" + aliyunOss.getRegion() + ".aliyuncs.com");
        Constants.AK = aliyunOss.getAccessKeyId();
        Constants.SK = aliyunOss.getAccessKeySecret();
        Constants.STOKEN = aliyunOss.getSecurityToken();
        Constants.Expiration = aliyunOss.getExpiration();
        Constants.BUCKET = aliyunOss.getBucket();
        Constants.ENDPOINT = "http://" + aliyunOss.getRegion() + ".aliyuncs.com";

        getSTS_OSS(aliyunOss.getAccessKeyId(), aliyunOss.getAccessKeySecret(), aliyunOss.getSecurityToken(), "http://" + aliyunOss.getRegion() + ".aliyuncs.com");

    }

    @Override
    public void getOssFailure(int statusCode) {

    }

    @Override
    public void putBeforeUploadCallBack(int TotalSize, int successSize, int failureSize) {

    }

    //====================================================经销商名称==========================================
    private void showProjectName(List<ProjectsDB> projectsDBList) {
        project_name.clear();
        for (int i = 0; i < projectsDBList.size(); i++) {
            project_name.add(projectsDBList.get(i).getDname());
        }
        OptionsPickerView pvOption_project = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //获取相同经销商不同模板下的所有id
                projectIds = projectsDBList.get(options1).getPid();
//                projectId = projectsDBList.get(options1).getPid();
//                KLog.e("测试代码", "projectId == " + projectId);
                KLog.e("测试代码", "projectIds == " + projectIds);
                planDealerName.setText(project_name.get(options1));
                //经销商重选后，重置模板
                planTemplet.setText("");
                //经销商重选后，重置指标
                planQuota1.setText("");
                planQuota2.setText("");
                levelId = null;

                //获取选中的模板数据
                templateId = projectsDBList.get(options1).getTemplateId();
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_3))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        pvOption_project.setPicker(project_name);
        pvOption_project.show();
    }

    //==================================================指标选择器============================================

    /**
     * 获取指标
     */
    private void getQuota() {
        Map map = new HashMap();
        map.put("projectId", projectId);
        map.put("sessionid", Constants.sessionId);
        p.getSubjectList(map);
    }

    /*private void showQuota(List<SubjectListBean.DataBean.QuestionListRoot> questionListRootList) {
        quotaList_1.clear();
        quotaList_2.clear();
        quotaList_1.addAll(questionListRootList);
        for (int i = 0; i < questionListRootList.size(); i++) {
            if (questionListRootList.get(i).getValue() != null && questionListRootList.get(i).getValue().size() > 0) {
                ArrayList<SubjectListBean.DataBean.QuestionListRoot.valueBean> quesListBean_1s = new ArrayList<>();
                quesListBean_1s.addAll(questionListRootList.get(i).getValue());
                quotaList_2.add(quesListBean_1s);
            }

        }

        OptionsPickerView pvOption_quota = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                planQuota1.setText(quotaList_1.get(options1).getPickerViewText());
                levelId = quotaList_1.get(options1).getId();
                if (quotaList_2 != null && quotaList_2.size() > 0) {
                    planQuota2.setText(quotaList_2.get(options1).get(options2).getPickerViewText());
                    levelId = quotaList_2.get(options1).get(options2).getId();
                }
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_28))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        if (quotaList_2 != null && quotaList_2.size() > 0) {
            pvOption_quota.setPicker(quotaList_1, quotaList_2);

        } else {
            pvOption_quota.setPicker(quotaList_1);
        }
        pvOption_quota.show();
    }*/

    //=============================================模板选择器==================================================

    /**
     * 联网获取模板
     */
    private void getTemplateList() {
        Map map = new HashMap();
        map.put("userId", Constants.sessionId);
        p.getProjectTemplates(map);
    }

    /**
     * 弹出模板选择器
     */
    private void showTemplateView(List<Template_list.TemplateListBean> templateListBeanList) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                planTemplet.setText(templateListBeanList.get(options1).getPickerViewText());

                String[] split = null;
                if (!TextUtils.isEmpty(projectIds)) {
                    split = projectIds.split(",");
                }

                KLog.e("测试代码", "templateIds == " + templateIds);
                KLog.e("测试代码", "templateListBeanList.get(options1).getId() == " + templateListBeanList.get(options1).getId());
                if (split != null && split.length > 0) {
//                该模板下的项目id
                    projectId = split[templateIds.indexOf(templateListBeanList.get(options1).getId())];
                }
                KLog.e("测试代码", "projectId == " + projectId);
            }
        })
                .setTitleText(getResources().getString(R.string.checking_template))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(templateListBeanList);//一级选择器
        pvOptions.show();
    }


//==================================================城市列表选择器=============================================

    /**
     * 城市列表选择器
     */
    private void showCityPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                select_city = options2Items.get(options1).get(options2);

                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2);
                planProvince.setText(tx);

                //城市重选后，其他项全部重选
                projectId = "";
                projectIds = "";
                planDealerName.setText("");
                planTemplet.setText("");
                //经销商重选后，重置指标
                planQuota1.setText("");
                planQuota2.setText("");
                levelId = null;
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_2))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    /**
     * 将城市json字符串解析为javabean
     *
     * @param result
     * @return
     */
    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 将各个转载指定数组
     */
    public void getJson() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
        String JsonData = JsonFileReader.getJson(BaseApplication.mContext, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 拍照
     */
    private void openMulti(int count) {
        setPath();
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(getActivity())
                .image()
                .multiple();
        rxGalleryFinal.maxSize(count)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        select_pic.clear();
                        List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();
                        if (mediaBeanList != null && mediaBeanList.size() > 0) {
                            for (int i = 0; i < mediaBeanList.size(); i++) {
                                select_pic.add(mediaBeanList.get(i).getOriginalPath());
                            }
                            List<String> data = adapter.getData();
                            data.addAll(select_pic);
                            adapter.setImageList(data);
                            adapter.setNewData(data);
                        }

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        Toast.makeText(BaseApplication.mContext, "OVER", Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();
    }

    /**
     * 设置 照片路径 和 裁剪路径
     */
    private void setPath() {
        RxGalleryFinalApi.setImgSaveRxDir(new File(Constants.SAVE_DIR_TAKE_PHOTO));
        RxGalleryFinalApi.setImgSaveRxCropDir(new File(Constants.SAVE_DIR_ZIP_PHOTO));//裁剪会自动生成路径；也可以手动设置裁剪的路径；
    }

    /**
     * OSS对象实例
     */
    private void getSTS_OSS(String ak, String sk, String stoken, String endpoint) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ak, sk, stoken);
                ossClient = new OSSClient(_mActivity, endpoint, credentialProvider, conf);
                mHandler.sendEmptyMessage(MSG_LOAD_OSS);
                // p.getBeforePic(ossClient, projectId, levelId);
            }
        }).start();
    }

    /**
     * 获取阿里云Oss
     */
    private void getAliyunOss() {
        Map map = new HashMap();
        p.getOss(map);
    }
}