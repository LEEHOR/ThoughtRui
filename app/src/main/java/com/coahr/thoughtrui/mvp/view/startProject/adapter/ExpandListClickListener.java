package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectParentListAdapterBean;

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
    void onExpandChildren(SubjectParentListAdapterBean entity);

    /**
     * 隐藏子Item
     *
     * @param entity
     */
    void onHideChildren(SubjectParentListAdapterBean entity);

}
