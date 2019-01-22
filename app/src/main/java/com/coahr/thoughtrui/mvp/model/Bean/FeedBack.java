package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/22
 * 描述：意见反馈
 */
public class FeedBack {

    private String msg;
    private int result;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public FeedBack(String msg, int result) {
        this.msg = msg;
        this.result = result;
    }

    public FeedBack() {
    }


}
