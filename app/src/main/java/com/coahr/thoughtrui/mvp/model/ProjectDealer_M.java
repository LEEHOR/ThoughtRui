package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ProjectDealer_c;
import com.coahr.thoughtrui.mvp.constract.ProjectTemplate_c;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class ProjectDealer_M extends BaseModel<ProjectDealer_c.Presenter> implements ProjectDealer_c.Model {
   @Inject
    public ProjectDealer_M() {
       super();
    }

    @Override
    public void getProjectDealer(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<Dealer_List>(getApiService().dealer(map)))
                .subscribeWith(new SimpleDisposableSubscriber<Dealer_List>() {
                    @Override
                    public void _onNext(Dealer_List dealer_list) {
                        if (getPresenter() != null) {
                            if (dealer_list.getStatus() == 1) {
                                getPresenter().getProjectDealerSuccess(dealer_list);
                            } else {
                                getPresenter().getProjectDealerFailure(dealer_list.getStatus()+"");
                            }
                        }
                    }
                }));
    }
}
