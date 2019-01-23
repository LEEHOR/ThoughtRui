package com.coahr.thoughtrui.mvp.constract;

import com.baidu.location.BDLocation;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.CensorBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 17:07
 */
public interface ReviewPagerFragment_C {
    interface View extends BaseContract.View {

        void getCensorListSuccess(CensorBean censorBean);

        void getCensorListFailure(String failure);
    }

    interface Presenter extends BaseContract.Presenter {

        void getCensorList(Map<String, Object> map);

        void getCensorListSuccess(CensorBean censorBean);

        void getCensorListFailure(String failure);

    }

    interface Model extends BaseContract.Model {

        void getCensorList(Map<String, Object> map);

    }
}
