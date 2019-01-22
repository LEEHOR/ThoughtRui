package com.coahr.thoughtrui.dagger.modules;

import com.coahr.thoughtrui.mvp.view.mydata.Fragment_Feedback;
import com.coahr.thoughtrui.mvp.view.reviewed.ReviewInfoList;

import dagger.Module;
import dagger.Provides;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/1/7
 * 描述：
 */
@Module
public class Fragment_FeedBack_Module {
    @Provides
    public  String provideName() {
        return Fragment_Feedback.class.getName();
    }
}
