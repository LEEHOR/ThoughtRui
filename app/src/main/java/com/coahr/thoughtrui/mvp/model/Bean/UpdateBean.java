package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Author： hengzwd on 2017/9/20.
 * Email：hengzwdhengzwd@qq.com
 */

public class UpdateBean {
    /**
     * result : 1
     * msg : 请求成功！
     * data : {"version":"1.0.3","url":"http://leinuo.coahr.com:8085/research/RBJ掌上检核v1.0.3.apk"}
     */

    private int result;
    private String msg;
    private DataBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * version : 1.0.3
         * url : http://leinuo.coahr.com:8085/research/RBJ掌上检核v1.0.3.apk
         */

        private String version;
        private String url;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

 /*   {
        "result": 1,
            "msg": "请求成功！",
            "data": {
        "version": "1.0.3",
                "url": "http://leinuo.coahr.com:8085/research/RBJ掌上检核v1.0.3.apk"
    }
    }*/


}
