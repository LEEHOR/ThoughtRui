package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/12/24
 * on 16:19
 */
public class AttendanceHistory {

    /**
     * data : {"attendance":{"dateTime":"2019-01-09","id":"11bebb019e624067a2ebd62d76a9089c","inLat":30.512119,"inLng":114.165045,"inTime":1547013234914,"inType":1,"outTime":0,"outType":1,"progress":"506/48","startLocationStatus":1,"startTimeStatus":-1,"status":-1}}
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
         * attendance : {"dateTime":"2019-01-09","id":"11bebb019e624067a2ebd62d76a9089c","inLat":30.512119,"inLng":114.165045,"inTime":1547013234914,"inType":1,"outTime":0,"outType":1,"progress":"506/48","startLocationStatus":1,"startTimeStatus":-1,"status":-1}
         */

        private AttendanceBean attendance;

        public AttendanceBean getAttendance() {
            return attendance;
        }

        public void setAttendance(AttendanceBean attendance) {
            this.attendance = attendance;
        }

        public static class AttendanceBean {
            /**
             * dateTime : 2019-01-09
             * id : 11bebb019e624067a2ebd62d76a9089c
             * inLat : 30.512119
             * inLng : 114.165045
             * inTime : 1547013234914
             * inType : 1
             * outTime : 0
             * outType : 1
             * progress : 506/48
             * startLocationStatus : 1
             * startTimeStatus : -1
             * status : -1
             */

            private String dateTime;
            private String id;
            private double inLat;
            private double inLng;
            private long inTime;
            private int inType;
            private long outTime;
            private int outType;
            private String progress;
            private int startLocationStatus;
            private int startTimeStatus;
            private int endTimeStatus;
            private int endLocationStatus;
            private double outLng;
            private double outLat;
            private int status;
            private String remark;

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getInLat() {
                return inLat;
            }

            public void setInLat(double inLat) {
                this.inLat = inLat;
            }

            public double getInLng() {
                return inLng;
            }

            public void setInLng(double inLng) {
                this.inLng = inLng;
            }

            public long getInTime() {
                return inTime;
            }

            public void setInTime(long inTime) {
                this.inTime = inTime;
            }

            public int getInType() {
                return inType;
            }

            public void setInType(int inType) {
                this.inType = inType;
            }

            public long getOutTime() {
                return outTime;
            }

            public void setOutTime(long outTime) {
                this.outTime = outTime;
            }

            public int getOutType() {
                return outType;
            }

            public void setOutType(int outType) {
                this.outType = outType;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getEndTimeStatus() {
                return endTimeStatus;
            }

            public void setEndTimeStatus(int endTimeStatus) {
                this.endTimeStatus = endTimeStatus;
            }

            public int getEndLocationStatus() {
                return endLocationStatus;
            }

            public void setEndLocationStatus(int endLocationStatus) {
                this.endLocationStatus = endLocationStatus;
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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
