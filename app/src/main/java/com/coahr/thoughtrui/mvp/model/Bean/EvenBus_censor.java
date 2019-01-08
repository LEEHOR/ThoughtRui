package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：审核搜索
 */
public class EvenBus_censor {
    private String inputText;
    private int type;

    public EvenBus_censor() {
    }

    public EvenBus_censor(String inputText, int type) {
        this.inputText = inputText;
        this.type = type;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
