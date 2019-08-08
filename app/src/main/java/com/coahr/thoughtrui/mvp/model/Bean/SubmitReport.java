package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/17
 * 描述：
 */
public class SubmitReport {

    /**
     * msg : 请求成功！
     * result : 1
     */

    private String msg;
    private int result;

    @Override
    public String toString() {
        return "SubmitReport{" +
                "msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

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
}
