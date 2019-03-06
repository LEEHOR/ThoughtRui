package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
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
public class ProjectTemplate_M extends BaseModel<ProjectTemplate_c.Presenter> implements ProjectTemplate_c.Model {
   @Inject
    public ProjectTemplate_M() {
       super();
    }

    @Override
    public void getProjectTemplates(Map<String, Object> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<Template_list>(getApiService().template(map)))
                .subscribeWith(new SimpleDisposableSubscriber<Template_list>() {
                    @Override
                    public void _onNext(Template_list template_list) {
                        if (getPresenter() != null) {
                            if (template_list.getStatus() == 1) {
                                getPresenter().getProjectTemplatesSuccess(template_list);
                            } else {
                                getPresenter().getProjectTemplateFailure(template_list.getStatus()+"");
                            }
                        }
                    }
                }));
    }
}
