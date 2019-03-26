package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * Created by 李浩
 * 2018/5/11
 */
public class HomeDataList {


    /**
     * data : {"allList":[{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"rrtret","areaAddress":"湖北省武汉市东西湖区","completeStatus":2,"endTime":1534237834291,"grade":"QQQQQ","id":"b6ecf9f2dedd4f23be066b31cf05ccdb","latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"modifyTime":1534237834291,"notice":"terter","progress":"79/48","sale_code":"1111111","service_code":"FB2","startTime":1533785239618},{"Cname":"09:00-17:00","Dname":"2233333","Pname":"rrtret","areaAddress":"安徽省芜湖市繁昌县","completeStatus":2,"endTime":1,"grade":"222222","id":"b547e79a54fa40cf8c45ed9461907e8e","latitude":31.080896,"location":"2222","longitude":118.201349,"modifyTime":1537150122216,"notice":"terter","progress":"20/48","sale_code":"2222222222","service_code":"FB2","startTime":1537150122216},{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"rrtret","areaAddress":"湖北省武汉市东西湖区","completeStatus":2,"endTime":1,"grade":"QQQQQ","id":"7d1c7a64d98f4046ad6ed4a36379a6c9","latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"modifyTime":1550454821937,"notice":"terter","progress":"0/20","sale_code":"1111111","service_code":"FB2","startTime":1550454821937},{"Dname":"东风本田东合店","Pname":"rrtret","areaAddress":"湖北省武汉市蔡甸区","completeStatus":1,"grade":"ABC","id":"668745247f294993a878516ca637da1b","latitude":30.506405,"location":"东合中心B座","longitude":114.163499,"modifyTime":1551929144902,"notice":"terter","progress":"0/0","sale_code":"123","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"2cee2a04e8e7474ab7490b6a370c5734","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551940078434,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"0d0f50728a7f47148c8334d1a8d5572f","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941076211,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"35d7568c042a45ed80657b36879d5761","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941103028,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"7488c5e00c40400aa1780bf6d13f6efa","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941144276,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"1872fa352e4d41ec9bd12b962b9db5ec","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941202848,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"0bc550c8086848f5ba168468e3091caf","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941238541,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":1,"grade":"A级","id":"f5c0894dd4a94bafab758b4f94b60a19","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941426011,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":2,"grade":"A级","id":"9b22aa8ca6d04331a54a87bbce2d943d","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941767108,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"},{"Dname":"东风本田东合店","Pname":"rrtret","areaAddress":"湖北省武汉市蔡甸区","completeStatus":1,"grade":"ABC","id":"51e3e69e42a044ce9d3aaa87dbea2f93","latitude":30.506405,"location":"东合中心B座","longitude":114.163499,"modifyTime":1551942812731,"notice":"terter","progress":"0/0","sale_code":"123","service_code":"FB2"},{"Dname":"东风本田东合店","Pname":"rrtret","areaAddress":"湖北省武汉市蔡甸区","completeStatus":1,"grade":"ABC","id":"bcaa02272be94c3ea09788ef38837645","latitude":30.506405,"location":"东合中心B座","longitude":114.163499,"modifyTime":1551942828135,"notice":"terter","progress":"0/0","sale_code":"123","service_code":"FB2"},{"Dname":"东风本田东合店","Pname":"rrtret","areaAddress":"湖北省武汉市蔡甸区","completeStatus":1,"grade":"ABC","id":"dde3c340ab5845e4af3582fdec7141f6","latitude":30.506405,"location":"东合中心B座","longitude":114.163499,"modifyTime":1551942994230,"notice":"terter","progress":"0/0","sale_code":"123","service_code":"FB2"},{"Dname":"梅德赛斯奔驰朝阳公园店","Pname":"rrtret","areaAddress":"北京市北京市朝阳区","completeStatus":1,"grade":"A级","id":"0d7c75de6a464923af82dc68831b036c","latitude":39.944304,"location":"朝阳公园","longitude":116.482534,"modifyTime":1551947702539,"notice":"terter","progress":"0/0","sale_code":"0231154","service_code":"FB2"}],"completeList":[],"unCompleteList":[{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"rrtret","areaAddress":"湖北省武汉市东西湖区","completeStatus":2,"endTime":1534237834291,"grade":"QQQQQ","id":"b6ecf9f2dedd4f23be066b31cf05ccdb","latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"modifyTime":1534237834291,"notice":"terter","progress":"79/48","sale_code":"1111111","service_code":"FB2","startTime":1533785239618},{"Cname":"09:00-17:00","Dname":"2233333","Pname":"rrtret","areaAddress":"安徽省芜湖市繁昌县","completeStatus":2,"endTime":1,"grade":"222222","id":"b547e79a54fa40cf8c45ed9461907e8e","latitude":31.080896,"location":"2222","longitude":118.201349,"modifyTime":1537150122216,"notice":"terter","progress":"20/48","sale_code":"2222222222","service_code":"FB2","startTime":1537150122216},{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"rrtret","areaAddress":"湖北省武汉市东西湖区","completeStatus":2,"endTime":1,"grade":"QQQQQ","id":"7d1c7a64d98f4046ad6ed4a36379a6c9","latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"modifyTime":1550454821937,"notice":"terter","progress":"0/20","sale_code":"1111111","service_code":"FB2","startTime":1550454821937},{"Dname":"华晨雷诺金杯武汉中北路店","Pname":"rrtret","areaAddress":"湖北省武汉市武昌区","completeStatus":2,"grade":"A级","id":"9b22aa8ca6d04331a54a87bbce2d943d","latitude":30.570267,"location":"中北路地铁站A出口旁","longitude":114.355809,"modifyTime":1551941767108,"notice":"terter","progress":"0/0","sale_code":"A20","service_code":"FB2"}]}
     * msg : 请求成功！
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
        private List<AllListBean> allList;
        private List<?> completeList;
        private List<UnCompleteListBean> unCompleteList;

        public List<AllListBean> getAllList() {
            return allList;
        }

        public void setAllList(List<AllListBean> allList) {
            this.allList = allList;
        }

        public List<?> getCompleteList() {
            return completeList;
        }

        public void setCompleteList(List<?> completeList) {
            this.completeList = completeList;
        }

        public List<UnCompleteListBean> getUnCompleteList() {
            return unCompleteList;
        }

        public void setUnCompleteList(List<UnCompleteListBean> unCompleteList) {
            this.unCompleteList = unCompleteList;
        }

        public static class AllListBean {
            /**
             * Cname : 09:00-17:00
             * Dname : 一汽奥迪武汉客厅店
             * Pname : rrtret
             * areaAddress : 湖北省武汉市东西湖区
             * completeStatus : 2
             * endTime : 1534237834291
             * grade : QQQQQ
             * id : b6ecf9f2dedd4f23be066b31cf05ccdb
             * latitude : 30.671419
             * location : 武汉客厅
             * longitude : 114.28061
             * modifyTime : 1534237834291
             * notice : terter
             * progress : 79/48
             * sale_code : 1111111
             * service_code : FB2
             * startTime : 1533785239618
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String areaAddress;
            private int completeStatus;
            private long endTime;
            private String grade;
            private String id;
            private double latitude;
            private String location;
            private double longitude;
            private long modifyTime;
            private String notice;
            private String progress;
            private String sale_code;
            private String service_code;
            private long startTime;
            private String manager;

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

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public String getSale_code() {
                return sale_code;
            }

            public void setSale_code(String sale_code) {
                this.sale_code = sale_code;
            }

            public String getService_code() {
                return service_code;
            }

            public void setService_code(String service_code) {
                this.service_code = service_code;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public String getManager() {
                return manager;
            }

            public void setManager(String manager) {
                this.manager = manager;
            }
        }

        public static class UnCompleteListBean {
            /**
             * Cname : 09:00-17:00
             * Dname : 一汽奥迪武汉客厅店
             * Pname : rrtret
             * areaAddress : 湖北省武汉市东西湖区
             * completeStatus : 2
             * endTime : 1534237834291
             * grade : QQQQQ
             * id : b6ecf9f2dedd4f23be066b31cf05ccdb
             * latitude : 30.671419
             * location : 武汉客厅
             * longitude : 114.28061
             * modifyTime : 1534237834291
             * notice : terter
             * progress : 79/48
             * sale_code : 1111111
             * service_code : FB2
             * startTime : 1533785239618
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String areaAddress;
            private int completeStatus;
            private long endTime;
            private String grade;
            private String id;
            private double latitude;
            private String location;
            private double longitude;
            private long modifyTime;
            private String notice;
            private String progress;
            private String sale_code;
            private String service_code;
            private long startTime;
            private String manager;

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

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public String getSale_code() {
                return sale_code;
            }

            public void setSale_code(String sale_code) {
                this.sale_code = sale_code;
            }

            public String getService_code() {
                return service_code;
            }

            public void setService_code(String service_code) {
                this.service_code = service_code;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public String getManager() {
                return manager;
            }

            public void setManager(String manager) {
                this.manager = manager;
            }
        }
    }
}
