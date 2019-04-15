package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.action_plan.Fragment_action_plan_viewPager;
import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
@Module
public class Fragment_plan_viewPager_Module {
    @Provides
    public  String provideName() {
        return Fragment_action_plan_viewPager.class.getName();
    }
}
