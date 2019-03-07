package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.SearchBeans;

import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface FragmentSearch_c {
    interface View extends BaseContract.View {
        void  getSearchSuccess(SearchBeans searchBeans);
        void  getSearchFailure(String fail);
    }

    interface Presenter extends BaseContract.Presenter {
       void  getSearch(Map<String, Object> map);
       void  getSearchSuccess(SearchBeans searchBeans);
       void  getSearchFailure(String fail);
    }

    interface Model extends BaseContract.Model {
        void getSearch(Map<String, Object> map);
    }
}
