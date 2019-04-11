package com.coahr.thoughtrui.mvp.view.startProject;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.Base.BaseFragment_not_padding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 18:02
 * 控制每一页pager的类型和数量
 */
public class PagerController {
    Map map=new HashMap();
    ArrayList<BaseFragment_not_padding> fragmentArrayList = new ArrayList<>();
    //题目序号
    String projectId;

    //项目类型 简答题、选择题、综合题
    int type;

    public PagerController(String projectId, int type) {
        this.projectId = projectId;
        this.type = type;
    }

    public ArrayList<BaseFragment_not_padding> getFragmentArrayList() {
        fragmentArrayList.clear();
        switch (type) {
            case 0: //单选题
                List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=?", projectId);
                if (subjectsDBS !=null && subjectsDBS.size()>0){
                    for (int i = 0; i < subjectsDBS.size(); i++) {
                       fragmentArrayList.add(PagerFragment_a.newInstance(i,projectId,10,Constants.name_Project,null)) ;
                    }
                }
                break;
        }

        return fragmentArrayList;
    }
}
