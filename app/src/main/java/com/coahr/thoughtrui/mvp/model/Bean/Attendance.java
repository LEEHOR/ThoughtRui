package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:23
 */
public class Attendance {

    /**
     * data : {"Cname":"09:00-17:00","Dname":"武汉仁和胜利车业有限公司","Pname":"SWM斯威汽车形象店运营检核表","areaAddress":"湖北省武汉市江岸区","attendance":{},"classId":"424ad558f1c4499988a4a9cba4c52ed0","closeStatus":0,"code":"C类","count":48,"dealerId":"aca1cfd2b5514f62a576153f16c08802","endTime":1,"latitude":30.662118,"location":"竹叶山中环商贸城C9-11","longitude":114.30359,"notice":"的撒啊实打实打算多阿达撒打算的啊实打实打撒大所啊实打实打撒大所啊实打实打阿萨德啊实打实打算多啊实打实打撒大声地啊实打实打撒打算啊实打实打撒大所","projectId":"e274a447c8e44f4396c031d4e933606e","startTime":1531450950920}
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
         * Cname : 09:00-17:00
         * Dname : 武汉仁和胜利车业有限公司
         * Pname : SWM斯威汽车形象店运营检核表
         * areaAddress : 湖北省武汉市江岸区
         * attendance : {}
         * classId : 424ad558f1c4499988a4a9cba4c52ed0
         * closeStatus : 0
         * code : C类
         * count : 48
         * dealerId : aca1cfd2b5514f62a576153f16c08802
         * endTime : 1
         * latitude : 30.662118
         * location : 竹叶山中环商贸城C9-11
         * longitude : 114.30359
         * notice : 的撒啊实打实打算多阿达撒打算的啊实打实打撒大所啊实打实打撒大所啊实打实打阿萨德啊实打实打算多啊实打实打撒大声地啊实打实打撒打算啊实打实打撒大所
         * projectId : e274a447c8e44f4396c031d4e933606e
         * startTime : 1531450950920
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
        private int endTime;
        private double latitude;
        private String location;
        private double longitude;
        private String notice;
        private String projectId;
        private long startTime;

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

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
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

        }
    }
}
