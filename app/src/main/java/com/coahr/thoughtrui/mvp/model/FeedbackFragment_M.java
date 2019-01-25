package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:53
 */
public class FeedbackFragment_M extends BaseModel<Feedback_Fragment_C.Presenter> implements Feedback_Fragment_C.Model {
    @Inject
    public FeedbackFragment_M() {
        super();
    }

    @Override
    public void sendSuggestion(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<FeedBack>(getApiService().feedback(map)))
                .subscribeWith(new SimpleDisposableSubscriber<FeedBack>() {
                    @Override
                    public void _onNext(FeedBack feedBack) {
                        if (getPresenter() != null) {
                            if (feedBack.getResult()==1) {
                                getPresenter().sendSuggestionSuccess(feedBack);
                            }else {
                                getPresenter().sendSuggestionFailure(feedBack.getMsg(),feedBack.getResult());
                            }
                        }
                    }
                }));
    }
}
