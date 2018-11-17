package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.Bean.LoginBean;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 16:01
 */
public interface ApiService {

    /**
     * 登陆接口
     *
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST("app/login.htm")
    Call<LoginBean> getLogin(@FieldMap Map<String, Object> para);

    /**
     * 首页数据请求接口
     *
     * @param para
     * @return
     */
    @FormUrlEncoded
    @POST(ApiContact.getHomeData)
    Call<HomeDataList> getHomeData(@FieldMap Map<String, Object> para);


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
}
