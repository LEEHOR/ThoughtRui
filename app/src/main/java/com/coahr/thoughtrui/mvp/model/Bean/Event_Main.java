package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/16
 * on 13:37
 */
public class Event_Main {
    private int isLoad;  //登陆状态
    private String Message; //文字描述
    private int page;    //当前页面
    public Event_Main() {

    }

    public Event_Main(int isLoad, String message, int page) {
        this.isLoad = isLoad;
        Message = message;
        this.page = page;
    }

    public int getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(int isLoad) {
        this.isLoad = isLoad;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
