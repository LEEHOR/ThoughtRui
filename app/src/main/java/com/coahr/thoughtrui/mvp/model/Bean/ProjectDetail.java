package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/6
 * 描述：
 */
public class ProjectDetail {

    /**
     * status : 1
     * data : {"Cname":"自由班次","progress":"0/2","manager":"sghah","location":"朝阳公园","id":"dfbb64f1f09740de981b1fb5819e146d","sale_code":"0231154","areaAddress":"北京市北京市朝阳区","grade":"A级","Pname":"rrtret","Dname":"梅德赛斯奔驰朝阳公园店","longitude":116.482534,"uploadTime":1552071600000,"latitude":39.944304,"service_code":"FB2","notice":"terter"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Cname : 自由班次
         * progress : 0/2
         * manager : sghah
         * location : 朝阳公园
         * id : dfbb64f1f09740de981b1fb5819e146d
         * sale_code : 0231154
         * areaAddress : 北京市北京市朝阳区
         * grade : A级
         * Pname : rrtret
         * Dname : 梅德赛斯奔驰朝阳公园店
         * longitude : 116.482534
         * uploadTime : 1552071600000
         * latitude : 39.944304
         * service_code : FB2
         * notice : terter
         */

        private String Cname;
        private String progress;
        private String manager;
        private String location;
        private String id;
        private String sale_code;
        private String areaAddress;
        private String grade;
        private String Pname;
        private String Dname;
        private double longitude;
        private long uploadTime;
        private double latitude;
        private String service_code;
        private String notice;

        public String getCname() {
            return Cname;
        }

        public void setCname(String Cname) {
            this.Cname = Cname;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSale_code() {
            return sale_code;
        }

        public void setSale_code(String sale_code) {
            this.sale_code = sale_code;
        }

        public String getAreaAddress() {
            return areaAddress;
        }

        public void setAreaAddress(String areaAddress) {
            this.areaAddress = areaAddress;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getPname() {
            return Pname;
        }

        public void setPname(String Pname) {
            this.Pname = Pname;
        }

        public String getDname() {
            return Dname;
        }

        public void setDname(String Dname) {
            this.Dname = Dname;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getService_code() {
            return service_code;
        }

        public void setService_code(String service_code) {
            this.service_code = service_code;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }
    }
}
