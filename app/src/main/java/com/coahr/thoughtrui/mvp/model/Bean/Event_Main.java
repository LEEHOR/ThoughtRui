package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/16
 * on 13:37
 */
public class Event_Main {
    private int isLoad;
    private String Message;

    public Event_Main() {
    }

    public Event_Main(int isLoad, String message) {
        this.isLoad = isLoad;
        Message = message;
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
}
