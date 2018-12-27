package com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 17:35
 * 父布局
 */
public class SubjectChildListAdapterBean {
  /*  "id": "cae4706073fa4936a107806e3a6a5041",
            "name": "日常运营",
            "quesList": [],
            "value": []*/
    public static final int PARENT_ITEM = 0;//父布局
    public static final int CHILD_ITEM = 1;//子布局
    private String id;
    private String name;
    private List<QuestionList> questList;
    private List<ValueBean> valueBeanList;
    private int type;// 显示类型
    private boolean isExpand;// 是否展开
    private List<SubjectChildListAdapterBean> subjectChildListAdapterBean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionList> getQuestList() {
        return questList;
    }

    public void setQuestList(List<QuestionList> questList) {
        this.questList = questList;
    }

    public List<ValueBean> getValueBeanList() {
        return valueBeanList;
    }

    public void setValueBeanList(List<ValueBean> valueBeanList) {
        this.valueBeanList = valueBeanList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public List<SubjectChildListAdapterBean> getSubjectChildListAdapterBean() {
        return subjectChildListAdapterBean;
    }

    public void setSubjectChildListAdapterBean(List<SubjectChildListAdapterBean> subjectChildListAdapterBean) {
        this.subjectChildListAdapterBean = subjectChildListAdapterBean;
    }
}
