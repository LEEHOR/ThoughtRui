package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.reviewed.ReViewStartAnswering;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/10
 * 描述：
 */
@Module
public class ReViewStartAnswering_Module {
    @Provides
    public  String provideName() {
        return ReViewStartAnswering.class.getName();
    }
}
