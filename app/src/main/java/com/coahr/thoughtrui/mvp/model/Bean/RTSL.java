package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/19
 * 描述：实时定位
 */
public class RTSL {

    /**
     * msg : 请求成功！
     * result : 1
     */

    private String msg;
    private int result;

    @Override
    public String toString() {
        return "RTSL{" +
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
