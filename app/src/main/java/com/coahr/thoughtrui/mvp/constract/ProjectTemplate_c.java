package com.coahr.thoughtrui.mvp.constract;

import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.model.Bean.ChangePassWord;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.model.Bean.Template_list;

import java.util.Map;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/25
 * 描述：
 */
public interface ProjectTemplate_c {
    interface View extends BaseContract.View {
        void  getProjectTemplatesSuccess(Template_list template_list);
        void  getProjectTemplateFailure(String fail);
    }

    interface Presenter extends BaseContract.Presenter {
       void  getProjectTemplates(Map<String,Object> map);
       void  getProjectTemplatesSuccess(Template_list template_list);
       void  getProjectTemplateFailure(String fail);
    }

    interface Model extends BaseContract.Model {
        void  getProjectTemplates(Map<String,Object> map);
    }
}
