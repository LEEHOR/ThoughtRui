package com.coahr.thoughtrui.mvp.view.UMPush.adapter;


import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.TimeUtils;
import com.coahr.thoughtrui.Utils.imageLoader.Imageloader;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.model.Bean.NotificationBean;

import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/8
 * 描述：
 */
public class NotificationAdapter extends BaseQuickAdapter<NotificationBean.Notification, BaseViewHolder> {

    private pushCallBack callBack;
    public NotificationAdapter() {
        super(R.layout.item_notification, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotificationBean.Notification item) {
        if (item != null) {
            helper.setImageResource(R.id.notifi_iv,item.getType()==1? R.mipmap.notification_sc
                    :item.getType()==2?R.mipmap.notification_cq
                    :item.getType()==3?R.mipmap.notification_sh
                    :R.mipmap.notification_cq)
                    .setText(R.id.notifi_tv_tittle,item.getNotificationTittle())
                    .setText(R.id.notifi_tv_content,item.getNotificationContent())
                    .setText(R.id.notifi_tv_time, TimeUtils.getStingYMDHM(item.getNotificationTime()));

            helper.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (callBack != null) {
                        callBack.itemClickLong(item);
                    }
                    return true;
                }
            });
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.itemClick(item);
                    }
                }
            });
        }
    }

    public interface pushCallBack{
        void itemClickLong(NotificationBean.Notification item);
        void itemClick(NotificationBean.Notification item);
    }

    public void setCallBack(pushCallBack callBack) {
        this.callBack = callBack;
    }
}
