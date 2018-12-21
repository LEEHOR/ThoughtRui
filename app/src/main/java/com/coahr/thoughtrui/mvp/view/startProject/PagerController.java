package com.coahr.thoughtrui.mvp.view.startProject;

import android.support.v4.app.Fragment;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWorkAsync;
import com.coahr.thoughtrui.Utils.JDBC.JDBCSelectMultiListener;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 18:02
 * 控制每一页pager的类型和数量
 */
public class PagerController {
    Map map=new HashMap();
    ArrayList<BaseChildFragment> fragmentArrayList = new ArrayList<>();
    //题目序号
    String projectId;

    //项目类型 简答题、选择题、综合题
    int type;

    public PagerController(String projectId, int type) {
        this.projectId = projectId;
        this.type = type;
    }

    public ArrayList<BaseChildFragment> getFragmentArrayList() {
        fragmentArrayList.clear();
        switch (type) {
            case 0: //单选题
                List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=?", projectId);
                if (subjectsDBS !=null && subjectsDBS.size()>0){
                    for (int i = 0; i < subjectsDBS.size(); i++) {
                       fragmentArrayList.add(PagerFragment_a.newInstance(i,projectId,Constants.ht_ProjectId,10)) ;
                    }
                }
                break;
        }

        return fragmentArrayList;
    }
}
