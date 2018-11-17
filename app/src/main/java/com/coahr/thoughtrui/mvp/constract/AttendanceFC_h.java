package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.AttendRemark;
import com.coahr.thoughtrui.mvp.model.Bean.Attendance;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 11:08
 */
public interface AttendanceFC_h {
    interface View extends BaseContract.View {


        void getMainDataSuccess(Attendance attendance);

        void getMainDataFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {


        void getMainData(Map<String,Object> map);

        void getMainDataSuccess(Attendance attendance);

        void getMainDataFailure(String failure);


    }

    interface Model extends BaseContract.Model {


        void getMainData(Map<String,Object> map);

    }
}
