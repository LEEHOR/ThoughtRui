package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 22:58
 */
public interface Fragment_Umeng_C {

    interface View extends BaseContract.View {

        void getNotification_DbSuccess(NotificationBean notificationBean);

        void getNotification_DbFailure(String failure);

        void getNotification_netSuccess(CensorBean censorBean);

        void getNotification_netFailure(String failure);
    }

    interface Presenter extends BaseContract.Presenter {
        void getNotification_net(Map<String, Object> map);

        void getNotification_netSuccess(CensorBean censorBean);

        void getNotification_netFailure(String failure);

        void getNotification_Db(String sessionId);

        void getNotification_DbSuccess(NotificationBean notificationBean);

        void getNotification_DbFailure(String failure);

    }

    interface Model extends BaseContract.Model {
        void getNotification_net(Map<String, Object> map);

        void getNotification_Db(String sessionId);
    }
}
