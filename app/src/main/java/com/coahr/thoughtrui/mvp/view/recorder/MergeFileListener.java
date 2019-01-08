package com.coahr.thoughtrui.mvp.view.recorder;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/8
 * 描述：
 */
public interface MergeFileListener {
    void mergeSuccess(String outPath);
    void mergeFailure(String failure);
}
