package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2019/1/3
 * on 14:21
 */
public class EvenBus_LoginSuccess {

    private int loginType;

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public EvenBus_LoginSuccess() {
    }

    public EvenBus_LoginSuccess(int loginType) {
        this.loginType = loginType;
    }
}
