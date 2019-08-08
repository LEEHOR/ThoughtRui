package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.DBbean.UsersDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.Feedback_Fragment_C;
import com.coahr.thoughtrui.mvp.constract.MainInfoFragment_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.FeedBack;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:53
 */
public class MainInfoFragment_M extends BaseModel<MainInfoFragment_C.Presenter> implements MainInfoFragment_C.Model {
    private List<NotificationBean.Notification> notificationList = new ArrayList<>();
    private int notificationNum = 0;

    @Inject
    public MainInfoFragment_M() {
        super();
    }

    @Override
    public void getProject(String sessionId) {
        List<UsersDB> usersDBS = DataBaseWork.DBSelectByTogether_Where(UsersDB.class, "sessionid=?", sessionId);
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBS = DataBaseWork.DBSelectByTogether_Where(ProjectsDB.class, "usersdb_id=?", String.valueOf(usersDBS.get(0).getId()));
            if (projectsDBS != null && projectsDBS.size()>0) {
                int count=0;
                for (int i = 0; i <projectsDBS.size() ; i++) {
//                    if (projectsDBS.get(i).getIsDeletes()!=1){  //判断是否删除
//                        count++;
//                    }
                    if (projectsDBS.get(i).getCompleteStatus() == 2 || projectsDBS.get(i).getCompleteStatus() == 1) {  //未完成
                        count++;
                    }
                }
                KLog.e("测试代码", "未完成 count == " + count);
                if (count !=0){
                    if (getPresenter() != null) {
                        getPresenter().getProjectSuccess();
                    }
                } else {
                    if (getPresenter() != null) {
                        getPresenter().getProjectFailure("没有项目");
                    }
                }
            } else {
                if (getPresenter() != null) {
                    getPresenter().getProjectFailure("没有项目");
                }
            }
        }
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
        NotificationBean notificationBean = new NotificationBean();
        notificationList.clear();
        notificationNum = 0;
        if (usersDBS != null && usersDBS.size() > 0) {
            List<ProjectsDB> projectsDBSList = usersDBS.get(0).getProjectsDBSList();
            if (projectsDBSList != null && projectsDBSList.size() > 0) {
                for (int i = 0; i < projectsDBSList.size(); i++) {
                    //可以展示在消息中心，才添加数据
                    if (!projectsDBSList.get(i).isHideInMessageCenter()) {
                        if (projectsDBSList.get(i).getIsComplete() == 1 && projectsDBSList.get(i).getpUploadStatus() == 0) {
                            NotificationBean.Notification notification = new NotificationBean.Notification();
                            notification.setType(1);
                            notification.setNotificationTittle(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_upload));
                            notification.setNotificationContent(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_upload_detail) + (projectsDBSList.get(i).getSale_code() != null ? projectsDBSList.get(i).getSale_code() : projectsDBSList.get(i).getService_code()));
                            notification.setNotificationTime(System.currentTimeMillis());
                            //设置项目id，用于更改显示在消息中心
                            notification.setProjectId(projectsDBSList.get(i).getId());
                            notificationList.add(notification);
                            notificationNum++;
                        }

                        KLog.e("测试代码", "UploadTime == " + projectsDBSList.get(i).getUploadTime() + " -- currentTimeMillis == " + System.currentTimeMillis());
                        if (projectsDBSList.get(i).getUploadTime() <= System.currentTimeMillis()) {
                            NotificationBean.Notification notification = new NotificationBean.Notification();
                            notification.setType(2);
                            notification.setNotificationTittle(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_overdue_reminder));
                            notification.setNotificationContent(BaseApplication.mContext.getResources().getString(R.string.umeng_fragment_overdue_reminder_detail) + (projectsDBSList.get(i).getSale_code() != null ? projectsDBSList.get(i).getSale_code() : projectsDBSList.get(i).getService_code()));
                            notification.setNotificationTime(System.currentTimeMillis());
                            notification.setProjectId(projectsDBSList.get(i).getId());
                            notificationList.add(notification);
                            notificationNum++;
                        }
                    }
                }
                notificationBean.setNotificationList(notificationList);
                if (getPresenter() != null) {
                    getPresenter().getNotification_DbSuccess(notificationBean, notificationNum);
                }
            } else {
                if (getPresenter() != null) {
                    getPresenter().getNotification_DbFailure(BaseApplication.mContext.getString(R.string.toast_36));
                }
            }
        }
    }
}
