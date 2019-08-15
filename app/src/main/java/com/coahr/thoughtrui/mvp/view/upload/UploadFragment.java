package com.coahr.thoughtrui.mvp.view.upload;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import com.coahr.thoughtrui.mvp.presenter.UploadP;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.upload.adapter.UpLoadAdapter;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:12
 * 上传页面
 */
public class UploadFragment extends BaseFragment<UploadC.Presenter> implements UploadC.View, View.OnClickListener {
    @Inject
    UploadP p;
    @BindView(R.id.up_swipe)
    SwipeRefreshLayout up_swipe;
    @BindView(R.id.re_top)
    RelativeLayout re_top;
    @BindView(R.id.tv_data_count)
    TextView tv_data_count;
    @BindView(R.id.tv_Batch_Management)
    TextView tv_Batch_Management;  //批量管理
    @BindView(R.id.tv_all_upload)
    TextView tv_all_upload;  //全部上传
    @BindView(R.id.up_recycler)
    RecyclerView up_recycler;  //列表
    @BindView(R.id.card_bottom)
    CardView card_bottom; //底部批量管理栏
    @BindView(R.id.ck_bottom)
    CheckBox ck_bottom; //底部选择
    @BindView(R.id.tv_Batch_UpLoad)
    TextView tv_Batch_UpLoad; //批量上传
    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.fr_top)
    FrameLayout frTop;
    //所有的项目列表（全部上传）
    private List<ProjectsDB> allProjectList;
    //所有项目个数
    private int all_project_size;
    //批量操作项目列表(批量上传)
    private List<ProjectsDB> ck_listProjectDb;
    //批量操作选中的项目个数
    private int ck_Project_size;
    //点击上传
    private List<ProjectsDB> click_project = new ArrayList<>();
    //图片上传

    //上传方式
    private int type;
    private UpLoadAdapter upLoadAdapter;
    private LinearLayoutManager manager;
    private boolean isLoading;
    private View inflate;
    private TextView tv_progress_info;
    private ProgressBar progressBar, pro_1;
    private OSSClient ossClient;
    private final int GETSUBJECTLIST = 1;
    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETSUBJECTLIST:
                    getSubjectListByOne();
                    break;
            }
        }
    };

    /**
     * 回调参数
     */
    private String uPAudioPath;
    private String textMassage;
    //上传文件的数组
    private int subject_position;
    private TextView tv_message_tittle;

    private ArrayList<String> pic = new ArrayList<>();
    private OSSAuthCredentialsProvider credentialProvider;
    private OSSFederationToken federationToken;

    @Override
    public UploadC.Presenter getPresenter() {
        return p;
    }

    public static UploadFragment newInstance() {
        return new UploadFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_upload;
    }

    @Override
    public void initView() {
        // getOSS();
        ivSearchBack.setVisibility(View.INVISIBLE);
        inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_progress, null);
        tv_message_tittle = inflate.findViewById(R.id.tv_message_tittle);
        tv_progress_info = inflate.findViewById(R.id.tv_progress_info);
        pro_1 = inflate.findViewById(R.id.pro_1);
        progressBar = inflate.findViewById(R.id.progress_bar);
        tv_Batch_Management.setOnClickListener(this);
        tv_all_upload.setOnClickListener(this);
        tv_Batch_UpLoad.setOnClickListener(this);
        ck_bottom.setOnClickListener(this);
        upLoadAdapter = new UpLoadAdapter();
        manager = new LinearLayoutManager(BaseApplication.mContext);
        up_recycler.setLayoutManager(manager);
        up_recycler.setAdapter(upLoadAdapter);
        up_recycler.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 5)));
        for (int i = 0; i < up_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                up_recycler.removeItemDecorationAt(i);
            }
        }

        up_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    if (haslogin()) {
                        p.getProjectList(Constants.sessionId);
                    }
                } else {
                    up_swipe.setRefreshing(false);
                }
            }
        });

       // NetWorkDialog("","",1);
    }

    @Override
    public void initData() {
        if (haslogin()) {
            p.getProjectList(Constants.sessionId);
        }
        upLoadAdapter.setSelectChangeListener(new UpLoadAdapter.onSelectChangeListener() {
            @Override
            public void onChange() {
                //选中的项目
                ck_listProjectDb = upLoadAdapter.getSelectedProject();
                ck_Project_size = ck_listProjectDb.size();
                if (upLoadAdapter.isAllSelected()) {
                    ck_bottom.setChecked(true);
                } else {
                    ck_bottom.setChecked(false);
                }
            }

            @Override
            public void OnClickItem(ProjectsDB projectsDB) {

                click_project.clear();
                click_project.add(projectsDB);
                type = 3;
                if (Constants.isNetWorkConnect) {
                    if (Constants.NetWorkType != null && Constants.NetWorkType.equals("WIFI")) {
                        NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_5), 1);
                    } else if (Constants.NetWorkType != null && Constants.NetWorkType.equals("MOBILE")) {
                        NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_6), 2);
                    }
                } else {
                    NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_7), 3);
                }
            }
        });
    }

    @Override
    public void showError(Throwable t) {
        KLog.d("网络请求错误", t.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void getProjectListSuccess(List<ProjectsDB> projectsDB) {
        up_recycler.setVisibility(View.VISIBLE);
        tv_all_upload.setEnabled(true);
        tv_Batch_Management.setEnabled(true);
        this.allProjectList = projectsDB;
        this.all_project_size = projectsDB.size();
        upLoadAdapter.setNewData(projectsDB);
        isLoading = false;
        up_swipe.setRefreshing(false);
    }

    @Override
    public void getProjectListFailure(String failure) {
        ToastUtils.showLong(failure);
        up_recycler.setVisibility(View.GONE);
        tv_all_upload.setEnabled(false);
        tv_Batch_Management.setEnabled(false);
        isLoading = false;
        up_swipe.setRefreshing(false);
    }

    //======获取上传题目
    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position) {
        showProgressDialog();
        //获取上传文件列表
        p.UpLoadFileList(subjectsDBList, projectsDBS, project_position, 0);
    }

    @Override
    public void getSubjectListFailure(String failure, List<ProjectsDB> projectsDBS, int project_position) {
        ToastUtils.showLong(failure);
        //开始查询下一个项目下的题目
        if (project_position < projectsDBS.size() - 1) {
            p.getSubjectList(projectsDBS, project_position += 1);
        } else {

        }
    }

    //========获取上传文件列表集合回调
    @Override
    public void getUpLoadFileListSuccess(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        //开始上传

        p.startUpLoad(ossClient, list, subjectsDBList, projectsDBS, project_position, subject_position);
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getOssSuccess(AliyunOss aliyunOss) {
        //  OSSFederationToken ossFederationToken = new OSSFederationToken(aliyunOss.getAccessKeyId(), aliyunOss.getAccessKeySecret(), aliyunOss.getSecurityToken(), aliyunOss.getExpiration());

        PreferenceUtils.setPrefString(_mActivity, Constants.AK_KEY, aliyunOss.getAccessKeyId());
        PreferenceUtils.setPrefString(_mActivity, Constants.SK_KEY, aliyunOss.getAccessKeySecret());
        PreferenceUtils.setPrefString(_mActivity, Constants.STOKEN_KEY, aliyunOss.getSecurityToken());
        PreferenceUtils.setPrefString(_mActivity, Constants.BUCKET_KEY, aliyunOss.getBucket());
        PreferenceUtils.setPrefLong(_mActivity, Constants.Expiration_KEY, aliyunOss.getExpiration());
        PreferenceUtils.setPrefString(_mActivity, Constants.ENDPOINT_KEY, "http://" + aliyunOss.getRegion() + ".aliyuncs.com");
        Constants.AK = aliyunOss.getAccessKeyId();
        Constants.SK = aliyunOss.getAccessKeySecret();
        Constants.STOKEN = aliyunOss.getSecurityToken();
        Constants.Expiration = aliyunOss.getExpiration();
        Constants.BUCKET = aliyunOss.getBucket();
        Constants.ENDPOINT = "http://" + aliyunOss.getRegion() + ".aliyuncs.com";


        getOSS(aliyunOss.getAccessKeyId(), aliyunOss.getAccessKeySecret(), aliyunOss.getSecurityToken(), "http://" + aliyunOss.getRegion() + ".aliyuncs.com");
       /* ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(aliyunOss.getAccessKeyId(), aliyunOss.getAccessKeySecret(), aliyunOss.getSecurityToken());
        ossClient = new OSSClient(_mActivity, "http://" + aliyunOss.getRegion()+".aliyuncs.com", credentialProvider, conf);
        getSubjectListByOne();*/
    }

    @Override
    public void getOssFailure(int statusCode) {
        ToastUtils.showLong("临时安全令牌失败,请重新上传");
    }

    //============进度更新
    @Override
    public void StartUiProgressSuccess(int currentSize, int totalSize, String info) {

    }

    //=================OSS上传回调
    @Override
    public void UploadCallBack(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, List<String> picList, int project_position, int subject_position, int uploadSuccessSize, int uploadFailSize, int totalSize) {

        if (totalSize == (uploadSuccessSize + uploadFailSize)) {
            if (totalSize == uploadSuccessSize) {  //上传成功
                //回调服务器
                uPAudioPath = null;
                textMassage = null;
                pic.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).endsWith(".wav") || list.get(i).endsWith(".arm") || list.get(i).endsWith(".mp3")) {
                        uPAudioPath = list.get(i);
                    } else if (list.get(i).endsWith(".txt")) {
                        textMassage = SaveOrGetAnswers.readFromFile(list.get(i));
                    } else {
                        pic.add(list.get(i));
                    }
                }
                //回调到后台服务器
                callbackForServer(projectsDBS, subjectsDBList, pic, textMassage, uPAudioPath, project_position, subject_position);
            } else {         //上传失败
                p.UpDataDb(subjectsDBList, projectsDBS, project_position, subject_position, false);
            }
        }
    }

    @Override
    public void Pic_Compulsory(List<String> list, List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        textMassage = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).endsWith("txt")) {
                    textMassage = SaveOrGetAnswers.readFromFile(list.get(i));
                }
            }
            callbackForServer(projectsDBS, subjectsDBList, null, textMassage, null, project_position, subject_position);
        } else {
            p.UpDataDb(subjectsDBList, projectsDBS, project_position, subject_position, false);
        }


    }

    @Override
    public void CallBackServerSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, UpLoadCallBack upLoadCallBack) {
        // Message mes = mHandler.obtainMessage(UPDATE_PROGRESS, String.format(getResources().getString(R.string.upload_fragment_6),subject_position+1,subjectsDBList.size()));
        // mes.sendToTarget();
        if (upLoadCallBack.getMsg().equals("FORBID")) {
            if (project_position < projectsDBS.size() - 1) {
                p.getSubjectList(projectsDBS, project_position += 1);
            } else {
                // tv_progress_info.setText(getResources().getString(R.string.toast_39));
                // pro_1.setVisibility(View.INVISIBLE);
                // ToastUtils.showLong(getResources().getString(R.string.toast_39));
            }
            tv_progress_info.setText(getResources().getString(R.string.upload_fragment_7));
            p.UpDataDb(subjectsDBList, projectsDBS, project_position, subject_position, false);
        } else {
            tv_progress_info.setText(String.format(getResources().getString(R.string.upload_fragment_6), subject_position + 1, subjectsDBList.size()));
            p.UpDataDb(subjectsDBList, projectsDBS, project_position, subject_position, true);
        }
    }

    @Override
    public void CallBackServerFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position, UpLoadCallBack upLoadCallBack) {
        p.UpDataDb(subjectsDBList, projectsDBS, project_position, subject_position, false);
    }

    @Override
    public void UpDataDbSuccess(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        //如果当前项目下的题目数据上传完毕，则开始传下一个项目
        if (subject_position < subjectsDBList.size() - 1) { //项目下还有题目没传，开始下一题
            p.UpLoadFileList(subjectsDBList, projectsDBS, project_position, subject_position += 1);
        } else {   //上一个项目传完了，开始下一个项目
            //1.查询下一个项目
            if (project_position < projectsDBS.size() - 1) {
                p.getSubjectList(projectsDBS, project_position += 1);
            } else {
                tv_progress_info.setText(getResources().getString(R.string.toast_39));
                pro_1.setVisibility(View.INVISIBLE);
                ToastUtils.showLong(getResources().getString(R.string.toast_39));
                p.getProjectList(Constants.sessionId);
            }
        }
    }

    @Override
    public void UpDataDbFailure(List<SubjectsDB> subjectsDBList, List<ProjectsDB> projectsDBS, int project_position, int subject_position) {
        //如果当前项目下的题目数据上传完毕，则开始传下一个项目
        if (subject_position < subjectsDBList.size() - 1) { //项目下还有题目没传，开始下一题
            p.UpLoadFileList(subjectsDBList, projectsDBS, project_position, subject_position += 1);
        } else {   //上一个项目传完了，开始下一个项目
            //1.查询下一个项目
            if (project_position < projectsDBS.size() - 1) {
                p.getSubjectList(projectsDBS, project_position += 1);
            } else {
                tv_progress_info.setText(getResources().getString(R.string.toast_39));
                pro_1.setVisibility(View.INVISIBLE);
                ToastUtils.showLong(getResources().getString(R.string.toast_39));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Batch_Management:
                if (tv_Batch_Management.getTag() == null || tv_Batch_Management.getTag().equals("1")) {
                    card_bottom.setVisibility(View.VISIBLE);
                    tv_all_upload.setVisibility(View.INVISIBLE);
                    tv_Batch_Management.setText(getResources().getString(R.string.upload_fragment_1));
                    upLoadAdapter.setCheckViewVisible(true);
                    tv_Batch_Management.setTag("2");
                } else {
                    card_bottom.setVisibility(View.GONE);
                    tv_all_upload.setVisibility(View.VISIBLE);
                    upLoadAdapter.setCheckViewVisible(false);
                    tv_Batch_Management.setText(getResources().getString(R.string.upload_fragment_2));
                    tv_Batch_Management.setTag("1");
                }
                break;
            case R.id.tv_all_upload:
                //getOSS();
                type = 1;
                if (allProjectList != null && allProjectList.size() > 0) {
                    if (Constants.isNetWorkConnect) {
                        if (Constants.NetWorkType != null && Constants.NetWorkType.equals("WIFI")) {
                            NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_5), 1);
                        } else if (Constants.NetWorkType != null && Constants.NetWorkType.equals("MOBILE")) {
                            NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_6), 2);
                        }
                    } else {
                        NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_7), 3);
                    }
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_42));
                }


                break;
            case R.id.tv_Batch_UpLoad:
                // getOSS();
                type = 2;
                if (ck_listProjectDb != null && ck_listProjectDb.size() > 0) {
                    if (Constants.isNetWorkConnect) {
                        if (Constants.NetWorkType != null && Constants.NetWorkType.equals("WIFI")) {
                            NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_5), 1);
                        } else if (Constants.NetWorkType != null && Constants.NetWorkType.equals("MOBILE")) {
                            NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_6), 2);
                        }
                    } else {
                        NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_7), 3);
                    }
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_40));
                }
                break;
            case R.id.ck_bottom:
                if (ck_bottom.isChecked()) {
                    upLoadAdapter.checkAll();
                } else {
                    upLoadAdapter.unCheckAll();
                }
                break;
        }

    }

    /**
     * 上传进度回调
     */
    private void showProgressDialog() {
        pro_1.setVisibility(View.VISIBLE);
        new MaterialDialog.Builder(_mActivity)
                .customView(inflate, false)
                .canceledOnTouchOutside(false)
                .cancelable(false)
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
     * 网络类型提示
     *
     * @param title
     * @param Content
     * @param types
     */
    private void NetWorkDialog(String title, String Content, final int types) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //有网络
                        if (types == 1 || types == 2) {
                            //getSubjectListByOne();

                            //判断令牌有无过期
                            if (Constants.Expiration > System.currentTimeMillis()) {
                                getOSS(Constants.AK, Constants.SK, Constants.STOKEN, Constants.ENDPOINT);
                            } else {
                                getAliyunOss();
                            }
                        }
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * OSS对象实例
     */
    private void getOSS(String ak, String sk, String stoken, String endpoint) {
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
                mHandler.sendEmptyMessage(GETSUBJECTLIST);
            }
        }).start();

        //getSubjectListByOne();
    }

    /**
     * 获取第一个项目下的题目
     */
    private void getSubjectListByOne() {
        //获取第一各项目的题目
        /*if (ossClient != null) {
         */
        //获取题目下可上传的题目
        if (type == 1) {  //全部上传模式
            p.getSubjectList(allProjectList, 0);
        }
        if (type == 2) {    //批量上传模式
            p.getSubjectList(ck_listProjectDb, 0);
        }
        if (type == 3) {  //点击单个上传模式
            p.getSubjectList(click_project, 0);
        }

    }

    /**
     * 获取字符串
     *
     * @param path
     * @param flag
     * @return
     */
    public static String getName(String path, String flag) {
        int i = path.lastIndexOf(flag);
        String substring_name = path.substring(i + 1);
        return substring_name;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            p.getProjectList(Constants.sessionId);
        }
    }

    /**
     * 回调给服务器
     *
     * @param projectsDB
     * @param subjectsDB
     * @param picList
     * @param text
     * @param audioPath
     * @param project_position
     * @param subject_position
     */
    private void callbackForServer(List<ProjectsDB> projectsDB, List<SubjectsDB> subjectsDB, List<String> picList, String text, String audioPath, int project_position, int subject_position) {
        Map map = new HashMap();
        map.put("projectId", projectsDB.get(project_position).getPid());
        //map.put("censor", subjectsDB.get(subject_position).getCensor());
        map.put("answerId", subjectsDB.get(subject_position).getHt_id());
        map.put("number", subjectsDB.get(subject_position).getNumber());
        map.put("stage", subjectsDB.get(subject_position).getStage());
        if (text != null) {
            String[] split = text.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        String s = split[0];
                        String string = SaveOrGetAnswers.getString(s, ":");
                        if (string != null && !string.equals("") && !string.equals("null")) {
                            map.put("answer", string);
                            KLog.d("anwser" + string);
                        } else {
                            map.put("anwser", "");
                        }
                    }
                    if (i == 1) {
                        String s1 = split[1];
                        String string1 = SaveOrGetAnswers.getString(s1, ":");
                        if (string1 != null && !string1.equals("") && !string1.equals("null")) {
                            map.put("description", string1);
                            KLog.d("description" + string1);
                        } else {
                            map.put("description", "");
                        }
                    }
                }
            }
        } else {
            map.put("answer", "");
            map.put("description", "");
        }
        map.put("audioCount", audioPath != null ? 1 : 0);
        map.put("audio", audioPath != null ? subjectsDB.get(subject_position).getNumber() + ".wav" : "");
        if (picList != null && picList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < picList.size(); i++) {
                if (picList.size() == 1) {
                    stringBuffer.append(getName(picList.get(i), ".").toLowerCase().equals("jpg") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".jpg"
                            : getName(picList.get(i), ".").toLowerCase().equals("png") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".png"
                            : getName(picList.get(i), ".").toLowerCase().equals("gif") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".gif"
                            : getName(picList.get(i), ".").toLowerCase().equals("jpeg") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".jpeg"
                            : subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".png");
                } else {
                    if (i == (picList.size() - 1)) {
                        stringBuffer.append(getName(picList.get(i), ".").toLowerCase().equals("jpg") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".jpg"
                                : getName(picList.get(i), ".").toLowerCase().equals("png") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".png"
                                : getName(picList.get(i), ".").toLowerCase().equals("gif") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".gif"
                                : getName(picList.get(i), ".").toLowerCase().equals("jpeg") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".jpeg"
                                : subjectsDB.get(subject_position) + "_" + (i + 1) + ".png");
                    } else {
                        stringBuffer.append(getName(picList.get(i), ".").toLowerCase().equals("jpg") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".jpg" + ";"
                                : getName(picList.get(i), ".").toLowerCase().equals("png") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".png" + ";"
                                : getName(picList.get(i), ".").toLowerCase().equals("gif") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".gif" + ";"
                                : getName(picList.get(i), ".").toLowerCase().equals("jpeg") ? subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".jpeg" + ";"
                                : subjectsDB.get(subject_position).getNumber() + "_" + (i + 1) + ".png" + ";");
                    }
                }
                map.put("picture", stringBuffer.toString());
                map.put("pictureCount", picList.size());
            }
        } else {
            map.put("picture", "");
            map.put("pictureCount", 0);
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                p.CallBackServer(map, subjectsDB, projectsDB, project_position, subject_position);
            }
        });
    }

    /**
     * 获取阿里云Oss
     */
    private void getAliyunOss() {
        Map map = new HashMap();
        p.getOss(map);
    }
}