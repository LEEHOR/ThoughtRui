package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/24
 * on 16:19
 */
public class AttendanceHistory {

    /**
     * data : {"attendanceList":[{"dateTime":"2018-12-01","endLocationStatus":-1,"endTimeStatus":-1,"inTime":0,"outTime":0,"startLocationStatus":-1,"startTimeStatus":-1},{"dateTime":"2018-05-14","endLocationStatus":-1,"endTimeStatus":1,"inTime":1526083200000,"outTime":1526119200000,"startLocationStatus":-1,"startTimeStatus":1},{"dateTime":"2018-05-15","endLocationStatus":-1,"endTimeStatus":-1,"inTime":1526365438826,"outTime":1526368036538,"startLocationStatus":-1,"startTimeStatus":-1}]}
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
        private List<AttendanceListBean> attendanceList;

        public List<AttendanceListBean> getAttendanceList() {
            return attendanceList;
        }

        public void setAttendanceList(List<AttendanceListBean> attendanceList) {
            this.attendanceList = attendanceList;
        }

        public static class AttendanceListBean {
            private String dateTime;
            private int endLocationStatus;
            private int startLocationStatus;
            private int startTimeStatus;
            private int endTimeStatus;
            private long inTime;
            private long outTime;
            private double inLng;
            private double inLat;
            private double outLng;
            private double outLat;
            private int    status;
            private String progress;
            private String remark;

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public int getEndLocationStatus() {
                return endLocationStatus;
            }

            public void setEndLocationStatus(int endLocationStatus) {
                this.endLocationStatus = endLocationStatus;
            }

            public int getStartLocationStatus() {
                return startLocationStatus;
            }

            public void setStartLocationStatus(int startLocationStatus) {
                this.startLocationStatus = startLocationStatus;
            }

            public int getStartTimeStatus() {
                return startTimeStatus;
            }

            public void setStartTimeStatus(int startTimeStatus) {
                this.startTimeStatus = startTimeStatus;
            }

            public int getEndTimeStatus() {
                return endTimeStatus;
            }

            public void setEndTimeStatus(int endTimeStatus) {
                this.endTimeStatus = endTimeStatus;
            }

            public long getInTime() {
                return inTime;
            }

            public void setInTime(long inTime) {
                this.inTime = inTime;
            }

            public long getOutTime() {
                return outTime;
            }

            public void setOutTime(long outTime) {
                this.outTime = outTime;
            }

            public double getInLng() {
                return inLng;
            }

            public void setInLng(double inLng) {
                this.inLng = inLng;
            }

            public double getInLat() {
                return inLat;
            }

            public void setInLat(double inLat) {
                this.inLat = inLat;
            }

            public double getOutLng() {
                return outLng;
            }

            public void setOutLng(double outLng) {
                this.outLng = outLng;
            }

            public double getOutLat() {
                return outLat;
            }

            public void setOutLat(double outLat) {
                this.outLat = outLat;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
