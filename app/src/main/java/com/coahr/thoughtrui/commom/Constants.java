package com.coahr.thoughtrui.commom;

import com.coahr.thoughtrui.Utils.StoreSpaceUtils;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 11:23
 * 基础配置
 */
public class Constants {
    public static String devicestoken = "";
    //Latitude
    public static double Latitude;
    //Longitude
    public static double Longitude;
    //sessionId
    public static String sessionId = "";
    //ht_projectId
    public static String ht_ProjectId;  //服务器端项目id
    //name_project
    public static String name_Project;  //项目名
    //user_id;
    public static String user_id;
    //DbProjectId 数据库中的id
    public static String DbProjectId;
    //当前用户名
    public static String user_name;
    //用户类型
    public static int user_type;
    //安装包下载位置
    public static final String URL = "url";

    //网络访问超时时间
    public static int timeout = 15;
    //首页定位次数
    public static int location_counts;
    //消息个数
    public static int message;
    //早班班次
    public static String zao_ka;
    //晚班次
    public static String wan_ka;
    //语言
    public static Locale system_locale = Locale.CHINA;
    //SDCard路径
    public static String SDCARD_PATH = StoreSpaceUtils.getSDCardPath();

    /**
     * 本地存储总目录
     */
    public static String SAVE_DIR_BASE = SDCARD_PATH.concat("/com.thoughtRui.coahr/");

    /**
     * 版本更新下载
     */
    public static String SAVE_DOWNLOAD_APK=SAVE_DIR_BASE.concat("downLoadApk/");
    /**
     *
     */
    public static String SAVE_DIR_GLIDE_CACHE = SAVE_DIR_BASE.concat("GlideCache/");
    /**
     * 相机拍摄图片存储位置
     */
    public static String SAVE_DIR_TAKE_PHOTO = SAVE_DIR_BASE.concat("takePhoto/");

    /**
     * 工程文件位置
     */
    public static String SAVE_DIR_PROJECT_Document = SAVE_DIR_BASE.concat("projectDocument/");
    /**
     * 压缩图片存储位置
     */
    public static String SAVE_DIR_ZIP_PHOTO = SAVE_DIR_BASE.concat("zipPhoto/");
    /**
     * 头像存储位置
     */
    public static String SAVE_DIR_ICON = SAVE_DIR_BASE.concat("icon/");
    /**
     * 视频存储位置
     */
    public static String SAVE_DIR_VIDEO = SAVE_DIR_BASE.concat("video/");
    /**
     * 语音存储位置
     */
    public static String SAVE_DIR_VOICE = SAVE_DIR_BASE.concat("voice/");
    /**
     * 语音临时存储位置
     */
    public static String SAVE_DIR_VOICE_TEM = SAVE_DIR_BASE.concat("voice_tem/");
    /**
     * 奔溃存储路径
     */
    public static String SAVE_DIR_CRASH = SAVE_DIR_BASE.concat("crash/");

    //跳转常数
    public static final int MainActivityCode = 1;

    public static final int MyTabFragmentCode = 2; //首页

    public static final int ProjectDetailFragmentCode = 3;

    public static final int loginFragmentCode = 4;

    public static final int startProjectFragment = 5;

    public static final int fragment_topics = 6; //题目列表

    public static final int fragment_myFragment = 7; //我的页面

    public static final int fragment_ChangePass = 8; //修改密码

    public static final int fragment_AnnexViewPager = 9; //项目附件

    public static final int fragment_review_pager = 10;//审核首页

    public static final int fragment_review_list = 11; //审核列表

    public static final int fragment_feedback = 12; //帮助与反馈

    public static final int fragment_mainInfo = 13; //首页选择

    public static final int fragment_main = 14;  //首页

    public static final int fragment_template = 15; //项目模板

    public static final int fragment_webview = 16; //网页

    public static final int fragment_Dealer = 17; //经销商信息

    public static final int fragment_search = 18; //搜索页

    public static final int fragment_umeng = 19; //消息中心

    public static final int fragment_uploadOptions = 20; //上传设置

    public static final int BaseApp = 21; //App

    public static final int fragment_action_pan = 22; //行动报表

    /**
     * PreferenceUtils键
     */
    public static String sessionId_key = "sessionId";
    //user_key
    public static String user_key = "user";
    //用户类型
    public static String user_type_key = "user_type";
    //token_key
    public static String token_key = "token";
    //uid
    public static String uid_key = "uid";
    //devicestoken
    public static String devicestoken_key = "devicestoken";
    //是否单次打开消息中心;
    public static boolean isOpenMessage;
    //消息中心列表;
    public static ArrayList<NotificationBean.Notification> notificationList = new ArrayList<>();
    //消息中心数量
    public static int notificationNum = 0;

    //Wifi链接后，上传文件
    public static final String WIFI_CONNECTED_UPLOAD_KEY = "WIFI_CONNECTED_UPLOAD_KEY";

    //阿里云
    public static String AK_KEY = "AK";
    public static String SK_KEY = "SK";
    public static String STOKEN_KEY = "STOKEN";
    public static String Expiration_KEY = "EXPIRATION";
    public static String BUCKET_KEY = "BUCKET";
    public static String ENDPOINT_KEY = "ENDPOINT";

    public static String AK;
    public static String SK;
    public static String STOKEN;
    public static long Expiration;
    public static String BUCKET;
    public static String ENDPOINT;


    //网络类型判断
    //是否连接
    public static boolean isNetWorkConnect;
    //网络类型
    public static String NetWorkType;
    //网络提示弹框是否已提示
    public static boolean isWifiDialogAlreadyShow;
    //消息数量
    public static String messageNum = "messageNum";
}
