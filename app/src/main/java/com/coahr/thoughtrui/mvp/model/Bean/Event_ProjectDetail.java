package com.coahr.thoughtrui.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/14
 * on 19:04
 */
public class Event_ProjectDetail {
    /**
     * Cname : 09:00-17:00
     * Dname : 一汽奥迪武汉客厅店
     * Pname : 测试案例
     * areaAddress : 湖北省武汉市东西湖区
     * code : 1111111
     * completeStatus : 2
     * downloadTime : 1533518791033
     * endTime : 1
     * grade : QQQQQ
     * id : a26c182c29ea4dc7835c34d7610fc486
     * inspect : 3
     * latitude : 30.671419
     * location : 武汉客厅
     * longitude : 114.28061
     * manager : 刘先生
     * modifyTime : 1533518636339
     * notice : 大实打实大师多啊实打实大声地啊实打实打啊实打实大所大声地啊实打实打阿萨德啊实打实打啊实打实打算多啊打算打算多啊实打实大声地
     * progress : 506/48
     * record : 2
     * startTime : 1533518636339
     * offline ：是否离线
     */

    private String cname;
    private String dname;
    private String pname;
    private String areaAddress;
    private String code;
    private int completeStatus;
    private long downloadTime;
    private Long endTime;
    private String grade;
    private int db_Id;
    private String p_Id;
    private int inspect;
    private double latitude;
    private String location;
    private double longitude;
    private String manager;
    private long modifyTime;
    private String notice;
    private String progress;
    private int record;
    private long startTime;
    private boolean offline;
    public Event_ProjectDetail() {
    }

    public Event_ProjectDetail(String cname, String dname, String pname, String areaAddress, String code, int completeStatus, long downloadTime, Long endTime, String grade, int db_Id, String p_Id, int inspect, double latitude, String location, double longitude, String manager, long modifyTime, String notice, String progress, int record, long startTime, boolean offline) {
        this.cname = cname;
        this.dname = dname;
        this.pname = pname;
        this.areaAddress = areaAddress;
        this.code = code;
        this.completeStatus = completeStatus;
        this.downloadTime = downloadTime;
        this.endTime = endTime;
        this.grade = grade;
        this.db_Id = db_Id;
        this.p_Id = p_Id;
        this.inspect = inspect;
        this.latitude = latitude;
        this.location = location;
        this.longitude = longitude;
        this.manager = manager;
        this.modifyTime = modifyTime;
        this.notice = notice;
        this.progress = progress;
        this.record = record;
        this.startTime = startTime;
        this.offline = offline;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getDb_Id() {
        return db_Id;
    }

    public void setDb_Id(int db_Id) {
        this.db_Id = db_Id;
    }

    public String getP_Id() {
        return p_Id;
    }

    public void setP_Id(String p_Id) {
        this.p_Id = p_Id;
    }

    public String getAreaAddress() {
        return areaAddress;
    }

    public void setAreaAddress(String areaAddress) {
        this.areaAddress = areaAddress;
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

    public long getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(long downloadTime) {
        this.downloadTime = downloadTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    public int getInspect() {
        return inspect;
    }

    public void setInspect(int inspect) {
        this.inspect = inspect;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
