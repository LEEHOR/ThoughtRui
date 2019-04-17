package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_1;
import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_Action_plan_presentation_2;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
@Module
public class Fragment_action_plan_presentation_2_Module {
    @Provides
    public  String provideName() {
        return Fragment_Action_plan_presentation_2.class.getName();
    }
}
