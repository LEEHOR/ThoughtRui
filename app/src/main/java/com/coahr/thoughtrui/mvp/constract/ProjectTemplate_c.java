package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;

import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface ProjectTemplate_c {
    interface View extends BaseContract.View {
        void  getProjectTemplatesSuccess();
        void  getProjectTemplateFailure(String fail);
    }

    interface Presenter extends BaseContract.Presenter {
       void  getProjectTemplates(Map<String,Object> map);
       void  getProjectTemplatesSuccess();
       void  getProjectTemplateFailure(String fail);
    }

    interface Model extends BaseContract.Model {
        void  getProjectTemplates(Map<String,Object> map);
    }
}
