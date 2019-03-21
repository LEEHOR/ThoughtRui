package com.coahr.thoughtrui.mvp.view.recorder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.coahr.thoughtrui.Utils.DeleteFileUtil;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/8
 * 描述：录音服务
 */
public class RecorderService extends Service {
    private String mFileName = null;
    private String mFilePath = null;
    private String temName;
    private static Handler mHandle;
    private String outPath;  //输出录音路径
    private String outName;  //录音文件名
    //默认录音最大最小时长
    private int MAX_TIME = 60 * 5;
    private int MIN_TIME = 3 ;
    private List<String> audioList = new ArrayList<>();
    private int time;
    private  AudioRecordListener audioRecordListener;
    private static MediaRecorder mRecorder = null;
    private recorderStatus status;  //录音状态
    private boolean isPrepare;

    // 监听器对象
    private MyListener listener;
    //是否在通话中
    private boolean Call = false;
    // 电话管理器
    private TelephonyManager tm;
    // 监听器对象
    private AudioManager ams = null;//音频管理器
    private RecorderBinder mBinder = new RecorderBinder();

    public RecorderService() {
    }

    public class RecorderBinder extends Binder{
        /**
         * 初始化
         */
            public void init(){
                getInstance();
            }

        /**
         * 配置录音参数
         */
        public void configureMediaRecorder(){
                prepareRecording();
            }

        /**
         * 配置路径和名字
         * @param outPath
         * @param outName
         */
        public void PrepareMediaRecorder(String outPath, String outName){
            prepare(outPath,outName);
        }

        /**
         * 开始录音
         */
        public  void  Start(){
            startRecorder();
        }

        /**
         * 停止录音
         */
        public void Stop(){
            stopRecorder();
        }

        /**
         * 暂停
         */
        public void Pause(){
            pauseRecorder();
        }

        /**
         * 继续
         */
        public void Resume() {
            resumeRecorder();
        }
        public RecorderService getMyService(){
            return RecorderService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }

    public static void getInstance(){
        if (mHandle == null) {
            mHandle = new Handler();
        }
    }
    /**
     * 服务创建的时候调用的方法
     */
    @Override
    public void onCreate() {
        // 后台监听电话的呼叫状态。
        // 得到电话管理器
        tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        listener = new MyListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        initAudio();
        super.onCreate();
    }

    //微信，qq通话监听
    private void initAudio() {
        ams = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ams.getMode();//这里getmode返回值为3时代表，接通qq或者微信电话
        ams.requestAudioFocus(mAudioFocusListener, 1, 1);
    }

    /**
     * 配置录音参数
     */
    public void prepareRecording() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
           /* mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);*/
            mRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
           // mRecorder.setAudioChannels(1);
          //  mRecorder.setAudioSamplingRate(44100);
          //  mRecorder.setAudioEncodingBitRate(192000);

        }

    }

    /**
     * 准备录音
     */
    public void prepare(String outPath, String outName) {
        this.outPath = outPath;
        this.outName = outName;
        if (isPrepare){
            return;
        }
        if (mRecorder != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                String s = setRecorderOutPath_max(outPath, outName);
                mRecorder.setOutputFile(s);
            } else {
                String s = setRecorderOutPath_min(outPath);
                mRecorder.setOutputFile(s);
            }
            try {
                mRecorder.prepare();
                isPrepare = true;
            } catch (IOException e) {
            }
        }
    }

    /**
     * 开始录制
     */
    public void startRecorder() {
        audioList.clear();
        if (mRecorder != null && status != recorderStatus.Start && isPrepare && !Call) {
            mRecorder.start();
            status = recorderStatus.Start;
            mHandle.post(runnable);
            if (audioRecordListener != null) {
                audioRecordListener.StartRecorder();
            }
        }
    }

    public void stopRecorder() {
        if (time <= MIN_TIME) {
            if (audioRecordListener != null) {
                audioRecordListener.tooShort();
            }
        } else {
            if (mRecorder != null && status != recorderStatus.Stop) {
                mRecorder.stop();
                release();
                status = recorderStatus.Stop;
                mHandle.removeCallbacks(runnable);
                time = 0;
                //判断版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (audioRecordListener != null) {
                        audioRecordListener.finish(time, mFilePath);
                    }
                } else {
                    //判断数组否大于1，大于1就合并
                    audioList.add(mFilePath);
                    if (audioList.size() > 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MergeFiles.uniteAMRFile(audioList, outPath, outName, new MergeFileListener() {
                                    @Override
                                    public void mergeSuccess(String outPath) {
                                        //删除文件
                                        deleteAudio();
                                        if (audioRecordListener != null) {
                                            audioRecordListener.finish(time, outPath);
                                        }
                                    }

                                    @Override
                                    public void mergeFailure(String failure) {
                                        ToastUtils.showLong(failure);
                                    }
                                });
                            }
                        }).start();

                    }

                }
            }

        }
    }

    /**
     * 暂停录音
     */
    public void pauseRecorder() {
        if (mRecorder != null && isPrepare && (status == recorderStatus.Start || status == recorderStatus.Resume)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mRecorder.pause();
                status = recorderStatus.Pause;
                mHandle.removeCallbacks(runnable);
                if (audioRecordListener != null) {
                    audioRecordListener.hasPause();
                }
            } else {
                //先停止并保存
                mRecorder.stop();
                release();
                audioList.add(mFilePath);
                if (audioList.size() > 1) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MergeFiles.uniteAMRFile(audioList, outPath, outName, new MergeFileListener() {
                                @Override
                                public void mergeSuccess(String outPath) {
                                    //删除文件
                                    deleteAudio();
                                    audioList.add(outPath);
                                    status = recorderStatus.Pause;
                                    mHandle.removeCallbacks(runnable);
                                    if (audioRecordListener != null) {
                                        audioRecordListener.hasPause();
                                    }
                                }

                                @Override
                                public void mergeFailure(String failure) {
                                    ToastUtils.showLong(failure);
                                }
                            });
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * 继续录音
     */
    public void resumeRecorder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (mRecorder != null && isPrepare && status == recorderStatus.Pause && !Call) {
                mRecorder.start();
                status = recorderStatus.Resume;
                mHandle.post(runnable);
                if (audioRecordListener != null) {
                    audioRecordListener.hasResume();
                }
            }
        } else {
            if (status == recorderStatus.Pause && !Call) {
                prepareRecording();
                prepare(outPath, outName);
                mRecorder.start();
                status = recorderStatus.Resume;
                mHandle.post(runnable);
                if (audioRecordListener != null) {
                    audioRecordListener.hasResume();
                }
            }
        }

    }

    public enum recorderStatus {
        Stop,
        Start,
        Pause,
        Resume
    }

    //get max record time
    public void setMaxTime(int maxTime) {
        this.MAX_TIME = maxTime;
    }

    //get min record time
    public void setMinTime(int minTime) {
        this.MIN_TIME = minTime;
    }

    /**
     * 释放录音
     */
    public void release() {
        if (mRecorder != null) {
            isPrepare = false;
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * 录音时间计时线程
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (status == recorderStatus.Start  || status == recorderStatus.Resume) {
                if (time<=MAX_TIME){
                    time++;
                    if (audioRecordListener != null) {
                        audioRecordListener.RecorderTime(time);
                    }
                    mHandle.postDelayed(this, 1000);
                } else {
                    stopRecorder();
                }

            }
        }
    };

    /**
     * 版本大于等于24
     * 设置保存路径和文件名
     *
     * @param outPath
     * @param outName
     * @return
     */
    private String setRecorderOutPath_max(String outPath, String outName) {
        File audioPath = new File(outPath);
        if (!audioPath.exists()) {
            audioPath.mkdirs();
        }
        File OutRecorderPath = new File(audioPath, outName + ".amr");
        this.mFilePath = OutRecorderPath.getAbsolutePath();
        this.mFileName = outName + ".amr";
        return OutRecorderPath.getAbsolutePath();
    }

    /**
     * 版本小于24
     * 先用时间戳命名
     * 设置保存路径和文件名
     *
     * @param outPath
     * @return
     */
    private String setRecorderOutPath_min(String outPath) {
        File audioPath = new File(outPath);
        if (!audioPath.exists()) {
            audioPath.mkdirs();
        }
        String timeRecorderName = getTimeRecorderName();
        File OutRecorderPath = new File(audioPath, timeRecorderName);
        this.mFilePath = OutRecorderPath.getAbsolutePath();
        this.temName = timeRecorderName;
        return OutRecorderPath.getAbsolutePath();
    }

    private String getTimeRecorderName() {
        Log.e("录音类", "自定义录音文件名：" + System.currentTimeMillis() + ".amr");
        return System.currentTimeMillis() + ".amr";
    }

    public String getRecorderOutPath() {
        return mFilePath;
    }

    public String getRecorderName() {
        return mFileName;
    }

    private class MyListener extends PhoneStateListener {

        // 当电话的呼叫状态发生变化的时候调用的方法
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d("qcl111", "state" + state);
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://空闲状态。
                        Call = false;
                        Log.v("myService", "空闲状态");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://铃响状态。
                        Call = true;
                        pauseRecorder();
                        //暂停录音
                        Log.v("myService", "铃响状态");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://通话状态

                        Log.v("myService", "通话状态");
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            Log.d("qcl111", "focusChange----------" + focusChange);

            if (focusChange == 1) {//视频语音挂断状态
                Call = false;
            } else {//微信或者qq语音视频接通状态
                Call = true;
                pauseRecorder();
            }
        }
    };

    private void deleteAudio() {
        //删除文件
        for (int i = 0; i < audioList.size(); i++) {
            boolean delete = DeleteFileUtil.delete(audioList.get(i));
            if (delete) {
                audioList.remove(i);
            }
        }
    }
    /**
     * 服务销毁的时候调用的方法
     */
    @Override
    public void onDestroy() {
            tm.listen(listener, PhoneStateListener.LISTEN_NONE);
            listener = null;
            ams.abandonAudioFocus(mAudioFocusListener);
        if (mHandle != null) {
            mHandle.removeCallbacks(runnable);
        }
            release();
        super.onDestroy();
    }

    public  void setRecordListener(AudioRecordListener recordListener) {
        this.audioRecordListener = recordListener;
    }

    public AudioRecordListener getRecordListener() {
        return audioRecordListener;
    }
}
