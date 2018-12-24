package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/12/24
 * on 14:00
 * 打卡
 */
public class PushAttendanceCard {

      /**
     * data : {"id":"d8cb88de448e432da5d8acf822e90ca8","signInTime":1526376715815,"timeStatus":1}
     * msg : 请求成功
     * result : 1
     */

    private DataBean data;
    private String msg;
    private int result;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * id : d8cb88de448e432da5d8acf822e90ca8
         * signInTime : 1526376715815
         * timeStatus : 1
         */

        private String id;
        private long signInTime;
        private int timeStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getSignInTime() {
            return signInTime;
        }

        public void setSignInTime(long signInTime) {
            this.signInTime = signInTime;
        }

        public int getTimeStatus() {
            return timeStatus;
        }

        public void setTimeStatus(int timeStatus) {
            this.timeStatus = timeStatus;
        }
    }
}
