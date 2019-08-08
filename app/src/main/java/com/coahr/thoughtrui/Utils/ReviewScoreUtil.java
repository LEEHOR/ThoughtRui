package com.coahr.thoughtrui.Utils;

import android.text.TextUtils;

import com.coahr.thoughtrui.DBbean.SubjectsDB;

import java.util.List;

/**
 * 检核总得分计算工具类
 */
public class ReviewScoreUtil {
    /**
     * 项目的所有题目数据
     */
    public static int getTotalScore(List<SubjectsDB> subjectsDBS){
        int totalScore = 0;

        if (subjectsDBS != null && subjectsDBS.size() >0){
            for (int j = 0; j < subjectsDBS.size(); j++) {
                SubjectsDB subjectsDB = subjectsDBS.get(j);
                if (subjectsDB != null){
                    totalScore += Integer.parseInt(TextUtils.isEmpty(subjectsDB.getAnswer())? "0":subjectsDB.getAnswer());
                }
            }
        }
        return totalScore;
    }
}