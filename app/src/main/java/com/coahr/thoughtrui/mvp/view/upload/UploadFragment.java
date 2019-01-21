package com.coahr.thoughtrui.mvp.view.upload;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.UploadC;
import com.coahr.thoughtrui.mvp.model.ApiContact;
import com.coahr.thoughtrui.mvp.presenter.UploadP;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.upload.adapter.UpLoadAdapter;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.NetWorkReceiver;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.security.auth.Subject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:12
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
    private NetWorkReceiver netWorkReceiver;
    private boolean isRegistered;
    //所有的项目列表
    private List<ProjectsDB> allProjectList;
    //所有项目个数
    private int all_project_size;
    //选中的项目列表
    private List<ProjectsDB> ck_listProjectDb;
    //选中的项目个数
    private int ck_Project_size;

    //当前在上传的项目位置(从0开始)
    private int up_project_position = 0;
    //单个项目下的题目列表
    private List<SubjectsDB> subjectsDBList;
    //当前项目下待上传题目个数
    private int Subject_size;
    //当前正在上传的题目位置(从0开始)
    private int up_subject_position = 0;
    //全部上传or批量上传or点击上传
    private int type;
    //上传文件的数组
    private List<String> fileList = new ArrayList<>();
    private UpLoadAdapter upLoadAdapter;
    private LinearLayoutManager manager;
    private boolean isLoading;
    private ProjectsDB projectsDB_click; //点击上传的项目
    //网络状态
    private int NetType;
    //是否连接
    private boolean isNetConnect;
    private OSS ossClient;
    private final int GETSUBJECTLIST = 1;
    private Handler mHandler = new Handler() {
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
        //注册网络状态监听广播
        netWorkReceiver = new NetWorkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        _mActivity.registerReceiver(netWorkReceiver, filter);
        isRegistered = true;
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
        netWorkReceiver.setNetStatusListener(new NetWorkReceiver.INetStatusListener() {
            @Override
            public void getNetState(String netWorkType, boolean isConnect, NetworkInfo.DetailedState detailedState) {
                isNetConnect = isConnect;
                if (netWorkType != null) {
                    if (netWorkType.equals("WIFI")) {
                        NetType = 1;
                    }
                    if (netWorkType.equals("MOBILE")) {
                        NetType = 2;
                    }
                }
            }
        });
        up_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    isLoading = true;
                    p.getProjectList(Constants.sessionId);
                } else {
                    up_swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void initData() {

        //请求AK鉴权
        // p.getSTS(ApiContact.STSSERVER);
        //获取项目数组
        p.getProjectList(Constants.sessionId);
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
                projectsDB_click=projectsDB;
                type=3;
                if (NetType == 2) {
                    Dialog("提示", "当前为4G信号是否继续上传", type);
                } else {
                    getOSS();
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
        if (isRegistered) {
            _mActivity.unregisterReceiver(netWorkReceiver);
        }
    }

    @Override
    public void getSTSSuccess(OSSCredentialProvider ossCredentialProvider) {
        //获取阿里云上传实例
        //  p.getOSS(BaseApplication.mContext, ApiContact.endpoint, ossCredentialProvider, conf);
    }

    @Override
    public void getSTSFailure(String failure) {
        ToastUtils.showLong(failure);
    }

    @Override
    public void getOSSSuccess(OSS oss) {
    /*    myoss = oss;
        //获取第一各项目的题目
        if (type == 1) {  //全部
            p.getSubjectList(allProjectList.get(up_project_position));
        }
        if (type == 2) {    //批量
            p.getSubjectList(ck_listProjectDb.get(up_project_position));
        }*/
    }

    @Override
    public void getOSSFailure(String failure) {
        ToastUtils.showLong(failure);
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
        //获取第一个项目下的题目
        // p.getSubjectList(listProjectDb.get(up_project_position));

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

    @Override
    public void getSubjectListSuccess(List<SubjectsDB> subjectsDBList, ProjectsDB projectsDB) {
        this.subjectsDBList = subjectsDBList;
        Subject_size = subjectsDBList.size();
        //获取第一个题目下的上传文件
        up_subject_position = 0;
        p.UpLoadFileList(projectsDB, subjectsDBList.get(0), _mActivity);
    }

    @Override
    public void getSubjectListFailure(String failure) {
        ToastUtils.showLong(failure);
        dismissLoading();
    }

    @Override
    public void getUoLoadFileListSuccess(List<String> list, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        //开始上传
        p.StartUpLoad(ossClient, list, projectsDB, subjectsDB);
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        ToastUtils.showLong(failure);
        dismissLoading();
    }

    @Override
    public void StartUpLoadSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB, List<String> list) {
        String audioPath = null;
        String textMassage = null;
        fileList.clear();
        KLog.d("阿里云上传成功" + projectsDB.getPname() + "/" + subjectsDB.getNumber());
        //当前题目下数据上传成功
        //执行回调
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).endsWith(".wav") || list.get(i).endsWith(".arm") || list.get(i).endsWith(".mp3")) {
                audioPath = list.get(i);
            } else if (list.get(i).endsWith(".txt")) {
                textMassage = SaveOrGetAnswers.readFromFile(list.get(i));
            } else {
                fileList.add(list.get(i));
            }
        }
        callbackForServer(projectsDB, subjectsDB, audioPath, fileList, textMassage);
    }

    @Override
    public void StartUpLoadFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        KLog.d("阿里云上传失败" + projectsDB.getPname() + "/" + subjectsDB.getNumber());
        upDateDB(projectsDB, subjectsDB, false);

    }

    @Override
    public void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        KLog.d("阿里云上传回调成功" + projectsDB.getPname() + "/" + subjectsDB.getNumber());
        upDateDB(projectsDB, subjectsDB, true);
    }

    @Override
    public void CallBackFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        KLog.d("阿里云上传回调失败" + projectsDB.getPname() + "/" + subjectsDB.getNumber());
        upDateDB(projectsDB, subjectsDB, false);
    }


    @Override
    public void onClick(View v) {
        if (isNetConnect) {
            switch (v.getId()) {
                case R.id.tv_Batch_Management:
                    if (tv_Batch_Management.getTag() == null || tv_Batch_Management.getTag().equals("1")) {
                        card_bottom.setVisibility(View.VISIBLE);
                        tv_all_upload.setVisibility(View.INVISIBLE);
                        tv_Batch_Management.setText("取消");
                        upLoadAdapter.setCheckViewVisible(true);
                        tv_Batch_Management.setTag("2");
                    } else {
                        card_bottom.setVisibility(View.GONE);
                        tv_all_upload.setVisibility(View.VISIBLE);
                        upLoadAdapter.setCheckViewVisible(false);
                        tv_Batch_Management.setText("批量管理");
                        tv_Batch_Management.setTag("1");
                    }
                    break;
                case R.id.tv_all_upload:
                    type = 1;
                    if (NetType == 2) {
                        Dialog("提示", "当前为4G信号是否继续上传", type);
                    } else {
                        // p.getSTS(ApiContact.STSSERVER);
                        getOSS();
                    }

                    break;
                case R.id.tv_Batch_UpLoad:
                    type = 2;
                    if (NetType == 2) {
                        Dialog("提示", "当前为4G信号是否继续上传", type);
                    } else {
                        //p.getSTS(ApiContact.STSSERVER);
                        getOSS();
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
        } else {
            Dialog("提示", "当前网络不可用", 3);
        }
    }

    private void callbackForServer(final ProjectsDB projectsDB, final SubjectsDB subjectsDB, String recorderPath, List<String> picList, String text) {
        final Map map = new HashMap();
        map.put("projectId", projectsDB.getPid());
        map.put("answerId", subjectsDB.getHt_id());
        map.put("number", subjectsDB.getNumber());
        map.put("stage", projectsDB.getStage());
       /* map.put("answer","是");
        map.put("description", "哈哈哈");*/
        if (text != null) {
            String[] split = text.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        String s = split[0];
                        String string = SaveOrGetAnswers.getString(s, ":");
                        if (string != null && !string.equals("") && !string.equals("null")) {
                            map.put("answer", s);
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
        }
        map.put("audioCount", recorderPath != null ? 0 : 1);
        KLog.d("录音名字", recorderPath);
        map.put("audio", recorderPath != null ? getName(recorderPath, "/") : "");
        map.put("pictureCount", picList.size());

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < picList.size(); i++) {
            if (picList.size() == 1) {
                // stringBuffer.append(subjectsDB.getNumber() + "_" + (i + 1) + "." + "jpg");
                stringBuffer.append(getName(picList.get(i), "/"));
            } else {
                if (i == (picList.size() - 1)) {
                    // stringBuffer.append(subjectsDB.getNumber() + "_" + (i + 1) + "." + "jpg");
                    stringBuffer.append(getName(picList.get(i), "/"));
                } else {
                    stringBuffer.append(getName(picList.get(i), "/") + ";");
                    // stringBuffer.append(subjectsDB.getNumber() + "_" + (i + 1) + "." + "jpg" + ";");
                }
            }
        }
        map.put("picture", stringBuffer.toString());
        KLog.d("回调", picList.size(), text);

        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                p.CallBack(map, projectsDB, subjectsDB);
            }
        });

    }


    /**
     * 修改数据库操作
     *
     * @param projectsDB
     * @param subjectsDB
     */
    private void upDateDB(ProjectsDB projectsDB, SubjectsDB subjectsDB, boolean isSuccess) {
        if (type == 1) {  //全部上传
            if (isSuccess) {
                SubjectsDB subjectsDB1 = new SubjectsDB();
                subjectsDB1.setsUploadStatus(1);
                int update = subjectsDB1.update(subjectsDB.getId());
            }
            if (up_subject_position == subjectsDBList.size() - 1) { //当前项目下的题目传完了
                up_subject_position = 0;
                //先判断项目下的题目是否传完
                List<SubjectsDB> subjectsDBList = projectsDB.getSubjectsDBList();
                if (subjectsDBList != null && subjectsDBList.size() > 0) {
                    int up_subject = 0;
                    for (int i = 0; i < subjectsDBList.size(); i++) {
                        if (subjectsDBList.get(i).getsUploadStatus() == 1) {
                            up_subject++;
                        }
                    }
                    //题目是否都传完了
                    if (up_subject == subjectsDBList.size()) {
                        ProjectsDB projectsDB1 = new ProjectsDB();
                        projectsDB1.setIsComplete(1);
                        projectsDB1.setpUploadStatus(1);
                        projectsDB1.update(projectsDB.getId());
                    } else {
                        up_project_position = 0;
                    }
                }
                //开始上传下一个项目
                if (up_project_position == all_project_size - 1) {  //如果所有项目都传完了
                    up_project_position = 0;
                    p.getProjectList(Constants.sessionId);
                    dismissLoading();
                } else {
                    p.getSubjectList(allProjectList.get(up_project_position++));
                }

            } else {  //当前项目没有传完传下一题
                p.UpLoadFileList(projectsDB, subjectsDBList.get(up_subject_position++), _mActivity);
            }

        }
        if (type == 2) {  //批量上传
            if (isSuccess) {
                SubjectsDB subjectsDB1 = new SubjectsDB();
                subjectsDB1.setsUploadStatus(1);
                int update = subjectsDB1.update(subjectsDB.getId());
            }
            if (up_subject_position == Subject_size - 1) { //当前项目下的题目传完了
                up_subject_position = 0;
                //先判断项目下的题目是否传完
                List<SubjectsDB> subjectsDBList = projectsDB.getSubjectsDBList();
                if (subjectsDBList != null && subjectsDBList.size() > 0) {
                    int up_subject = 0;
                    for (int i = 0; i < subjectsDBList.size(); i++) {
                        if (subjectsDBList.get(i).getsUploadStatus() == 1) {
                            up_subject++;
                        }
                    }
                    //题目是否都传完了
                    if (up_subject == subjectsDBList.size()) {
                        ProjectsDB projectsDB1 = new ProjectsDB();
                        projectsDB1.setIsComplete(1);
                        projectsDB1.setpUploadStatus(1);
                        projectsDB1.update(projectsDB.getId());
                    }
                }
                //开始上传下一个项目
                if (up_project_position == ck_Project_size - 1) {  //如果所有项目都传完了
                    up_project_position = 0;
                    p.getProjectList(Constants.sessionId);
                    dismissLoading();
                } else {
                    up_project_position = 0;
                    p.getSubjectList(ck_listProjectDb.get(up_project_position++));
                }

            } else {  //当前项目没有传完传下一题
                p.UpLoadFileList(projectsDB, subjectsDBList.get(up_subject_position++), _mActivity);
            }
        }

        if (type==3){  //
            if (isSuccess) {
                SubjectsDB subjectsDB1 = new SubjectsDB();
                subjectsDB1.setsUploadStatus(1);
                int update = subjectsDB1.update(subjectsDB.getId());
            }

            if (up_subject_position == Subject_size - 1) { //当前项目下的题目传完了
                up_subject_position = 0;
                //先判断项目下的题目是否传完
                List<SubjectsDB> subjectsDBList = projectsDB.getSubjectsDBList();
                if (subjectsDBList != null && subjectsDBList.size() > 0) {
                    int up_subject = 0;
                    for (int i = 0; i < subjectsDBList.size(); i++) {
                        if (subjectsDBList.get(i).getsUploadStatus() == 1) {
                            up_subject++;
                        }
                    }
                    //题目是否都传完了
                    if (up_subject == subjectsDBList.size()) {
                        ProjectsDB projectsDB1 = new ProjectsDB();
                        projectsDB1.setIsComplete(1);
                        projectsDB1.setpUploadStatus(1);
                        projectsDB1.update(projectsDB.getId());
                    }
                }
                p.getProjectList(Constants.sessionId);
                dismissLoading();
            } else {
                p.UpLoadFileList(projectsDB, subjectsDBList.get(up_subject_position++), _mActivity);
            }
        }
    }

    private void Dialog(String title, String Content, final int type) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("确认")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showLoading();
                        if (type == 1 || type == 2) {
                            getOSS();
                        } else {
                            getOSS();
                        }
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * OSS对象实例
     */
    private void getOSS() {
        /**
         * 获取密钥
         */
        if (ossClient == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OSSLog.enableLog();
                    OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(ApiContact.STSSERVER);
                    ClientConfiguration conf = new ClientConfiguration();
                    conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
                    conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
                    conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                    conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                    ossClient = new OSSClient(_mActivity.getApplicationContext(), ApiContact.endpoint, credentialProvider, conf);
                    mHandler.sendEmptyMessage(GETSUBJECTLIST);
                }
            }).start();
        } else {
            mHandler.sendEmptyMessage(GETSUBJECTLIST);
        }

    }

    /**
     * 获取第一个项目下的题目
     */
    private void getSubjectListByOne() {
        //获取第一各项目的题目
        if (ossClient != null) {
            if (type == 1) {  //全部
                p.getSubjectList(allProjectList.get(0));
            }
            if (type == 2) {    //批量
                p.getSubjectList(ck_listProjectDb.get(0));
            }
            if (type==3){  //点击上传
                p.getSubjectList(projectsDB_click);
            }
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            KLog.d("onResume", "上传页面");
            p.getProjectList(Constants.sessionId);
        }
    }

    /**
     * 点击提示
     *
     * @param projectsDB
     */
    private void showDialog(final ProjectsDB projectsDB) {
        new MaterialDialog.Builder(_mActivity)
                .title("提示")
                .content("是否上传")
                .negativeText("取消")
                .positiveText("确定")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //获取当前题目
                        showLoading();
                        getOSS();
                    }
                }).build().show();
    }
}
