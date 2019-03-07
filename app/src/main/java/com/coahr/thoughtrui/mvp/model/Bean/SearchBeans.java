package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/7
 * 描述：搜索
 */
public class SearchBeans {

    /**
     * data : {"searchList":[{"Dname":"东风本田东合店","Pname":"斯锐调查系统132456","areaAddress":"湖北省武汉市蔡甸区","code":"123","endTime":1,"id":"43c4065cc2494634b71bc870ebbd1766","inspect":1,"location":"东合中心B座","modifyTime":1526029757614,"record":3,"startTime":1526029757500},{"Dname":"上汽别克沌口店","Pname":"dasdadd","areaAddress":"湖北省武汉市蔡甸区","code":"1234","endTime":1,"id":"5b3e4b1ca80b4e6e930fc6782dee3060","inspect":1,"location":"后官湖湿地公园","modifyTime":1525946794431,"record":1,"startTime":1523721600000}]}
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
        private List<SearchListBean> searchList;

        public List<SearchListBean> getSearchList() {
            return searchList;
        }

        public void setSearchList(List<SearchListBean> searchList) {
            this.searchList = searchList;
        }

        public static class SearchListBean {
            /**
             * Dname : 东风本田东合店
             * Pname : 斯锐调查系统132456
             * areaAddress : 湖北省武汉市蔡甸区
             * code : 123
             * endTime : 1
             * id : 43c4065cc2494634b71bc870ebbd1766
             * inspect : 1
             * location : 东合中心B座
             * modifyTime : 1526029757614
             * record : 3
             * startTime : 1526029757500
             */

            private String Dname;
            private String Pname;
            private String areaAddress;
            private String code;
            private int endTime;
            private String id;
            private int inspect;
            private String location;
            private long modifyTime;
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

            public int getEndTime() {
                return endTime;
            }

            public void setEndTime(int endTime) {
                this.endTime = endTime;
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

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public long getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(long modifyTime) {
                this.modifyTime = modifyTime;
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
