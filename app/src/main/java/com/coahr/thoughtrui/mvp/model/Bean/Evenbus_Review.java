package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/22
 * 描述：审核数据传递
 */
public class Evenbus_Review {
    private  String projectId;
    private int position;
    private List<String> list;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Evenbus_Review(String projectId, int position, List<String> list) {
        this.projectId = projectId;
        this.position = position;
        this.list = list;
    }

    public Evenbus_Review() {
    }
}
