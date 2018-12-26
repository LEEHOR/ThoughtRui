package com.example.hd.cuterecorder;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HONGDA on 2017/7/4.11:32
 */

public class CuteRecorder implements AudioManger.AudioStateListener {

    private AudioManger audioManger;
    //is recording ?
    private boolean isRecording = false;

    private int time = 0;
    private Handler handler;
    //合并录音的路径
    private String outTemPath;
    private boolean isPrepared = false;
    public static final int HIGH = 20;
    public static final int NORMAL = 14;
    public static final int LOW = 8;
    public static List<String> audioPathList=new ArrayList();
    private String DIR_PATH;
    private int MAX_TIME;
    private int MIN_TIME;
    private int VOICE_LEVEL;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRecording) {
                time++;
                audioRecordListener.hasRecord(time);
                audioRecordListener.curVoice(audioManger.getVoiceLevel(VOICE_LEVEL));
                Log.e("录音时间",time+"");
                handler.postDelayed(this, 1000);
            }
        }
    };

    public CuteRecorder(Builder builder) {
        this.DIR_PATH = builder.DIR_PATH;
        this.MAX_TIME = builder.MAX_TIME;
        this.MIN_TIME = builder.MIN_TIME;
        this.VOICE_LEVEL = builder.VOICE_LEVEL;

        audioManger = AudioManger.getInstance();
        audioManger.setmDir(DIR_PATH);
        audioManger.setOnAudioStateListener(this);
        handler = new Handler();

    }

    //the callback for prepared to record
    @Override
    public void wellPrepared() {
        isRecording = false;
        time = 0;
        isPrepared = true;
    }

//==============================================准备之前调用=============================================//
    /**
     * 设置录音文件名
     * 默认为时间戳
     * 每次start()开始之前调用有效否则为时间戳
     * @param audioName
     */
    public void  setAudioName(String audioName){
        audioManger.setAudioName(audioName);
    }
    /**
     * 自定义保存路径
     * @param OutAudioPath
     */
    public void setOutAudioPath(String OutAudioPath){
                audioManger.setAudioPath(OutAudioPath);
    }

    /**
     * 自定义临时保存文件夹
     * @param OutTemPath
     */
    public void setOutTemPath(String OutTemPath){
        audioManger.setAudioPath_tem(OutTemPath);
    }
  //===================================================准备之前调用===============================================

    //准备
    public void Prepared(){
        audioManger.prepareAudio();
    }

    public void start(Activity activity) {
        audioPathList.clear();
        if (!isPrepared) {
            audioManger.prepareAudio();
        }
        audioManger.start();
       // audioManger.getNotification(activity);
        isRecording = true;
        handler.post(runnable);
        Log.e("录音控制器","录音开始");
    }

    //结束录制
    public void stop() {
        if (time <= MIN_TIME) {
            Log.e("录音控制器","录音室时间太短");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileIOUtils.deleteFile(audioManger.getCurrentFilePath());
                }
            }).start();
            audioRecordListener.tooShort();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                audioRecordListener.finish(time, audioManger.getCurrentFilePath());
                Log.e("录音控制器24","stop");
            } else {
                audioPathList.add(audioManger.getCurrentFilePath());
                if (audioPathList.size()>1){
                    String s = uniteAMRFile(audioPathList, audioManger.getAudioPath(),audioManger.getAudioName());
                    //拷贝文件到正式文件夹
                    audioRecordListener.finish(time, s);
                    Log.e("录音控制器","stop1");
                } else {
                    boolean b = FileIOUtils.copyDate(audioManger.getCurrentFilePath(), audioManger.getAudioPath(), audioManger.getAudioName(), true);
                    audioRecordListener.finish(time, audioManger.getAudioPath()+"/"+audioManger.getAudioName());
                    Log.e("录音控制器","stop2");
                }
                audioPathList.clear();
            }
        }
        audioManger.release();
        isPrepared = false;
        reset();
        audioManger.setAudioName(null);
       // audioManger.close_notification();
    }

    //暂停录制

    /**
     *
     */
    public void pause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            audioManger.pause_new();
            audioRecordListener.hasPause(1,audioManger.getCurrentFilePath());
            Log.e("录音控制器24","pause");
        } else {
            audioPathList.add(audioManger.getCurrentFilePath());
            if (audioPathList.size()>=1){
                //保存到临时文件夹里
                String s = uniteAMRFile(audioPathList, audioManger.getAudioPath_tem(),audioManger.generateFileName());
                audioPathList.clear();
                audioPathList.add(s);
            }
            audioManger.pause_old();
            audioRecordListener.hasPause(2,audioManger.getCurrentFilePath());
            audioManger.release();
            isPrepared = false;
            Log.e("录音控制器","pause");
        }
        handler.removeCallbacks(runnable);
    }

    public void  resume(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            audioManger.resume_new();
            isRecording = true;
            handler.post(runnable);
            audioRecordListener.hasResume(1);
            Log.e("录音控制器24","resume");
        } else {
            if (!isPrepared) {
                audioManger.prepareAudio();
            }
            audioManger.start();
            isRecording = true;
            handler.post(runnable);
            audioRecordListener.hasResume(2);
            Log.e("录音控制器","resume");
        }

    }
    //取消录音
    public void  cancel(){
        audioManger.cancel();
        audioManger.release();
        audioManger.setAudioName(null);
        if (audioPathList != null && audioPathList.size()>0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i <audioPathList.size() ; i++) {
                        boolean b = FileIOUtils.deleteFile(audioPathList.get(i));
                        if (b){
                            audioPathList.remove(i);
                        }
                    }
                }
            }).start();
        }
        if (audioManger.getCurrentFilePath() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileIOUtils.deleteFile(audioManger.getCurrentFilePath());
                }
            }).start();
        }
        reset();
        audioManger.close_notification();
    }
    //置位
    private void reset() {
        isRecording = false;
        time = 0;
        handler.removeCallbacks(runnable);
    }

    /**
     * 默认的初始化设置
     */
    public static class Builder {

        /*
         *    the default output dir path is SD/audios
         * */
        private String DIR_PATH = Environment.getExternalStorageDirectory() + "/audios";

        /**
         * 默认临时文件
         */
        private String TEM_DIR  =Environment.getExternalStorageDirectory() + "/tem_audios";
        /*
         *    max record time  60s
         * */
        private int MAX_TIME = 60;
        /*
         *   min record time 3s
         * */
        private int MIN_TIME = 3;

        /*
         *  the voice level HIGH 20 NORMAL 14  LOW 8  default NORMAL
         * */
        private int VOICE_LEVEL = NORMAL;

        public CuteRecorder build() {
            return new CuteRecorder(this);
        }

        public Builder outPutDir(String dirPath) {
            this.DIR_PATH = dirPath;
            return this;
        }
        public Builder outTemPath(String temPath){
            this.TEM_DIR=temPath;
            return this;
        }
        public Builder maxTime(int maxTime) {
            this.MAX_TIME = maxTime;
            return this;
        }

        public Builder minTime(int minTime) {
            this.MIN_TIME = minTime;
            return this;
        }

        public Builder voiceLevel(int voiceLevel) {
            this.VOICE_LEVEL = voiceLevel;
            return this;
        }

    }

    public interface AudioRecordListener {
        //has record
        void hasRecord(int seconds);

        void finish(int seconds, String filePath);

        void tooShort();

        //current voice level
        void curVoice(int voice);

        void hasPause(int SDK_INT,String audioPath);

        void hasResume(int SDK_INT);
    }

    private AudioRecordListener audioRecordListener;

    public void setOnAudioRecordListener(AudioRecordListener listener) {
        audioRecordListener = listener;
    }

    //is prepare to record
    public boolean isPrepared() {
        return isPrepared;
    }

    //get the output file
    public String getOutputDirPath() {
        return DIR_PATH;
    }

    //get max record time
    public int getMaxTime() {
        return MAX_TIME;
    }

    //get min record time
    public int getMinTime() {
        return MIN_TIME;
    }

    //get the voice level
    public int getVoiceLevel() {
        return VOICE_LEVEL;
    }
    /**
     * 需求:将两个amr格式音频文件合并为1个
     * 注意:amr格式的头文件为6个字节的长度
     * @param partsPaths       各部分路径
     * @param unitedFilePath   合并后路径
     */
    public String uniteAMRFile(final List<String> partsPaths, final String unitedFilePath, final String audioName) {
        Log.e("合并文件操作","开始合并");
        if (partsPaths != null && partsPaths.size()>0) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File unitedFile = new File(unitedFilePath);
                    if (!unitedFile.exists()) {
                        unitedFile.mkdirs();
                    }
                    File file = new File(unitedFilePath, audioName);
                    if (!file.exists()){
                        file.mkdir();
                    }
                    FileOutputStream fos = new FileOutputStream(unitedFile);
                    RandomAccessFile ra = null;
                    for (int i = 0; i < partsPaths.size(); i++) {
                        ra = new RandomAccessFile(partsPaths.get(i), "r");
                        if (i != 0) {
                            ra.seek(6);
                        }
                        byte[] buffer = new byte[1024 * 8];
                        int len = 0;
                        while ((len = ra.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                    }
                    ra.close();
                    fos.close();
                    Log.e("合并文件操作","成功");
                    outTemPath=file.getAbsolutePath();
                    for (int i = 0; i <partsPaths.size() ; i++) {
                        boolean b = FileIOUtils.deleteFile(partsPaths.get(i));
                        Log.e("合并文件操作","删除旧文件"+b+"/文件路径："+partsPaths.get(i));
                    }
                } catch (Exception e) {
                    Log.e("合并文件操作",e.toString());
                    outTemPath=null;
                }
            }
        }).start();
        } else {
            Log.e("合并文件操作","List为空");
            return null;
        }
        return outTemPath;
    }
}
