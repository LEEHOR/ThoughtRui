package com.coahr.thoughtrui.mvp.model.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
public class CensorInfoList {

    /**
     * data : {"list":[{"id":"4654654","name":"王五","quota1":"湖北","quota2":"武汉","quota3":"汉口","stage":2,"suggestion":"rewrwerwe","title":"3432"},{"id":"4654654","name":"张三","quota1":"湖北","quota2":"武汉","quota3":"汉口","stage":2,"suggestion":"1","title":"545et"},{"id":"4654654","name":"张三","quota1":"湖北","quota2":"武汉","quota3":"汉口","stage":3,"suggestion":"ewqeqeq","title":"drrrrqe"}]}
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

        public static class ListBean  {
            /**
             * id : 4654654
             * name : 王五
             * quota1 : 湖北
             * quota2 : 武汉
             * quota3 : 汉口
             * stage : 2
             * suggestion : rewrwerwe
             * title : 3432
             */

            private String id;
            private String name;
            private String quota1;
            private String quota2;
            private String quota3;
            private int stage;
            private String suggestion;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQuota1() {
                return quota1;
            }

            public void setQuota1(String quota1) {
                this.quota1 = quota1;
            }

            public String getQuota2() {
                return quota2;
            }

            public void setQuota2(String quota2) {
                this.quota2 = quota2;
            }

            public String getQuota3() {
                return quota3;
            }

            public void setQuota3(String quota3) {
                this.quota3 = quota3;
            }

            public int getStage() {
                return stage;
            }

            public void setStage(int stage) {
                this.stage = stage;
            }

            public String getSuggestion() {
                return suggestion;
            }

            public void setSuggestion(String suggestion) {
                this.suggestion = suggestion;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
