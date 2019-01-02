package com.coahr.thoughtrui.mvp.view;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.AnimationUtil;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.NetWorkAvailable;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseActivity;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseSupportActivity;
import com.coahr.thoughtrui.mvp.constract.StartProjectActivity_C;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_recorderType;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.StartProjectActivity_P;
import com.coahr.thoughtrui.mvp.view.startProject.Fragment_Topics;
import com.coahr.thoughtrui.mvp.view.startProject.PagerController;
import com.coahr.thoughtrui.mvp.view.startProject.StartProjectFragment;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.AudioListenerComplete;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.StartProjectAdapter;
import com.coahr.thoughtrui.widgets.CustomScrollViewPager;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.example.hd.cuterecorder.AudioManger;
import com.example.hd.cuterecorder.CuteRecorder;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/17
 * on 8:28
 * 开始访问页面
 */
public class StartProjectActivity extends BaseActivity<StartProjectActivity_C.Presenter> implements StartProjectActivity_C.View,View.OnClickListener {
    @Inject
    StartProjectActivity_P p;
    @BindView(R.id.project_viewPage)
    CustomScrollViewPager project_viewPage;
    @BindView(R.id.p_mytitle)
    MyTittleBar p_mytitle;
    @BindView(R.id.recorder_model)
    View recorder_model;

    private PagerController pagerController;
    private StartProjectAdapter startProjectAdapter;
    private int subject_size; //题目个数
    private TextView recorder_time;
    private TextView tv_start_recorder;
    private TextView tv_stop_recorder;
    private CuteRecorder cuteRecorder;
    private Drawable start_recorder_drawable;
    private Drawable stop_recorder_drawable;
    private Drawable stoping_recorder_drawable;
    private Drawable pause_recorder_drawable;
    private String audioSavePath;
    private String audioTemSavePath;
    private String recorderName;
    private final int RECORDER_START=1;
    private final int RECORDER_PAUSE=2;
    private final int RECORDER_RESUME=3;
    private final int RECORDER_STOP=4;
    private int recorder_type;
    public static AudioListenerComplete audioListenerComplete;
    @Override
    public StartProjectActivity_C.Presenter getPresenter() {
        return p;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    public int binLayout() {
        return R.layout.activity_startproject;
    }

    @Override
    public void initView() {
        start_recorder_drawable = BaseApplication.mContext.getResources().getDrawable(R.drawable.audio_start, null);
        stop_recorder_drawable = BaseApplication.mContext.getResources().getDrawable(R.drawable.audio_stop, null);
        stoping_recorder_drawable = BaseApplication.mContext.getResources().getDrawable(R.drawable.audio_stoping, null);
        pause_recorder_drawable = BaseApplication.mContext.getResources().getDrawable(R.drawable.audio_pause, null);
        Rect rect = new Rect(10, 10, 10, 10);
        start_recorder_drawable.setBounds(rect);
        stop_recorder_drawable.setBounds(rect);
        pause_recorder_drawable.setBounds(rect);
        recorder_model.setVisibility(View.GONE);
        recorder_time = recorder_model.findViewById(R.id.tv_recorderTime);
        tv_start_recorder = recorder_model.findViewById(R.id.tv_start_recorder);
        tv_stop_recorder = recorder_model.findViewById(R.id.tv_stop_recorder);
        tv_start_recorder.setOnClickListener(this);
        tv_stop_recorder.setOnClickListener(this);
        project_viewPage.setScrollable(false);
        p_mytitle.setPadding(p_mytitle.getPaddingLeft(),
                ScreenUtils.getStatusBarHeight(BaseApplication.mContext),
                p_mytitle.getPaddingRight(), p_mytitle.getPaddingBottom());
        p_mytitle.getRightText().setVisibility(View.VISIBLE);
        p_mytitle.getRightText().setText("题目列表");
        p_mytitle.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartProjectActivity.this,ConstantsActivity.class);
                intent.putExtra("to",Constants.fragment_topics);
                startActivity(intent);
            }
        });
        p_mytitle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("提示", "退出答题");
            }
        });
        p_mytitle.getTvTittle().setText("第" + 1 + "题");

        //一个小时
//输出录音文件路径
//最大音量
        cuteRecorder = new CuteRecorder.Builder()
                .maxTime(60 * 60)//一个小时
                .minTime(3)
                .outPutDir(Constants.SAVE_DIR_VOICE) //输出录音文件路径
                .voiceLevel(CuteRecorder.NORMAL)//最大音量
                .build();
        cuteRecorder.setOnAudioRecordListener(new recorderListener());
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
    public void showError(@Nullable Throwable e) {
        super.showError(e);
    }

    private boolean getNetWork() {
        boolean networkAvailable = NetWorkAvailable.isNetworkAvailable(BaseApplication.mContext);
        return networkAvailable;
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

    @Override
    public void getOfflineSuccess(int size, String dbProjectId, String ht_projectId) {
        this.subject_size = size;
        startProjectAdapter = new StartProjectAdapter(getSupportFragmentManager(),size,dbProjectId,ht_projectId,Constants.name_Project);
        project_viewPage.setAdapter(startProjectAdapter);
        project_viewPage.setCurrentItem(0);
    }

    @Override
    public void getOfflineFailure(int failure) {
        ToastUtils.showLong("没有本地题目");
    }
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", Constants.ht_ProjectId);
        p.getMainData(map);
    }
    @Override
    public void onBackPressedSupport() {
        showDialog("提示","退出答题");
    }

    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(this)
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
                finish();
            }
        }).build().show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 翻页获取返回询问数据
     *
     * @param isCompleteBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(isCompleteBean isCompleteBean) {
        int isposition = isCompleteBean.getPosition();
        int isupOrDown = isCompleteBean.getUpOrDown();
        boolean complete = isCompleteBean.isComplete();

        if (complete){
            if (isupOrDown == 1){  //上翻页
                KLog.d("上翻页"+isposition);
                project_viewPage.setCurrentItem(isposition-=1,true);
                if (isposition==0){
                    p_mytitle.getTvTittle().setText("第"+(1)+"题");
                } else {
                    p_mytitle.getTvTittle().setText("第"+(isposition)+"题");
                }

            }
            if (isupOrDown == 2){
                KLog.d("下翻页"+isposition);
                project_viewPage.setCurrentItem(isposition+=1);
                if (isposition==1){
                    p_mytitle.getTvTittle().setText("第"+(2)+"题");
                } else {
                    p_mytitle.getTvTittle().setText("第"+(isposition+1)+"题");
                }

            }
        } else {
            ToastUtils.showLong("当前题目未完成");
        }
    }

    /**
     * 录音控制
     *
     * @param evenBus_recorderType
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event_recorder(EvenBus_recorderType evenBus_recorderType) {
       int type = evenBus_recorderType.getType();
        audioSavePath = evenBus_recorderType.getAudioSavePath();
        audioTemSavePath = evenBus_recorderType.getAudioTemSavePath();
        recorderName = evenBus_recorderType.getRecorderName();
        updateUi(type);
    }

    /**
     * 更新录音图标
     *
     * @param type
     */
    private void updateUi(final int type) {
        runOnUiThread(new Runnable() {
            private Intent intent;

            @Override
            public void run() {
                if (type == 1) { //开始录音
                    AnimationUtil.topMoveToViewLocation_Visible(recorder_model, 300);
                    //intent = new Intent(StartProjectActivity.this, AudioManger.class);
                    //开启服务
                  //  startService(intent);
                    tv_start_recorder.setCompoundDrawables(pause_recorder_drawable, null, null, null);
                    tv_stop_recorder.setCompoundDrawables(stoping_recorder_drawable, null, null, null);
                    tv_start_recorder.setText("暂停录音");
                    cuteRecorder.setAudioName(recorderName);
                    cuteRecorder.setOutAudioPath(audioSavePath);
                    cuteRecorder.setOutTemPath(audioTemSavePath);
                    cuteRecorder.start(StartProjectActivity.this);
                    recorder_type=RECORDER_START;
                }
                if (type == 2) {  //暂停录音
                    tv_start_recorder.setCompoundDrawables(start_recorder_drawable, null, null, null);
                    tv_start_recorder.setText("开始录音");
                    cuteRecorder.pause();
                    recorder_type=RECORDER_PAUSE;
                }
                if (type == 3) {  //继续录音
                    tv_start_recorder.setCompoundDrawables(pause_recorder_drawable, null, null, null);
                    tv_start_recorder.setText("暂停录音");
                    cuteRecorder.resume();
                    recorder_type=RECORDER_RESUME;
                }
                if (type== 4) {  //停止录音
                    tv_start_recorder.setCompoundDrawables(start_recorder_drawable, null, null, null);
                    tv_stop_recorder.setCompoundDrawables(stop_recorder_drawable, null, null, null);
                    tv_start_recorder.setText("开始录音");
                   // stopService(intent);
                    cuteRecorder.stop();
                    recorder_type=RECORDER_STOP;
                    TimeUtils.updataTimeFormat(recorder_time, 0);
                    AnimationUtil.moveToViewTop_Gone(recorder_model, 300);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_start_recorder:
                if (recorder_type==RECORDER_START){  //开始状态---点击暂停
                    updateUi(2);
                } else if (recorder_type==RECORDER_PAUSE){  //暂停状态---点击继续
                    updateUi(3);
                } else if (recorder_type==RECORDER_RESUME){  //继续录音状态---点击暂停
                    updateUi(2);
                } else if (recorder_type==RECORDER_STOP){  //停止状态---点击开始
                    updateUi(1);
                }
                break;
            case R.id.tv_stop_recorder:
                updateUi(4);
                break;
        }
    }

    class recorderListener implements CuteRecorder.AudioRecordListener {

        @Override
        public void hasRecord(final int seconds) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    KLog.d("录音时长",seconds);
                    TimeUtils.updataTimeFormat(recorder_time, seconds*1000);
                }
            });
        }

        @Override
        public void finish(int seconds, String filePath) {
            KLog.d("录音文件1", filePath);
            if (audioListenerComplete != null) {
                audioListenerComplete.AudioSuccess(filePath);
                KLog.d("录音文件2", filePath);
            }

        }

        @Override
        public void tooShort() {
            KLog.d("录音时间太短");
        }

        @Override
        public void curVoice(int voice) {

        }

        @Override
        public void hasPause(int SDK_INT, String audioPath) {

            KLog.d("录音时间暂停", SDK_INT, audioPath);
        }

        @Override
        public void hasResume(int SDK_INT) {
            KLog.d("录音时间继续", SDK_INT);
        }
    }

    public static void setAudioListenerComplete(AudioListenerComplete audioListenerComplete) {
        StartProjectActivity.audioListenerComplete = audioListenerComplete;
    }
}
