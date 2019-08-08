package com.coahr.thoughtrui.DBbean;

import androidx.annotation.NonNull;

import org.litepal.crud.DataSupport;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 14:19
 */
public class AnswersDB extends DataSupport {


    private  String answerId;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    private  String answer;
//    private  String remakes;
//    private SubjectsDB subjectsDB;
    private String subjectsDBId;

    @Override
    public String toString() {
        return "AnswersDB{" +
                "answerId='" + answerId + '\'' +
                ", answer='" + answer + '\'' +
                ", subjectsDBId='" + subjectsDBId + '\'' +
                '}';
    }

    public String getSubjectsDBId() {
        return subjectsDBId;
    }

    public void setSubjectsDBId(String subjectsDBId) {
        this.subjectsDBId = subjectsDBId;
    }

    public AnswersDB() {
    }

    public AnswersDB(String answerId, String answer/*, String remakes, SubjectsDB subjectsDB*/) {
        this.answerId = answerId;
        this.answer = answer;
//        this.remakes = remakes;
//        this.subjectsDB = subjectsDB;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

//    public String getRemakes() {
//        return remakes;
//    }
//
//    public void setRemakes(String remakes) {
//        this.remakes = remakes;
//    }
//
//    public SubjectsDB getSubjectsDB() {
//        return subjectsDB;
//    }
//
//    public void setSubjectsDB(SubjectsDB subjectsDB) {
//        this.subjectsDB = subjectsDB;
//    }


}
