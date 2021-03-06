package com.coahr.thoughtrui.mvp.view.reviewed;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.PreferenceUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment_not_padding;
import com.coahr.thoughtrui.mvp.constract.ReViewStartAnswering_C;
import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.ReViewStartAnswering_P;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.widgets.AltDialog.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.Fill_in_blankDialog;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

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
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：审核开始答题
 */
public class ReViewStartAnswering extends BaseFragment_not_padding<ReViewStartAnswering_C.Presenter> implements ReViewStartAnswering_C.View, View.OnClickListener {
    @Inject
    ReViewStartAnswering_P p;
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
    @BindView(R.id.tv_Unfold)
    TextView tv_Unfold; //图片展开收起
    @BindView(R.id.tv_recorder_name)
    TextView tv_recorder_name;  //录音文件名
    @BindView(R.id.tv_play_recorder)
    TextView tv_play_recorder;  //播放录音
    @BindView(R.id.img_recycler)
    RecyclerView img_recycler;  //图片列表
    @BindView(R.id.tv_last)
    TextView tv_last; //上一页
    @BindView(R.id.tv_next)
    TextView tv_next;  //下一页
    @BindView(R.id.fr_upload)
    FrameLayout fr_upload; //上传
    @BindView(R.id.iv_upload_tag)
    ImageView iv_upload_tag;
    private String dbProjectId;
    private String ht_projectId;
    private int countSize;
    private String ht_id;
    private int number;
    private Recorder recorder;
    //录音播放按钮Ui
    private Drawable imgs[] = {BaseApplication.mContext.getResources().getDrawable(R.mipmap.ico_recorder, null)
            , BaseApplication.mContext.getResources().getDrawable(R.mipmap.recordering, null)
            , BaseApplication.mContext.getResources().getDrawable(R.mipmap.recorder_play_w, null)};
    // private SpacesItemDecoration spacesItemDecoration;
    private PagerFragmentPhotoAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private String answers;
    private String remark;
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    //评论输入窗口
    EvaluateInputDialogFragment dialogFragment = EvaluateInputDialogFragment.newInstance(100);
    //是否处于删除
    private boolean isDeletePic;
    private boolean isPhotos;
    private int imageSize;
    private boolean isAnswer;
    //是否正在录音
    private boolean isRecorder;

    //是否有备注
    private boolean isDescribe;

    private String audioPath;
    private boolean isHaveRecorder;
    private int type = 1;
    private String audioName;
    private SubjectsDB subjectsDB_now;
    private int subjectsDBType;
    private int position;
    private OSSClient ossClient;

    //上传文件的数组
    private List<String> fileList_Call = new ArrayList<>();
    private View inflate;
    private TextView tv_tittle;
    private ProgressBar progressBar;
    private final int GETUPLOADLIST = 1;
    private final int UIPROGRESS = 2;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETUPLOADLIST:
                    p.UpLoadFileList(ht_projectId, subjectsDB_now);
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
    private String uPAudioPath;
    private String textMassage;
    private String standard_score;
    private Fill_in_blankDialog fill_in_blankDialog;
    private OSSAuthCredentialsProvider credentialProvider;

    public static ReViewStartAnswering newInstance(int position, String DbProjectId, String ht_ProjectId, int countSize, String ht_id) {
        ReViewStartAnswering reViewStartAnswering = new ReViewStartAnswering();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("DbProjectId", DbProjectId);
        bundle.putString("ht_ProjectId", ht_ProjectId);
        bundle.putInt("countSize", countSize);
        bundle.putString("ht_id", ht_id);
        reViewStartAnswering.setArguments(bundle);
        return reViewStartAnswering;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public ReViewStartAnswering_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_review_project;
    }

    @Override
    public void initView() {
        iv_upload_tag.setVisibility(View.VISIBLE);
        fill_in_blankDialog = Fill_in_blankDialog.newInstance();
        inflate = LayoutInflater.from(_mActivity).inflate(R.layout.dialog_progress, null);
        tv_tittle = inflate.findViewById(R.id.tv_progress_info);
        progressBar = inflate.findViewById(R.id.progress_bar);
        if (getArguments() != null) {
            dbProjectId = getArguments().getString("DbProjectId");
            ht_projectId = getArguments().getString("ht_ProjectId");
            countSize = getArguments().getInt("countSize");
            ht_id = getArguments().getString("ht_id");
            position = getArguments().getInt("position");
            List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectBy_Where(SubjectsDB.class, new String[]{"number"}, "ht_id=?", ht_id);
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                number = subjectsDBS.get(0).getNumber();
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
        //输入备注
        dialogFragment.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
            @Override
            public void onInputSend(String input, AppCompatDialogFragment dialog) {
                if (input != null && !input.equals("")) {
                    tv_bianji.setText(input);
                    p.saveAnswers(answers, input, ht_projectId, number, ht_id, 2);
                    dialogFragment.dismiss();
                }
            }
        });
        //录音控制
        Fr_takeRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHaveRecorder) {  //有无录音
                    showDeleteAudioDialog(getResources().getString(R.string.dialog_tittle_5), getResources().getString(R.string.dialog_content_3));
                } else {
                    if (type == 1) { //开始录音
                        setupRecorder();
                        startAudio();
                    } else if (type == 2) { //暂停录音

                    } else if (type == 3) { //继续录音

                    } else if (type == 4) {  //停止录音
                        stopAudio();
                    } else {

                    }
                }
            }

        });
        //输入分数
        ed_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fill_in_blankDialog.show(getFragmentManager(), TAG);
            }
        });
        fill_in_blankDialog.setOnClick(new Fill_in_blankDialog.InPutOnClick() {
            @Override
            public void setOnClick(String text) {
                if (text != null && standard_score != null) {
                    if (Integer.parseInt(text) <= Integer.parseInt(standard_score) && Integer.parseInt(text) >= 0) {
                        ed_score.setText(text);
                        p.saveAnswers(text, remark, ht_projectId, number, ht_id, 1);
                    } else {
                        ToastUtils.showLong(getResources().getString(R.string.toast_16));
                    }
                }
                // ed_score.setText(text);
                // p.saveAnswers(text, remark, ht_projectId, number, ht_id);
            }

            @Override
            public void setOnClickFailure() {
                ToastUtils.showLong(getResources().getString(R.string.toast_16));
            }
        });
        //图片的展开和关闭
        tv_Unfold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_Unfold.getTag() == null || tv_Unfold.getTag().equals("1")) { //展开
                    img_recycler.setVisibility(View.GONE);
                    tv_Unfold.setText(getResources().getString(R.string.re_start_spread));
                    tv_Unfold.setTag("2");
                } else if (tv_Unfold.getTag().equals("2")) {  //关闭
                    img_recycler.setVisibility(View.VISIBLE);
                    tv_Unfold.setText(getResources().getString(R.string.re_start_close));
                    tv_Unfold.setTag("1");
                } else if (tv_Unfold.getTag().equals("3")) {
                    tv_Unfold.setText(getResources().getString(R.string.re_start_close));
                    tv_Unfold.setTag("1");
                    isDeletePic = false;
                    p.getImage(ht_projectId, _mActivity, number, ht_id);
                }

            }
        });
    }

    @Override
    public void initData() {

        p.getSubject(dbProjectId, ht_projectId, _mActivity, number, ht_id);
        //单选监听
        rg_gr.setOnCheckedChangeListener(new RadioGroupListener());
        //图片监听
        adapter.setListener(new PagerAdapterListener());
        //
        ed_score.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
        this.subjectsDB_now = subjectsDB;
        this.subjectsDBType = subjectsDB.getType();
        if (subjectsDB != null) {
            //题目
            project_detail_titlle.setText(subjectsDB.getTitle());

            int i = subjectsDB.getsUploadStatus();
            if (i == 1) {
                iv_upload_tag.setImageResource(R.mipmap.uploaded);
            } else {
                iv_upload_tag.setImageResource(R.mipmap.not_uploaded);
            }
            if (subjectsDB.getType() == 0) {  //判断
                re_score.setVisibility(View.GONE);
                rg_gr.setVisibility(View.VISIBLE);
                String options = subjectsDB.getOptions();
                String[] split = options.split("&");
                if (split != null && split.length > 0) {
                    rb_yes.setText(split[0]);
                    rb_no.setText(split[1]);
                } else {
                    rb_yes.setText("0");
                    rb_no.setText("5");
                }

            }

            if (subjectsDB.getType() == 1) {  //填空题
                re_score.setVisibility(View.VISIBLE);
                rg_gr.setVisibility(View.GONE);
                standard_score = subjectsDB.getOptions();
                tv_standard_score.setText(getResources().getString(R.string.phrases_19) + subjectsDB.getOptions());

            }
            tv_describe.setText(getResources().getString(R.string.phrases_20) + subjectsDB.getDescription());
            p.getAnswer(ht_projectId, _mActivity, number, ht_id);
            p.getImage(ht_projectId, _mActivity, number, ht_id);
            p.getAudio(ht_projectId, _mActivity, number, ht_id);
        }
    }

    @Override
    public void getSubjectFailure(String failure) {
        ToastUtils.showLong(failure);
    }

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
        } else {
            isDeletePic = false;
            adapter.setIsDel(false);
            adapter.setNewData(imagePathList);
            adapter.setImageList(imagePathList);
        }
        UpdateDB();
    }

    @Override
    public void getImageFailure() {
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
                                if (string.equals(rb_yes.getText().toString())) {
                                    rb_yes.toggle();
                                }
                                if (string.equals(rb_no.getText().toString())) {
                                    rb_no.toggle();
                                }
                            }
                        }
                        if (i == 1) {
                            String s1 = split[1];
                            String string1 = SaveOrGetAnswers.getString(s1, ":");
                            if (string1 != null && !string1.equals("") && !string1.equals("null")) {
                                remark = string1;
                                isDescribe = true;
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
        UpdateDB();
    }

    @Override
    public void getAnswerFailure() {

    }

    @Override
    public void DeleteImageSuccess(String Massage) {
        ToastUtils.showLong(Massage);
        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                p.getImage(ht_projectId, _mActivity, number, ht_id);
            }
        });
    }

    @Override
    public void DeleteImageFailure(String Massage) {
        p.getAnswer(ht_projectId, _mActivity, number, ht_id);
        ToastUtils.showLong(Massage);
    }

    @Override
    public void saveAnswersSuccess(int type) {
        ed_score.setFocusable(false);
        ed_score.setFocusableInTouchMode(false);
        KeyBoardUtils.hideKeybord(ed_score, _mActivity);
        p.getAnswer(ht_projectId, _mActivity, number, ht_id);
        ToastUtils.showLong(getResources().getString(R.string.toast_22));
    }

    @Override
    public void saveAnswersFailure(int type) {
        if (type == 1) {
            isAnswer = false;
        } else {
            isDescribe = false;
        }
        ToastUtils.showShort(getResources().getString(R.string.toast_23));
    }

    @Override
    public void SaveImagesSuccess() {
        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                p.getImage(ht_projectId, _mActivity, number, ht_id);
            }
        });
        ToastUtils.showShort(getResources().getString(R.string.toast_22));
    }

    @Override
    public void SaveImagesFailure() {
        ToastUtils.showShort(getResources().getString(R.string.toast_23));
    }

    @Override
    public void getAudioSuccess(List<String> audioList) {
        if (!isRecorder) {
            if (audioList != null && audioList.size() > 0) {
                audioPath = audioList.get(0);
                audioName = FileIOUtils.getE(audioPath, "/");
                isHaveRecorder = true;
                updateUi(4); //播放录音
                tv_recorder_name.setText(audioName);
                tv_recorder_name.setTextColor(getResources().getColor(R.color.origin_3));
                //UpdateDB();
            }
        }
    }

    @Override
    public void getAudioFailure(String failure) {

        isHaveRecorder = false;
        if (!isRecorder) {
            updateUi(1); //开始录音
            tv_recorder_name.setText(getResources().getString(R.string.phrases_17));
            tv_recorder_name.setTextColor(getResources().getColor(R.color.material_grey_200));
        }

      /*  isHaveRecorder = false;
        ToastUtils.showLong(failure + number);
        if (!isRecorder) {
            updateUi(1); //开始录音
            tv_recorder_name.setText(getResources().getString(R.string.phrases_17));
            tv_recorder_name.setTextColor(getResources().getColor(R.color.material_grey_200));
        }*/
    }

    @Override
    public void getUpLoadFileListSuccess(List<String> list, String projectsDB_id, SubjectsDB subjectsDB) {
        showProgressDialog();
        p.startUpload(ossClient, list, projectsDB, subjectsDB);
    }

    @Override
    public void getUpLoadFileListFailure(String failure) {
        fr_upload.setEnabled(true);
        ToastUtils.showLong(failure);
    }

    @Override
    public void getOssSuccess(AliyunOss aliyunOss) {
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.AK_KEY, aliyunOss.getAccessKeyId());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.SK_KEY, aliyunOss.getAccessKeySecret());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.STOKEN_KEY, aliyunOss.getSecurityToken());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.BUCKET_KEY, aliyunOss.getBucket());
        PreferenceUtils.setPrefLong(BaseApplication.mContext, Constants.Expiration_KEY, aliyunOss.getExpiration());
        PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.ENDPOINT_KEY, "http://" + aliyunOss.getRegion()+".aliyuncs.com");
        Constants.AK=aliyunOss.getAccessKeyId();
        Constants.SK=aliyunOss.getAccessKeySecret();
        Constants.STOKEN=aliyunOss.getSecurityToken();
        Constants.Expiration=aliyunOss.getExpiration();
        Constants.BUCKET=aliyunOss.getBucket();
        Constants.ENDPOINT="http://" + aliyunOss.getRegion()+".aliyuncs.com";

        getSTS_OSS(aliyunOss.getAccessKeyId(),aliyunOss.getAccessKeySecret(),aliyunOss.getSecurityToken(),"http://" + aliyunOss.getRegion()+".aliyuncs.com");

    }

    @Override
    public void getOssFailure(int statusCode) {

    }

    @Override
    public void startUploadCallBack(List<String> list, int uploadSuccessSize, int uploadFailSize, int totalSize, ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        KLog.a("上传", totalSize, uploadFailSize, uploadSuccessSize);
        fileList_Call.clear();
        uPAudioPath = null;
        textMassage = null;
        //上传成功
        if (totalSize == (uploadSuccessSize + uploadFailSize)) {

            if (totalSize == uploadSuccessSize) {
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
    public void Up_Pic_Compulsory(ProjectsDB projectsDB, SubjectsDB subjectsDB, List<String> list) {
        fileList_Call.clear();
        uPAudioPath = null;
        textMassage = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).endsWith(".txt")) {
                    textMassage = SaveOrGetAnswers.readFromFile(list.get(i));
                }
            }
        }
        callbackForServer(projectsDB, subjectsDB, uPAudioPath, fileList_Call, textMassage);

    }

    @Override
    public void showProgress(int currentSize, int totalSize, String info) {
       /* if (currentSize > 100) {
            currentSize = 100;
        } else if (currentSize < 0) {
            currentSize = 0;
        }*/
        Message mes = mHandler.obtainMessage(UIPROGRESS, info);
        mes.arg1 = currentSize;
        mes.arg2 = totalSize;
        mes.sendToTarget();
    }

    @Override
    public void CallBackSuccess(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        p.UpDataDb(projectsDB, subjectsDB);
    }

    @Override
    public void CallBackFailure(ProjectsDB projectsDB, SubjectsDB subjectsDB) {
        fr_upload.setEnabled(true);
    }

    @Override
    public void UpDataDbSuccess() {
        fr_upload.setEnabled(true);
        ToastUtils.showLong(getResources().getString(R.string.toast_33));
        iv_upload_tag.setImageResource(R.mipmap.uploaded);
    }

    @Override
    public void UpDataDbFailure(String fail) {
        ToastUtils.showLong(fail);
        fr_upload.setEnabled(true);
        iv_upload_tag.setImageResource(R.mipmap.not_uploaded);

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
        map.put("answerId", subjectsDB.getHt_id());
        map.put("number", subjectsDB.getNumber());
        map.put("stage", subjectsDB.getStage());
        if (text != null) {
            String[] split = text.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        String s = split[0];
                        String string = SaveOrGetAnswers.getString(s, ":");
                        if (string != null && !string.equals("") && !string.equals("null")) {
                            map.put("answer", string);
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
            map.put("anwser", "");
            map.put("description", "");
        }
        map.put("audioCount", recorderPath != null ? 0 : 1);
        map.put("audio", recorderPath != null ? subjectsDB_now.getNumber()+".wav" : "");

        if (picList != null && picList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < picList.size(); i++) {
                if (picList.size() == 1) {
                    stringBuffer.append(FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("jpg") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".jpg"
                            : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("png") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".png"
                            : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("gif") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".gif"
                            : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("jpeg") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".jpeg"
                            : subjectsDB_now.getNumber() + "_" + (i + 1) + ".png");
                } else {
                    if (i == (picList.size() - 1)) {
                        stringBuffer.append(FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("jpg") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".jpg"
                                : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("png") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".png"
                                : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("gif") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".gif"
                                : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("jpeg") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".jpeg"
                                : subjectsDB_now.getNumber() + "_" + (i + 1) + ".png");
                    } else {
                        stringBuffer.append(FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("jpg") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".jpg" + ";"
                                : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("png") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".png" + ";"
                                : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("gif") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".gif" + ";"
                                : FileIOUtils.getE(picList.get(i), ".").toLowerCase().equals("jpeg") ? subjectsDB_now.getNumber() + "_" + (i + 1) + ".jpeg" + ";"
                                : subjectsDB_now.getNumber() + "_" + (i + 1) + ".png" + ";");
                    }
                }
            }
            map.put("pictureCount", picList.size());
            map.put("picture", stringBuffer.toString());
        } else {
            map.put("pictureCount", 0);
            map.put("picture", "");
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                p.CallBack(map, projectsDB, subjectsDB);
            }
        });

    }
//=========================================录音=====================================================

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
                            }
                        }, 200
                ), file()
        );
    }

    private void animateVoice(final float maxPeak) {
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
        File file = new File(Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id + "/" + "audio" );
//        File file = new File(Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, "audio" + number + ".wav");
    }

    //==========================================点击监听====================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //上一题
            case R.id.tv_last:
                if (position > 0) {
                    if (!isRecorder) {
                        if (UpdateDB()) {
                            EventBus.getDefault().postSticky(new isCompleteBean(true, number - 1, 1, 2));
                        } else {
                            EventBus.getDefault().postSticky(new isCompleteBean(true, number - 1, 1, 2));
                        }

                    } else {
                        showDialog(getResources().getString(R.string.dialog_tittle_6), getResources().getString(R.string.dialog_content_4));
                    }

                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_17));
                }
                break;
            //下一题
            case R.id.tv_next:
                if (position < countSize - 1) {
                    if (!isRecorder) {
                        if (UpdateDB()) {
                            EventBus.getDefault().postSticky(new isCompleteBean(true, number + 1, 2, 2));
                        }

                    } else {
                        showDialog(getResources().getString(R.string.dialog_tittle_6), getResources().getString(R.string.dialog_content_4));
                    }

                } else {
                    if (!isRecorder) {
                        if (UpdateDB()) {
                            ProjectSuccessDialog projectSuccessDialog = ProjectSuccessDialog.newInstance(ht_projectId);
                            projectSuccessDialog.show(getChildFragmentManager(), TAG);
                        }

                    } else {
                        showDialog(getResources().getString(R.string.dialog_tittle_6), getResources().getString(R.string.dialog_content_4));
                    }

                }
                break;
            //拍照
            case R.id.Fr_takePhoto:
                if (imageSize < 10) {
                    openMulti((10 - imageSize));
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_19));
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
                        ToastUtils.showLong(getResources().getString(R.string.toast_15));
                    }
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_20));
                }
                break;
            case R.id.fr_upload:
                fr_upload.setEnabled(false);
                if (Constants.isNetWorkConnect) {
                    if (Constants.NetWorkType != null && Constants.NetWorkType.equals("WIFI")) {
                        NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_5), 1);
                    } else if (Constants.NetWorkType != null && Constants.NetWorkType.equals("MOBILE")) {
                        NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_6), 2);
                    }
                } else {
                    NetWorkDialog(getResources().getString(R.string.dialog_tittle_7), getResources().getString(R.string.dialog_content_7), 3);
                }
                break;

        }
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
     * 单选监听
     */
    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton button = rg_gr.findViewById(i);

            if (!button.isPressed()) {
                return;
            }
            if (rb_yes.isChecked()) {
                answers = rb_yes.getText().toString();
            }
            if (rb_no.isChecked()) {
                answers = rb_no.getText().toString();
            }
            p.saveAnswers(answers, remark, ht_projectId, number, ht_id, 1);
        }
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
            tv_Unfold.setText(getResources().getString(R.string.re_start_cancel));
            tv_Unfold.setTag("3");
        }

        @Override
        public void onDel(List<String> imageList, int position) {
            //删除
            isDeletePic = true;
            p.DeleteImage(imageList.get(position));
        }
    }

    /**
     * UI更新
     *
     * @param types;
     */
    private void updateUi(final int types) {
        if (types == 1) {
            this.tv_recorderBtn.setText(getResources().getString(R.string.phrases_21));
            imgs[0].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext, 15), DensityUtils.dp2px(BaseApplication.mContext, 15));
            this.tv_recorderBtn.setCompoundDrawables(imgs[0], null, null, null);
        }
        if (types == 2) {
            this.tv_recorderBtn.setText(getResources().getString(R.string.phrases_22));
            imgs[1].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext, 15), DensityUtils.dp2px(BaseApplication.mContext, 15));
            this.tv_recorderBtn.setCompoundDrawables(imgs[1], null, null, null);
        }
        if (types == 4) {
            this.tv_recorderBtn.setText(getResources().getString(R.string.phrases_23));
            imgs[2].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext, 15), DensityUtils.dp2px(BaseApplication.mContext, 15));
            this.tv_recorderBtn.setCompoundDrawables(imgs[2], null, null, null);
        }
    }


    /**
     * 开始录音
     */
    private void startAudio() {
        Fr_takeRecorder.setEnabled(false);
        isRecorder = true;
        recorder.startRecording();
        Fr_takeRecorder.postDelayed(new Runnable() {
            @Override
            public void run() {
                type = 4;
                updateUi(2);
                Fr_takeRecorder.setEnabled(true);
            }
        }, 1000);
    }

    /**
     * 关闭录音
     */
    private void stopAudio() {
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
     * 录音停止弹窗
     *
     * @param title
     * @param Content
     */
    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                stopAudio();
            }
        }).build().show();
    }

    /**
     * OSS对象实例
     */
    private void getSTS_OSS(String ak,String sk,String stoken,String endpoint) {

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

                mHandler.sendEmptyMessage(GETUPLOADLIST);
            }
        }).start();

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
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
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
                            //判断令牌有无过期
                            if (Constants.Expiration>System.currentTimeMillis()){
                                getSTS_OSS(Constants.AK,Constants.SK,Constants.STOKEN,Constants.ENDPOINT);
                            } else {
                                getAliyunOss();
                            }
                        }
                        dialog.dismiss();
                    }
                }).build().show();
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
     * 修改数据库状态为完成
     */
    private boolean UpdateDB() {
        if (subjectsDB_now.getPhotoStatus() == 1
                && subjectsDB_now.getDescribeStatus() == 1
                && subjectsDB_now.getRecordStatus() == 1) {
            if (isPhotos && isAnswer && isDescribe && isHaveRecorder) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isDescribe) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_27));
                }
                if (!isPhotos) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                }
                if (!isHaveRecorder) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_29));
                }
                return false;
            }
        } else if (subjectsDB_now.getPhotoStatus() == -1
                && subjectsDB_now.getDescribeStatus() == 1
                && subjectsDB_now.getRecordStatus() == 1) {
            if (isAnswer && isDescribe && isHaveRecorder) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());

                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isDescribe) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_27));
                }
                if (!isHaveRecorder) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_29));
                }
                return false;
            }

        } else if (subjectsDB_now.getPhotoStatus() == 1
                && subjectsDB_now.getDescribeStatus() == -1
                && subjectsDB_now.getRecordStatus() == 1) {
            if (isAnswer && isPhotos && isHaveRecorder) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isPhotos) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                }
                if (!isHaveRecorder) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_29));
                }
                return false;
            }
        } else if (subjectsDB_now.getPhotoStatus() == 1
                && subjectsDB_now.getDescribeStatus() == 1
                && subjectsDB_now.getRecordStatus() == -1) {
            if (isAnswer && isDescribe && isPhotos) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isPhotos) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                }
                if (!isDescribe) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_27));
                }
                return false;
            }
        } else if (subjectsDB_now.getPhotoStatus() == 1
                && subjectsDB_now.getDescribeStatus() == -1
                && subjectsDB_now.getRecordStatus() == -1) {
            if (isAnswer && isPhotos) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isPhotos) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_28));
                }

                return false;
            }
        } else if (subjectsDB_now.getPhotoStatus() == -1
                && subjectsDB_now.getDescribeStatus() == -1
                && subjectsDB_now.getRecordStatus() == 1) {
            if (isAnswer && isHaveRecorder) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isHaveRecorder) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_29));
                }
                return false;
            }

        } else if (subjectsDB_now.getPhotoStatus() == -1
                && subjectsDB_now.getDescribeStatus() == 1
                && subjectsDB_now.getRecordStatus() == -1) {
            if (isAnswer && isHaveRecorder) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                if (!isDescribe) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_27));
                }
                return false;
            }
        } else if (subjectsDB_now.getPhotoStatus() == -1
                && subjectsDB_now.getDescribeStatus() == -1
                && subjectsDB_now.getRecordStatus() == -1) {
            if (isAnswer) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                return false;
            }
        } else {
            if (isAnswer) {
                SubjectsDB subjectsDB = new SubjectsDB();
                subjectsDB.setIsComplete(1);
                subjectsDB.update(subjectsDB_now.getId());
                return true;
            } else {
                if (!isAnswer) {
                    ToastUtils.showLong(getResources().getString(R.string.toast_26));
                }
                return false;
            }
        }
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
                .negativeText(getResources().getString(R.string.cancel))
                .positiveText(getResources().getString(R.string.resume))
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
     * 删除录音
     *
     * @param audioPath
     */
    private void deleteAudio(String audioPath) {
        FileIOUtils.deleteFile(audioPath);
        p.getSubject(dbProjectId, ht_projectId, _mActivity, number, ht_id);
    }

    /**
     * 获取阿里云Oss
     */
    private void getAliyunOss() {
        Map map = new HashMap();
        p.getOss(map);
    }
}
