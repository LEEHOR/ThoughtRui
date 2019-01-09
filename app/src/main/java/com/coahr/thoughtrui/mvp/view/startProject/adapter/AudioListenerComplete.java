package com.coahr.thoughtrui.mvp.view.startProject.adapter;

/**
 * Created by Leehor
 * on 2018/12/26
 * on 15:56
 */
public interface AudioListenerComplete {

    void isStart();

    void isRecorderTime(int time);

    void isComplete(int seconds, String filePath);

    void isShort();

    void isPause();

    void isResume();
}
