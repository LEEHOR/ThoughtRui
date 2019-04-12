package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 23:22
 */
public interface ExpandListClickListener {
    /**
     * 展开子Item
     *
     *
     */
    void onExpandChildren(SubjectListBean.DataBean.QuestionListRoot questionListRoot);

    /**
     * 隐藏子Item
     *
     */
    void onHideChildren(SubjectListBean.DataBean.QuestionListRoot questionListRoot);

}
