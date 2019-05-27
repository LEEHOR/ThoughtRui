package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.Bean.UnDownLoad;

import java.util.List;
import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:43
 */
public interface MyTabFragmentC {
    interface View extends BaseContract.View {

        void getHomeDataSuccess(HomeDataList homeDataList);

        void getHomeDataFailure(String fail, int code);

        void getTypeDateSuccess(List<ProjectsDB> projectsDB);

        void getTypeDateFailure(int fail);

        void getUnDownLoadSuccess(UnDownLoad unDownLoad);

        void getUnDownLoadFailure(String failure);

        void getSaveDbSuccess(List<String> dbList);

        void getSaveDbFailure();
    }

    interface Presenter extends BaseContract.Presenter {

        void getHomeData(Map map);

        void getHomeDataSuccess(HomeDataList homeDataList);

        void getHomeDataFailure(String fail, int code);

        void getSaveDb(HomeDataList homeDataList);

        void getSaveDbSuccess(List<String> dbList);

        void getSaveDbFailure();

        //获取离线数据
        void getTypeDate(int type);

        void getTypeDateSuccess(List<ProjectsDB> projectsDB);

        void getTypeDateFailure(int fail);


        //假下载
        void getUnDownLoadProject(Map<String, Object> map);

        void getUnDownLoadSuccess(UnDownLoad unDownLoad);

        void getUnDownLoadFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void getUnDownLoadProject(Map<String, Object> map);

        void getHomeData(Map map);

        void getSaveDb(HomeDataList homeDataList);

        void getTypeDate(int type);


    }
}
