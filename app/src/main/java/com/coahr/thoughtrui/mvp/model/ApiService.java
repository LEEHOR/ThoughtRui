package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.model.Bean.AliyunOss;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.CensorInfoList;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.DeleteProjectBean;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;
import com.coahr.thoughtrui.mvp.model.Bean.ProjectDetail;
import com.coahr.thoughtrui.mvp.model.Bean.PushAttendanceCard;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.RTSL;
import com.coahr.thoughtrui.mvp.model.Bean.ReportList;
import com.coahr.thoughtrui.mvp.model.Bean.SearchBeans;
import com.coahr.thoughtrui.mvp.model.Bean.SubmitReport;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.model.Bean.UnDownLoad;
import com.coahr.thoughtrui.mvp.model.Bean.UpLoadCallBack;
import com.coahr.thoughtrui.mvp.model.Bean.UpdateBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 16:01
 */
public interface  ApiService {

    /**
     * 登陆接口
     *
     * @param para
     *
     */
    @FormUrlEncoded
    @POST(ApiContact.login)
    Call<LoginBean> getLogin(@FieldMap Map<String, Object> para);

    /**
     * 首页数据请求接口
     *
     * @param para
     *
     */
    @FormUrlEncoded
    @POST(ApiContact.getHomeData)
    Call<HomeDataList> getHomeData(@FieldMap Map<String, Object> para);
    /**
     * 首页数据请求接口
     *
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getUnDownLoad)
    Call<UnDownLoad> getUnDownLoad(@FieldMap Map<String, Object> para);

    /**
     * 考勤打卡
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.attendanceInfo)
    Call<Attendance> getAttendanceInfo(@FieldMap Map<String, Object> para);

    /**
     * 考勤打卡备注
     * @param para
     * @return
     * getSubjects
     */
    @FormUrlEncoded
    @POST(ApiContact.remark)
    Call<AttendRemark> getRemark(@FieldMap Map<String, Object> para);

    /**
     * 获取题目列表
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getSubjects)
    Call<QuestionBean> getSubjects(@FieldMap Map<String, Object> para);

    /**
     * 考勤打卡
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getPushAttendance)
    Call<PushAttendanceCard> getPushAttendance(@FieldMap Map<String, Object> para);


    /**
     * 题目列表
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.AttendanceHistory)
    Call<AttendanceHistory> AttendanceHistory(@FieldMap Map<String, Object> para);

    /**
     * 考勤历史
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getSubjectList)
    Call<SubjectListBean> getSubjectList(@FieldMap Map<String, Object> para);

    /**
     * 获取审核列表或搜索
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getCensorList)
    Call<CensorBean> getCensorList(@FieldMap Map<String,Object> para);

    /**
     * 获取审核详情列表
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getCensorInfoList)
    Call<CensorInfoList> getCensorInfoList(@FieldMap Map<String,Object> para);

    /**
     * 修改密码
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getChangePassWord)
    Call<ChangePassWord> getChangePassWord(@FieldMap Map<String,Object> para);

    /**
     * 上传回调
     */
    @FormUrlEncoded
    @POST(ApiContact.uploadDate)
    Call<UpLoadCallBack>upLoadCallBack (@FieldMap Map<String,Object> para);


    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST(ApiContact.FeedBack)
    Call<FeedBack>feedback (@FieldMap Map<String,Object> para);

    /**
     * 经销商模板列表
     */
    @FormUrlEncoded
    @POST(ApiContact.template)
    Call<Template_list>template (@FieldMap Map<String,Object> para);

    /**
     * 经销商列表
     *
     */
    @FormUrlEncoded
    @POST(ApiContact.dealer_list)
    Call<Dealer_List>dealer (@FieldMap Map<String,Object> para);

    /**
     * 项目详情页
     */
    @FormUrlEncoded
    @POST(ApiContact.project_detail)
    Call<ProjectDetail>project_detail (@FieldMap Map<String,Object> para);

    /**
     * 搜索请求
     */
    @FormUrlEncoded
    @POST(ApiContact.search)
    Call<SearchBeans>search (@FieldMap Map<String,Object> para);

    /**
     * 搜索请求
     */
    @FormUrlEncoded
    @POST(ApiContact.Rtsl)
    Call<RTSL>RTSL (@FieldMap Map<String,Object> para);

    /**
     * 通知消息
     */
    @FormUrlEncoded
    @POST(ApiContact.notification)
    Call<CensorBean>Notification(@FieldMap Map<String,Object> para);

    /**
     * 刪除工程
     */
    @FormUrlEncoded
    @POST(ApiContact.delete_project)
    Call<DeleteProjectBean> deleteProject(@FieldMap Map<String,Object> para);

    /**
     * 提报列表
     */
    @FormUrlEncoded
    @POST(ApiContact.plan_report_list)
    Call<ReportList> getReportList(@FieldMap Map<String,Object> para);

    /**
     * 提交报表
     */
    @FormUrlEncoded
    @POST(ApiContact.plan_report_submit)
    Call<SubmitReport> submitReport(@FieldMap Map<String,Object> para);

    /**
     * 获取阿里云OSS临时安全令牌
     */
    @FormUrlEncoded
    @POST(ApiContact.getOss)
    Call<AliyunOss> getAliYunOss(@FieldMap Map<String,Object> para);


    //版本更新检测
    @POST(ApiContact.getVersion)
    Call<UpdateBean> updatecheck(@QueryMap Map<String, Object> options);
}
