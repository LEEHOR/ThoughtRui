package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * 审核列表
 */
public class CensorBean {

    /**
     * data : {"list":[{"Dname":"东风本田东合店","Pname":"斯锐调查系统132456","areaAddress":"湖北省武汉市蔡甸区","code":"123","endTime":1,"id":"43c4065cc2494634b71bc870ebbd1766","inspect":1,"location":"东合中心B座","modifyTime":1527216075903,"number":1,"progress":"0/100","record":3,"startTime":1527216074020}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * Dname : 东风本田东合店
             * Pname : 斯锐调查系统132456
             * areaAddress : 湖北省武汉市蔡甸区
             * code : 123
             * endTime : 1
             * id : 43c4065cc2494634b71bc870ebbd1766
             * inspect : 1
             * location : 东合中心B座
             * modifyTime : 1527216075903
             * number : 1
             * progress : 0/100
             * record : 3
             * startTime : 1527216074020
             */

            private String Dname;
            private String Pname;
            private String areaAddress;
            private String code;
            private long endTime;
            private String id;
            private int inspect;
            private String location;
            private long modifyTime;
            private int number;
            private String progress;
            private int record;
            private long startTime;
            private String sale_code;
            private String service_code;

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

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
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

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
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
        }
    }
}
