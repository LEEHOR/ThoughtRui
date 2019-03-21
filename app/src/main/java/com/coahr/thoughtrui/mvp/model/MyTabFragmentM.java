package com.coahr.thoughtrui.mvp.model;

import android.app.KeyguardManager;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWorkAsync;
import com.coahr.thoughtrui.Utils.JDBC.JDBCSelectMultiListener;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.MyTabFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.HomeDataList;
import com.coahr.thoughtrui.mvp.model.Bean.UnDownLoad;
import com.socks.library.KLog;

import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 15:49
 */
public class MyTabFragmentM extends BaseModel<MyTabFragmentC.Presenter> implements MyTabFragmentC.Model {

    private int totalSize;
    private List<String>  ht_List = new ArrayList<>();;
    private int saveSize;
    private List<UsersDB> usersDBS;
    private int updateSize;

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
                                getPresenter().getHomeDataFailure(homeDataList.getMsg(),homeDataList.getResult());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getSaveDb(HomeDataList homeDataList) {
        List<HomeDataList.DataBean.AllListBean> allList = homeDataList.getData().getAllList();
        if (allList != null && allList.size() > 0) {
            usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", Constants.sessionId);
            totalSize = allList.size();
            updateSize=0;
            saveSize=0;
            ht_List.clear();
            for (int i = 0; i < allList.size(); i++) {
                ht_List.add(allList.get(i).getId());
                SaveProject(allList.get(i).getId(), allList.get(i));
            }
        }
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

        } else if (type == 1){ //已完成
            List<ProjectsDB> projectsDBS_complete = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus=? and isdeletes =?", Constants.user_id, String.valueOf(3), String.valueOf(0));
            if (projectsDBS_complete != null && projectsDBS_complete.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_complete);
            } else {
                getPresenter().getTypeDateFailure(0);
            }

        } else if (type == 2){  //未完成
            List<ProjectsDB> projectsDBS_unComplete = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus!=? and isdeletes =?",Constants.user_id, String.valueOf(3),String.valueOf(0));
            if (projectsDBS_unComplete != null && projectsDBS_unComplete.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_unComplete);
            } else {
                getPresenter().getTypeDateFailure(0);
            }

        } else if(type == 3 ){ //全部
            List<ProjectsDB> projectsDBS_all = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, " usersdb_id=? and isdeletes=?",Constants.user_id,String.valueOf(0));
            if (projectsDBS_all != null && projectsDBS_all.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_all);
            } else {
                getPresenter().getTypeDateFailure(0);
            }
        } else {
            List<ProjectsDB> projectsDBS_other = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=? and completestatus=? and isdeletes =? and downloadtime !=? ", Constants.user_id, String.valueOf(1), String.valueOf(0), String.valueOf(0));
            if (projectsDBS_other != null && projectsDBS_other.size()>0) {
                getPresenter().getTypeDateSuccess(projectsDBS_other);
            } else {
                getPresenter().getTypeDateFailure(0);
            }

        }
    }
    private void SaveProject(String Pid, HomeDataList.DataBean.AllListBean listBean) {
        //查询数据库有没有当前项目
        List<ProjectsDB> ProjectDBList = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", Pid);
        if (ProjectDBList != null && !ProjectDBList.isEmpty() && ProjectDBList.size() > 0) {
            ProjectsDB projectsDB=new ProjectsDB();
           // projectsDB.setDownloadTime(listBean.getDownloadTime());
            projectsDB.setCompleteStatus(listBean.getCompleteStatus());
            projectsDB.setCname(listBean.getCname());
            projectsDB.setProgress(listBean.getProgress());
            projectsDB.setSale_code(listBean.getSale_code());
            projectsDB.setService_code(listBean.getService_code());
            if (usersDBS != null && usersDBS.size()>0) {
                projectsDB.setUser(usersDBS.get(0));
            }
            int update = projectsDB.update(ProjectDBList.get(0).getId());
            if (update>0){
                updateSize++;
            }
        } else {
            ProjectsDB projectsDB = new ProjectsDB();
            projectsDB.setPid(listBean.getId());
           // projectsDB.setRecord(listBean.getRecord()); //录音方式
          //  projectsDB.setInspect(listBean.getInspect()); //检验方式
            projectsDB.setPname(listBean.getPname());  //项目名
            projectsDB.setAddress(listBean.getAreaAddress());
           // projectsDB.setStartTime(listBean.getStartTime());
          //  projectsDB.setcName(listBean.getCname());
           // projectsDB.setCode(listBean.getCode());
           // projectsDB.setDownloadTime(listBean.getDownloadTime());
            projectsDB.setCompleteStatus(listBean.getCompleteStatus());
            projectsDB.setProgress(listBean.getProgress());
           // projectsDB.setdName(listBean.getDname());
            projectsDB.setLatitude(listBean.getLatitude());
            projectsDB.setLocation(listBean.getLocation());
            projectsDB.setLongitude(listBean.getLongitude());
            projectsDB.setModifyTime(listBean.getModifyTime());
            projectsDB.setNotice(listBean.getNotice());
          //  projectsDB.setEndTime(listBean.getEndTime());
           // projectsDB.setManager(listBean.getManager());
            projectsDB.setService_code(listBean.getService_code());
            projectsDB.setSale_code(listBean.getSale_code());
            projectsDB.setIsComplete(0);
            projectsDB.setStage("1");
            if (usersDBS != null && usersDBS.size() > 0) {
                projectsDB.setUser(usersDBS.get(0));
            }
            boolean save = projectsDB.save();
            if (save) {
                saveSize++;
            }
        }
        if (totalSize==(saveSize+updateSize)){
            if (ht_List != null && ht_List.size()>0) {
                getPresenter().getSaveDbSuccess(ht_List);
            } else {
                getPresenter().getSaveDbFailure();
            }
        }
    }
}
