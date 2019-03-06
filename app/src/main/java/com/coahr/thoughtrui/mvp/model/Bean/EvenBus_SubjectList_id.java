package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：点击三级列表回调给答题页
 */
public class EvenBus_SubjectList_id {
    private String Sub_id;

    public String getSub_id() {
        return Sub_id;
    }

    public void setSub_id(String sub_id) {
        Sub_id = sub_id;
    }

    public EvenBus_SubjectList_id() {
    }

    public EvenBus_SubjectList_id(String sub_id) {
        Sub_id = sub_id;
    }
}
