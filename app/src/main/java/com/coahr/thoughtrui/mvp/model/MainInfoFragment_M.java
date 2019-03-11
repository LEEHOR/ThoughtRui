package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.constract.MainInfoFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:53
 */
public class MainInfoFragment_M extends BaseModel<MainInfoFragment_C.Presenter> implements MainInfoFragment_C.Model {
    @Inject
    public MainInfoFragment_M() {
        super();
    }


    @Override
    public void getProject(String sessionId) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=?", String.valueOf(usersDBS.get(0).getId()));
            if (projectsDBS != null && projectsDBS.size()>0) {
                int count=0;
                for (int i = 0; i <projectsDBS.size() ; i++) {
                    if (projectsDBS.get(i).getIsDeletes()!=1){  //判断是否删除
                        count++;
                    }
                }
                if (count !=0){
                    if (getPresenter() != null) {
                        getPresenter().getProjectSuccess();
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().getProjectFailure("没有项目");
                    }
                }
            } else {
                if (getPresenter() != null) {
                    getPresenter().getProjectFailure("没有项目");
                }
            }
        }
    }
}
