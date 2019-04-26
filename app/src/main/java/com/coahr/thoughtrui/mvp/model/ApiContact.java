package com.coahr.thoughtrui.mvp.model;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:09
 */
public class ApiContact {
    //根路径
    public static String baseUrl = "http://leinuo.coahr.com:8085/research/";
    //登陆接口
    public static final String login = "app/login.htm";
    //首页请求接口
    public static final String getHomeData = "app/home/list.htm";
    //首页加下载
    public static final String getUnDownLoad = "app/home/download.htm";
    //考勤打卡
    public static final String attendanceInfo = "app/attendance/infor.htm";
    //考勤备注
    public static final String remark = "app/attendance/remark.htm";
    //题目信息接口
    public static final String getSubjects = "app/question/dList.htm";
    //考勤打卡
    public static final String getPushAttendance = "app/attendance/signIn.htm";
    //考勤接触历史
    public static final String AttendanceHistory = "app/attendance/history.htm";
    //题目列表
    public static final String getSubjectList = "app/question/list.htm";
    //审核列表和搜索
    public static final String getCensorList = "app/censor/list.htm";
    //审核详情列表
    public static final String getCensorInfoList = "app/censor/infor.htm";
    //修改密码
    public static final String getChangePassWord = "app/update/password.htm";
    //上传回调
    public static final String uploadDate = "app/answer/callback.htm";
    //意见反馈
    public static final String FeedBack = "app/opinion/add.htm";
    //经销商列表
    public static final String dealer_list = "app/dealer/list.htm";
    //模板列表
    public static final String template = "app/template/list.htm";
    //项目详情页
    public static final String project_detail = "app/template/genProject.htm";
    //搜索
    public static final String search = "app/home/search.htm";
    //评审详情
    public static final String CensorDetail = "app/censor/detail.htm";
    //实时定位
    public static final String Rtsl = "app/rta/send.htm";
    //通知
    public static final String notification = "app/censor/list.htm";
    //提报列表
    public static final String plan_report_list = "app/report/list.htm";
    //提报提交
    public static final String plan_report_submit = "app/report/submit.htm";
    //获取阿里云OSS临时安全令牌
    public static final String getOss = "http://leinuo.coahr.com:8085/research/app/answer/oss/token.htm";

}
