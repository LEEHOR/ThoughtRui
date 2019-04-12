package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_action_projects;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
@Module
public class Fragment_action_projects_Module {
    @Provides
    public  String provideName() {
        return Fragment_action_projects.class.getName();
    }
}
