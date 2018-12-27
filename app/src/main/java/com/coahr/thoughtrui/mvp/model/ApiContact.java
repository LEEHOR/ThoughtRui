package com.coahr.thoughtrui.mvp.model;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:09
 */
public class ApiContact {
    //根路径
    public static String baseUrl="http://survey.three3.cn:8081/three_research/";
    //测试路径
   // public static String baseUrl="http://192.168.191.1:8080/three_research/";
     //登陆接口
    public static final String login="app/login.htm";
    //首页请求接口
    public static final String getHomeData="app/home/list.htm";
    //考勤打卡
    public static final String attendanceInfo="app/attendance/infor.htm";
    //考勤备注
    public static final String remark="app/attendance/remark.htm";
    //题目信息接口
    public static final String getSubjects="app/question/list.htm";
    //考勤打卡
    public static final String getPushAttendance="app/attendance/signIn.htm";
    //考勤接触历史
    public static final String  AttendanceHistory="app/attendance/history.htm";

    //考勤接触历史
    public static final String  getSubjectList="app/question/list.htm";
}
