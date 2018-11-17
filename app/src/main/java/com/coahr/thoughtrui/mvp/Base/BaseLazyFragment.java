package com.coahr.thoughtrui.mvp.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ScreenUtils;

import butterknife.ButterKnife;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 16:44
 */
public  abstract  class BaseLazyFragment<P extends BaseContract.Presenter> extends BaseFragment {

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
        View tittleView = ((ViewGroup) view.getRootView()).getChildAt(0);
        if (tittleView != null) {
            tittleView.setPadding(tittleView.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tittleView.getPaddingRight(), tittleView.getPaddingBottom());
        }
        addFooterView = inflater.inflate(R.layout.recyclerview_item_foot, container, false);
        UpdateUI(view.getRootView());
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
