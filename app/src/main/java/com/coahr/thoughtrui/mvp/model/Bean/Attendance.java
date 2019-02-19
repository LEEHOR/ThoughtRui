package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:23
 */
public class Attendance {


    /**
     * data : {"Cname":"自由班次","Dname":"一汽奥迪武汉客厅店","Pname":"项目B1","areaAddress":"湖北省武汉市东西湖区","attendance":{"endLocationStatus":1,"endTimeStatus":1,"id":"8447e7a9d74745dab932eef5fa5aa6c4","inLat":30.62002,"inLng":114.136886,"inTime":1529383834194,"outLat":30.62002,"outLng":114.136886,"outTime":1529387996985,"startLocationStatus":-1,"startTimeStatus":1},"classId":"bb8e3d0f8cf7461fb1b95395b1034c2b","closeStatus":0,"code":"1111111","count":0,"dealerId":"9952cc55fec14d3b922d7e1d96ba3497","endTime":1,"latitude":30.62002,"location":"武汉客厅","longitude":114.136886,"notice":"","projectId":"c89c42ec8cef4f7794c6fc6920e4365d","startTime":1529032353245}
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
         * Cname : 自由班次
         * Dname : 一汽奥迪武汉客厅店
         * Pname : 项目B1
         * areaAddress : 湖北省武汉市东西湖区
         * attendance : {"endLocationStatus":1,"endTimeStatus":1,"id":"8447e7a9d74745dab932eef5fa5aa6c4","inLat":30.62002,"inLng":114.136886,"inTime":1529383834194,"outLat":30.62002,"outLng":114.136886,"outTime":1529387996985,"startLocationStatus":-1,"startTimeStatus":1}
         * classId : bb8e3d0f8cf7461fb1b95395b1034c2b
         * closeStatus : 0
         * code : 1111111
         * count : 0
         * dealerId : 9952cc55fec14d3b922d7e1d96ba3497
         * endTime : 1
         * latitude : 30.62002
         * location : 武汉客厅
         * longitude : 114.136886
         * notice :
         * projectId : c89c42ec8cef4f7794c6fc6920e4365d
         * startTime : 1529032353245
         * type: 1上班卡/2下班卡
         */

        private String Cname;
        private String Dname;
        private String Pname;
        private String areaAddress;
        private AttendanceBean attendance;
        private String classId;
        private int closeStatus;
        private String code;
        private int count;
        private String dealerId;
        private long endTime;
        private double latitude;
        private String location;
        private double longitude;
        private String notice;
        private String projectId;
        private long startTime;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCname() {
            return Cname;
        }

        public void setCname(String Cname) {
            this.Cname = Cname;
        }

        public String getDname() {
            return Dname;
        }

        public void setDname(String Dname) {
            this.Dname = Dname;
        }

        public String getPname() {
            return Pname;
        }

        public void setPname(String Pname) {
            this.Pname = Pname;
        }

        public String getAreaAddress() {
            return areaAddress;
        }

        public void setAreaAddress(String areaAddress) {
            this.areaAddress = areaAddress;
        }

        public AttendanceBean getAttendance() {
            return attendance;
        }

        public void setAttendance(AttendanceBean attendance) {
            this.attendance = attendance;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public int getCloseStatus() {
            return closeStatus;
        }

        public void setCloseStatus(int closeStatus) {
            this.closeStatus = closeStatus;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDealerId() {
            return dealerId;
        }

        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }


        public static class AttendanceBean {
            /**
             * endLocationStatus : 1
             * endTimeStatus : 1
             * id : 8447e7a9d74745dab932eef5fa5aa6c4
             * inLat : 30.62002
             * inLng : 114.136886
             * inTime : 1529383834194
             * outLat : 30.62002
             * outLng : 114.136886
             * outTime : 1529387996985
             * startLocationStatus : -1
             * startTimeStatus : 1
             */

            private int endLocationStatus;
            private int endTimeStatus;
            private String id;
            private double inLat;
            private double inLng;
            private long inTime;
            private double outLat;
            private double outLng;
            private long outTime;
            private int startLocationStatus;
            private int startTimeStatus;

            public int getEndLocationStatus() {
                return endLocationStatus;
            }

            public void setEndLocationStatus(int endLocationStatus) {
                this.endLocationStatus = endLocationStatus;
            }

            public int getEndTimeStatus() {
                return endTimeStatus;
            }

            public void setEndTimeStatus(int endTimeStatus) {
                this.endTimeStatus = endTimeStatus;
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

            public double getOutLat() {
                return outLat;
            }

            public void setOutLat(double outLat) {
                this.outLat = outLat;
            }

            public double getOutLng() {
                return outLng;
            }

            public void setOutLng(double outLng) {
                this.outLng = outLng;
            }

            public long getOutTime() {
                return outTime;
            }

            public void setOutTime(long outTime) {
                this.outTime = outTime;
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
        }
    }
}
