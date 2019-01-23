package com.coahr.thoughtrui.mvp.view.reviewed;

import android.Manifest;
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

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.KeyBoardUtils;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.ReViewStart_C;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.ReViewStart_P;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.startProject.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.DialogFragmentAudioPlay;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.coahr.thoughtrui.widgets.AltDialog.ProjectSuccessDialog;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.async.UpdateOrDeleteExecutor;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
 * 描述：
 */
public class ReViewStart extends BaseChildFragment<ReViewStart_C.Presenter> implements ReViewStart_C.View, View.OnClickListener {
    @Inject
    ReViewStart_P p;
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
    EditText ed_score;    //填写得分
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
    private SpacesItemDecoration spacesItemDecoration;
    private PagerFragmentPhotoAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private String answers;
    private String remark;
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    //评论输入窗口
    EvaluateInputDialogFragment dialogFragment = EvaluateInputDialogFragment.newInstance();
    //是否处于删除
    private boolean isDeletePic;
    private boolean isPhotos;
    private int imageSize;
    private boolean isAnswer;
    private boolean isRecorder;
    private String audioPath;
    private boolean isHaveRecorder;
    private int type = 1;
    private String audioName;
    private SubjectsDB subjectsDB_now;
    private int subjectsDBType;
    private int position;

    public static ReViewStart newInstance(int position, String DbProjectId, String ht_ProjectId, int countSize, String ht_id) {
        ReViewStart reViewStart = new ReViewStart();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("DbProjectId", DbProjectId);
        bundle.putString("ht_ProjectId", ht_ProjectId);
        bundle.putInt("countSize", countSize);
        bundle.putString("ht_id", ht_id);
        reViewStart.setArguments(bundle);
        return reViewStart;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spacesItemDecoration = new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 2), DensityUtils.dp2px(BaseApplication.mContext, 2), getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public ReViewStart_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_review_project;
    }

    @Override
    public void initView() {
        setupRecorder();
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
        }
        adapter = new PagerFragmentPhotoAdapter();
        gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 5);
        img_recycler.setLayoutManager(gridLayoutManager);
        img_recycler.setAdapter(adapter);
        img_recycler.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 4), DensityUtils.dp2px(BaseApplication.mContext, 4), getResources().getColor(R.color.colorWhite)));
        for (int i = 0; i < img_recycler.getItemDecorationCount(); i++) {
            if (i != 0) {
                img_recycler.removeItemDecorationAt(i);
            }
        }
        tv_Unfold.setOnClickListener(this);
        tv_last.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        Fr_takePhoto.setOnClickListener(this);
        tv_bianji.setOnClickListener(this);
        tv_play_recorder.setOnClickListener(this);
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
        //录音控制
        Fr_takeRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHaveRecorder) {  //有无录音

                } else {
                    if (type == 1) { //开始录音
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
        ed_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_score.setFocusable(true);
                ed_score.setFocusableInTouchMode(true);
            }
        });
        ed_score.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //发送键
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    if (v.getText() != null && !v.getText().equals("")) {
                        p.saveAnswers(v.getText().toString(), remark, ht_projectId, number, ht_id);
                    }
                }
                return true;
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
    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
        this.subjectsDB_now = subjectsDB;
        this.subjectsDBType = subjectsDB.getType();
        if (subjectsDB != null) {
            //题目
            project_detail_titlle.setText(subjectsDB.getTitle());
            if (subjectsDB.getType()==0) {  //判断
                re_score.setVisibility(View.GONE);
                rg_gr.setVisibility(View.VISIBLE);
            }

            if (subjectsDB.getType()==1){  //填空题
                re_score.setVisibility(View.VISIBLE);
                rg_gr.setVisibility(View.GONE);
                tv_standard_score.setText("标准分数："+subjectsDB.getOptions());

            }
            p.getAnswer(ht_projectId,_mActivity,number,ht_id);
            p.getImage(ht_projectId,_mActivity,number,ht_id);
            p.getAudio(ht_projectId,_mActivity,number,ht_id);
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
            if (subjectsDBType==0) { //判断题
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

            if (subjectsDBType==1) {    //填空题
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
        ToastUtils.showLong("图片删除失败");
    }

    @Override
    public void saveAnswersSuccess() {
        ed_score.setFocusable(false);
        ed_score.setFocusableInTouchMode(false);
        KeyBoardUtils.hideKeybord(ed_score,_mActivity);

        p.getAnswer(ht_projectId, _mActivity, number, ht_id);
        ToastUtils.showLong("答案保存成功");
    }

    @Override
    public void saveAnswersFailure() {

        ToastUtils.showShort("答案保存失败");
    }

    @Override
    public void SaveImagesSuccess() {
        _mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                p.getImage(ht_projectId, _mActivity, number, ht_id);
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
            if (audioList != null && audioList.size() > 0) {
                audioPath = audioList.get(0);
                audioName = FileIOUtils.getE(audioPath, "/");
                isHaveRecorder = true;
                updateUi(4); //播放录音
                tv_recorder_name.setText(audioName);
                tv_recorder_name.setTextColor(getResources().getColor(R.color.origin_3));
            }
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
                                Log.e("silenceTime", String.valueOf(silenceTime));
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

    //==========================================点击监听====================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //上一题
            case R.id.tv_last:
                if (position >0) {
                    if (isComplete()) {
                        SubjectsDB subjectsDB = new SubjectsDB();
                        subjectsDB.setIsComplete(1);
                        UpdateOrDeleteExecutor updateOrDeleteExecutor = subjectsDB.updateAsync(subjectsDB_now.getId());
                        updateOrDeleteExecutor.listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                if (rowsAffected == 1) {
                                    EventBus.getDefault().postSticky(new isCompleteBean(true, number - 1, 1));
                                }
                            }
                        });
                        //  EventBus.getDefault().post(new isCompleteBean(true, number-1, 1));
                    } else {
                        ToastUtils.showLong("当前题目未完成");
                    }
                } else {
                    ToastUtils.showLong("已经是第一题");
                }
                break;
            //下一题
            case R.id.tv_next:
                if (position <countSize-1) {
                    if (isComplete()) {
                        SubjectsDB subjectsDB = new SubjectsDB();
                        subjectsDB.setIsComplete(1);
                        UpdateOrDeleteExecutor updateOrDeleteExecutor = subjectsDB.updateAsync(subjectsDB_now.getId());
                        updateOrDeleteExecutor.listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                if (rowsAffected == 1) {
                                    EventBus.getDefault().postSticky(new isCompleteBean(true, number + 1, 2));
                                }
                            }
                        });
                        // EventBus.getDefault().post(new isCompleteBean(true, number+1, 2));
                    } else {
                        ToastUtils.showLong("当前题目未完成");
                    }
                } else {
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
                    ToastUtils.showLong("已经是最后一题");
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
                Fr_takeRecorder.setEnabled(true);
                type = 1;
            }
        }, 1500);
    }
}
