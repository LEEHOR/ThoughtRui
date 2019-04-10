package com.coahr.thoughtrui.mvp.model;

import android.app.Notification;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Fragment_Umeng_C;
import com.coahr.thoughtrui.mvp.constract.MyFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/11
 * 描述：
 */
public class Fragment_Umeng_M extends BaseModel<Fragment_Umeng_C.Presenter> implements Fragment_Umeng_C.Model {
    private List<NotificationBean.Notification> notificationList = new ArrayList<>();
    ;

    @Inject
    public Fragment_Umeng_M() {
        super();
    }

    @Override
    public void getNotification_net(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<CensorBean>(getApiService().Notification(map)))
                .subscribeWith(new SimpleDisposableSubscriber<CensorBean>() {
                    @Override
                    public void _onNext(CensorBean censorBean) {
                        if (getPresenter() != null) {
                            if (censorBean.getResult() == 1) {
                                getPresenter().getNotification_netSuccess(censorBean);
                            } else {
                                getPresenter().getNotification_netFailure(censorBean.getMsg());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getNotification_Db(String sessionId) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
        NotificationBean notificationBean=new NotificationBean();
        notificationList.clear();
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
            if (projectsDBSList != null && projectsDBSList.size() > 0) {
                for (int i = 0; i < projectsDBSList.size(); i++) {
                    if (projectsDBSList.get(i).getIsComplete() == 1 && projectsDBSList.get(i).getpUploadStatus() == 0) {
                        NotificationBean.Notification notification = new NotificationBean.Notification();
                        notification.setType(1);
                        notification.setNotificationTittle(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_upload));
                        notification.setNotificationContent(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_upload_detail) + (projectsDBSList.get(i).getSale_code() !=null?projectsDBSList.get(i).getSale_code():projectsDBSList.get(i).getService_code()));
                        notification.setNotificationTime(System.currentTimeMillis());
                        notificationList.add(notification);
                    }
                    if (projectsDBSList.get(i).getModifyTime()<=System.currentTimeMillis()){
                        NotificationBean.Notification notification = new NotificationBean.Notification();
                        notification.setType(2);
                        notification.setNotificationTittle(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_overdue_reminder));
                        notification.setNotificationContent(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_overdue_reminder_detail)+ (projectsDBSList.get(i).getSale_code() !=null?projectsDBSList.get(i).getSale_code():projectsDBSList.get(i).getService_code()));
                        notification.setNotificationTime(System.currentTimeMillis());
                        notificationList.add(notification);
                    }
                }
                notificationBean.setNotificationList(notificationList);
                if (getPresenter() != null) {
                    getPresenter().getNotification_DbSuccess(notificationBean);
                }
            } else {
                if (getPresenter() != null) {
                    getPresenter().getNotification_DbFailure(BaseApplication.mContext.getString(R.string.toast_36));
                }
            }
        }
    }
}
