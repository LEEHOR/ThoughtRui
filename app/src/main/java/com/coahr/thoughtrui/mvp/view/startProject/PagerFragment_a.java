package com.coahr.thoughtrui.mvp.view.startProject;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_recorderType;
import com.coahr.thoughtrui.mvp.model.Bean.PagePostEvent;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.PagerFragment_aP;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.recorder.AudioRecordListener;
import com.coahr.thoughtrui.mvp.view.recorder.RecorderService;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.AudioListenerComplete;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.async.UpdateOrDeleteExecutor;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.crypto.spec.DESedeKeySpec;
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

import static android.content.Context.BIND_AUTO_CREATE;

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
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    //评论输入窗口
    EvaluateInputDialogFragment dialogFragment = EvaluateInputDialogFragment.newInstance();
    //录音播放按钮Ui
    private Drawable imgs[] = {BaseApplication.mContext.getResources().getDrawable(R.mipmap.ico_recorder, null)
            , BaseApplication.mContext.getResources().getDrawable(R.mipmap.recordering, null)
            , BaseApplication.mContext.getResources().getDrawable(R.mipmap.recorder_play_w, null)};
    private Recorder recorder;
    private int type=1;
    private String audioName;
    private boolean isHavePermission=false;
    private SubjectsDB subjectsDB_now;

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
        if (getArguments() != null) {
            dbProjectId = getArguments().getString("DbProjectId");
            ht_projectId = getArguments().getString("ht_ProjectId");
            countSize = getArguments().getInt("countSize");
            ht_id = getArguments().getString("ht_id");
            List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectBy_Where(SubjectsDB.class, new String[]{"number"}, "ht_id=?", ht_id);
            if (subjectsDBS != null && subjectsDBS.size() > 0) {
                number = subjectsDBS.get(0).getNumber();
            }
        }
        adapter = new PagerFragmentPhotoAdapter();
        gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 5);
        img_recycler.setLayoutManager(gridLayoutManager);
        img_recycler.setAdapter(adapter);
        img_recycler.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext,4),DensityUtils.dp2px(BaseApplication.mContext,4),getResources().getColor(R.color.colorWhite)));
        for (int i = 0; i <img_recycler.getItemDecorationCount(); i++) {
            if (i!=0){
                img_recycler.removeItemDecorationAt(i);
            }
        }
        tv_Unfold.setOnClickListener(this);
        tv_last.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        Fr_takePhoto.setOnClickListener(this);
        tv_bianji.setOnClickListener(this);
        tv_play_recorder.setOnClickListener(this);
        setupRecorder();
        //录音控制
        Fr_takeRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHaveRecorder) {  //有无录音
                       ToastUtils.showLong("当前题目下有录音");
                } else {
                    if (getAudioPermission()) {
                    if (type == 1) { //开始录音
                        Fr_takeRecorder.setEnabled(false);
                        isRecorder = true;
                        recorder.startRecording();
                        updateUi(2);
                        Fr_takeRecorder.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                type = 4;
                                Fr_takeRecorder.setEnabled(true);
                            }
                        },1000);

                    } else if (type == 2) { //暂停录音

                    } else if (type == 3) { //继续录音

                    } else if (type == 4) {  //停止录音
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
                                Fr_takeRecorder.setEnabled(true);
                                type = 1;
                            }
                        }, 1500);

                    } else {

                    }
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
    }

    @Override
    public void initData() {
        getSubjectDetail();
        //单选监听
        rg_gr.setOnCheckedChangeListener(new RadioGroupListener());
        //图片监听
        adapter.setListener(new PagerAdapterListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //上一题
            case R.id.tv_last:
                if (number > 1) {
                    if (isComplete()) {
                        SubjectsDB subjectsDB=new SubjectsDB();
                        subjectsDB.setIsComplete(1);
                        UpdateOrDeleteExecutor updateOrDeleteExecutor = subjectsDB.updateAsync(subjectsDB_now.getId());
                        updateOrDeleteExecutor.listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                if (rowsAffected==1){
                                    EventBus.getDefault().postSticky(new isCompleteBean(true, number-1, 1));
                                }
                            }
                        });

                    } else {
                        ToastUtils.showLong("当前题目未完成");
                    }
                } else {
                    ToastUtils.showLong("已经是第一题");
                }
                break;
            //下一题
            case R.id.tv_next:
                if (number < countSize) {
                    if (isComplete()) {
                        SubjectsDB subjectsDB=new SubjectsDB();
                        subjectsDB.setIsComplete(1);
                        UpdateOrDeleteExecutor updateOrDeleteExecutor = subjectsDB.updateAsync(subjectsDB_now.getId());
                        updateOrDeleteExecutor.listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                if (rowsAffected==1){
                                    EventBus.getDefault().postSticky(new isCompleteBean(true, number+1, 2));
                                    ProjectSuccessDialog projectSuccessDialog = ProjectSuccessDialog.newInstance(ht_projectId);
                                    projectSuccessDialog.show(getChildFragmentManager(), TAG);
                                }
                            }
                        });

                    } else {
                        ToastUtils.showLong("当前题目未完成");
                    }
                } else {
                    if (isComplete()) {
                        SubjectsDB subjectsDB = new SubjectsDB();
                        subjectsDB.setIsComplete(1);
                        UpdateOrDeleteExecutor updateOrDeleteExecutor = subjectsDB.updateAsync(subjectsDB_now.getId());
                        updateOrDeleteExecutor.listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                if (rowsAffected == 1) {
                                    ProjectSuccessDialog projectSuccessDialog = ProjectSuccessDialog.newInstance(ht_projectId);
                                    projectSuccessDialog.show(getChildFragmentManager(), TAG);
                                }
                            }
                        });
                    } else {
                        ToastUtils.showLong("已经是最后一题");
                        ToastUtils.showLong("当前题目未完成");
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
                        DialogFragmentAudioPlay audioPlay=DialogFragmentAudioPlay.newInstance(audioPath,audioName);
                        audioPlay.show(getChildFragmentManager(),TAG);
                    } else {
                        ToastUtils.showLong("没有录音文件");
                    }
                } else {
                    ToastUtils.showLong("正在录音");
                }

                break;

        }

    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
       this.subjectsDB_now=subjectsDB;
        if (subjectsDB != null) {
            //题目
            project_detail_titlle.setText(subjectsDB.getTitle());
            //选项
            String options = subjectsDB.getOptions();
            if (options != null) {    //选项
                String[] split = options.split("&");
                if (split != null && split.length > 0) {
                    for (int i = 0; i < split.length; i++) {
                        if (i == 0) {
                            rb_yes.setText(split[0]);
                        }
                        if (i == 1) {
                            rb_no.setText(split[1]);
                        }
                    }
                }
            }
            //描述
            String description = subjectsDB.getDescription();
            if (description != null && !description.equals("")) {
                tv_describe.setText(description);
            }
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
        p.getAnswer(ht_projectId,_mActivity,number,ht_id);
        ToastUtils.showLong("答案保存成功");
    }

    @Override
    public void saveAnswersFailure() {
        ToastUtils.showLong("答案保存失败");
    }

    @Override
    public void SaveImagesSuccess() {
        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                p.getImage(ht_projectId,_mActivity,number,ht_id);
            }
        });

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

        }
    }

    @Override
    public void getAudioFailure(String failure) {
        isHaveRecorder = false;
        ToastUtils.showLong(failure+number);
        if (!isRecorder) {
            updateUi(1); //开始录音
            tv_recorder_name.setText("暂无录音");
            tv_recorder_name.setTextColor(getResources().getColor(R.color.material_grey_200));
        }
        //mHandler.sendEmptyMessage(RecorderType_1);
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
     * 动态获取录音权限
     */
    private boolean getAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                        @Override
                        public void PermissionSuccess(List<String> permissions) {
                           isHavePermission=true;
                            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            Toast.makeText(_mActivity, "获取权限失败", Toast.LENGTH_LONG).show();
                            isHavePermission=false;
                        }

                        @Override
                        public void PermissionHave() {
                            isHavePermission=true;
                            //EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
                        }
                    }, Manifest.permission.RECORD_AUDIO
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE);

        } else {
            isHavePermission=true;
            // EventBus.getDefault().postSticky(new EvenBus_recorderType(1, String.valueOf(number), Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number + "_" + ht_id));
        }
        return isHavePermission;
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
                    imgs[0].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext,15), DensityUtils.dp2px(BaseApplication.mContext,15));
                    this.tv_recorderBtn.setCompoundDrawables(imgs[0], null, null, null);
                }
                if (types == 2) {
                   this.tv_recorderBtn.setText("正在录音");
                    imgs[1].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext,15), DensityUtils.dp2px(BaseApplication.mContext,15));
                    this.tv_recorderBtn.setCompoundDrawables(imgs[1], null, null, null);
                }
                if (types == 4) {
                    this. tv_recorderBtn.setText("播放录音");
                    imgs[2].setBounds(0, 0, DensityUtils.dp2px(BaseApplication.mContext,15), DensityUtils.dp2px(BaseApplication.mContext,15));
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
                    @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
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
                            @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
                                animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                            }
                        },
                        new WriteAction.Default(),
                        new Recorder.OnSilenceListener() {
                            @Override public void onSilence(long silenceTime) {
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
        File file=new File(Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number+"_"+ht_id);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, "录音"+number+".wav");
    }
}
