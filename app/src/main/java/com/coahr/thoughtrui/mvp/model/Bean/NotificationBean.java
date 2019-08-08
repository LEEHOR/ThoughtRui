package com.coahr.thoughtrui.mvp.model.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/27
 * 描述：通知消息
 */
public class NotificationBean  {
        private List<Notification> notificationList;

    public NotificationBean() {
    }

    public NotificationBean(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public  static  class  Notification implements  Parcelable {
        /**
         * 通知消息
         */
        private int type;
        private String NotificationTittle;
        private String NotificationContent;
        private long NotificationTime;
        private int projectId;

        @Override
        public String toString() {
            return "Notification{" +
                    "type=" + type +
                    ", NotificationTittle='" + NotificationTittle + '\'' +
                    ", NotificationContent='" + NotificationContent + '\'' +
                    ", NotificationTime=" + NotificationTime +
                    ", projectId=" + projectId +
                    '}';
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public Notification() {
        }

        public Notification(int type, String notificationTittle, String notificationContent, long notificationTime) {
            this.type = type;
            NotificationTittle = notificationTittle;
            NotificationContent = notificationContent;
            NotificationTime = notificationTime;
        }

        protected Notification(Parcel in) {
            type = in.readInt();
            NotificationTittle = in.readString();
            NotificationContent = in.readString();
            NotificationTime = in.readLong();
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getNotificationTittle() {
            return NotificationTittle;
        }

        public void setNotificationTittle(String notificationTittle) {
            NotificationTittle = notificationTittle;
        }

        public String getNotificationContent() {
            return NotificationContent;
        }

        public void setNotificationContent(String notificationContent) {
            NotificationContent = notificationContent;
        }

        public long getNotificationTime() {
            return NotificationTime;
        }

        public void setNotificationTime(long notificationTime) {
            NotificationTime = notificationTime;
        }

        public static final Creator<Notification> CREATOR = new Creator<Notification>() {
            @Override
            public Notification createFromParcel(Parcel in) {
                return new Notification(in);
            }

            @Override
            public Notification[] newArray(int size) {
                return new Notification[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(type);
            parcel.writeString(NotificationTittle);
            parcel.writeString(NotificationContent);
            parcel.writeLong(NotificationTime);
        }
    }
}
