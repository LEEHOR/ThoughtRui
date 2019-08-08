package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Author： hengzwd on 2017/9/20.
 * Email：hengzwdhengzwd@qq.com
 */

public class UpdateBean {
    private int result;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "UpdateBean{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

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
        private String version;
        private String url;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "version='" + version + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
