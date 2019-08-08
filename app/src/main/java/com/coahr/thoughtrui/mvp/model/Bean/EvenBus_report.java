package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/16
 * 描述：
 */
public class EvenBus_report {
    private boolean isCanReport;

    @Override
    public String toString() {
        return "EvenBus_report{" +
                "isCanReport=" + isCanReport +
                '}';
    }

    public EvenBus_report() {
    }

    public EvenBus_report(boolean isCanReport) {
        this.isCanReport = isCanReport;
    }

    public boolean isCanReport() {
        return isCanReport;
    }

    public void setCanReport(boolean canReport) {
        isCanReport = canReport;
    }
}
