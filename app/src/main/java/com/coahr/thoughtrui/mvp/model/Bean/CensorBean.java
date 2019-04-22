package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * 审核列表
 */
public class CensorBean {

    /**
     * data : {"list":[{"Dname":"重庆市万州区驰龙汽车服务有限公司","Pname":"测试325","areaAddress":"重庆市重庆市万州区","id":"099469b797f44afda3c73782cd8e6154","location":"重庆市万州区万全五组","modifyTime":1553843665784,"passCount":1,"progress":"1/20","repulseCount":0,"service_code":"F10023A10","type":1}]}
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
             * Dname : 重庆市万州区驰龙汽车服务有限公司
             * Pname : 测试325
             * areaAddress : 重庆市重庆市万州区
             * id : 099469b797f44afda3c73782cd8e6154
             * location : 重庆市万州区万全五组
             * modifyTime : 1553843665784
             * passCount : 1
             * progress : 1/20
             * repulseCount : 0
             * service_code : F10023A10
             * type : 1
             */

            private String Dname;
            private String Pname;
            private String areaAddress;
            private String id;
            private String location;
            private long modifyTime;
            private int passCount;
            private String progress;
            private int repulseCount;
            private String service_code;
            private int type;

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getPassCount() {
                return passCount;
            }

            public void setPassCount(int passCount) {
                this.passCount = passCount;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public int getRepulseCount() {
                return repulseCount;
            }

            public void setRepulseCount(int repulseCount) {
                this.repulseCount = repulseCount;
            }

            public String getService_code() {
                return service_code;
            }

            public void setService_code(String service_code) {
                this.service_code = service_code;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
