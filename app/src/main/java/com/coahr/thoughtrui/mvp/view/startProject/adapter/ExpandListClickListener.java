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
     * @param entity
     */
    void onExpandChildren(SubjectListBean.DataBean.QuestionListBean entity);

    /**
     * 隐藏子Item
     *
     * @param entity
     */
    void onHideChildren(SubjectListBean.DataBean.QuestionListBean entity);

}
