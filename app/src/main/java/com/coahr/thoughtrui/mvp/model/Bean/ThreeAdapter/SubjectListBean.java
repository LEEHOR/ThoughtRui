package com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 15:50
 */
public class SubjectListBean {
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

            private String id;
            private String name;
            private List<?> quesList;
            private List<ValueBeanX> value;

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

            public List<?> getQuesList() {
                return quesList;
            }

            public void setQuesList(List<?> quesList) {
                this.quesList = quesList;
            }

            public List<ValueBeanX> getValue() {
                return value;
            }

            public void setValue(List<ValueBeanX> value) {
                this.value = value;
            }

            public static class ValueBeanX {
                private String id;
                private String name;
                private List<QuesListX> quesList;
                private List<ValueBean> value;

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

                public List<QuesListX> getQuesList() {
                    return quesList;
                }

                public void setQuesList(List<QuesListX> quesList) {
                    this.quesList = quesList;
                }

                public List<ValueBean> getValue() {
                    return value;
                }

                public void setValue(List<ValueBean> value) {
                    this.value = value;
                }

                public static class QuesListX {
                    /**
                     * id : 4472672568fc4b959fd9c82c4f11fd2f
                     * title : 展厅内每个展车旁均需放置一个技术参数牌，参数牌保持整洁、无破损，技术参数牌内容与展车车型吻合。
                     * answer : 否
                     * picture : 4_1.jpg;4_2.jpg
                     */

                    private String id;
                    private String title;
                    private String answer;
                    private String picture;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getAnswer() {
                        return answer;
                    }

                    public void setAnswer(String answer) {
                        this.answer = answer;
                    }

                    public String getPicture() {
                        return picture;
                    }

                    public void setPicture(String picture) {
                        this.picture = picture;
                    }
                }

                public static class ValueBean {


                    private String id;
                    private String name;
                    private List<QuesListBean> quesList;

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

                    public List<QuesListBean> getQuesList() {
                        return quesList;
                    }

                    public void setQuesList(List<QuesListBean> quesList) {
                        this.quesList = quesList;
                    }

                    public static class QuesListBean {
                        /**
                         * id : 4472672568fc4b959fd9c82c4f11fd2f
                         * title : 展厅内每个展车旁均需放置一个技术参数牌，参数牌保持整洁、无破损，技术参数牌内容与展车车型吻合。
                         * answer : 否
                         * picture : 4_1.jpg;4_2.jpg
                         */

                        private String id;
                        private String title;
                        private String answer;
                        private String picture;

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
                            this.id = id;
                        }

                        public String getTitle() {
                            return title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public String getAnswer() {
                            return answer;
                        }

                        public void setAnswer(String answer) {
                            this.answer = answer;
                        }

                        public String getPicture() {
                            return picture;
                        }

                        public void setPicture(String picture) {
                            this.picture = picture;
                        }
                    }
                }
            }
        }
    }
}
