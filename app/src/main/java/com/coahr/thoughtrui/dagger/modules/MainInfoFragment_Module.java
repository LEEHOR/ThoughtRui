package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.home.MainInfoFragment;
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
public class MainInfoFragment_Module {
    @Provides
    public  String provideName() {
        return MainInfoFragment.class.getName();
    }
}
