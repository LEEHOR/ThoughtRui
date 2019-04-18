package com.coahr.thoughtrui.mvp.view.action_plan;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.LoadCity.JsonBean;
import com.coahr.thoughtrui.Utils.LoadCity.JsonFileReader;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.model.ApiContact;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.presenter.Fragment_action_plan_pre_1_P;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
    private ArrayList<String> select_pic=new ArrayList<>();  //相册选择
    private ArrayList<String> resultList = new ArrayList<>();  //Osskey
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    private ReportList.DataBean.AllListBean reportList;
    private OSSClient ossClient;
    private OSSAuthCredentialsProvider credentialProvider;
    private String projectId;  //项目Id
    private String levelId;
    private int type;  //判断是否为更新操作 1.首次 2.更新
    private String executor;  //监督人
    private String address; //省份
    private String name;   //模板
    private String dname;  //经销商名称
    private String quota1;
    private String quota2;
    private String targetDate;
    private List<String> before_date;

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
        planPhotoView.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            reportList = (ReportList.DataBean.AllListBean) getArguments().getParcelable("ReportList");
            type = getArguments().getInt("type");
        }
        if (reportList != null) {
            targetDate = reportList.getTargetDate();
            executor = reportList.getExecutor();
            address = reportList.getAddress();
            select_city = FileIOUtils.getE(address, "省");
            projectId = reportList.getProjectId();
            levelId = reportList.getLevelId();

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
        }
        getSTS_OSS();
    }

    @OnClick({R.id.plan_province, R.id.plan_dealer_name, R.id.plan_templet, R.id.plan_quota_1, R.id.plan_quota_2, R.id.plan_take_photo, R.id.plan_1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_province:  //城市选择
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                break;
            case R.id.plan_dealer_name:
                p.getProjectName(Constants.sessionId);
                break;
            case R.id.plan_templet:
                break;
            case R.id.plan_quota_1:
                if (projectId != null) {
                    getQuota();
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_37));
                }
                break;
            case R.id.plan_quota_2:
                break;
            case R.id.plan_take_photo:
                openMulti(10 - adapter.getData().size());
                break;
            case R.id.plan_1_next:
                before_date = adapter.getData();
                if (select_city == null) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_7));
                } else if (projectId == null) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_37));
                } else if (levelId == null) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_38));
                } else if (before_date.size()<=0) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                } else {
                   
                    start(Fragment_Action_plan_presentation_2.newInstance(reportList, projectId, levelId, (ArrayList<String>) adapter.getData()));
                }
                break;
        }
    }

    @Override
    public void getProjectNameSuccess(List<ProjectsDB> projectsDBList) {
        showProjectName(projectsDBList);
    }

    @Override
    public void getProjectNameFailure(String fail) {
        ToastUtils.showLong(fail);
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (subjectListBean != null
                && subjectListBean.getData().getQuestionList() != null
                && subjectListBean.getData().getQuestionList().size() > 0) {
            showQuota(subjectListBean.getData().getQuestionList());
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
            for (int i = 0; i <resultList.size() ; i++) {
                KLog.d("图片排序",resultList.get(i));
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
    public void putBeforeUploadCallBack(int TotalSize, int successSize, int failureSize) {

    }

    //====================================================经销商名称==========================================
    private void showProjectName(List<ProjectsDB> projectsDBList) {
        project_name.clear();
        for (int i = 0; i < projectsDBList.size(); i++) {
            project_name.add(projectsDBList.get(i).getPname());
        }
        OptionsPickerView pvOption_project = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                projectId = projectsDBList.get(options1).getPid();
                planDealerName.setText(project_name.get(options1));
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

    private void showQuota(List<SubjectListBean.DataBean.QuestionListRoot> questionListRootList) {
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
         *
         * */
        String JsonData = JsonFileReader.getJson(BaseApplication.mContext, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
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
    private void getSTS_OSS() {
        if (credentialProvider == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    credentialProvider = new OSSAuthCredentialsProvider(ApiContact.STSSERVER);
                    ClientConfiguration conf = new ClientConfiguration();
                    conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
                    conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
                    conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                    conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                    ossClient = new OSSClient(_mActivity, ApiContact.endpoint, credentialProvider, conf);
                    p.getBeforePic(ossClient, projectId, levelId);
                }
            }).start();
        } else {
            p.getBeforePic(ossClient, projectId, levelId);

        }

    }
}
