package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface MainInfoFragment_C {

    interface View extends BaseContract.View {

        void getProjectSuccess();

        void getProjectFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {

        void getProject(String sessionId);

        void getProjectSuccess();

        void getProjectFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void getProject(String sessionId);
    }
}
