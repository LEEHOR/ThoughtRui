package com.coahr.thoughtrui.DBbean;

import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWorkAsync;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 14:17
 */
public class UsersDB extends DataSupport {
    /**
     * id UserDB 主键
     * userName 用户名
     * sessionId 用户sessionId
     */
    private int id;
    private String userName;
    private String sessionId;
    private List<ProjectsDB> projectsDBSList=new ArrayList<>();
    public UsersDB() {
    }

    public UsersDB(int id, String userName, String sessionId, List<ProjectsDB> projectsDBSList) {
        this.id = id;
        this.userName = userName;
        this.sessionId = sessionId;
        this.projectsDBSList = projectsDBSList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<ProjectsDB> getProjectsDBSList() {
        return DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class,"usersdb_id=?",String.valueOf(id));
    }

    public void setProjectsDBSList(List<ProjectsDB> projectsDBSList) {
        this.projectsDBSList = projectsDBSList;
    }
}
