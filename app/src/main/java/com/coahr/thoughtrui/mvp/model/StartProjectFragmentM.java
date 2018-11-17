package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.StartProjectFragmentC;
import com.coahr.thoughtrui.mvp.model.Bean.QuestionBean;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 16:58
 */
public class StartProjectFragmentM extends BaseModel<StartProjectFragmentC.Presenter> implements StartProjectFragmentC.Model {
    @Inject
    public StartProjectFragmentM() {
        super();
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
                        getPresenter().getMainDataFailure(questionBean.getMsg());
                    }
                }
            }
        }));
    }

    @Override
    public void getOfflineDate() {

    }
}
