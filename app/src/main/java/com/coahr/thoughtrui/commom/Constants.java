package com.coahr.thoughtrui.commom;

import com.coahr.thoughtrui.Utils.StoreSpaceUtils;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 11:23
 * 基础配置
 */
public class Constants {
    //是否需要自杀进程以便打补丁
    public static boolean isKill=true;
    //Latitude
    public  static  double Latitude;
    //Longitude
    public static double Longitude;
    //sessionId
    public static String sessionId;
    //projectId
    public static String projectId;
    //user_id;
    public static String user_id;
    //DbProjectId 数据库中的id
    public static String DbProjectId;

    //网络访问超时时间
    public static int timeout=15;
    //首页定位次数
    public static int location_counts;

    //SDCard路径
    public static String SDCARD_PATH = StoreSpaceUtils.getSDCardPath();

    /**
     * 本地存储总目录
     */
    public static String SAVE_DIR_BASE = SDCARD_PATH.concat("/carsuper/");

    /**
     * 图片存储位置
     */
    public static String SAVE_DIR_GLIDE_CACHE = SAVE_DIR_BASE.concat("GlideCache/");
    /**
     * 图片存储位置
     */
    public static String SAVE_DIR_PHOTO = SAVE_DIR_BASE.concat("photo/");
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
     * 奔溃存储路径
     */
    public static String SAVE_DIR_CRASH = SAVE_DIR_BASE.concat("crash/");

    //跳转常数
    public  static final int MainActivityCode=1;

    public  static final int MyTabFragmentCode=2;

    public  static final int ProjectDetailFragmentCode=3;

    public  static final int loginFragmentCode=4;

    public static  final int startProjectFragment=5;




    /**
     * PreferenceUtils键
     */
    //sessionId_key
    public static  String sessionId_key;
    //user_key
    public static  String user_key;
}
