package com.coahr.thoughtrui.mvp.constract;

import android.app.Activity;

import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;

import java.util.List;
import java.util.Map;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface Feedback_Fragment_C {

    interface View extends BaseContract.View {

        void sendSuggestionSuccess(FeedBack feedBack);

        void sendSuggestionFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {

        void sendSuggestion(Map<String, Object> map);

        void sendSuggestionSuccess(FeedBack feedBack);

        void sendSuggestionFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void sendSuggestion(Map<String, Object> map);
    }
}
