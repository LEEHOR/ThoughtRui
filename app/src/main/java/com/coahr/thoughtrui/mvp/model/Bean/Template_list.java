package com.coahr.thoughtrui.mvp.model.Bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/3/5
 * 描述：
 */
public class Template_list implements Serializable {


    /**
     * template_list : [{"modify_time":1550823360000,"name":"rrtret","id":"e7a042c316c045d489d5f0e0e15fe57c"}]
     * status : 1
     */

    private int status;
    private List<TemplateListBean> template_list;

    @Override
    public String toString() {
        return "Template_list{" +
                "status=" + status +
                ", template_list=" + template_list +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TemplateListBean> getTemplate_list() {
        return template_list;
    }

    public void setTemplate_list(List<TemplateListBean> template_list) {
        this.template_list = template_list;
    }

    public static class TemplateListBean implements IPickerViewData {
        /**
         * modify_time : 1550823360000
         * name : rrtret
         * id : e7a042c316c045d489d5f0e0e15fe57c
         */

        private long modify_time;
        private String name;
        private String id;

        public long getModify_time() {
            return modify_time;
        }

        public void setModify_time(long modify_time) {
            this.modify_time = modify_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
}
