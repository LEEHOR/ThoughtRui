package com.coahr.thoughtrui.mvp.view.startProject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.Permission.RuntimeRationale;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.model.ApiContact;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.PagerFragment_aP;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.Fill_in_blankDialog;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;
import com.coahr.thoughtrui.widgets.BroadcastReceiver.NetWorkReceiver;
import com.coahr.thoughtrui.widgets.UIPlayer.uiDisPlayer;
import com.socks.library.KLog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.async.UpdateOrDeleteExecutor;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;
import omrecorder.WriteAction;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 18:31
 * 单选题类型
 */
public class PagerFragment_a extends BaseChildFragment<PagerFragment_aC.Presenter> implements PagerFragment_aC.View, View.OnClickListener {

    @Inject
    PagerFragment_aP p;
    @BindView(R.id.project_detail_titlle)
    TextView project_detail_titlle;  //题目
    @BindView(R.id.tv_describe)
    TextView tv_describe;   //描述
    @BindView(R.id.rg_gr)
    RadioGroup rg_gr;   //选择组
    @BindView(R.id.rb_yes)
    RadioButton rb_yes;  //选择是
    @BindView(R.id.rb_no)
    RadioButton rb_no;  //选择否
    //===============填空题组=================
    @BindView(R.id.re_score)
    RelativeLayout re_score;  //填空控制
    @BindView(R.id.tv_standard_score)
    TextView tv_standard_score;  //填空标准分
    @BindView(R.id.ed_score)
    TextView ed_score;    //填写得分
    @BindView(R.id.Fr_takePhoto)
    FrameLayout Fr_takePhoto;  //拍照
    @BindView(R.id.tv_recorderBtn)
    TextView tv_recorderBtn;  // 录音文字图片
    @BindView(R.id.Fr_takeRecorder)
    FrameLayout Fr_takeRecorder;  //录音总按钮
    @BindView(R.id.tv_bianji)
    TextView tv_bianji;    //编辑
    @BindView(R.id.tv_recorder_name)
    TextView tv_recorder_name;  //录音文件名
    @BindView(R.id.tv_play_recorder)
    TextView tv_play_recorder;  //播放录音
    @BindView(R.id.tv_Unfold)
    TextView tv_Unfold; //图片展开收起
    @BindView(R.id.img_recycler)
    RecyclerView img_recycler;  //图片列表
    @BindView(R.id.tv_last)
    TextView tv_last; //上一页
    @BindView(R.id.tv_next)
    TextView tv_next;  //下一页
    @BindView(R.id.fr_upload)
    FrameLayout fr_upload; //上传
    //==========================================================
    //项目本地数据库Id
    private String dbProjectId;
    //项目后台Id
    private String ht_projectId;
    //题目个数
    private int countSize;
    //题目Id
    private String ht_id;
    //数据库题目序号
    private int number;
    private SpacesItemDecoration spacesItemDecoration;
    private int imageSize = 0; //图片个数
    private PagerFragmentPhotoAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    //是否正在删除图片操作
    private boolean isDeletePic;
    //选择的答案
    private String answers;
    //填写的描述
    private String remark;
    //是否选取单选按钮
    private boolean isAnswer;
    //是否有录音
    private boolean isHaveRecorder;
    //是否有图片
    private boolean isPhotos;
    private String audioPath;
    //是否在录音
    private boolean isRecorder;
    //标准分数
    private String standard_score;
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    //评论输入窗口
    EvaluateInputDialogFragment dialogFragment = EvaluateInputDialogFragment.newInstance();
    //录音播放按钮Ui
    private Drawable imgs[] = {BaseApplication.mContext.getResources().getDrawable(R.mipmap.ico_recorder, null)
            , BaseApplication.mContext.getResources().getDrawable(R.mipmap.recordering, null)
            , BaseApplication.mContext.getResources().getDrawable(R.mipmap.recorder_play_w, null)};
    private Recorder recorder;
    private int type = 1;
    private String audioName;
    private SubjectsDB subjectsDB_now;
    private int subjectsDBType;
    private OSSClient ossClient;
    private final int GETUPLOADLIST = 1;
    private final int UIPROGRESS = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETUPLOADLIST:
                    p.UpLoadFileList(projectsDB.getPid(), subjectsDB_now);
                    break;
                case UIPROGRESS:
                    //  progressBar.setMax(msg.arg2);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBar.setProgress(msg.arg1, true);
                    } else {
                        progressBar.setProgress(msg.arg1);
                    }
                    //   progressBar.setProgress(msg.arg1);
                    tv_tittle.setText(msg.obj.toString());
                    break;
            }
        }
    };
    private ProjectsDB projectsDB;
    private View inflate;
    private TextView tv_tittle;
    private ProgressBar progressBar;
    //上传文件的数组
    private List<String> fileList_Call = new ArrayList<>();
    private String textMassage;
    private String uPAudioPath;

    public static PagerFragment_a newInstance(int position, String DbProjectId, String ht_ProjectId, int countSize, String name_project, String ht_id) {
        PagerFragment_a pagerFragment_a = new PagerFragment_a();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("DbProjectId", DbProjectId);
        bundle.putString("ht_ProjectId", ht_ProjectId);
        bundle.putInt("countSize", countSize);
        bundle.putString("name_project", name_project);
        bundle.putString("ht_id", ht_id);
        pagerFragment_a.setArguments(bundle);
        return pagerFragment_a;
    }

    @Override
    public PagerFragment_aC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_review_project;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spacesItemDecoration = new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 2), DensityUtils.dp2px(BaseApplication.mContext, 2), getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void initView() {
        inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_progress, null);
        tv_tittle = inflate.findViewById(R.id.tv_progress_info);
        progressBar = inflate.findViewById(R.id.progress_bar);

        if (getArguments() != null) {
            dbProjectId = getArguments().getString("DbProjectId");
            ht_projectId = getArguments().getString("ht_ProjectId");
            countSize = getArguments().getInt("countSize");
            ht_id = getArguments().getString("ht_id");
            List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectBy_Where(SubjectsDB.class, new String[]{"number"}, "ht_id=?", ht_id);
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                number = subjectsDBS.get(0).getNumber();
                KLog.d("题号", number, ht_id);
            }
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", ht_projectId);
            if (projectsDBS != null && projectsDBS.size() > 0) {
                projectsDB = projectsDBS.get(0);
            }
        }
        adapter = new PagerFragmentPhotoAdapter();
        gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
        img_recycler.setLayoutManager(gridLayoutManager);
        img_recycler.setAdapter(adapter);
        img_recycler.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 4), DensityUtils.dp2px(BaseApplication.mContext, 4), getResources().getColor(R.color.colorWhite)));
        for (int i = 0; i < img_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                img_recycler.removeItemDecorationAt(i);
            }
        }
        tv_last.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        Fr_takePhoto.setOnClickListener(this);
        tv_bianji.setOnClickListener(this);
        tv_play_recorder.setOnClickListener(this);
        fr_upload.setOnClickListener(this);

        //录音控制
        Fr_takeRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHaveRecorder) {  //有无录音
                    showDeleteAudioDialog("当前题目下有录音", "是否删除");
                } else {
                    if (type == 1) { //开始录音
                        setupRecorder();
                        startAudio();
                    } else if (type == 2) { //暂停录音

                    } else if (type == 3) { //继续录音

                    } else if (type == 4) {  //停止录音
                        StopAudio();
                    }

                }

            }
        });
        //输入备注
        dialogFragment.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
            @Override
            public void onInputSend(String input, AppCompatDialogFragment dialog) {
                if (input != null && !input.equals("")) {
                    KLog.d("输入", input);
                    tv_bianji.setText(input);
                    p.saveAnswers(answers, input, ht_projectId, number, ht_id);
                    dialogFragment.dismiss();
                }
            }
        });
        //输入分数
        ed_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fill_in_blankDialog fill_in_blankDialog = Fill_in_blankDialog.newInstance();
                fill_in_blankDialog.setOnClick(new Fill_in_blankDialog.InPutOnClick() {
                    @Override
                    public void setOnClick(String text) {
                        if (text != null && standard_score!=null) {
                            if (Integer.parseInt(text)<=Integer.parseInt(standard_score) && Integer.parseInt(text)>=0){
                                ed_score.setText(text);
                                p.saveAnswers(text, remark, ht_projectId, number, ht_id);
                            } else {
                                    ToastUtils.showLong("请输入正确的分数");
                            }
                        }

                    }

                    @Override
                    public void setOnClickFailure() {
                        ToastUtils.showLong("请输入正确的数值");
                    }
                });
                fill_in_blankDialog.show(getFragmentManager(), TAG);
            }
        });

        //图片的展开和关闭
        tv_Unfold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_Unfold.getTag()==null || tv_Unfold.getTag().equals("1")){ //展开
                    img_recycler.setVisibility(View.VISIBLE);
                    tv_Unfold.setText("关闭");
                    tv_Unfold.setTag("2");
                }  else if (tv_Unfold.getTag().equals("2")){  //关闭
                    img_recycler.setVisibility(View.GONE);
                    tv_Unfold.setText("展开");
                    tv_Unfold.setTag("1");
                } else if (tv_Unfold.getTag().equals("3")){
                    tv_Unfold.setText("关闭");
                    tv_Unfold.setTag("2");
                    isDeletePic=false;
                    p.getImage(ht_projectId, _mActivity, number, ht_id);
                }

            }
        });
    }

    @Override
    public void initData() {
        getSubjectDetail();
        //单选监听
        rg_gr.setOnCheckedChangeListener(new RadioGroupListener());
        //图片监听
        adapter.setListener(new PagerAdapterListener());
        //
        ed_score.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //上一题
            case R.id.tv_last:
                if (!isRecorder) {  //判断是否在录音
                    KLog.d("题号", number);
                    if (number > 1) {
                        if (isComplete()) {
                            SubjectsDB subjectsDB = new SubjectsDB();
                            subjectsDB.setIsComplete(1);
                            subjectsDB.update(subjectsDB_now.getId());
                        }
                        EventBus.getDefault().postSticky(new isCompleteBean(true, number - 1, 1,1));
                    } else {
                        ToastUtils.showLong("已经是第一题");
                    }
                } else {
                    showDeleteAudioDialog("", "确定停止录音");
                }

                break;
            //下一题
            case R.id.tv_next:
                if (number < countSize) {
                    if (!isRecorder) {
                        if (isComplete()) {
                            SubjectsDB subjectsDB = new SubjectsDB();
                            subjectsDB.setIsComplete(1);
                            subjectsDB.update(subjectsDB_now.getId());
                        }
                        EventBus.getDefault().postSticky(new isCompleteBean(true, number + 1, 2,1));
                    } else {
                        showStopAudioDialog("", "是否停止录音");
                    }

                } else {
                    if (!isRecorder) {
                        if (isComplete()) {
                            SubjectsDB subjectsDB = new SubjectsDB();
                            subjectsDB.setIsComplete(1);
                            subjectsDB.updateAsync(subjectsDB_now.getId());
                        }
                        ToastUtils.showLong("已经是最后一题");
                        ProjectSuccessDialog projectSuccessDialog = ProjectSuccessDialog.newInstance(ht_projectId);
                        projectSuccessDialog.show(getChildFragmentManager(), TAG);
                    } else {
                        showStopAudioDialog("", "是否停止录音");
                    }

                }
                break;
            //拍照
            case R.id.Fr_takePhoto:
                if (imageSize < 10) {
                    openMulti((10 - imageSize));
                } else {
                    ToastUtils.showLong("图片数量已经足够");
                }
                break;
            case R.id.tv_bianji:
                dialogFragment.show(_mActivity.getSupportFragmentManager(), TAG);
                break;
            case R.id.tv_play_recorder:
                if (!isRecorder) {
                    if (audioPath != null) {
                        DialogFragmentAudioPlay audioPlay = DialogFragmentAudioPlay.newInstance(audioPath, audioName);
                        audioPlay.show(getChildFragmentManager(), TAG);
                    } else {
                        ToastUtils.showLong("没有录音文件");
                    }
                } else {
                    ToastUtils.showLong("正在录音");

                }

                break;

            case R.id.fr_upload:
                fr_upload.setEnabled(false);
                if (Constants.isNetWorkConnect) {
                    if (Constants.NetWorkType != null && Constants.NetWorkType.equals("WIFI")) {
                        NetWorkDialog("提示", "是否上传", 1);
                    } else if (Constants.NetWorkType != null && Constants.NetWorkType.equals("MOBILE")) {
                        NetWorkDialog("提示", "当前为移动网络是否继续上传", 2);
                    }
                } else {
                    NetWorkDialog("提示", "当前网络不可用无法上传", 3);
                }
                break;

        }

    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
        this.subjectsDB_now = subjectsDB;

        if (subjectsDB != null) {
            //题目
            project_detail_titlle.setText(subjectsDB.getTitle());
            //String options = subjectsDB.getOptions();
            //判断是填空题还是选择题
            this.subjectsDBType = subjectsDB.getType();
            if (subjectsDB.getType() == 0) {  //判断
                re_score.setVisibility(View.GONE);
                rg_gr.setVisibility(View.VISIBLE);
            }

            if (subjectsDB.getType() == 1) {  //填空题
                re_score.setVisibility(View.VISIBLE);
                rg_gr.setVisibility(View.GONE);
                standard_score=subjectsDB.getOptions();
                tv_standard_score.setText("标准分数：" + subjectsDB.getOptions());
            }
            tv_describe.setText("说明：" + subjectsDB.getDescription());
            p.getAnswer(ht_projectId, _mActivity, number, ht_id);
            p.getImage(ht_projectId, _mActivity, number, ht_id);
            p.getAudio(ht_projectId, _mActivity, number, ht_id);
        }
    }

    @Override
    public void getSubjectFailure(String failure) {
        ToastUtils.showLong(failure);
    }


    /**
     * 图片
     *
     * @param imagePathList
     */
    @Override
    public void getImageSuccess(List<String> imagePathList) {
        imageSize = imagePathList.size();
        if (imagePathList.size() > 0) {
            isPhotos = true;
            if (isDeletePic) {
                adapter.setIsDel(true);
                adapter.setNewData(imagePathList);
                adapter.setImageList(imagePathList);
            } else {
                adapter.setIsDel(false);
                adapter.setNewData(imagePathList);
                adapter.setImageList(imagePathList);
            }
            //更新数据库
            UpdateDB();
        } else {
            isDeletePic = false;
            adapter.setIsDel(false);
            adapter.setNewData(imagePathList);
            adapter.setImageList(imagePathList);

        }
    }

    @Override
    public void getImageFailure() {
        ToastUtils.showLong("获取图片失败");
        isDeletePic = false;
        isPhotos = false;
    }

    @Override
    public void getAnswerSuccess(String Massage) {
        if (Massage != null) {
            if (subjectsDBType == 0) { //判断题
                String[] split = Massage.split("&");
                if (split != null && split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            String s = split[0];
                            String string = SaveOrGetAnswers.getString(s, ":");
                            if (string != null && !string.equals("") && !string.equals("null")) {
                                answers = string;
                                isAnswer = true;
                                if (string.equals("是")) {
                                    rb_yes.toggle();
                                }
                                if (string.equals("否")) {
                                    rb_no.toggle();
                                }
                            }
                        }
                        if (i == 1) {
                            String s1 = split[1];
                            String string1 = SaveOrGetAnswers.getString(s1, ":");
                            if (string1 != null && !string1.equals("") && !string1.equals("null")) {
                                remark = string1;
                                tv_bianji.setText(string1);
                            }
                        }
                    }
                }
            }

            if (subjectsDBType == 1) {    //填空题
                String[] split = Massage.split("&");
                if (split != null && split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            String s = split[0];
                            String string = SaveOrGetAnswers.getString(s, ":");
                            if (string != null && !string.equals("") && !string.equals("null")) {
                                answers = string;
                                isAnswer = true;
                                ed_score.setText(string);
                                KLog.d("选择" + string);
                            }
                        }
                        if (i == 1) {
                            String s1 = split[1];
                            String string1 = SaveOrGetAnswers.getString(s1, ":");
                            if (string1 != null && !string1.equals("") && !string1.equals("null")) {
                                remark = string1;
                                tv_bianji.setText(string1);
                            }
                        }
                    }
                }
            }

        }
    }

    @Override
    public void getAnswerFailure() {
        isAnswer = false;
    }

    @Override
    public void DeleteImageSuccess(String Massage) {
        ToastUtils.showLong(Massage);
        p.getImage(ht_projectId, _mActivity, number, ht_id);
    }

    @Override
    public void DeleteImageFailure(String Massage) {
        ToastUtils.showLong(Massage);
    }

    @Override
    public void saveAnswersSuccess() {
        ed_score.setFocusable(false);
        ed_score.setFocusableInTouchMode(false);
        KeyBoardUtils.hideKeybord(ed_score, _mActivity);
        p.getAnswer(ht_projectId, _mActivity, number, ht_id);
        ToastUtils.showLong("答案保存成功");
    }

    @Override
    public void saveAnswersFailure() {
        ToastUtils.showLong("答案保存失败");
    }

    @Override
    public void SaveImagesSuccess() {

        p.getImage(ht_projectId, _mActivity, number, ht_id);
        ToastUtils.showShort("图片保存成功");
    }

    @Override
    public void SaveImagesFailure() {
        ToastUtils.showShort("图片保存失败");
    }

    @Override
    public void getAudioSuccess(List<String> audioList) {
        if (!isRecorder) {
            audioPath = audioList.get(0);
            audioName = FileIOUtils.getE(audioPath, "/");
            isHaveRecorder = true;
            updateUi(1); //开始录音
            tv_recorder_name.setText(audioName);
            tv_recorder_name.setTextColor(getResources().getColor(R.color.origin_3));
            //更新数据库完成状态
            UpdateDB();
        }
    }

    @Override
    public void getAudioFailure(String failure) {
        isHaveRecorder = false;
        ToastUtils.showLong(failure + number);
        if (!isRecorder) {
            updateUi(1); //开始录音
            tv_recorder_name.setText("暂无录音");
            tv_recorder_name.setTextColor(getResources().getColor(R.color.material_grey_200));
        }
    }

    //==============================上传操作==================================//

    /**
     * 上传逻辑
     *
     * @param list
     * @param projectsDB_id
     * @param subjectsDB
     */
    @Override
    public void getUpLoadFileListSuccess(List<String> list, String projectsDB_id, SubjectsDB subjectsDB) {
        ToastUtils.showLong("阿里云上传获取题目列表成功");
        showProgressDialog();
        p.startUpload(ossClient, list, projectsDB, subjectsDB);
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        fr_upload.setEnabled(true);
        ToastUtils.showLong(failure);
    }

    @Override
    public void startUploadCallBack(List<String> list, int uploadSuccessSize, int uploadFailSize, int totalSize, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        KLog.d("阿里云上传回调", totalSize, uploadFailSize, uploadSuccessSize);
        //上传成功
        if (totalSize == (uploadSuccessSize + uploadFailSize)) {
            if (totalSize == uploadSuccessSize) {
                fileList_Call.clear();
                KLog.d("阿里云上传成功" + projectsDB.getPname() + "/" + subjectsDB.getNumber());
                //当前题目下数据上传成功
                //执行回调
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).endsWith(".wav") || list.get(i).endsWith(".arm") || list.get(i).endsWith(".mp3")) {
                        uPAudioPath = list.get(i);
                    } else if (list.get(i).endsWith(".txt")) {
                        textMassage = SaveOrGetAnswers.readFromFile(list.get(i));
                    } else {
                        fileList_Call.add(list.get(i));
                    }
                }
                callbackForServer(projectsDB, subjectsDB, uPAudioPath, fileList_Call, textMassage);
            } else {
                fr_upload.setEnabled(true);
            }
        }
    }

    @Override
    public void showProgress(int currentSize, int totalSize, String info) {
        if (currentSize > 100) {
            currentSize = 100;
        } else if (currentSize < 0) {
            currentSize = 0;
        }
        Message mes = mHandler.obtainMessage(UIPROGRESS, info);
        mes.arg1 = currentSize;
        mes.arg2 = totalSize;
        mes.sendToTarget();
    }

    @Override
    public void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        p.UpDataDb(projectsDB, subjectsDB);
        KLog.d("阿里云上传回调成功");
    }

    @Override
    public void CallBackFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        KLog.d("阿里云上传回调失败");
        fr_upload.setEnabled(true);
    }

    @Override
    public void UpDataDbSuccess() {
        fr_upload.setEnabled(true);
    }

    @Override
    public void UpDataDbFailure(String fail) {
        ToastUtils.showLong(fail);
        fr_upload.setEnabled(true);
    }

    /**
     * 获取题目信息
     */
    private void getSubjectDetail() {
        p.getSubject(dbProjectId, ht_projectId, _mActivity, number, ht_id);
    }

    /**
     * 拍照
     */
    private void openMulti(int count) {
        setPath();
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(_mActivity)
                .image()
                .multiple();
        rxGalleryFinal.maxSize(count)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();
                        if (mediaBeanList != null && mediaBeanList.size() > 0) {
                            KLog.d(mediaBeanList.get(0).getOriginalPath());
                            p.SaveImages(mediaBeanList, ht_projectId, number, ht_id);
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
     * 图片监听
     */
    class PagerAdapterListener implements PagerFragmentPhotoListener {

        @Override
        public void onClick(List<String> imageList, int position) {
            photoAlbumDialogFragment = PhotoAlbumDialogFragment.newInstance();
            photoAlbumDialogFragment.setImgList(imageList);
            photoAlbumDialogFragment.setFirstSeePosition(position);
            FragmentManager fragmentManager = getFragmentManager();
            photoAlbumDialogFragment.show(fragmentManager, TAG);
        }

        @Override
        public void onLongClick(List<String> imageList, int position) {
            adapter.setIsDel(true);
            adapter.setImageList(imageList);
            adapter.notifyDataSetChanged();
            tv_Unfold.setText("取消");
            tv_Unfold.setTag("3");
        }

        @Override
        public void onDel(List<String> imageList, int position) {
            //删除
            isDeletePic = true;
            p.DeleteImage(imageList.get(position));
        }
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton button = rg_gr.findViewById(i);

            if (!button.isPressed()) {
                return;
            }
            if (rb_yes.isChecked()) {
                answers = "是";
            }
            if (rb_no.isChecked()) {
                answers = "否";
            }
            KLog.d("选择", answers);
            p.saveAnswers(answers, remark, ht_projectId, number, ht_id);
        }
    }

    /**
     * 判断是否完成
     *
     * @return
     */
    private boolean isComplete() {
        if (isPhotos && isAnswer) {
            return true;
        }
        return false;
    }

    /**
     * UI更新
     *
     * @param types;
     */
    private void updateUi(final int types) {
        if (types == 1) {
            this.tv_recorderBtn.setText("开始录音");
            imgs[0].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext, 15), DensityUtils.dp2px(BaseApplication.mContext, 15));
            this.tv_recorderBtn.setCompoundDrawables(imgs[0], null, null, null);
        }
        if (types == 2) {
            this.tv_recorderBtn.setText("正在录音");
            imgs[1].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext, 15), DensityUtils.dp2px(BaseApplication.mContext, 15));
            this.tv_recorderBtn.setCompoundDrawables(imgs[1], null, null, null);
        }
        if (types == 4) {
            this.tv_recorderBtn.setText("播放录音");
            imgs[2].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext, 15), DensityUtils.dp2px(BaseApplication.mContext, 15));
            this.tv_recorderBtn.setCompoundDrawables(imgs[2], null, null, null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 正常模式录音
     */
    private void setupRecorder() {
        recorder = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override
                    public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file());
    }

    /**
     * 去除噪音录音
     */
    private void setupNoiseRecorder() {
        recorder = OmRecorder.wav(
                new PullTransport.Noise(mic(),
                        new PullTransport.OnAudioChunkPulledListener() {
                            @Override
                            public void onAudioChunkPulled(AudioChunk audioChunk) {
                                animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                            }
                        },
                        new WriteAction.Default(),
                        new Recorder.OnSilenceListener() {
                            @Override
                            public void onSilence(long silenceTime) {
                                Log.e("silenceTime", String.valueOf(silenceTime));
                              /*  Toast.makeText(WavRecorderActivity.this, "silence of " + silenceTime + " detected",
                                        Toast.LENGTH_SHORT).show();*/
                            }
                        }, 200
                ), file()
        );
    }

    private void animateVoice(final float maxPeak) {
        // recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }

    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 44100
                )
        );
    }

    @NonNull
    private File file() {
        File file = new File(Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, "录音" + number + ".wav");
    }

    /**
     * 开始录音
     */
    private void startAudio() {
        isRecorder = true;
        Fr_takeRecorder.setEnabled(false);
        recorder.startRecording();
        Fr_takeRecorder.postDelayed(new Runnable() {
            @Override
            public void run() {

                updateUi(2);
                type = 4;
                Fr_takeRecorder.setEnabled(true);
            }
        }, 1000);

    }

    /**
     * 停止录音
     */
    private void StopAudio() {
        Fr_takeRecorder.setEnabled(false);
        isRecorder = false;
        try {
            recorder.stopRecording();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Fr_takeRecorder.postDelayed(new Runnable() {
            @Override
            public void run() {

                p.getAudio(ht_projectId, _mActivity, number, ht_id);
                updateUi(1);
                type = 1;
                Fr_takeRecorder.setEnabled(true);
            }
        }, 1500);
    }

    /**
     * 录音停止
     * 弹窗
     *
     * @param title
     * @param Content
     */
    private void showStopAudioDialog(String title, String Content) {
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
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                StopAudio();
            }
        }).build().show();
    }

    /**
     * 录音删除
     * 弹窗
     *
     * @param title
     * @param Content
     */
    private void showDeleteAudioDialog(String title, String Content) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("删除")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                deleteAudio(audioPath);
                dialog.dismiss();
            }
        }).build().show();
    }

    /**
     * 回调
     *
     * @param projectsDB
     * @param subjectsDB
     * @param recorderPath
     * @param picList
     * @param text
     */
    private void callbackForServer(final ProjectsDB projectsDB, final SubjectsDB subjectsDB, String recorderPath, List<String> picList, String text) {
        final Map map = new HashMap();
        map.put("projectId", projectsDB.getPid());
        map.put("censor",subjectsDB.getCensor());
        map.put("answerId", subjectsDB.getHt_id());
        map.put("number", subjectsDB.getNumber());
        map.put("stage", projectsDB.getStage());
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
        map.put("audioCount", recorderPath != null ? 1 : 0);
        map.put("audio", recorderPath != null ? FileIOUtils.getE(recorderPath, "/") : "");
        map.put("pictureCount", picList.size());

        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < picList.size(); i++) {
            if (picList.size() == 1) {
                stringBuffer.append(FileIOUtils.getE(picList.get(i), "/"));
            } else {
                if (i == (picList.size() - 1)) {
                    stringBuffer.append(FileIOUtils.getE(picList.get(i), "/"));
                } else {
                    stringBuffer.append(FileIOUtils.getE(picList.get(i), "/") + ";");
                }
            }
        }
        map.put("picture", stringBuffer.toString());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                p.CallBack(map, projectsDB, subjectsDB);
            }
        });

    }


    /**
     * 上传进度回调
     */
    private void showProgressDialog() {
        new MaterialDialog.Builder(_mActivity)
                .customView(inflate, false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
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

                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 网络类型提示
     *
     * @param title
     * @param Content
     * @param type
     */
    private void NetWorkDialog(String title, String Content, final int type) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("确认")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        fr_upload.setEnabled(true);
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //获取阿里云上传实例
                        if (type == 1 || type == 2) {
                            getSTS_OSS();
                        }
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 删除录音
     *
     * @param audioPath
     */
    private void deleteAudio(String audioPath) {
        FileIOUtils.deleteFile(audioPath);
        getSubjectDetail();
    }

    /**
     * OSS对象实例
     */
    private void getSTS_OSS() {
        /**
         * 获取密钥
         */
        if (ossClient == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OSSAuthCredentialsProvider ossAuthCredentialsProvider = new OSSAuthCredentialsProvider(ApiContact.STSSERVER);
                    ClientConfiguration conf = new ClientConfiguration();
                    conf.setConnectionTimeout(10 * 1000); // 连接超时，默认15秒
                    conf.setSocketTimeout(10 * 1000); // socket超时，默认15秒
                    conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                    conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                    ossClient = new OSSClient(BaseApplication.mContext, ApiContact.endpoint, ossAuthCredentialsProvider, conf);
                    mHandler.sendEmptyMessage(GETUPLOADLIST);
                }
            }).start();
        } else {
            mHandler.sendEmptyMessage(GETUPLOADLIST);
        }
    }

    /**
     * 修改数据库状态为完成
     */
    private void UpdateDB(){
        SubjectsDB subjectsDB = new SubjectsDB();
        subjectsDB.setIsComplete(1);
        subjectsDB.update(subjectsDB_now.getId());
    }
}

