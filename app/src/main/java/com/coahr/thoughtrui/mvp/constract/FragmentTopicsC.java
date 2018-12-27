package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/13
 * on 19:34
 */
public interface FragmentTopicsC {
    interface View extends BaseContract.View {

        void  getSubjectListSuccess(SubjectListBean subjectListBean);

        void  getSubjectListFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {

        void  getSubjectList(Map<String,Object> map);

        void  getSubjectListSuccess(SubjectListBean subjectListBean);

        void  getSubjectListFailure(String failure);
    }

    interface Model extends BaseContract.Model {

        void  getSubjectList(Map<String,Object> map);

    }
}
