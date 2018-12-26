package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/12/26
 * on 9:42
 * 录音类
 */
public class EvenBus_recorderType {
    /**type 1,2,3,4 开始。暂停，继续，停止
     *
     * recorderName 录音名
     *
     * audioSavePath 录音保存文件夹
     *
     * audioTemSavePath 录音保存临时文件夹
     */
    private int type;
    private String recorderName;
    private String audioSavePath;
    private String audioTemSavePath;

    public EvenBus_recorderType() {
    }

    public EvenBus_recorderType(int type, String recorderName, String audioSavePath, String audioTemSavePath) {
        this.type = type;
        this.recorderName = recorderName;
        this.audioSavePath = audioSavePath;
        this.audioTemSavePath = audioTemSavePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public String getAudioSavePath() {
        return audioSavePath;
    }

    public void setAudioSavePath(String audioSavePath) {
        this.audioSavePath = audioSavePath;
    }

    public String getAudioTemSavePath() {
        return audioTemSavePath;
    }

    public void setAudioTemSavePath(String audioTemSavePath) {
        this.audioTemSavePath = audioTemSavePath;
    }
}
