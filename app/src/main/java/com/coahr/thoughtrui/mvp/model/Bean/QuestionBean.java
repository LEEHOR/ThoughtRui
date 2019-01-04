package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 16:51
 */
public class QuestionBean {
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
        private List<QuestionListBean> questionList;

        public List<QuestionListBean> getQuestionList() {
            return questionList;
        }

        public void setQuestionList(List<QuestionListBean> questionList) {
            this.questionList = questionList;
        }

        public static class QuestionListBean {
            /**
             * describeStatus : 1    是否说明
             * description : dsadas  题目描述
             * id : 4
             * quota1   指标
             * name : 汉口  指标
             * options : 是&否  答案
             * photoStatus : 1  拍照
             * recordStatus : 1 录音
             * title : dasd    标题
             * type : 0    题目类型
             */
//"censor":2,
//        "describeStatus":-1,
//        "description":"",
//        "id":"5a399f74604541caa0ed9bd3a3215efe",
//        "number":18,
//        "options":"是&否",
//        "photoStatus":1,
//        "quota1":"日常运营",
//        "quota2":"客户回访",
//        "quota3":"客户回访管理",
//        "recordStatus":1,
//        "title":"有录音设备支持回访工作，并对回访录音按照日期、回访内容分类存档管理。",
//        "type":0
            private Integer censor;
            private Integer describeStatus;
            private String description;
            private String id;
            private int number;
            private String options;
            private Integer photoStatus;
            private String quota1;
            private String quota2;
            private String quota3;

            private String title;
            private Integer type;

            public Integer getDescribeStatus() {
                return describeStatus;
            }

            public void setDescribeStatus(Integer describeStatus) {
                this.describeStatus = describeStatus;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public Integer getPhotoStatus() {
                return photoStatus;
            }

            public void setPhotoStatus(Integer photoStatus) {
                this.photoStatus = photoStatus;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public Integer getCensor() {
                return censor;
            }

            public void setCensor(Integer censor) {
                this.censor = censor;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }
}
