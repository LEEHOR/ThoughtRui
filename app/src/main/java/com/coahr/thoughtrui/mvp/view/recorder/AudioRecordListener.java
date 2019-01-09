package com.coahr.thoughtrui.mvp.view.recorder;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/9
 * 描述：
 */
public interface AudioRecordListener {
    //has record
    void StartRecorder();

    void RecorderTime(int time);

    void finish(int seconds, String filePath);

    void tooShort();

    void hasPause();

    void hasResume();
}
