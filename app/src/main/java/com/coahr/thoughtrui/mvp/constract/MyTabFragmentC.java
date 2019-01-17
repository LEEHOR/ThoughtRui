package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
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

        void getHomeDataFailure(String fail);

        void getHomeMoreSuccess(HomeDataList homeDataList);

        void getHomeMoreFailure(String fail);

        void getTypeDateSuccess(List<ProjectsDB> projectsDB);

        void getTypeDateFailure(int fail);

        void getUnDownLoadSuccess(UnDownLoad unDownLoad);

        void getUnDownLoadFailure(String failure);
    }

    interface Presenter extends BaseContract.Presenter {

        void getHomeData(Map map);

        void getHomeDataSuccess(HomeDataList homeDataList);

        void getHomeDataFailure(String fail);

        void getHomeMore(Map map);

        void getHomeMoreSuccess(HomeDataList homeDataList);

        void getHomeMoreFailure(String fail);

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

        void getHomeMore(Map map);

        void getTypeDate(int type);


    }
}
