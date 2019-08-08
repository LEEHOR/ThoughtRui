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
             * answer : 2
             * censor : 1
             * describeStatus : -1
             * describes : 现场观察
             * id : c0dde6cc69f54f92be22ae0850bc4897
             * number : 1
             * options : 0&2
             * photoStatus : -1
             * quota2 : 停车场
             * quota3 : 停车场环境
             * recordStatus : -1
             * title : 【停车场-停车场环境】
             停车场地面整洁，无垃圾杂物
             * type : 0
             */

            private String answer;
            private int censor;
            private int describeStatus;
            private String describes;
            private String id;
            private int number;
            private String options;
            private int photoStatus;
            private String quota2;
            private String quota3;
            private int recordStatus;
            private String title;
            private int type;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public int getCensor() {
                return censor;
            }

            public void setCensor(int censor) {
                this.censor = censor;
            }

            public int getDescribeStatus() {
                return describeStatus;
            }

            public void setDescribeStatus(int describeStatus) {
                this.describeStatus = describeStatus;
            }

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getOptions() {
                return options;
            }

            public void setOptions(String options) {
                this.options = options;
            }

            public int getPhotoStatus() {
                return photoStatus;
            }

            public void setPhotoStatus(int photoStatus) {
                this.photoStatus = photoStatus;
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

            public int getRecordStatus() {
                return recordStatus;
            }

            public void setRecordStatus(int recordStatus) {
                this.recordStatus = recordStatus;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
