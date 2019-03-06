package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;

import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface ProjectDealer_c {
    interface View extends BaseContract.View {
        void  getProjectDealerSuccess(Dealer_List dealer_list);
        void  getProjectDealerFailure(String fail);
    }

    interface Presenter extends BaseContract.Presenter {
       void  getProjectDealer(Map<String, Object> map);
       void  getProjectDealerSuccess(Dealer_List dealer_list);
       void  getProjectDealerFailure(String fail);
    }

    interface Model extends BaseContract.Model {
        void  getProjectDealer(Map<String, Object> map);
    }
}
