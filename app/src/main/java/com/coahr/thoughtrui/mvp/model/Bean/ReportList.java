package com.coahr.thoughtrui.mvp.model.Bean;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/15
 * 描述：提报列表
 */
public class ReportList {

    /**
     * result : 1
     * msg : 请求成功！
     * data : {"unCompleteList":[{"quota1":null,"Uname":"liuc","quota2":"组织架构","firstTime":"1555311372409","address":"湖北省武汉市蔡甸区","count":2,"diagnosis":"不行的","reasonList":[{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}],"measures":"rewrwerw","executor":"小刘","levelId":"68e5e84121334af7b37aa19d95d13b3a","name":"【流程】华晨雷诺金杯授权经销商销售流程检核","newestTime":1555311609518,"projectId":"6a59a7206bae499b80e527207d1c5f5e","Dname":"湖北三环盛通汽车有限公司"}],"allList":[{"quota1":null,"Uname":"liuc","quota2":"组织架构","firstTime":"1555311372409","address":"湖北省武汉市蔡甸区","count":2,"diagnosis":"不行的","reasonList":[{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}],"measures":"rewrwerw","executor":"小刘","levelId":"68e5e84121334af7b37aa19d95d13b3a","name":"【流程】华晨雷诺金杯授权经销商销售流程检核","newestTime":1555311609518,"projectId":"6a59a7206bae499b80e527207d1c5f5e","Dname":"湖北三环盛通汽车有限公司"}],"completeList":[],"submitStatus":true}
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
         * unCompleteList : [{"quota1":null,"Uname":"liuc","quota2":"组织架构","firstTime":"1555311372409","address":"湖北省武汉市蔡甸区","count":2,"diagnosis":"不行的","reasonList":[{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}],"measures":"rewrwerw","executor":"小刘","levelId":"68e5e84121334af7b37aa19d95d13b3a","name":"【流程】华晨雷诺金杯授权经销商销售流程检核","newestTime":1555311609518,"projectId":"6a59a7206bae499b80e527207d1c5f5e","Dname":"湖北三环盛通汽车有限公司"}]
         * allList : [{"quota1":null,"Uname":"liuc","quota2":"组织架构","firstTime":"1555311372409","address":"湖北省武汉市蔡甸区","count":2,"diagnosis":"不行的","reasonList":[{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}],"measures":"rewrwerw","executor":"小刘","levelId":"68e5e84121334af7b37aa19d95d13b3a","name":"【流程】华晨雷诺金杯授权经销商销售流程检核","newestTime":1555311609518,"projectId":"6a59a7206bae499b80e527207d1c5f5e","Dname":"湖北三环盛通汽车有限公司"}]
         * completeList : []
         * submitStatus : true
         */

        private boolean submitStatus;
        private List<UnCompleteListBean> unCompleteList;
        private List<AllListBean> allList;
        private List<CompleteListBean> completeList;

        public boolean isSubmitStatus() {
            return submitStatus;
        }

        public void setSubmitStatus(boolean submitStatus) {
            this.submitStatus = submitStatus;
        }

        public List<UnCompleteListBean> getUnCompleteList() {
            return unCompleteList;
        }

        public void setUnCompleteList(List<UnCompleteListBean> unCompleteList) {
            this.unCompleteList = unCompleteList;
        }

        public List<AllListBean> getAllList() {
            return allList;
        }

        public void setAllList(List<AllListBean> allList) {
            this.allList = allList;
        }

        public List<CompleteListBean> getCompleteList() {
            return completeList;
        }

        public void setCompleteList(List<CompleteListBean> completeList) {
            this.completeList = completeList;
        }

        public static class UnCompleteListBean {
            /**
             * quota1 : null
             * Uname : liuc
             * quota2 : 组织架构
             * firstTime : 1555311372409
             * address : 湖北省武汉市蔡甸区
             * count : 2
             * diagnosis : 不行的
             * reasonList : [{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}]
             * measures : rewrwerw
             * executor : 小刘
             * levelId : 68e5e84121334af7b37aa19d95d13b3a
             * name : 【流程】华晨雷诺金杯授权经销商销售流程检核
             * completeStatus :  3
             * newestTime : 1555311609518
             * projectId : 6a59a7206bae499b80e527207d1c5f5e
             * Dname : 湖北三环盛通汽车有限公司
             */

            private Object quota1;
            private String Uname;
            private String quota2;
            private String firstTime;
            private String address;
            private int count;
            private String diagnosis;
            private String measures;
            private String executor;
            private String levelId;
            private String name;
            private int completeStatus;
            private long newestTime;
            private String projectId;
            private String Dname;
            private List<ReasonListBean> reasonList;

            public Object getQuota1() {
                return quota1;
            }

            public void setQuota1(Object quota1) {
                this.quota1 = quota1;
            }

            public String getUname() {
                return Uname;
            }

            public void setUname(String Uname) {
                this.Uname = Uname;
            }

            public String getQuota2() {
                return quota2;
            }

            public void setQuota2(String quota2) {
                this.quota2 = quota2;
            }

            public String getFirstTime() {
                return firstTime;
            }

            public void setFirstTime(String firstTime) {
                this.firstTime = firstTime;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getDiagnosis() {
                return diagnosis;
            }

            public void setDiagnosis(String diagnosis) {
                this.diagnosis = diagnosis;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public String getExecutor() {
                return executor;
            }

            public void setExecutor(String executor) {
                this.executor = executor;
            }

            public String getLevelId() {
                return levelId;
            }

            public void setLevelId(String levelId) {
                this.levelId = levelId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getNewestTime() {
                return newestTime;
            }

            public void setNewestTime(long newestTime) {
                this.newestTime = newestTime;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public String getDname() {
                return Dname;
            }

            public void setDname(String Dname) {
                this.Dname = Dname;
            }

            public List<ReasonListBean> getReasonList() {
                return reasonList;
            }

            public void setReasonList(List<ReasonListBean> reasonList) {
                this.reasonList = reasonList;
            }

            public static class ReasonListBean {
                /**
                 * submitTime : 1555311609518
                 * incompleteReason : 2222
                 */

                private long submitTime;
                private String incompleteReason;

                public long getSubmitTime() {
                    return submitTime;
                }

                public void setSubmitTime(long submitTime) {
                    this.submitTime = submitTime;
                }

                public String getIncompleteReason() {
                    return incompleteReason;
                }

                public void setIncompleteReason(String incompleteReason) {
                    this.incompleteReason = incompleteReason;
                }
            }
        }

        public static class AllListBean {
            /**
             * quota1 : null
             * Uname : liuc
             * quota2 : 组织架构
             * firstTime : 1555311372409
             * address : 湖北省武汉市蔡甸区
             * count : 2
             * diagnosis : 不行的
             * reasonList : [{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}]
             * measures : rewrwerw
             * executor : 小刘
             * levelId : 68e5e84121334af7b37aa19d95d13b3a
             * name : 【流程】华晨雷诺金杯授权经销商销售流程检核
             * completeStatus : -1
             * newestTime : 1555311609518
             * projectId : 6a59a7206bae499b80e527207d1c5f5e
             * Dname : 湖北三环盛通汽车有限公司
             */

            private Object quota1;
            private String Uname;
            private String quota2;
            private String firstTime;
            private String address;
            private int count;
            private String diagnosis;
            private String measures;
            private String executor;
            private String levelId;
            private String name;
            private int completeStatus;
            private long newestTime;
            private String projectId;
            private String Dname;
            private List<ReasonListBeanX> reasonList;

            public Object getQuota1() {
                return quota1;
            }

            public void setQuota1(Object quota1) {
                this.quota1 = quota1;
            }

            public String getUname() {
                return Uname;
            }

            public void setUname(String Uname) {
                this.Uname = Uname;
            }

            public String getQuota2() {
                return quota2;
            }

            public void setQuota2(String quota2) {
                this.quota2 = quota2;
            }

            public String getFirstTime() {
                return firstTime;
            }

            public void setFirstTime(String firstTime) {
                this.firstTime = firstTime;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getDiagnosis() {
                return diagnosis;
            }

            public void setDiagnosis(String diagnosis) {
                this.diagnosis = diagnosis;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public String getExecutor() {
                return executor;
            }

            public void setExecutor(String executor) {
                this.executor = executor;
            }

            public String getLevelId() {
                return levelId;
            }

            public void setLevelId(String levelId) {
                this.levelId = levelId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getNewestTime() {
                return newestTime;
            }

            public void setNewestTime(long newestTime) {
                this.newestTime = newestTime;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public String getDname() {
                return Dname;
            }

            public void setDname(String Dname) {
                this.Dname = Dname;
            }

            public List<ReasonListBeanX> getReasonList() {
                return reasonList;
            }

            public void setReasonList(List<ReasonListBeanX> reasonList) {
                this.reasonList = reasonList;
            }

            public static class ReasonListBeanX {
                /**
                 * submitTime : 1555311609518
                 * incompleteReason : 2222
                 */

                private long submitTime;
                private String incompleteReason;

                public long getSubmitTime() {
                    return submitTime;
                }

                public void setSubmitTime(long submitTime) {
                    this.submitTime = submitTime;
                }

                public String getIncompleteReason() {
                    return incompleteReason;
                }

                public void setIncompleteReason(String incompleteReason) {
                    this.incompleteReason = incompleteReason;
                }
            }
        }

        public static class CompleteListBean {
            /**
             * quota1 : null
             * Uname : liuc
             * quota2 : 组织架构
             * firstTime : 1555311372409
             * address : 湖北省武汉市蔡甸区
             * count : 2
             * diagnosis : 不行的
             * reasonList : [{"submitTime":1555311609518,"incompleteReason":"2222"},{"submitTime":1555311372409,"incompleteReason":"11111"}]
             * measures : rewrwerw
             * executor : 小刘
             * levelId : 68e5e84121334af7b37aa19d95d13b3a
             * name : 【流程】华晨雷诺金杯授权经销商销售流程检核
             * completeStatus: 1
             * newestTime : 1555311609518
             * projectId : 6a59a7206bae499b80e527207d1c5f5e
             * Dname : 湖北三环盛通汽车有限公司
             */

            private Object quota1;
            private String Uname;
            private String quota2;
            private String firstTime;
            private String address;
            private int count;
            private String diagnosis;
            private String measures;
            private String executor;
            private String levelId;
            private String name;
            private int completeStatus;
            private long newestTime;
            private String projectId;
            private String Dname;
            private List<ReasonListBeanX> reasonList;

            public Object getQuota1() {
                return quota1;
            }

            public void setQuota1(Object quota1) {
                this.quota1 = quota1;
            }

            public String getUname() {
                return Uname;
            }

            public void setUname(String Uname) {
                this.Uname = Uname;
            }

            public String getQuota2() {
                return quota2;
            }

            public void setQuota2(String quota2) {
                this.quota2 = quota2;
            }

            public String getFirstTime() {
                return firstTime;
            }

            public void setFirstTime(String firstTime) {
                this.firstTime = firstTime;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getDiagnosis() {
                return diagnosis;
            }

            public void setDiagnosis(String diagnosis) {
                this.diagnosis = diagnosis;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public String getExecutor() {
                return executor;
            }

            public void setExecutor(String executor) {
                this.executor = executor;
            }

            public String getLevelId() {
                return levelId;
            }

            public void setLevelId(String levelId) {
                this.levelId = levelId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public long getNewestTime() {
                return newestTime;
            }

            public void setNewestTime(long newestTime) {
                this.newestTime = newestTime;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public String getDname() {
                return Dname;
            }

            public void setDname(String Dname) {
                this.Dname = Dname;
            }

            public List<ReasonListBeanX> getReasonList() {
                return reasonList;
            }

            public void setReasonList(List<ReasonListBeanX> reasonList) {
                this.reasonList = reasonList;
            }

            public static class ReasonListBeanX {
                /**
                 * submitTime : 1555311609518
                 * incompleteReason : 2222
                 */

                private long submitTime;
                private String incompleteReason;

                public long getSubmitTime() {
                    return submitTime;
                }

                public void setSubmitTime(long submitTime) {
                    this.submitTime = submitTime;
                }

                public String getIncompleteReason() {
                    return incompleteReason;
                }

                public void setIncompleteReason(String incompleteReason) {
                    this.incompleteReason = incompleteReason;
                }
            }
        }
    }
}
