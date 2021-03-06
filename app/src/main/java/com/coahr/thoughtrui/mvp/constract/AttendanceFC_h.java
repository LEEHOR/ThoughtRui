package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.AttendanceHistory;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface AttendanceFC_h {
    interface View extends BaseContract.View {

        void getAttendanceHistorySuccess(AttendanceHistory history);

        void getAttendanceHistoryFailure(String failure,int code);
    }

    interface Presenter extends BaseContract.Presenter {

        void getAttendanceHistory(Map<String,Object> map);

        void getAttendanceHistorySuccess(AttendanceHistory history);

        void getAttendanceHistoryFailure(String failure,int code);




    }

    interface Model extends BaseContract.Model {
        void getAttendanceHistory(Map<String,Object> map);
    }
}
