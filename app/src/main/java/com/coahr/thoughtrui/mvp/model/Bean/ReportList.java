package com.coahr.thoughtrui.mvp.model.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

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
     * data : {"unCompleteList":[{"quota1":"销售流程","Uname":"ITA01","quota2":"潜客开发","newestTime":1564453360231,"city":"天津","bigRegion":"北区","type":1,"reasonList":[{"submitTime":1564453360231,"incompleteReason":""}],"beforeDesc":"MSN","duration":1,"measures":"热热？","province":"天津","executor":"彭先生","levelId":"ce8e8ac710c448f89b1e275a5a869325","completeStatus":-1,"address":"天津天津","targetDate":"2019-07-30","smallRegion":"天津、河北二区","count":1,"diagnosis":"二弟","firstTime":"1564453360231","afterDesc":"","name":"销售流程检核-2019年三季度","projectId":"8d59d56ee40641e4bb0e313300c39aa6","Dname":"天津市泰歌汽车销售有限公司"}],"allList":[{"quota1":"销售流程","Uname":"ITA01","quota2":"潜客开发","newestTime":1564453360231,"city":"天津","bigRegion":"北区","type":1,"reasonList":[{"submitTime":1564453360231,"incompleteReason":""}],"beforeDesc":"MSN","duration":1,"measures":"热热？","province":"天津","executor":"彭先生","levelId":"ce8e8ac710c448f89b1e275a5a869325","completeStatus":-1,"address":"天津天津","targetDate":"2019-07-30","smallRegion":"天津、河北二区","count":1,"diagnosis":"二弟","firstTime":"1564453360231","afterDesc":"","name":"销售流程检核-2019年三季度","projectId":"8d59d56ee40641e4bb0e313300c39aa6","Dname":"天津市泰歌汽车销售有限公司"},{"quota1":"停车场","Uname":"ITA01","quota2":"停车场环境","address":"北京北京","newestTime":1564453225575,"city":"北京","targetDate":"2019-07-30","smallRegion":"北京","bigRegion":"北区","diagnosis":"咩","type":1,"reasonList":[{"submitTime":1563521764246,"incompleteReason":"，"},{"submitTime":1563521748621,"incompleteReason":"，"}],"beforeDesc":"MSN","duration":1,"measures":"哦婆婆","province":"北京","afterDesc":"哦婆婆","executor":"彭先生","levelId":"573cb4f9f82b4ad486e59dddb3ba26eb","name":"销售标准检核-2019年三季度","completeStatus":1,"projectId":"85acdcec59324cd0827058944ea8ba49","Dname":"北京友福祥瑞汽车销售有限责任公司"}],"completeList":[{"quota1":"停车场","Uname":"ITA01","quota2":"停车场环境","address":"北京北京","newestTime":1564453225575,"city":"北京","targetDate":"2019-07-30","smallRegion":"北京","bigRegion":"北区","diagnosis":"咩","type":1,"reasonList":[{"submitTime":1563521764246,"incompleteReason":"，"},{"submitTime":1563521748621,"incompleteReason":"，"}],"beforeDesc":"MSN","duration":1,"measures":"哦婆婆","province":"北京","afterDesc":"哦婆婆","executor":"彭先生","levelId":"573cb4f9f82b4ad486e59dddb3ba26eb","name":"销售标准检核-2019年三季度","completeStatus":1,"projectId":"85acdcec59324cd0827058944ea8ba49","Dname":"北京友福祥瑞汽车销售有限责任公司"}],"submitStatus":true}
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
         * unCompleteList : [{"quota1":"销售流程","Uname":"ITA01","quota2":"潜客开发","newestTime":1564453360231,"city":"天津","bigRegion":"北区","type":1,"reasonList":[{"submitTime":1564453360231,"incompleteReason":""}],"beforeDesc":"MSN","duration":1,"measures":"热热？","province":"天津","executor":"彭先生","levelId":"ce8e8ac710c448f89b1e275a5a869325","completeStatus":-1,"address":"天津天津","targetDate":"2019-07-30","smallRegion":"天津、河北二区","count":1,"diagnosis":"二弟","firstTime":"1564453360231","afterDesc":"","name":"销售流程检核-2019年三季度","projectId":"8d59d56ee40641e4bb0e313300c39aa6","Dname":"天津市泰歌汽车销售有限公司"}]
         * allList : [{"quota1":"销售流程","Uname":"ITA01","quota2":"潜客开发","newestTime":1564453360231,"city":"天津","bigRegion":"北区","type":1,"reasonList":[{"submitTime":1564453360231,"incompleteReason":""}],"beforeDesc":"MSN","duration":1,"measures":"热热？","province":"天津","executor":"彭先生","levelId":"ce8e8ac710c448f89b1e275a5a869325","completeStatus":-1,"address":"天津天津","targetDate":"2019-07-30","smallRegion":"天津、河北二区","count":1,"diagnosis":"二弟","firstTime":"1564453360231","afterDesc":"","name":"销售流程检核-2019年三季度","projectId":"8d59d56ee40641e4bb0e313300c39aa6","Dname":"天津市泰歌汽车销售有限公司"},{"quota1":"停车场","Uname":"ITA01","quota2":"停车场环境","address":"北京北京","newestTime":1564453225575,"city":"北京","targetDate":"2019-07-30","smallRegion":"北京","bigRegion":"北区","diagnosis":"咩","type":1,"reasonList":[{"submitTime":1563521764246,"incompleteReason":"，"},{"submitTime":1563521748621,"incompleteReason":"，"}],"beforeDesc":"MSN","duration":1,"measures":"哦婆婆","province":"北京","afterDesc":"哦婆婆","executor":"彭先生","levelId":"573cb4f9f82b4ad486e59dddb3ba26eb","name":"销售标准检核-2019年三季度","completeStatus":1,"projectId":"85acdcec59324cd0827058944ea8ba49","Dname":"北京友福祥瑞汽车销售有限责任公司"}]
         * completeList : [{"quota1":"停车场","Uname":"ITA01","quota2":"停车场环境","address":"北京北京","newestTime":1564453225575,"city":"北京","targetDate":"2019-07-30","smallRegion":"北京","bigRegion":"北区","diagnosis":"咩","type":1,"reasonList":[{"submitTime":1563521764246,"incompleteReason":"，"},{"submitTime":1563521748621,"incompleteReason":"，"}],"beforeDesc":"MSN","duration":1,"measures":"哦婆婆","province":"北京","afterDesc":"哦婆婆","executor":"彭先生","levelId":"573cb4f9f82b4ad486e59dddb3ba26eb","name":"销售标准检核-2019年三季度","completeStatus":1,"projectId":"85acdcec59324cd0827058944ea8ba49","Dname":"北京友福祥瑞汽车销售有限责任公司"}]
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
             * quota1 : 销售流程
             * Uname : ITA01
             * quota2 : 潜客开发
             * newestTime : 1564453360231
             * city : 天津
             * bigRegion : 北区
             * type : 1
             * reasonList : [{"submitTime":1564453360231,"incompleteReason":""}]
             * beforeDesc : MSN
             * duration : 1
             * measures : 热热？
             * province : 天津
             * executor : 彭先生
             * levelId : ce8e8ac710c448f89b1e275a5a869325
             * completeStatus : -1
             * address : 天津天津
             * targetDate : 2019-07-30
             * smallRegion : 天津、河北二区
             * count : 1
             * diagnosis : 二弟
             * firstTime : 1564453360231
             * afterDesc :
             * name : 销售流程检核-2019年三季度
             * projectId : 8d59d56ee40641e4bb0e313300c39aa6
             * Dname : 天津市泰歌汽车销售有限公司
             */

            private String quota1;
            private String Uname;
            private String quota2;
            private long newestTime;
            private String city;
            private String bigRegion;
            private int type;
            private String beforeDesc;
            private int duration;
            private String measures;
            private String province;
            private String executor;
            private String levelId;
            private int completeStatus;
            private String address;
            private String targetDate;
            private String smallRegion;
            private int count;
            private String diagnosis;
            private String firstTime;
            private String afterDesc;
            private String name;
            private String projectId;
            private String Dname;
            private List<ReasonListBean> reasonList;

            public String getQuota1() {
                return quota1;
            }

            public void setQuota1(String quota1) {
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

            public long getNewestTime() {
                return newestTime;
            }

            public void setNewestTime(long newestTime) {
                this.newestTime = newestTime;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getBigRegion() {
                return bigRegion;
            }

            public void setBigRegion(String bigRegion) {
                this.bigRegion = bigRegion;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getBeforeDesc() {
                return beforeDesc;
            }

            public void setBeforeDesc(String beforeDesc) {
                this.beforeDesc = beforeDesc;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
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

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTargetDate() {
                return targetDate;
            }

            public void setTargetDate(String targetDate) {
                this.targetDate = targetDate;
            }

            public String getSmallRegion() {
                return smallRegion;
            }

            public void setSmallRegion(String smallRegion) {
                this.smallRegion = smallRegion;
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

            public String getFirstTime() {
                return firstTime;
            }

            public void setFirstTime(String firstTime) {
                this.firstTime = firstTime;
            }

            public String getAfterDesc() {
                return afterDesc;
            }

            public void setAfterDesc(String afterDesc) {
                this.afterDesc = afterDesc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
                 * submitTime : 1564453360231
                 * incompleteReason :
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

        public static class AllListBean implements Parcelable, MultiItemEntity  {
            /**
             * quota1 : 销售流程
             * Uname : ITA01
             * quota2 : 潜客开发
             * newestTime : 1564453360231
             * city : 天津
             * bigRegion : 北区
             * type : 1
             * reasonList : [{"submitTime":1564453360231,"incompleteReason":""}]
             * beforeDesc : MSN
             * duration : 1
             * measures : 热热？
             * province : 天津
             * executor : 彭先生
             * levelId : ce8e8ac710c448f89b1e275a5a869325
             * completeStatus : -1
             * address : 天津天津
             * targetDate : 2019-07-30
             * smallRegion : 天津、河北二区
             * count : 1
             * diagnosis : 二弟
             * firstTime : 1564453360231
             * afterDesc :
             * name : 销售流程检核-2019年三季度
             * projectId : 8d59d56ee40641e4bb0e313300c39aa6
             * Dname : 天津市泰歌汽车销售有限公司
             */
            public static final int FINISH = 1;
            public static final int UN_FINISH = -1;
            private String quota1;
            private String Uname;
            private String quota2;
            private long newestTime;
            private String city;
            private String bigRegion;
            private int type;
            private String beforeDesc;
            private int duration;
            private String measures;
            private String province;
            private String executor;
            private String levelId;
            private int completeStatus;
            private String address;
            private String targetDate;
            private String smallRegion;
            private int count;
            private String diagnosis;
            private String firstTime;
            private String afterDesc;
            private String name;
            private String projectId;
            private String Dname;
            private List<ReasonListBeanX> reasonList;

            protected AllListBean(Parcel in) {
                quota1 = in.readString();
                Uname = in.readString();
                quota2 = in.readString();
                newestTime = in.readLong();
                city = in.readString();
                bigRegion = in.readString();
                type = in.readInt();
                beforeDesc = in.readString();
                duration = in.readInt();
                measures = in.readString();
                province = in.readString();
                executor = in.readString();
                levelId = in.readString();
                completeStatus = in.readInt();
                address = in.readString();
                targetDate = in.readString();
                smallRegion = in.readString();
                count = in.readInt();
                diagnosis = in.readString();
                firstTime = in.readString();
                afterDesc = in.readString();
                name = in.readString();
                projectId = in.readString();
                Dname = in.readString();
            }

            public static final Creator<AllListBean> CREATOR = new Creator<AllListBean>() {
                @Override
                public AllListBean createFromParcel(Parcel in) {
                    return new AllListBean(in);
                }

                @Override
                public AllListBean[] newArray(int size) {
                    return new AllListBean[size];
                }
            };

            public String getQuota1() {
                return quota1;
            }

            public void setQuota1(String quota1) {
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

            public long getNewestTime() {
                return newestTime;
            }

            public void setNewestTime(long newestTime) {
                this.newestTime = newestTime;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getBigRegion() {
                return bigRegion;
            }

            public void setBigRegion(String bigRegion) {
                this.bigRegion = bigRegion;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getBeforeDesc() {
                return beforeDesc;
            }

            public void setBeforeDesc(String beforeDesc) {
                this.beforeDesc = beforeDesc;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
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

            public int getCompleteStatus() {
                return completeStatus;
            }

            public void setCompleteStatus(int completeStatus) {
                this.completeStatus = completeStatus;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTargetDate() {
                return targetDate;
            }

            public void setTargetDate(String targetDate) {
                this.targetDate = targetDate;
            }

            public String getSmallRegion() {
                return smallRegion;
            }

            public void setSmallRegion(String smallRegion) {
                this.smallRegion = smallRegion;
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

            public String getFirstTime() {
                return firstTime;
            }

            public void setFirstTime(String firstTime) {
                this.firstTime = firstTime;
            }

            public String getAfterDesc() {
                return afterDesc;
            }

            public void setAfterDesc(String afterDesc) {
                this.afterDesc = afterDesc;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(quota1);
                dest.writeString(Uname);
                dest.writeString(quota2);
                dest.writeLong(newestTime);
                dest.writeString(city);
                dest.writeString(bigRegion);
                dest.writeInt(type);
                dest.writeString(beforeDesc);
                dest.writeInt(duration);
                dest.writeString(measures);
                dest.writeString(province);
                dest.writeString(executor);
                dest.writeString(levelId);
                dest.writeInt(completeStatus);
                dest.writeString(address);
                dest.writeString(targetDate);
                dest.writeString(smallRegion);
                dest.writeInt(count);
                dest.writeString(diagnosis);
                dest.writeString(firstTime);
                dest.writeString(afterDesc);
                dest.writeString(name);
                dest.writeString(projectId);
                dest.writeString(Dname);
            }

            @Override
            public int getItemType() {
                return completeStatus;
            }

            public static class ReasonListBeanX {
                /**
                 * submitTime : 1564453360231
                 * incompleteReason :
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
             * quota1 : 停车场
             * Uname : ITA01
             * quota2 : 停车场环境
             * address : 北京北京
             * newestTime : 1564453225575
             * city : 北京
             * targetDate : 2019-07-30
             * smallRegion : 北京
             * bigRegion : 北区
             * diagnosis : 咩
             * type : 1
             * reasonList : [{"submitTime":1563521764246,"incompleteReason":"，"},{"submitTime":1563521748621,"incompleteReason":"，"}]
             * beforeDesc : MSN
             * duration : 1
             * measures : 哦婆婆
             * province : 北京
             * afterDesc : 哦婆婆
             * executor : 彭先生
             * levelId : 573cb4f9f82b4ad486e59dddb3ba26eb
             * name : 销售标准检核-2019年三季度
             * completeStatus : 1
             * projectId : 85acdcec59324cd0827058944ea8ba49
             * Dname : 北京友福祥瑞汽车销售有限责任公司
             */

            private String quota1;
            private String Uname;
            private String quota2;
            private String address;
            private long newestTime;
            private String city;
            private String targetDate;
            private String smallRegion;
            private String bigRegion;
            private String diagnosis;
            private int type;
            private String beforeDesc;
            private int duration;
            private String measures;
            private String province;
            private String afterDesc;
            private String executor;
            private String levelId;
            private String name;
            private int completeStatus;
            private String projectId;
            private String Dname;
            private List<ReasonListBeanXX> reasonList;

            public String getQuota1() {
                return quota1;
            }

            public void setQuota1(String quota1) {
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public long getNewestTime() {
                return newestTime;
            }

            public void setNewestTime(long newestTime) {
                this.newestTime = newestTime;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getTargetDate() {
                return targetDate;
            }

            public void setTargetDate(String targetDate) {
                this.targetDate = targetDate;
            }

            public String getSmallRegion() {
                return smallRegion;
            }

            public void setSmallRegion(String smallRegion) {
                this.smallRegion = smallRegion;
            }

            public String getBigRegion() {
                return bigRegion;
            }

            public void setBigRegion(String bigRegion) {
                this.bigRegion = bigRegion;
            }

            public String getDiagnosis() {
                return diagnosis;
            }

            public void setDiagnosis(String diagnosis) {
                this.diagnosis = diagnosis;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getBeforeDesc() {
                return beforeDesc;
            }

            public void setBeforeDesc(String beforeDesc) {
                this.beforeDesc = beforeDesc;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getAfterDesc() {
                return afterDesc;
            }

            public void setAfterDesc(String afterDesc) {
                this.afterDesc = afterDesc;
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

            public List<ReasonListBeanXX> getReasonList() {
                return reasonList;
            }

            public void setReasonList(List<ReasonListBeanXX> reasonList) {
                this.reasonList = reasonList;
            }

            public static class ReasonListBeanXX {
                /**
                 * submitTime : 1563521764246
                 * incompleteReason : ，
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
