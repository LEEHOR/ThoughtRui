package com.coahr.thoughtrui.mvp.model;

import com.coahr.thoughtrui.mvp.Base.BaseModel;
import com.coahr.thoughtrui.mvp.constract.ProjectTemplate_c;

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

    }
}
