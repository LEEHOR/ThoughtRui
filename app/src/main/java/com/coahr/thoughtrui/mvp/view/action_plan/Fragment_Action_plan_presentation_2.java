package com.coahr.thoughtrui.mvp.view.action_plan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.model.OSSObjectSummary;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_2_c;
import com.coahr.thoughtrui.mvp.model.ApiContact;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.SubmitReport;
import com.coahr.thoughtrui.mvp.presenter.Fragment_action_plan_pre_2_P;
import com.coahr.thoughtrui.mvp.view.action_plan.Adapter.item_plan_2_history_reason;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
 * 创建日期：2019/4/17
 * 描述：
 */
public class Fragment_Action_plan_presentation_2 extends BaseFragment<Fragment_action_plan_pre_2_c.Presenter> implements Fragment_action_plan_pre_2_c.View, OnDateSetListener {
    @Inject
    Fragment_action_plan_pre_2_P p;
    @BindView(R.id.plan_2_tittle)
    MyTittleBar plan2Tittle;
    @BindView(R.id.plan_2_tv_cause_diagnosis)
    TextView plan2TvCauseDiagnosis;   //原因诊断
    @BindView(R.id.plan_2_tv_corrective_actions)  //改善措施
            TextView plan2TvCorrectiveActions;
    @BindView(R.id.plan_2_tv_time)
    TextView plan2TvTime;     //计划完成日期
    @BindView(R.id.plan_2_tv_time_count)
    TextView plan2TvTimeCount;  //计划持续时间
    @BindView(R.id.plan_2_tv_executor)
    TextView plan2TvExecutor;      //执行人
    @BindView(R.id.plan_2_sp_finished)
    Spinner plan2SpFinished;
    @BindView(R.id.plan_take_photo)
    TextView planTakePhoto;
    @BindView(R.id.plan_photo_recyclerView)
    RecyclerView planPhotoRecyclerView;
    @BindView(R.id.plan_2_recyclerView)
    RecyclerView plan2RecyclerView;
    @BindView(R.id.plan_photo_view)
    LinearLayout planPhotoView;
    @BindView(R.id.plan_recycler_view)
    LinearLayout planRecyclerView;
    @BindView(R.id.plan_2_tv_reason)
    TextView plan2TvReason;
    @BindView(R.id.plan_2_tv_submit)
    TextView plan2TvSubmit;
    private ReportList.DataBean.AllListBean report;
    private String projectId;
    private String levelId;
    private ArrayList<String> beforeImage;
    private ArrayList<String> resultList = new ArrayList<>();  //oss回调的key
    private ArrayList<String> afterImage_0ss = new ArrayList<>(); //改善后Oss上的图片
    private ArrayList<String> select_after = new ArrayList<>();   //相册选择的图片
    private OSSAuthCredentialsProvider credentialProvider;
    private OSSClient ossClient;
    private static final int MSG_LOAD_OSS = 0x0001;
    private static final int MSG_LOAD_PIC = 0x0002;
    private static final int UPDATE_PROGRESS = 0x0003;
    private static final int UPDATE_SUCCESS = 0x0004;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOAD_PIC:
                    // p.getAfterPic(ossClient,projectId,levelId);
                    break;
                case UPDATE_PROGRESS:
                    progressBar.setMax(msg.arg2);
                    progressBar.setProgress(msg.arg1);
                    tv_tittle.setText(msg.obj != null ? msg.obj.toString() : "");
                    break;
                case UPDATE_SUCCESS:
                    tv_tittle.setText(msg.obj != null ? msg.obj.toString() : "");
                    break;
            }
        }
    };
    private GridLayoutManager photomanager;
    private PagerFragmentPhotoAdapter adapter_photo;
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    private com.coahr.thoughtrui.mvp.view.action_plan.Adapter.item_plan_2_history_reason item_plan_2_history_reason;
    private LinearLayoutManager reason_manager;
    private String select_sp;   //选择spinner
    private int count;   //持续天数
    private int completeStatus,status;  //是否完成
    private String targetDate;  //计划完成时间
    private String diagnosis;  //原因诊断
    private String measures; //具体改善措施
    private String executor;
    private View inflate;
    private TextView tv_message_tittle;
    private ProgressBar progressBar;
    private TextView tv_tittle;
    private TimePickerDialog datePickerDialog;
    private int days;
    private List<String> after_data;

    @Override
    public Fragment_action_plan_pre_2_c.Presenter getPresenter() {
        return p;
    }

    public static Fragment_Action_plan_presentation_2 newInstance(ReportList.DataBean.AllListBean allListBean, String projectId, String levelId, ArrayList<String> beforeImageList) {
        Fragment_Action_plan_presentation_2 fragmentActionPlanPresentation2 = new Fragment_Action_plan_presentation_2();
        Bundle bundle = new Bundle();
        bundle.putParcelable("report", allListBean);
        bundle.putString("projectId", projectId);
        bundle.putString("levelId", levelId);
        bundle.putStringArrayList("beforeImage", beforeImageList);
        fragmentActionPlanPresentation2.setArguments(bundle);
        return fragmentActionPlanPresentation2;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_action_plan_presentation_2;
    }

    @Override
    public void initView() {
        getSTS_OSS();
        if (getArguments() != null) {
            report = (ReportList.DataBean.AllListBean) getArguments().getParcelable("report");
            projectId = getArguments().getString("projectId");
            levelId = getArguments().getString("levelId");
            beforeImage = getArguments().getStringArrayList("beforeImage");
            for (int i = 0; i < beforeImage.size(); i++) {
                KLog.d("图片", beforeImage.get(i));
            }
            if (report != null) {
                projectId = report.getProjectId();
                count = report.getCount();
                completeStatus = report.getCompleteStatus();
                targetDate = report.getTargetDate();
                diagnosis = report.getDiagnosis();
                measures = report.getMeasures();
                executor = report.getExecutor();

                plan2TvCauseDiagnosis.setText(diagnosis);
                plan2TvCorrectiveActions.setText(measures);
                plan2TvTime.setText(targetDate);
                plan2TvTimeCount.setText(count);
                plan2TvExecutor.setText(executor);

            } else {
                plan2TvExecutor.setText(Constants.user_name);
            }
        }

        if (completeStatus==0 || completeStatus==1){
            status=1;
        } else if (completeStatus==-1){
            status=-1;
        }
        if (status == 1) {
            planPhotoView.setVisibility(View.VISIBLE);
            planRecyclerView.setVisibility(View.GONE);
            plan2SpFinished.setSelection(0, true);
        } else {
            planPhotoView.setVisibility(View.GONE);
            planRecyclerView.setVisibility(View.VISIBLE);
            plan2SpFinished.setSelection(1, true);
        }
        inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_progress, null);
        tv_message_tittle = inflate.findViewById(R.id.tv_message_tittle);
        tv_tittle = inflate.findViewById(R.id.tv_progress_info);
        progressBar = inflate.findViewById(R.id.progress_bar);
    }

    @Override
    public void initData() {
        //==========================图片===============================================
        adapter_photo = new PagerFragmentPhotoAdapter();
        photomanager = new GridLayoutManager(BaseApplication.mContext, 3);
        planPhotoRecyclerView.setLayoutManager(photomanager);
        planPhotoRecyclerView.setAdapter(adapter_photo);
        planPhotoRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 5), DensityUtils.dp2px(BaseApplication.mContext, 5)), getResources().getColor(R.color.colorWhite));
        adapter_photo.setIsDel(true);
        adapter_photo.setListener(new PagerFragmentPhotoListener() {
            @Override
            public void onClick(List<String> imagePathList, int position) {
                photoAlbumDialogFragment = PhotoAlbumDialogFragment.newInstance();
                photoAlbumDialogFragment.setImgList(adapter_photo.getData());
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
                adapter_photo.notifyItemRemoved(position);
                adapter_photo.setImageList(imagePathList);
            }
        });
        //===================================历史记录=====================================
        item_plan_2_history_reason = new item_plan_2_history_reason();
        reason_manager = new LinearLayoutManager(BaseApplication.mContext);
        plan2RecyclerView.setAdapter(item_plan_2_history_reason);
        plan2RecyclerView.setLayoutManager(reason_manager);
        plan2RecyclerView.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 5)), getResources().getColor(R.color.colorWhite));
        if (report != null) {
            item_plan_2_history_reason.setNewData(report.getReasonList());
        }
        //=======================================Spinner是否完成======================================
        plan2SpFinished.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] stringArray = getResources().getStringArray(R.array.select_plan);
                select_sp = stringArray[i];
                if (i == 0) {
                    status = 1;
                    planPhotoView.setVisibility(View.VISIBLE);
                    planRecyclerView.setVisibility(View.GONE);
                } else {
                    status = -1;
                    planPhotoView.setVisibility(View.GONE);
                    planRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initTimePickerDialog();
    }

    @Override
    public void getAfterPicSuccess(List<OSSObjectSummary> ossObjectSummaries) {

        if (ossObjectSummaries != null && ossObjectSummaries.size() > 0) {
            resultList.clear();
            Collections.sort(ossObjectSummaries, new Comparator<OSSObjectSummary>() {
                @Override
                public int compare(OSSObjectSummary ossObjectSummary, OSSObjectSummary t1) {
                    int i = ossObjectSummary.getLastModified().compareTo(t1.getLastModified());
                    if (i > 0) {
                        return 1;
                    } else if (i < 0) {
                        return -1;
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

        }
        p.getAfterPicUrl(ossClient, resultList);
    }

    @Override
    public void getAfterPicFailure(String failure) {

    }

    @Override
    public void getAfterPicUrlSuccess(List<String> picUrlList) {
        afterImage_0ss.clear();
        afterImage_0ss.addAll(picUrlList);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adapter_photo.setNewData(picUrlList);
                adapter_photo.setImageList(picUrlList);
            }
        });
    }

    @Override
    public void putUploadImagesCallBack(int TotalSize, int successSize, int failureSize) {
        if (TotalSize == (successSize + failureSize)) {
            if (TotalSize == successSize) {
                Message mes = mHandler.obtainMessage(UPDATE_PROGRESS, getResources().getString(R.string.toast_39));
                mes.sendToTarget();
                sendReport();
            } else {
                Message mes = mHandler.obtainMessage(UPDATE_PROGRESS, getResources().getString(R.string.toast_24));
                mes.sendToTarget();
            }
        }
    }

    @Override
    public void SubmitReportSuccess(SubmitReport submitReport) {
        ToastUtils.showLong(submitReport.getMsg());
    }

    @Override
    public void SubmitReportFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void putUploadProgress(long currentSize, long totalSize, String FileName) {
        Message mes = mHandler.obtainMessage(UPDATE_PROGRESS, FileName);
        mes.arg1 = (int) currentSize;
        mes.arg2 = (int) totalSize;
        mes.sendToTarget();
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
                    p.getAfterPic(ossClient, projectId, levelId);
                }
            }).start();
        } else {
            p.getAfterPic(ossClient, projectId, levelId);
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
                        List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();
                        if (mediaBeanList != null && mediaBeanList.size() > 0) {
                            select_after.clear();
                            for (int i = 0; i < mediaBeanList.size(); i++) {
                                select_after.add(mediaBeanList.get(i).getOriginalPath());
                            }
                            List<String> data = adapter_photo.getData();
                            data.addAll(select_after);
                            adapter_photo.setNewData(data);
                            adapter_photo.setImageList(data);
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

    @OnClick({R.id.plan_2_tv_cause_diagnosis, R.id.plan_2_tv_corrective_actions
            , R.id.plan_2_tv_reason, R.id.plan_take_photo, R.id.plan_2_tv_submit
            , R.id.plan_2_tv_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_2_tv_cause_diagnosis:
                EvaluateInputDialogFragment cause_diagnosis_Input = EvaluateInputDialogFragment.newInstance(100);
                cause_diagnosis_Input.show(_mActivity.getSupportFragmentManager(), TAG);
                //原因诊断
                cause_diagnosis_Input.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
                    @Override
                    public void onInputSend(String input, AppCompatDialogFragment dialog) {
                        if (input != null && !input.equals("")) {
                            plan2TvCauseDiagnosis.setText(input);
                            dialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.plan_2_tv_corrective_actions:
                EvaluateInputDialogFragment corrective_actions_Input = EvaluateInputDialogFragment.newInstance(300);
                corrective_actions_Input.show(_mActivity.getSupportFragmentManager(), TAG);
                //具体改善
                corrective_actions_Input.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
                    @Override
                    public void onInputSend(String input, AppCompatDialogFragment dialog) {
                        if (input != null && !input.equals("")) {
                            plan2TvCorrectiveActions.setText(input);
                            dialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.plan_2_tv_reason:
                EvaluateInputDialogFragment reason_Input = EvaluateInputDialogFragment.newInstance(300);
                reason_Input.show(_mActivity.getSupportFragmentManager(), TAG);
                //未完成原因
                reason_Input.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
                    @Override
                    public void onInputSend(String input, AppCompatDialogFragment dialog) {
                        if (input != null && !input.equals("")) {
                            plan2TvReason.setText(input);
                            dialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.plan_take_photo:
                openMulti(10 - adapter_photo.getData().size());
                break;
            case R.id.plan_2_tv_submit:
                after_data = adapter_photo.getData();
                if (TextUtils.isEmpty(plan2TvCauseDiagnosis.getText())) {
                    ToastUtils.showLong(getResources().getString(R.string.plan_2_13));
                } else if (TextUtils.isEmpty(plan2TvCorrectiveActions.getText())) {
                    ToastUtils.showLong(getResources().getString(R.string.plan_2_14));
                } else if (TextUtils.isEmpty(plan2TvTime.getText())) {
                    ToastUtils.showLong(getResources().getString(R.string.plan_2_15));
                } else if (after_data.size() <=0) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                } else {
                    showProgressDialog();
                    Iterator<String> before_it = beforeImage.iterator();
                    while (before_it.hasNext()) {
                        String x = before_it.next();
                        if (x.startsWith("http")) {
                            before_it.remove();
                        }
                    }
                    Iterator<String> after_it = after_data.iterator();
                    while (after_it.hasNext()) {
                        String x = after_it.next();
                        if (x.startsWith("http")) {
                            after_it.remove();
                        }
                    }
                    p.putImagesUpload(ossClient, beforeImage, after_data, projectId, levelId);
                }

                break;
            case R.id.plan_2_tv_time:
                datePickerDialog.show(getChildFragmentManager(), TAG);
                break;
        }
    }

    /**
     * 上传进度回调
     */
    private void showProgressDialog() {

        new MaterialDialog.Builder(_mActivity)
                .customView(inflate, false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                    }
                }).build().show();
    }


    /**
     * 提交报表
     */
    private void sendReport() {
        StringBuffer before_buffer = new StringBuffer();
        StringBuffer after_buffer = new StringBuffer();
        for (int i = 0; i < beforeImage.size(); i++) {
            KLog.d("beforeImage_1", beforeImage.get(i));
            if (beforeImage.size() == 1) {
                before_buffer.append(beforeImage.get(i));
            } else {
                if (i == beforeImage.size() - 1) {
                    before_buffer.append(beforeImage.get(i));
                } else {
                    before_buffer.append(beforeImage.get(i) + ";");
                }
            }
        }

        for (int i = 0; i < after_data.size(); i++) {
            KLog.d("afterImage_1", after_data.get(i));
            if (after_data.size() == 1) {
                after_buffer.append(after_data.get(i));
            } else {
                if (i == after_data.size() - 1) {
                    after_buffer.append(after_data.get(i));
                } else {
                    after_buffer.append(after_data.get(i) + ";");
                }
            }
        }
        Map map = new HashMap();
        map.put("projectId", projectId);
        map.put("levelId", levelId);
        map.put("diagnosis", plan2TvCauseDiagnosis.getText().toString().trim());
        map.put("measures", plan2TvCorrectiveActions.getText().toString().trim());
        map.put("targetDate", plan2TvTime.getText().toString().trim());
        map.put("completeStatus", status);
        map.put("incompleteReason", plan2TvReason.getText().toString().trim());
        map.put("beforePicture", before_buffer.toString().trim());
        map.put("afterPicture", after_buffer.toString().trim());
        map.put("executor", plan2TvExecutor.getText().toString().trim());
        map.put("sessionId", Constants.sessionId);
        map.put("duration", days);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                p.SubmitReport(map);
            }
        });

    }


    /**
     * 日期选择
     */
    private void initTimePickerDialog() {
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        datePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(this::onDateSet)
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.resume))
                .setTitleStringId(getString(R.string.attendance_appointment_date))
                .setYearText(getString(R.string.phrases_11))
                .setMonthText(getString(R.string.phrases_12))
                .setDayText(getString(R.string.phrases_13))
                .setHourText(getString(R.string.phrases_14))
                .setMinuteText(getString(R.string.phrases_14))
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.material_blue_600))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.black))
                .setWheelItemTextSize(12)
                .build();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String stingYMD = TimeUtils.getStingYMD(millseconds);
        plan2TvTime.setText(stingYMD);
        days = differentDaysByMillisecond(millseconds, System.currentTimeMillis());
        plan2TvTimeCount.setText(String.format(getResources().getString(R.string.plan_2_11), days));
    }


    /**
     * 两个日期的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(long date1, long date2) {
        int days = (int) ((date1 - date2) / (1000 * 3600 * 24));
        return days + 1;
    }

}
