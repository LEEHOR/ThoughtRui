package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;

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

        void getNotification_DbSuccess(NotificationBean notificationBean, int notificationNum);

        void getNotification_DbFailure(String failure);

        void getNotification_netSuccess(CensorBean censorBean);

        void getNotification_netFailure(String failure);
    }

    interface Presenter extends BaseContract.Presenter {

        void getProject(String sessionId);

        void getProjectSuccess();

        void getProjectFailure(String failure);

        void getNotification_net(Map<String,Object> map);

        void getNotification_netSuccess(CensorBean censorBean);

        void getNotification_netFailure(String failure);

        void  getNotification_Db(String sessionId);

        void getNotification_DbSuccess(NotificationBean notificationBean, int notificationNum);

        void getNotification_DbFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void getProject(String sessionId);

        void getNotification_net(Map<String,Object> map);

        void  getNotification_Db(String sessionId);
    }
}
