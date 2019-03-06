package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ProjectDealer_c;
import com.coahr.thoughtrui.mvp.constract.ProjectTemplate_c;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;
import com.coahr.thoughtrui.mvp.model.ProjectDealer_M;
import com.coahr.thoughtrui.mvp.model.ProjectTemplate_M;
import com.coahr.thoughtrui.mvp.view.home.DealerFragment;
import com.coahr.thoughtrui.mvp.view.home.ProjectTemplate;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class ProjectDealer_P extends BasePresenter<ProjectDealer_c.View,ProjectDealer_c.Model> implements ProjectDealer_c.Presenter {
    @Inject
    public ProjectDealer_P(DealerFragment mview, ProjectDealer_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getProjectDealer(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getProjectDealer(map);
        }
    }

    @Override
    public void getProjectDealerSuccess(Dealer_List dealer_list) {
        if (getView() != null) {
            getView().getProjectDealerSuccess(dealer_list);
        }
    }

    @Override
    public void getProjectDealerFailure(String fail) {
        if (getView() != null) {
            getView().getProjectDealerFailure(fail);
        }
    }
}
