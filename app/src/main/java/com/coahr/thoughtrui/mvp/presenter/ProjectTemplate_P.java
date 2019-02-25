package com.coahr.thoughtrui.mvp.presenter;

import com.coahr.thoughtrui.mvp.Base.BasePresenter;
import com.coahr.thoughtrui.mvp.constract.ProjectTemplate_c;
import com.coahr.thoughtrui.mvp.model.ProjectTemplate_M;
import com.coahr.thoughtrui.mvp.view.home.ProjectTemplate;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public class ProjectTemplate_P extends BasePresenter<ProjectTemplate_c.View,ProjectTemplate_c.Model> implements ProjectTemplate_c.Presenter {
    @Inject
    public ProjectTemplate_P(ProjectTemplate mview, ProjectTemplate_M mModel) {
        super(mview, mModel);
    }

    @Override
    public void getProjectTemplates(Map<String, Object> map) {
        if (mModle != null) {
            mModle.getProjectTemplates(map);
        }
    }

    @Override
    public void getProjectTemplatesSuccess() {
        if (getView() != null) {
            getView().getProjectTemplatesSuccess();
        }
    }

    @Override
    public void getProjectTemplateFailure(String fail) {
        if (getView() != null) {
            getView().getProjectTemplateFailure(fail);
        }
    }
}
