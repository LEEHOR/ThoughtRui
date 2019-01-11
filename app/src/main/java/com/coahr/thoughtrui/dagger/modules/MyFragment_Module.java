package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.mydata.MyFragment;
import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStart;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
@Module
public class MyFragment_Module {
    @Provides
    public  String provideName() {
        return MyFragment.class.getName();
    }
}
