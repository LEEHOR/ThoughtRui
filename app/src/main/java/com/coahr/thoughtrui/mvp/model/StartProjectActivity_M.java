package com.coahr.thoughtrui.mvp.model;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.Utils.BaiDuLocation.BaiduLocationHelper;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.StartProjectActivity_C;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;

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
    BaiduLocationHelper baiduLocationHelper;

    private BaiduLocationHelper.OnLocationCallBack onLocationCallBack = new BaiduLocationHelper.OnLocationCallBack() {
        @Override
        public void onLocationSuccess(BDLocation location) {
            //连续定位成功
            if (locationType == type){
                if (getPresenter() != null) {
                    getPresenter().getLocationSuccess(location,baiduLocationHelper);
                }
            }

        }

        @Override
        public void onLocationFailure(int locType) {
            //连续定位失败
            if (locationType == type){
                if (getPresenter() != null) {
                    getPresenter().getLocationFailure(locType,baiduLocationHelper);
                }
            }
        }
    };
    @Override
    public void getLocation(int type) {
        this.type = type;
        initlocation();
        baiduLocationHelper.startLocation();
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
    public void getOfflineDate(String dbProjectId,String ht_projectId) {
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectByTogether_Where(SubjectsDB.class, "projectsdb_id=?", dbProjectId);
        List<String> ht_list=new ArrayList<>();
        if (subjectsDBS !=null && subjectsDBS.size()>0){
            ht_list.clear();
            for (int i = 0; i <subjectsDBS.size() ; i++) {
                String ht_id = subjectsDBS.get(i).getHt_id();
                ht_list.add(ht_id);
            }
           getPresenter().getOfflineSuccess(subjectsDBS.size(), dbProjectId,ht_projectId,ht_list);
        } else {
            getPresenter().getOfflineFailure(0);
        }
    }
    private void initlocation() {
        baiduLocationHelper.registerLocationCallback(onLocationCallBack);
    }
    @Override
    public void detachPresenter() {
        super.detachPresenter();
        baiduLocationHelper.unRegisterLocationCallback(onLocationCallBack);
    }
}
