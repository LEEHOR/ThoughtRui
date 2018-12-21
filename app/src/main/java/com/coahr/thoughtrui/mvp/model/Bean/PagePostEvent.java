package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/18
 * on 17:01
 * 用来询问当前题目是否完成
 */
public class PagePostEvent {
    /**
     * upOrDown
     * 1 上  2 下
     * position
     * 当前题目位置
     *
     */
    private int upOrDown;
    private int position;
    private boolean isXunWen;

    public PagePostEvent() {
    }

    public PagePostEvent(int upOrDown, int position, boolean isXunWen) {
        this.upOrDown = upOrDown;
        this.position = position;
        this.isXunWen = isXunWen;
    }

    public int getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(int upOrDown) {
        this.upOrDown = upOrDown;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isXunWen() {
        return isXunWen;
    }

    public void setXunWen(boolean xunWen) {
        isXunWen = xunWen;
    }
}
