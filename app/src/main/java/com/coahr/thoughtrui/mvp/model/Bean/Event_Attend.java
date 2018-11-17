package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 13:10
 */
public class Event_Attend {
    private String project_name;
    private long start_time;
    private long end_time;
    private String project_companyName;
    private String project_code;
    private String project_companyAddress;

    public Event_Attend() {
    }

    public Event_Attend(String project_name, long start_time, long end_time, String project_companyName, String project_code, String project_companyAddress) {
        this.project_name = project_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.project_companyName = project_companyName;
        this.project_code = project_code;
        this.project_companyAddress = project_companyAddress;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getProject_companyName() {
        return project_companyName;
    }

    public void setProject_companyName(String project_companyName) {
        this.project_companyName = project_companyName;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getProject_companyAddress() {
        return project_companyAddress;
    }

    public void setProject_companyAddress(String project_companyAddress) {
        this.project_companyAddress = project_companyAddress;
    }
}
