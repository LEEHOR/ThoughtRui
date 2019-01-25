package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.model.FeedbackFragment_M;
import com.coahr.thoughtrui.mvp.view.mydata.Fragment_Feedback;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:50
 */
public class Feedback_Fragment_P extends BasePresenter<Feedback_Fragment_C.View,Feedback_Fragment_C.Model> implements Feedback_Fragment_C.Presenter
{
    @Inject
    public Feedback_Fragment_P(Fragment_Feedback mview, FeedbackFragment_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void sendSuggestion(Map<String, Object> map) {
        if (mModle != null) {
            mModle.sendSuggestion(map);
        }
    }

    @Override
    public void sendSuggestionSuccess(FeedBack feedBack) {
        if (getView() != null) {
            getView().sendSuggestionSuccess(feedBack);
        }
    }

    @Override
    public void sendSuggestionFailure(String failure,int code) {
        if (getView() != null) {
            getView().sendSuggestionFailure(failure,code);
        }
    }
}
