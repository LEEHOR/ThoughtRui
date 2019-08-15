package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * Created by 李浩
 * 2018/5/11
 */
public class HomeDataList {

    /**
     * data : {"allList":[{"Cname":"自由班次","Dname":"北京三江慧达汽车销售服务有限公司","Pname":"测试\u2014\u20142","city":"北京","areaAddress":"北京市北京市朝阳区","completeStatus":1,"grade":"授权一级","id":"849431134df3446e98659c651ad4feeb","latitude":40.006458,"location":"北京市朝阳区东辛店村|333号","longitude":116.504593,"manager":"付保东","modifyTime":1553678836706,"notice":"世界冠军就好了经济发达","progress":"0/20","sale_code":"AA11021","templateId":"fb485155f08e46b1b7c6819881812c43","totalScore":"0","uploadTime":1553194800000,"service_code":"A10021A43"},{"Cname":"09:00-17:00","Dname":"上海交运明友汽车销售服务有限公司","Pname":"AS-Standard 判断题 华晨雷诺核检系统题目 上传表","city":"上海","areaAddress":"上海市上海市闵行区","completeStatus":1,"id":"c32c2e12e67e40169d3a8f71b5b60ee7","latitude":31.227098,"location":"上海市闵行区华江路|1318号","longitude":121.326415,"manager":"邱纯亮","modifyTime":1554188959800,"notice":"","progress":"0/24","service_code":"A10021A43","templateId":"fb485155f08e46b1b7c6819881812c43","totalScore":"0","uploadTime":1555268400000},{"Cname":"09:00-17:00","Dname":"北京三江慧达汽车销售服务有限公司","Pname":"AS-Process华晨雷诺金杯授权经销商及服务站售后检核","city":"北京","areaAddress":"北京市北京市朝阳区","completeStatus":1,"grade":"授权一级","id":"cf0e377de0684914bb5fa1ddd2789ec4","latitude":40.006458,"location":"北京市朝阳区东辛店村|333号","longitude":116.504593,"manager":"付保东","modifyTime":1554275734612,"notice":"","progress":"24/24","sale_code":"AA11021","templateId":"fb485155f08e46b1b7c6819881812c43","totalScore":"48","uploadTime":1555268400000}],"completeList":[],"unCompleteList":[{"Cname":"自由班次","Dname":"北京三江慧达汽车销售服务有限公司","Pname":"测试\u2014\u20142","city":"北京","areaAddress":"北京市北京市朝阳区","completeStatus":1,"grade":"授权一级","id":"849431134df3446e98659c651ad4feeb","latitude":40.006458,"location":"北京市朝阳区东辛店村|333号","longitude":116.504593,"manager":"付保东","modifyTime":1553678836706,"notice":"世界冠军就好了经济发达","progress":"0/20","sale_code":"AA11021","templateId":"fb485155f08e46b1b7c6819881812c43","totalScore":"0","uploadTime":1553194800000,"service_code":"A10021A43"},{"Cname":"09:00-17:00","Dname":"上海交运明友汽车销售服务有限公司","Pname":"AS-Standard 判断题 华晨雷诺核检系统题目 上传表","city":"上海","areaAddress":"上海市上海市闵行区","completeStatus":1,"id":"c32c2e12e67e40169d3a8f71b5b60ee7","latitude":31.227098,"location":"上海市闵行区华江路|1318号","longitude":121.326415,"manager":"邱纯亮","modifyTime":1554188959800,"notice":"","progress":"0/24","service_code":"A10021A43","templateId":"fb485155f08e46b1b7c6819881812c43","totalScore":"0","uploadTime":1555268400000},{"Cname":"09:00-17:00","Dname":"北京三江慧达汽车销售服务有限公司","Pname":"AS-Process华晨雷诺金杯授权经销商及服务站售后检核","city":"北京","areaAddress":"北京市北京市朝阳区","completeStatus":1,"grade":"授权一级","id":"cf0e377de0684914bb5fa1ddd2789ec4","latitude":40.006458,"location":"北京市朝阳区东辛店村|333号","longitude":116.504593,"manager":"付保东","modifyTime":1554275734612,"notice":"","progress":"24/24","sale_code":"AA11021","templateId":"fb485155f08e46b1b7c6819881812c43","totalScore":"48","uploadTime":1555268400000}]}
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
             * Cname : 自由班次
             * Dname : 北京三江慧达汽车销售服务有限公司
             * Pname : 测试——2
             * city : 北京
             * areaAddress : 北京市北京市朝阳区
             * completeStatus : 1
             * grade : 授权一级
             * id : 849431134df3446e98659c651ad4feeb
             * latitude : 40.006458
             * location : 北京市朝阳区东辛店村|333号
             * longitude : 116.504593
             * manager : 付保东
             * modifyTime : 1553678836706
             * notice : 世界冠军就好了经济发达
             * progress : 0/20
             * sale_code : AA11021
             * templateId : fb485155f08e46b1b7c6819881812c43
             * totalScore : 0
             * uploadTime : 1553194800000
             * service_code : A10021A43
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String city;
            private String areaAddress;
            private int completeStatus;
            private String grade;
            private String id;
            private double latitude;
            private String location;
            private double longitude;
            private String manager;
            private long modifyTime;
            private String notice;
            private String progress;
            private String sale_code;
            private String templateId;
            private String totalScore;
            private long uploadTime;
            private String service_code;

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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

            public String getManager() {
                return manager;
            }

            public void setManager(String manager) {
                this.manager = manager;
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

            public String getTemplateId() {
                return templateId;
            }

            public void setTemplateId(String templateId) {
                this.templateId = templateId;
            }

            public String getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(String totalScore) {
                this.totalScore = totalScore;
            }

            public long getUploadTime() {
                return uploadTime;
            }

            public void setUploadTime(long uploadTime) {
                this.uploadTime = uploadTime;
            }

            public String getService_code() {
                return service_code;
            }

            public void setService_code(String service_code) {
                this.service_code = service_code;
            }
        }

        public static class UnCompleteListBean {
            /**
             * Cname : 自由班次
             * Dname : 北京三江慧达汽车销售服务有限公司
             * Pname : 测试——2
             * city : 北京
             * areaAddress : 北京市北京市朝阳区
             * completeStatus : 1
             * grade : 授权一级
             * id : 849431134df3446e98659c651ad4feeb
             * latitude : 40.006458
             * location : 北京市朝阳区东辛店村|333号
             * longitude : 116.504593
             * manager : 付保东
             * modifyTime : 1553678836706
             * notice : 世界冠军就好了经济发达
             * progress : 0/20
             * sale_code : AA11021
             * templateId : fb485155f08e46b1b7c6819881812c43
             * totalScore : 0
             * uploadTime : 1553194800000
             * service_code : A10021A43
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String city;
            private String areaAddress;
            private int completeStatus;
            private String grade;
            private String id;
            private double latitude;
            private String location;
            private double longitude;
            private String manager;
            private long modifyTime;
            private String notice;
            private String progress;
            private String sale_code;
            private String templateId;
            private String totalScore;
            private long uploadTime;
            private String service_code;

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

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
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

            public String getManager() {
                return manager;
            }

            public void setManager(String manager) {
                this.manager = manager;
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

            public String getTemplateId() {
                return templateId;
            }

            public void setTemplateId(String templateId) {
                this.templateId = templateId;
            }

            public String getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(String totalScore) {
                this.totalScore = totalScore;
            }

            public long getUploadTime() {
                return uploadTime;
            }

            public void setUploadTime(long uploadTime) {
                this.uploadTime = uploadTime;
            }

            public String getService_code() {
                return service_code;
            }

            public void setService_code(String service_code) {
                this.service_code = service_code;
            }
        }
    }
}
