package com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 18:53
 */
public class Value {
    private String id;
    private String name;
    private List<QuestionList> quesList;
    private List<ValueBean> value;

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

    public List<QuestionList> getQuesList() {
        return quesList;
    }

    public void setQuesList(List<QuestionList> quesList) {
        this.quesList = quesList;
    }

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }
}
