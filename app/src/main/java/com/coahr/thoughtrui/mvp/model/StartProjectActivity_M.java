package com.coahr.thoughtrui.mvp.model;

import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.StartProjectActivity_C;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.Bean.RTSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 16:58
 */
public class StartProjectActivity_M extends BaseModel<StartProjectActivity_C.Presenter> implements StartProjectActivity_C.Model {
    @Inject
    public StartProjectActivity_M() {
        super();
    }
    private int locationType=2;
    private int type;
    @Inject
    GaodeMapLocationHelper gaodeMapLocationHelper;

    private GaodeMapLocationHelper.OnLocationCallBack onLocationCallBack = new GaodeMapLocationHelper.OnLocationCallBack() {
        @Override
        public void onLocationSuccess(AMapLocation location) {
            //连续定位成功
            if (locationType == type){
                if (getPresenter() != null) {
                    getPresenter().getLocationSuccess(location,gaodeMapLocationHelper);
                }
            }

        }

        @Override
        public void onLocationFailure(int locType) {
            //连续定位失败
            if (locationType == type){
                if (getPresenter() != null) {
                    getPresenter().getLocationFailure(locType,gaodeMapLocationHelper);
                }
            }
        }
    };
    @Override
    public void getLocation(int type) {
        this.type = type;
        initlocation();
        gaodeMapLocationHelper.startLocation();
    }

    @Override
    public void getMainData(Map map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<QuestionBean>(getApiService().getSubjects(map))).subscribeWith(new SimpleDisposableSubscriber<QuestionBean>() {
            @Override
            public void _onNext(QuestionBean questionBean) {
                if (getPresenter() != null) {
                    if (questionBean.getResult()==1) {
                        getPresenter().getMainDataSuccess(questionBean);
                    }else {
                        getPresenter().getMainDataFailure(questionBean.getMsg(),questionBean.getResult());
                    }
                }
            }
        }));
    }

    @Override
    public void getOfflineDate(String ht_projectId) {
        List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "pid=?", ht_projectId);
        if (projectsDBS != null && projectsDBS.size()>0) {
            List<SubjectsDB> subjectsDBList = DataBaseWork.DBSelectByTogether_order(SubjectsDB.class, new String[]{"projectsdb_id=?", String.valueOf(projectsDBS.get(0).getId())}, "number");
            if (subjectsDBList !=null && subjectsDBList.size()>0){
                List<String> ht_list=new ArrayList<>();
                ht_list.clear();
                for (int i = 0; i <subjectsDBList.size() ; i++) {
                    String ht_id = subjectsDBList.get(i).getHt_id();
                    ht_list.add(ht_id);
                }
                getPresenter().getOfflineSuccess(subjectsDBList.size(),ht_projectId,ht_list);
            } else {
                getPresenter().getOfflineFailure(0);
            }
        } else {
            getPresenter().getOfflineFailure(0);
        }

    }

    @Override
    public void sendRtsl(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<RTSL>(getApiService().RTSL(map)))
                .subscribeWith(new SimpleDisposableSubscriber<RTSL>() {
                    @Override
                    public void _onNext(RTSL rtsl) {
                        if (getPresenter() != null) {
                            if (rtsl.getResult()==1){
                                getPresenter().sendRtslSuccess(rtsl.getMsg(),rtsl.getResult());
                            } else {
                                getPresenter().sendRtslFail(rtsl.getMsg(),rtsl.getResult());
                            }
                        }
                    }
                }));
    }
    private void initlocation() {
        gaodeMapLocationHelper.registerLocationCallback(onLocationCallBack);
    }
    @Override
    public void detachPresenter() {
        super.detachPresenter();
        gaodeMapLocationHelper.unRegisterLocationCallback(onLocationCallBack);
    }
}
