package com.coahr.thoughtrui.DBbean;

import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 14:15
 */
public class ProjectsDB extends DataSupport {
    /**
     * id 项目Id
     * user 所属user
     * Pname  项目名称
     * Cname  经销商
     * code   代码
     * upload 是否上传
     * inspect 项目检验方式（1 ：飞检  2：神秘顾客 3：新店验收）
     * record 项目录音方式 (1 : 不录音 2：单题录音 3 ： 全程录音)
     */
    private int id;
    private String Pid;
    private String progress;
    private String Pname;
    private long startTime;
    private long endTime;
    private String grade;
    private String address;
    private String location;
    private String cName;
    private String dName;
    private String code;
    private int completeStatus;
    private double latitude;
    private double longitude;
    private long  modifyTime;
    private int pUploadStatus;
    private int isComplete;
    private String stage;
    private String notice;
    private long downloadTime;
    private int isDeletes;
    private String manager;
    private String sale_code;
    private String service_code;
    private long uploadTime;
    //所属User
    private UsersDB user;
    //包含多个题目
    private List<SubjectsDB> subjectsDBList =new ArrayList<>();
    public int getId() {
        return id;
    }

    public ProjectsDB() {
    }

    public ProjectsDB(int id, String pid, String progress, String pname, long startTime, long endTime, String grade, String address, String location, String cName, String dName, String code, int completeStatus, double latitude, double longitude, long modifyTime, int pUploadStatus, int isComplete, String stage, String notice, long downloadTime, int isDeletes, String manager, String sale_code, String service_code, long uploadTime, UsersDB user, List<SubjectsDB> subjectsDBList) {
        this.id = id;
        Pid = pid;
        this.progress = progress;
        Pname = pname;
        this.startTime = startTime;
        this.endTime = endTime;
        this.grade = grade;
        this.address = address;
        this.location = location;
        this.cName = cName;
        this.dName = dName;
        this.code = code;
        this.completeStatus = completeStatus;
        this.latitude = latitude;
        this.longitude = longitude;
        this.modifyTime = modifyTime;
        this.pUploadStatus = pUploadStatus;
        this.isComplete = isComplete;
        this.stage = stage;
        this.notice = notice;
        this.downloadTime = downloadTime;
        this.isDeletes = isDeletes;
        this.manager = manager;
        this.sale_code = sale_code;
        this.service_code = service_code;
        this.uploadTime = uploadTime;
        this.user = user;
        this.subjectsDBList = subjectsDBList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(int completeStatus) {
        this.completeStatus = completeStatus;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getpUploadStatus() {
        return pUploadStatus;
    }

    public void setpUploadStatus(int pUploadStatus) {
        this.pUploadStatus = pUploadStatus;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }

    public int getIsDeletes() {
        return isDeletes;
    }

    public void setIsDeletes(int isDeletes) {
        this.isDeletes = isDeletes;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public UsersDB getUser() {
        return user;
    }

    public void setUser(UsersDB user) {
        this.user = user;
    }

    public List<SubjectsDB> getSubjectsDBList() {
        return DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class,"projectsdb_id=?",String.valueOf(id));
    }

    public void setSubjectsDBList(List<SubjectsDB> subjectsDBList) {
        this.subjectsDBList = subjectsDBList;
    }

    public String getSale_code() {
        return sale_code;
    }

    public void setSale_code(String sale_code) {
        this.sale_code = sale_code;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
