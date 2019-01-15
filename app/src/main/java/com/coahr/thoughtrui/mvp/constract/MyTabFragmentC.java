package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;

import java.util.List;
import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:43
 */
public interface MyTabFragmentC {
    interface View extends BaseContract.View {

       /* void onLocationSuccess(BDLocation location);

        void onLocationFailure(int failure);
*/
        void getHomeDataSuccess(HomeDataList homeDataList);

        void getHomeDataFailure(String fail);

        void getHomeMoreSuccess(HomeDataList homeDataList);

        void getHomeMoreFailure(String fail);

        void  getTypeDateSuccess(List<ProjectsDB> projectsDB);

        void  getTypeDateFailure(int fail);
    }

    interface Presenter extends BaseContract.Presenter {

      /*  void startLocation(int type);

        void onLocationSuccess(BDLocation location);

        void onLocationFailure(int failure);*/

        void getHomeData(Map map);

        void getHomeDataSuccess(HomeDataList homeDataList);

        void getHomeDataFailure(String fail);

        void getHomeMore(Map map);

        void getHomeMoreSuccess(HomeDataList homeDataList);

        void getHomeMoreFailure(String fail);

        //获取离线数据
        void getTypeDate(int type);

        void  getTypeDateSuccess(List<ProjectsDB> projectsDB);

        void  getTypeDateFailure(int fail);
    }

    interface Model extends BaseContract.Model {

       // void startLocation(int type);

        void getHomeData(Map map);

        void getHomeMore(Map map);

        void getTypeDate(int type);

    }
}
