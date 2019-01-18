package com.coahr.thoughtrui.mvp.model;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWorkAsync;
import com.coahr.thoughtrui.Utils.JDBC.JDBCSelectMultiListener;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.MyTabFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.Bean.UnDownLoad;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:49
 */
public class MyTabFragmentM extends BaseModel<MyTabFragmentC.Presenter> implements MyTabFragmentC.Model {
    @Inject
    public MyTabFragmentM() {
        super();
    }

    @Override
    public void getUnDownLoadProject(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<UnDownLoad>(getApiService().getUnDownLoad(map)))
                .subscribeWith(new SimpleDisposableSubscriber<UnDownLoad>() {
                    @Override
                    public void _onNext(UnDownLoad unDownLoad) {
                        if (getPresenter() != null) {
                            if (unDownLoad.getResult()==1) {
                                getPresenter().getUnDownLoadSuccess(unDownLoad);
                            } else {
                                getPresenter().getUnDownLoadFailure(unDownLoad.getMsg());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getHomeData(Map map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<HomeDataList>(getApiService().getHomeData(map)))
                .subscribeWith(new SimpleDisposableSubscriber<HomeDataList>() {
                    @Override
                    public void _onNext(HomeDataList homeDataList) {
                        if (getPresenter() != null) {
                            if (homeDataList.getResult()==1) {
                                getPresenter().getHomeDataSuccess(homeDataList);
                            } else {
                                getPresenter().getHomeDataFailure(homeDataList.getMsg());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getHomeMore(Map map) {

    }

    //离线数据
    @Override
    public void getTypeDate(int type) {
        if (type == 0){ //新项目
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus=? and isdeletes =? and downloadtime !=? ", Constants.user_id, String.valueOf(1), String.valueOf(0), String.valueOf(0));
            if (projectsDBS !=null && projectsDBS.size()>0){
                getPresenter().getTypeDateSuccess(projectsDBS);
            } else {
                getPresenter().getTypeDateFailure(0);
            }
           /* DataBaseWorkAsync.DBSelectByTogether_Where(ProjectsDB.class, new JDBCSelectMultiListener() {
              @Override
              public <T> void SelectMulti(List<T> t) {
                  if (t !=null && t.size()>0) {
                      getPresenter().getTypeDateSuccess((List<ProjectsDB>) t);
                  } else {
                      getPresenter().getTypeDateFailure(0);
                  }
              }
          }, "usersdb_id=? and completestatus=? and isdeletes =? and downloadtime !=? ", Constants.user_id, String.valueOf(1), String.valueOf(0), String.valueOf(0));*/

        } else if (type == 1){ //已完成
            List<ProjectsDB> projectsDBS_complete = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus=? and isdeletes =?", Constants.user_id, String.valueOf(3), String.valueOf(0));
          //  List<ProjectsDB> projectsDBS = projectsDBS1;
            if (projectsDBS_complete != null && projectsDBS_complete.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_complete);
            } else {
                getPresenter().getTypeDateFailure(0);
            }
//            DataBaseWorkAsync.DBSelectByTogether_Where(ProjectsDB.class, new JDBCSelectMultiListener() {
//                @Override
//                public <T> void SelectMulti(List<T> t) {
//                    if (t !=null && t.size()>0) {
//                        getPresenter().getTypeDateSuccess((List<ProjectsDB>) t);
//                    } else {
//                        getPresenter().getTypeDateFailure(0);
//                    }
//                }
//            }, "usersdb_id=? and completestatus=? and isdeletes =?",Constants.user_id, String.valueOf(3),String.valueOf(0));
        } else if (type == 2){  //未完成
            List<ProjectsDB> projectsDBS_unComplete = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus=? and isdeletes =?",Constants.user_id, String.valueOf(2),String.valueOf(0));
            if (projectsDBS_unComplete != null && projectsDBS_unComplete.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_unComplete);
            } else {
                getPresenter().getTypeDateFailure(0);
            }

//            DataBaseWorkAsync.DBSelectByTogether_Where(ProjectsDB.class, new JDBCSelectMultiListener() {
//                @Override
//                public <T> void SelectMulti(List<T> t) {
//                    if (t !=null && t.size()>0) {
//                        getPresenter().getTypeDateSuccess((List<ProjectsDB>) t);
//                    } else {
//                        getPresenter().getTypeDateFailure(0);
//                    }
//                }
//            }, "usersdb_id=? and completestatus=? and isdeletes =?",Constants.user_id, String.valueOf(2),String.valueOf(0));
        } else if(type == 3 ){ //全部
            List<ProjectsDB> projectsDBS_all = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, " usersdb_id=? and isdeletes=?",Constants.user_id,String.valueOf(0));
            if (projectsDBS_all != null && projectsDBS_all.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_all);
            } else {
                getPresenter().getTypeDateFailure(0);
            }
//            DataBaseWorkAsync.DBSelectByTogether_Where(ProjectsDB.class, new JDBCSelectMultiListener() {
//                @Override
//                public <T> void SelectMulti(List<T> t) {
//                    if (t !=null && t.size()>0) {
//                        getPresenter().getTypeDateSuccess((List<ProjectsDB>) t);
//                    } else {
//                        getPresenter().getTypeDateFailure(0);
//                    }
//                }
//            }, " usersdb_id=? and isdeletes =?",Constants.user_id,String.valueOf(0));
        } else {
            List<ProjectsDB> projectsDBS_other = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus=? and isdeletes =? and downloadtime !=? ", Constants.user_id, String.valueOf(1), String.valueOf(0), String.valueOf(0));
            if (projectsDBS_other != null && projectsDBS_other.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_other);
            } else {
                getPresenter().getTypeDateFailure(0);
            }
//            DataBaseWorkAsync.DBSelectByTogether_Where(ProjectsDB.class, new JDBCSelectMultiListener() {
//                @Override
//                public <T> void SelectMulti(List<T> t) {
//                    if (t !=null && t.size()>0) {
//                        getPresenter().getTypeDateSuccess((List<ProjectsDB>) t);
//                    } else {
//                        getPresenter().getTypeDateFailure(0);
//                    }
//                }
//            }, "usersdb_id=? and completestatus=? and isdeletes =? and downloadtime !=? ", Constants.user_id, String.valueOf(1), String.valueOf(0), String.valueOf(0));
        }
    }

}
