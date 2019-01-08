package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.reviewed.ReviewInfoList;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewPager;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
@Module
public class ReviewInfoList_Module {
    @Provides
    public  String provideName() {
        return ReviewInfoList.class.getName();
    }
}
