package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * Created by 李浩
 * 2018/5/11
 */
public class HomeDataList {

    /**
     * data : {"allList":[{"Cname":"09:00-17:00","Dname":"武汉仁和胜利车业有限公司","Pname":"SWM斯威汽车形象店运营检核表","areaAddress":"湖北省武汉市江岸区","code":"C类","completeStatus":2,"downloadTime":1532934260126,"endTime":1,"grade":"11.43002","id":"e274a447c8e44f4396c031d4e933606e","inspect":3,"latitude":30.662118,"location":"竹叶山中环商贸城C9-11","longitude":114.30359,"manager":"刘畅","modifyTime":1531450950920,"notice":"的撒啊实打实打算多阿达撒打算的啊实打实打撒大所啊实打实打撒大所啊实打实打阿萨德啊实打实打算多啊实打实打撒大声地啊实打实打撒打算啊实打实打撒大所","progress":"123/48","record":2,"startTime":1531450950920},{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"测试案例","areaAddress":"湖北省武汉市东西湖区","code":"1111111","completeStatus":2,"downloadTime":1533518791033,"endTime":1,"grade":"QQQQQ","id":"a26c182c29ea4dc7835c34d7610fc486","inspect":3,"latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"manager":"刘先生","modifyTime":1533518636339,"notice":"大实打实大师多啊实打实大声地啊实打实打啊实打实大所大声地啊实打实打阿萨德啊实打实打啊实打实打算多啊打算打算多啊实打实大声地","progress":"506/48","record":2,"startTime":1533518636339},{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"test01","areaAddress":"湖北省武汉市东西湖区","code":"1111111","completeStatus":2,"downloadTime":1533785260036,"endTime":1534237834291,"grade":"QQQQQ","id":"b6ecf9f2dedd4f23be066b31cf05ccdb","inspect":3,"latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"manager":"李浩","modifyTime":1534237834291,"notice":"测试","progress":"33/48","record":3,"startTime":1533785239618},{"Cname":"09:00-17:00","Dname":"2233333","Pname":"10:07:22@SWM斯威汽车形象店运营检核表","areaAddress":"安徽省芜湖市繁昌县","code":"2222222222","completeStatus":2,"downloadTime":1537150763040,"endTime":1,"grade":"222222","id":"b547e79a54fa40cf8c45ed9461907e8e","inspect":3,"latitude":31.080896,"location":"2222","longitude":118.201349,"manager":"333333333","modifyTime":1537150122216,"progress":"0/48","record":3,"startTime":1537150122216},{"Cname":"09:00-17:00","Dname":"武汉友芝友","Pname":"111111","areaAddress":"湖北省武汉市汉阳区","code":"FB2","completeStatus":2,"downloadTime":1537151856306,"endTime":1,"grade":"2340112","id":"d5774045c3a54b45b5840acce883f0db","inspect":1,"latitude":30.544856,"location":"龙阳大道100号","longitude":114.201838,"manager":"22222","modifyTime":1537149722964,"notice":"22222","progress":"0/4","record":2,"startTime":1537149722964}],"completeList":[],"newList":[],"unCompleteList":[{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"测试案例","areaAddress":"湖北省武汉市东西湖区","code":"1111111","completeStatus":2,"downloadTime":1533518791033,"endTime":1,"grade":"QQQQQ","id":"a26c182c29ea4dc7835c34d7610fc486","inspect":3,"latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"manager":"刘先生","modifyTime":1533518636339,"notice":"大实打实大师多啊实打实大声地啊实打实打啊实打实大所大声地啊实打实打阿萨德啊实打实打啊实打实打算多啊打算打算多啊实打实大声地","progress":"506/48","record":2,"startTime":1533518636339},{"Cname":"09:00-17:00","Dname":"一汽奥迪武汉客厅店","Pname":"test01","areaAddress":"湖北省武汉市东西湖区","code":"1111111","completeStatus":2,"downloadTime":1533785260036,"endTime":1534237834291,"grade":"QQQQQ","id":"b6ecf9f2dedd4f23be066b31cf05ccdb","inspect":3,"latitude":30.671419,"location":"武汉客厅","longitude":114.28061,"manager":"李浩","modifyTime":1534237834291,"notice":"测试","progress":"33/48","record":3,"startTime":1533785239618},{"Cname":"09:00-17:00","Dname":"2233333","Pname":"10:07:22@SWM斯威汽车形象店运营检核表","areaAddress":"安徽省芜湖市繁昌县","code":"2222222222","completeStatus":2,"downloadTime":1537150763040,"endTime":1,"grade":"222222","id":"b547e79a54fa40cf8c45ed9461907e8e","inspect":3,"latitude":31.080896,"location":"2222","longitude":118.201349,"manager":"333333333","modifyTime":1537150122216,"progress":"0/48","record":3,"startTime":1537150122216},{"Cname":"09:00-17:00","Dname":"武汉友芝友","Pname":"111111","areaAddress":"湖北省武汉市汉阳区","code":"FB2","completeStatus":2,"downloadTime":1537151856306,"endTime":1,"grade":"2340112","id":"d5774045c3a54b45b5840acce883f0db","inspect":1,"latitude":30.544856,"location":"龙阳大道100号","longitude":114.201838,"manager":"22222","modifyTime":1537149722964,"notice":"22222","progress":"0/4","record":2,"startTime":1537149722964}]}
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
        private List<CompleteListBean> completeList;
        private List<newListBean> newList;
        private List<UnCompleteListBean> unCompleteList;

        public List<AllListBean> getAllList() {
            return allList;
        }

        public void setAllList(List<AllListBean> allList) {
            this.allList = allList;
        }

        public List<CompleteListBean> getCompleteList() {
            return completeList;
        }

        public void setCompleteList(List<CompleteListBean> completeList) {
            this.completeList = completeList;
        }

        public List<newListBean> getNewList() {
            return newList;
        }

        public void setNewList(List<newListBean> newList) {
            this.newList = newList;
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
             * Dname : 武汉仁和胜利车业有限公司
             * Pname : SWM斯威汽车形象店运营检核表
             * areaAddress : 湖北省武汉市江岸区
             * code : C类
             * completeStatus : 2
             * downloadTime : 1532934260126
             * endTime : 1
             * grade : 11.43002
             * id : e274a447c8e44f4396c031d4e933606e
             * inspect : 3
             * latitude : 30.662118
             * location : 竹叶山中环商贸城C9-11
             * longitude : 114.30359
             * manager : 刘畅
             * modifyTime : 1531450950920
             * notice : 的撒啊实打实打算多阿达撒打算的啊实打实打撒大所啊实打实打撒大所啊实打实打阿萨德啊实打实打算多啊实打实打撒大声地啊实打实打撒打算啊实打实打撒大所
             * progress : 123/48
             * record : 2
             * startTime : 1531450950920
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String areaAddress;
            private String code;
            private int completeStatus;
            private long downloadTime;
            private Long endTime;
            private String grade;
            private String id;
            private int inspect;
            private double latitude;
            private String location;
            private double longitude;
            private String manager;
            private long modifyTime;
            private String notice;
            private String progress;
            private int record;
            private long startTime;
            private String sale_code;
            private String service_code;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getDownloadTime() {
                return downloadTime;
            }

            public void setDownloadTime(long downloadTime) {
                this.downloadTime = downloadTime;
            }

            public Long getEndTime() {
                return endTime;
            }

            public void setEndTime(Long endTime) {
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

            public int getInspect() {
                return inspect;
            }

            public void setInspect(int inspect) {
                this.inspect = inspect;
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

            public int getRecord() {
                return record;
            }

            public void setRecord(int record) {
                this.record = record;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }
        }

        public static class UnCompleteListBean {
            /**
             * Cname : 09:00-17:00
             * Dname : 一汽奥迪武汉客厅店
             * Pname : 测试案例
             * areaAddress : 湖北省武汉市东西湖区
             * code : 1111111
             * completeStatus : 2
             * downloadTime : 1533518791033
             * endTime : 1
             * grade : QQQQQ
             * id : a26c182c29ea4dc7835c34d7610fc486
             * inspect : 3
             * latitude : 30.671419
             * location : 武汉客厅
             * longitude : 114.28061
             * manager : 刘先生
             * modifyTime : 1533518636339
             * notice : 大实打实大师多啊实打实大声地啊实打实打啊实打实大所大声地啊实打实打阿萨德啊实打实打啊实打实打算多啊打算打算多啊实打实大声地
             * progress : 506/48
             * record : 2
             * startTime : 1533518636339
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String areaAddress;
            private String code;
            private int completeStatus;
            private long downloadTime;
            private Long endTime;
            private String grade;
            private String id;
            private int inspect;
            private double latitude;
            private String location;
            private double longitude;
            private String manager;
            private long modifyTime;
            private String notice;
            private String progress;
            private int record;
            private long startTime;
            private String sale_code;
            private String service_code;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getDownloadTime() {
                return downloadTime;
            }

            public void setDownloadTime(long downloadTime) {
                this.downloadTime = downloadTime;
            }

            public Long getEndTime() {
                return endTime;
            }

            public void setEndTime(Long endTime) {
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

            public int getInspect() {
                return inspect;
            }

            public void setInspect(int inspect) {
                this.inspect = inspect;
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

            public int getRecord() {
                return record;
            }

            public void setRecord(int record) {
                this.record = record;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }
        }

        public static class CompleteListBean {
            /**
             * Cname : 09:00-17:00
             * Dname : 一汽奥迪武汉客厅店
             * Pname : 测试案例
             * areaAddress : 湖北省武汉市东西湖区
             * code : 1111111
             * completeStatus : 2
             * downloadTime : 1533518791033
             * endTime : 1
             * grade : QQQQQ
             * id : a26c182c29ea4dc7835c34d7610fc486
             * inspect : 3
             * latitude : 30.671419
             * location : 武汉客厅
             * longitude : 114.28061
             * manager : 刘先生
             * modifyTime : 1533518636339
             * notice : 大实打实大师多啊实打实大声地啊实打实打啊实打实大所大声地啊实打实打阿萨德啊实打实打啊实打实打算多啊打算打算多啊实打实大声地
             * progress : 506/48
             * record : 2
             * startTime : 1533518636339
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String areaAddress;
            private String code;
            private int completeStatus;
            private long downloadTime;
            private Long endTime;
            private String grade;
            private String id;
            private int inspect;
            private double latitude;
            private String location;
            private double longitude;
            private String manager;
            private long modifyTime;
            private String notice;
            private String progress;
            private int record;
            private long startTime;
            private String sale_code;
            private String service_code;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getDownloadTime() {
                return downloadTime;
            }

            public void setDownloadTime(long downloadTime) {
                this.downloadTime = downloadTime;
            }

            public Long getEndTime() {
                return endTime;
            }

            public void setEndTime(Long endTime) {
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

            public int getInspect() {
                return inspect;
            }

            public void setInspect(int inspect) {
                this.inspect = inspect;
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

            public int getRecord() {
                return record;
            }

            public void setRecord(int record) {
                this.record = record;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }
        }
        public static class newListBean {
            /**
             * Cname : 09:00-17:00
             * Dname : 一汽奥迪武汉客厅店
             * Pname : 测试案例
             * areaAddress : 湖北省武汉市东西湖区
             * code : 1111111
             * completeStatus : 2
             * downloadTime : 1533518791033
             * endTime : 1
             * grade : QQQQQ
             * id : a26c182c29ea4dc7835c34d7610fc486
             * inspect : 3
             * latitude : 30.671419
             * location : 武汉客厅
             * longitude : 114.28061
             * manager : 刘先生
             * modifyTime : 1533518636339
             * notice : 大实打实大师多啊实打实大声地啊实打实打啊实打实大所大声地啊实打实打阿萨德啊实打实打啊实打实打算多啊打算打算多啊实打实大声地
             * progress : 506/48
             * record : 2
             * startTime : 1533518636339
             */

            private String Cname;
            private String Dname;
            private String Pname;
            private String areaAddress;
            private String code;
            private int completeStatus;
            private long downloadTime;
            private Long endTime;
            private String grade;
            private String id;
            private int inspect;
            private double latitude;
            private String location;
            private double longitude;
            private String manager;
            private long modifyTime;
            private String notice;
            private String progress;
            private int record;
            private long startTime;
            private String sale_code;
            private String service_code;

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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getDownloadTime() {
                return downloadTime;
            }

            public void setDownloadTime(long downloadTime) {
                this.downloadTime = downloadTime;
            }

            public Long getEndTime() {
                return endTime;
            }

            public void setEndTime(Long endTime) {
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

            public int getInspect() {
                return inspect;
            }

            public void setInspect(int inspect) {
                this.inspect = inspect;
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

            public int getRecord() {
                return record;
            }

            public void setRecord(int record) {
                this.record = record;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }
        }
    }
}
