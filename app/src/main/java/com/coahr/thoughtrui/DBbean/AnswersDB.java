package com.coahr.thoughtrui.DBbean;

import org.litepal.crud.DataSupport;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 14:19
 */
public class AnswersDB extends DataSupport {

    private  int id;
    private  String answer;
    private  String remakes;
    private SubjectsDB subjectsDB;

    public AnswersDB() {
    }

    public AnswersDB(int id, String answer, String remakes, SubjectsDB subjectsDB) {
        this.id = id;
        this.answer = answer;
        this.remakes = remakes;
        this.subjectsDB = subjectsDB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

    public SubjectsDB getSubjectsDB() {
        return subjectsDB;
    }

    public void setSubjectsDB(SubjectsDB subjectsDB) {
        this.subjectsDB = subjectsDB;
    }
}
