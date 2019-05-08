package com.coahr.thoughtrui.mvp.view.UMPush;

import android.content.Intent;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.Base.BaseLazyFragment;
import com.coahr.thoughtrui.mvp.constract.Fragment_Umeng_C;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;
import com.coahr.thoughtrui.mvp.presenter.Fragment_UmengP;
import com.coahr.thoughtrui.mvp.view.ConstantsActivity;
import com.coahr.thoughtrui.mvp.view.MainActivity;
import com.coahr.thoughtrui.mvp.view.UMPush.adapter.NotificationAdapter;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.home.MainFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：推送列表
 */
public class Fragment_Umeng extends BaseFragment<Fragment_UmengP> implements Fragment_Umeng_C.View {
    @Inject
    Fragment_UmengP p;
    @BindView(R.id.umeng_tittle)
    MyTittleBar umeng_tittle;
    @BindView(R.id.umeng_rcyv)
    RecyclerView umeng_rcyv;
    private NotificationAdapter notificationAdapter;
    private List<NotificationBean.Notification> notificationList = new ArrayList<>();

    public static Fragment_Umeng newInstance() {
        return new Fragment_Umeng();
    }

    @Override
    public Fragment_UmengP getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_umeng;
    }

    @Override
    public void initView() {
        Constants.message=0;
        umeng_tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
        getDateBy_net();
    }

    @Override
    public void initData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BaseApplication.mContext);
        umeng_rcyv.setLayoutManager(layoutManager);
        notificationAdapter = new NotificationAdapter();
        umeng_rcyv.setAdapter(notificationAdapter);
        for (int i = 0; i < umeng_rcyv.getItemDecorationCount(); i++) {
            if (i != 0) {
                umeng_rcyv.removeItemDecorationAt(i);
            }
        }
        umeng_rcyv.addItemDecoration(new SpacesItemDecoration(0, DensityUtils.dp2px(BaseApplication.mContext, 5)));
        notificationAdapter.setCallBack(new NotificationAdapter.pushCallBack() {
            @Override
            public void itemClickLong(NotificationBean.Notification item) {
                new MaterialDialog.Builder(_mActivity)
                        .title(getResources().getString(R.string.dialog_tittle_7))
                        .content(getResources().getString(R.string.dialog_content_3))
                        .negativeText(getResources().getString(R.string.cancel))
                        .positiveText(getResources().getString(R.string.resume))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (notificationList != null) {
                                    notificationList.remove(item);
                                    notificationAdapter.setNewData(notificationList);
                                }
                                dialog.dismiss();
                            }
                        }).build().show();
            }

            @Override
            public void itemClick(NotificationBean.Notification item) {
                if (item != null) {
                    if (item.getType() == 1) {   //上传
                        JumpToMainActivity(1);
                    } else if (item.getType() == 2) { //超期
                        JumpToProjectList(Constants.fragment_main);
                    } else if (item.getType() == 3) {  //审核
                        JumpToMainActivity(2);
                    }
                }
            }
        });

    }

    @Override
    public void getNotification_DbSuccess(NotificationBean notificationBean) {

        if (notificationBean != null) {
            if (notificationBean.getNotificationList() != null && notificationBean.getNotificationList().size() > 0) {
                notificationList.addAll(notificationBean.getNotificationList());
            }
        }

        if (notificationList.size() > 0) {
            notificationAdapter.setNewData(notificationList);
        } else {

        }

    }

    @Override
    public void getNotification_DbFailure(String failure) {
        if (notificationList.size() > 0) {
            notificationAdapter.setNewData(notificationList);
        } else {
        }
    }

    @Override
    public void getNotification_netSuccess(CensorBean censorBean) {
        notificationList.clear();
        if (censorBean != null && censorBean.getData().getList() != null && censorBean.getData().getList().size() > 0) {
            for (int i = 0; i < censorBean.getData().getList().size(); i++) {
                NotificationBean.Notification notification = new NotificationBean.Notification();
                notification.setType(3);
                notification.setNotificationTittle(getResources().getString(R.string.umeng_fragment_not_pass));
                notification.setNotificationContent(getResources().getString(R.string.umeng_fragment_not_pass_detail) + (censorBean.getData().getList().get(i).getService_code()));
                notification.setNotificationTime(System.currentTimeMillis());
                notificationList.add(notification);
            }
        }
        p.getNotification_Db(Constants.sessionId);
    }

    @Override
    public void getNotification_netFailure(String failure) {
        ToastUtils.showLong(failure);
        p.getNotification_Db(Constants.sessionId);
    }

    private void getDateBy_net() {
        Map map = new HashMap();
        map.put("sessionId", Constants.sessionId);
        map.put("status", -1);
        p.getNotification_net(map);
    }

    /**
     * 跳转到历史项目
     */
    private void JumpToProjectList(int i) {
        start(MainFragment.newInstance(2,2));
    }

    private void JumpToMainActivity(int i) {
        Intent intent = new Intent(_mActivity, MainActivity.class);
        intent.putExtra("page", i);
        startActivity(intent);
    }
}
