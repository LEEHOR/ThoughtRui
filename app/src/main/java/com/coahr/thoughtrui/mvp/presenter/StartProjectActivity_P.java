package com.coahr.thoughtrui.mvp.presenter;

import com.amap.api.location.AMapLocation;
import com.coahr.thoughtrui.Utils.BaiDuLocation.GaodeMapLocationHelper;
import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.StartProjectActivity_C;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;
import com.coahr.thoughtrui.mvp.model.StartProjectActivity_M;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 17:00
 */
public class StartProjectActivity_P extends BasePresenter<StartProjectActivity_C.View,StartProjectActivity_C.Model> implements StartProjectActivity_C.Presenter {
    @Inject
    public StartProjectActivity_P(StartProjectActivity mview, StartProjectActivity_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getLocation(int type) {
        if (mModle != null) {
            mModle.getLocation(type);
        }
    }

    @Override
    public void getLocationSuccess(AMapLocation location, GaodeMapLocationHelper gaodeMapLocationHelper) {
        if (getView() != null) {
            getView().getLocationSuccess(location,gaodeMapLocationHelper);
        }
    }

    @Override
    public void getLocationFailure(int failure, GaodeMapLocationHelper gaodeMapLocationHelper) {
        if (getView() != null) {
            getView().getLocationFailure(failure,gaodeMapLocationHelper);
        }
    }

    @Override
    public void getMainData(Map map) {
        if (mModle != null) {
            mModle.getMainData(map);
        }
    }

    @Override
    public void getMainDataSuccess(QuestionBean questionBean) {
        if (getView() != null) {
            getView().getMainDataSuccess(questionBean);
        }
    }

    @Override
    public void getMainDataFailure(String failure,int code) {
        if (getView() != null) {
            getView().getMainDataFailure(failure,code);
        }
    }

    @Override
    public void getOfflineDate(String ht_projectId) {
        if (mModle != null) {
            mModle.getOfflineDate(ht_projectId);
        }
    }

    @Override
    public void getOfflineSuccess(int size,String ht_projectId, List<String> ht_list) {
        if (getView() != null) {
            getView().getOfflineSuccess(size,ht_projectId,ht_list);
        }
    }

    @Override
    public void getOfflineFailure(int failure) {
        if (getView() != null) {
            getView().getOfflineFailure(failure);
        }
    }

    @Override
    public void sendRtsl(Map<String, Object> map) {
        if (mModle != null) {
            mModle.sendRtsl(map);
        }
    }

    @Override
    public void sendRtslSuccess(String success,int result) {
        if (getView() != null) {
            getView().sendRtslSuccess(success,result);
        }
    }

    @Override
    public void sendRtslFail(String fail,int result) {
        if (getView() != null) {
            getView().sendRtslFail(fail,result);
        }
    }

    @Override
    public void saveAnswers(String answers, String remark, String ht_ProjectId, int number, String ht_id, int type) {
        if (mModle != null) {
            mModle.saveAnswers(answers, remark, ht_ProjectId, number, ht_id, type);
        }
    }

    @Override
    public void saveAnswersSuccess(int type) {
        if (getView() != null) {
            getView().saveAnswersSuccess(type);
        }
    }

    @Override
    public void saveAnswersFailure(int type) {
        if (getView() != null) {
            getView().saveAnswersFailure(type);
        }
    }
}
