package com.coahr.thoughtrui.mvp.model;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:09
 */
public class ApiContact {
    //根路径
  //  public static String baseUrl="http://survey.three3.cn:8081/three_research/";
    //测试路径
    public static String baseUrl="http://leinuo.coahr.com:8085/research/";
     //登陆接口
    public static final String login="app/login.htm";
    //首页请求接口
    public static final String getHomeData="app/home/list.htm";
    //考勤打卡
    public static final String attendanceInfo="app/attendance/infor.htm";
    //考勤备注
    public static final String remark="app/attendance/remark.htm";
    //题目信息接口
    public static final String getSubjects="app/question/dList.htm";
    //考勤打卡
    public static final String getPushAttendance="app/attendance/signIn.htm";
    //考勤接触历史
    public static final String  AttendanceHistory="app/attendance/history.htm";
    //题目列表
    public static final String  getSubjectList="app/question/list.htm";
    //审核列表和搜索
    public static final String  getCensorList="app/censor/list.htm";
    //审核详情列表
    public static final String getCensorInfoList="app/censor/infor.htm";
    //修改密码
    public static final String getChangePassWord="app/update/password.htm";

}
