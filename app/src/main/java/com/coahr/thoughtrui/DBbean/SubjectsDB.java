package com.coahr.thoughtrui.DBbean;

import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 14:18
 */
public class SubjectsDB extends DataSupport {
    /*
        id
        title	string	题目标题
        type	Integer	题目类型  (0:单选题  1：多选题)
        options	string	题目选项（一个字符串用“&”隔开）
        description	String	题目描述
        photoStatus	Integer	是否强制拍照（1：是 - 1：否）
        recordStatus	Integer	是否录音（1 ：是  -1：否）
        describeStatus	Integer	是否填写说明（1 ：是  -1：否）
        describe         说明
        number    排序
        censor 题目上传状态
        time 计时
        dh; 打回
        */

    private int id;
    private String ht_id;
    private String title;
    private int type;
    private String options;
    private String description;
    private int photoStatus;
    private int recordStatus;
    private int describeStatus;
    private String quota1;
    private String quota2;
    private String quota3;
    private int sUploadStatus;
    private int isComplete;
    private int number;
    private int Stage;
    private String dh;
    private long times;
    private String answer;

    @Override
    public String toString() {
        return "SubjectsDB{" +
                "id=" + id +
                ", ht_id='" + ht_id + '\'' +
//                ", title='" + title + '\'' +
                ", type=" + type +
                ", options='" + options + '\'' +
//                ", description='" + description + '\'' +
//                ", photoStatus=" + photoStatus +
//                ", recordStatus=" + recordStatus +
//                ", describeStatus=" + describeStatus +
                ", quota1='" + quota1 + '\'' +
                ", quota2='" + quota2 + '\'' +
                ", quota3='" + quota3 + '\'' +
//                ", sUploadStatus=" + sUploadStatus +
//                ", isComplete=" + isComplete +
                ", number=" + number +
//                ", Stage=" + Stage +
                ", dh='" + dh + '\'' +
//                ", times=" + times +
                ", answer='" + answer + '\'' +
//                ", projectsDB=" + projectsDB +
//                ", imagesDBList=" + imagesDBList +
//                ", answersDB=" + answersDB +
                '}';
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //项目类
    private ProjectsDB projectsDB;
    //包含多个图片
    private List<ImagesDB> imagesDBList=new ArrayList<>();
    //答案
    private AnswersDB answersDB;

    public SubjectsDB() {
    }

    public SubjectsDB(int id, String ht_id, String title, int type, String options, String description, int photoStatus, int recordStatus, int describeStatus, String quota1, String quota2, String quota3, int sUploadStatus, int isComplete, int number, int stage, String dh, long times, ProjectsDB projectsDB, List<ImagesDB> imagesDBList, AnswersDB answersDB) {
        this.id = id;
        this.ht_id = ht_id;
        this.title = title;
        this.type = type;
        this.options = options;
        this.description = description;
        this.photoStatus = photoStatus;
        this.recordStatus = recordStatus;
        this.describeStatus = describeStatus;
        this.quota1 = quota1;
        this.quota2 = quota2;
        this.quota3 = quota3;
        this.sUploadStatus = sUploadStatus;
        this.isComplete = isComplete;
        this.number = number;
        Stage = stage;
        this.dh = dh;
        this.times = times;
        this.projectsDB = projectsDB;
        this.imagesDBList = imagesDBList;
        this.answersDB = answersDB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHt_id() {
        return ht_id;
    }

    public void setHt_id(String ht_id) {
        this.ht_id = ht_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhotoStatus() {
        return photoStatus;
    }

    public void setPhotoStatus(int photoStatus) {
        this.photoStatus = photoStatus;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getDescribeStatus() {
        return describeStatus;
    }

    public void setDescribeStatus(int describeStatus) {
        this.describeStatus = describeStatus;
    }

    public String getQuota1() {
        return quota1;
    }

    public void setQuota1(String quota1) {
        this.quota1 = quota1;
    }

    public String getQuota2() {
        return quota2;
    }

    public void setQuota2(String quota2) {
        this.quota2 = quota2;
    }

    public String getQuota3() {
        return quota3;
    }

    public void setQuota3(String quota3) {
        this.quota3 = quota3;
    }

    public int getsUploadStatus() {
        return sUploadStatus;
    }

    public void setsUploadStatus(int sUploadStatus) {
        this.sUploadStatus = sUploadStatus;
    }

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStage() {
        return Stage;
    }

    public void setStage(int stage) {
        Stage = stage;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public List<ImagesDB> getImagesDBList() {
        return DataBaseWork.DBSelectByTogether_Where(ImagesDB.class,"subjectsdb_id=?",String.valueOf(id));
    }

    public void setImagesDBList(List<ImagesDB> imagesDBList) {
        this.imagesDBList = imagesDBList;
    }

    public AnswersDB getAnswersDB() {
        return answersDB;
    }

    public void setAnswersDB(AnswersDB answersDB) {
        this.answersDB = answersDB;
    }
    public List<AnswersDB> getAnswers(){
        return  DataBaseWork.DBSelectByTogether_Where(AnswersDB.class,"subjectsdb_id=?",String.valueOf(id));
    }

    public ProjectsDB getProjectsDB() {
        return projectsDB;
    }

    public void setProjectsDB(ProjectsDB projectsDB) {
        this.projectsDB = projectsDB;
    }
}
