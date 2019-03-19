package com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/12/27
 * on 15:50
 */
public class SubjectListBean {

    /**
     * data : {"questionList":[{"id":"5301df6a0f5f451a93a03a705a6280a3","name":"网络营销","quesList":[{"id":"257896794cc04aacba7babd317dd3b6b","title":"是否开通微信公众号，并每周至少转发5条华晨雷诺金杯官方微信"}],"value":[]},{"id":"98afa31a3cb645bc95c4be5ee6db44e0","name":"二网管理","quesList":[{"id":"03e19cfd0d044bc3bd761ffa5b92a52c","title":"是否每月制定二网销售计划，有书面文件且有总经理签字"},{"id":"a652cc2d9cda4742b9d5bcde985aa5af","title":"每家经销商至少有两家二级网络（二网标准：有金杯品牌标识且有金杯展车）"}],"value":[]},{"id":"9761fcfe9fba4ddd9f99f315c224b38b","name":"销售管理","quesList":[{"id":"0cc5a7f4eec247c7aac28820fe09fca5","title":"交车前是否进行详细全面的车辆准备，包括车辆状态、随车附件、随车文档等；离开前销售顾问将客户转交给服务顾问，由服务顾问介绍售后服务内容"},{"id":"d3b3e55c322c44cba4dc686b97813939","title":"是否在电话铃响3声内（音乐彩铃10秒内）接听电话；在结束电话时表示感谢致电，并等您挂上电话后，电话接线人员再挂电话。"},{"id":"f0d1dab031344f2ca760c83175edd5cb","title":"交车后是否有5日内电话回访，并记录回访情况"},{"id":"84017c7a2c9b4890b8eab5dbfb8454f7","title":"有明确详细的交车流程,有正式、符合当地特色且令顾客愉悦的交车仪式"},{"id":"50f7e84bfc0e43ac9fc4f6e29eb3f657","title":"是否对潜在客户有销售跟进记录表"}],"value":[]},{"id":"6555a57f9afa4859a0b9085401b1ec16","name":"培训","quesList":[{"id":"d720edcf51924d9288f9255d3ecacdbc","title":"是否有经销商内训的书面或电子版月度培训计划"},{"id":"dbe60fb60cf646bebfb5a3e6277ac30c","title":"是否有经销商内训的培训签到登记（有现场照片、销售经理签字）"}],"value":[]},{"id":"3e80a1d691224039b91b94ad4c5d6a0f","name":"车辆管理","quesList":[{"id":"be137f5eaf3c413591092535289a4069","title":"是否对库存车辆进行定期维护保养"},{"id":"028d788befe643a2985fd7043415c56f","title":"从经销商收车到向客户交车的整个过程中，产品车的每次车辆监管责任人变更时都要进行车辆质量检查及管控（快速检查）"}],"value":[]},{"id":"a0181e24baa04edaa6bca5c6b15ff9e3","name":"组织架构","quesList":[{"id":"ac3b795bc95b491e98fa1118673a5354","title":"是否配备华晨雷诺专职销售经理"},{"id":"68b1b4030af14893adecd45ae4a044e6","title":"是否配备华晨雷诺专职市场经理"},{"id":"ea0cdd8da69f4b56b8a58f25395e8ddd","title":"是否配备华晨雷诺专职销售顾问，最低不少于4人"}],"value":[]},{"id":"855fc07980294416930c27fa00f3fedc","name":"会议管理","quesList":[{"id":"3977d9f13c4149758c0793648ea3ebec","title":"是否每天召开晨会，并有会议记要"},{"id":"bd59a0994c8445389fbf7a63aaaed6e0","title":"总经理是否每月组织召开跨部门质量会议，总结上月市场活动/销售业绩/产品/服务/培训等工作完成情况，制定下月目标，并有会议记录，总经理、销售经理、市场经理需签字"}],"value":[]},{"id":"ac947d0b69d24bbc8c6069d9131d28f9","name":"试乘试驾","quesList":[{"id":"67d6565166e141f29c425bc5acac2624","title":"是否有试乘试驾协议书；销售顾问向每位到店客户推荐试乘试驾"},{"id":"3dfb9c9fc6fd47e29f2f50e5c526a6d0","title":"是否有试驾车辆维护管理规定"},{"id":"6abb4132c5c547878eeec2141d617970","title":"试乘试驾结束后，是否有试乘试驾报告（反馈表）"}],"value":[]}]}
     * msg : 请求成功
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
        private List<QuestionListBean> questionList;

        public List<QuestionListBean> getQuestionList() {
            return questionList;
        }

        public void setQuestionList(List<QuestionListBean> questionList) {
            this.questionList = questionList;
        }

        public static class QuestionListBean {
            /**
             * id : 5301df6a0f5f451a93a03a705a6280a3
             * name : 网络营销
             * quesList : [{"id":"257896794cc04aacba7babd317dd3b6b","title":"是否开通微信公众号，并每周至少转发5条华晨雷诺金杯官方微信"}]
             * value : []
             */

            private String id;
            private String name;
            private List<QuesListBean> quesList;
            private List<?> value;

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

            public List<?> getValue() {
                return value;
            }

            public void setValue(List<?> value) {
                this.value = value;
            }

            public static class QuesListBean {
                /**
                 * id : 257896794cc04aacba7babd317dd3b6b
                 * title : 是否开通微信公众号，并每周至少转发5条华晨雷诺金杯官方微信
                 */

                private String id;
                private String title;

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
            }
        }
    }
}
