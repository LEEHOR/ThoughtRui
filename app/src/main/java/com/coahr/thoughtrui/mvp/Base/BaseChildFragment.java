package com.coahr.thoughtrui.mvp.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Author： hengzwd on 2018/8/9.
 * Email：hengzwdhengzwd@qq.com
 */

//被其他fragment  load的子fragment
public abstract class BaseChildFragment<P extends BaseContract.Presenter> extends BaseFragment implements BaseContract.View {


    private boolean hasload = false;


    /**
     * onHiddenChanged()方法是否被调用，当一个fragment第一个被add到activity，这个方法不调用，使用懒加载的话，这个fragment的initview和initdata不调用
     * 所以这里做一个判断方法是否被调用来判断这个fragment是不是第一个被加入的fragment，然后在onstart（）中调用这个fragment的initview和initdata（）；
     */
    private boolean isonHiddenChangedMethodInvoke = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        //由于沉浸式要留白 标题栏，在这里统一设置，支出statusbar的空间，之后每个fragment的头顶第一个子view，都要
        //以一个viewgroup包含要显示tittle的子view形式进行布局，则此代码正确有效
//        view.getRootView().setBackgroundColor(getResources().getColor(R.color.app_background_color));
        UpdateUI(view.getRootView());
//        initView();
//        initData();
//        EventBus.getDefault().register(this);
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        if (!isonHiddenChangedMethodInvoke) {
            initView();
            initData();
            hasload = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isonHiddenChangedMethodInvoke= true;
        if (!hidden&&!hasload) {
            initView();
            initData();
            hasload = true;
        }
    }


}
