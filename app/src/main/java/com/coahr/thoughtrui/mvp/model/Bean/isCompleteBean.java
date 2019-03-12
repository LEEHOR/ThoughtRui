package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/18
 * on 17:23
 */
public class isCompleteBean {
    /**
     * isComplete 是否完成
     *position   当前题号
     * upOrDown  翻页标志 1.上翻页：2.下翻页
     */

    private boolean isComplete;

    private int position;

    private  int upOrDown;
    private int type;  //1.答题页面 2.审核页面

    public isCompleteBean(boolean isComplete, int position, int upOrDown, int type) {
        this.isComplete = isComplete;
        this.position = position;
        this.upOrDown = upOrDown;
        this.type = type;
    }

    public isCompleteBean() {
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getUpOrDown() {
        return upOrDown;
    }

    public void setUpOrDown(int upOrDown) {
        this.upOrDown = upOrDown;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
