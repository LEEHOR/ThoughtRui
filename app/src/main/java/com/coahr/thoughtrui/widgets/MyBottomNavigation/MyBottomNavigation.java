package com.coahr.thoughtrui.widgets.MyBottomNavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.widgets.SelectImageView;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 10:01
 * 底部菜单栏
 */
public class MyBottomNavigation extends LinearLayout {
    private Context context;
    private View rootView;//填充进来布局的跟布局

    private int mselect = 0;  //导航栏被选中的一栏

    //被选中时的颜色
    private int selectTextColor = R.color.material_blue_700;

    //失去选中时的颜色
    private int notSelectTextColor = R.color.gray;

    private OnTabPositionListener ontabpostionListener;

    public MyBottomNavigation(Context context) {
        super(context,null);
    }

    public MyBottomNavigation(Context context,AttributeSet attrs) {
        super(context, attrs,0);
        rootView =  LayoutInflater.from(getContext()).inflate(R.layout.layout_mybottom_navigation_item, this, true);
        initViewList();
    }

    private void initViewList(){

        getImage(((ViewGroup)rootView).getChildAt(0),0).toggle();
        getTextView(((ViewGroup)rootView).getChildAt(0),0).setTextColor(getResources().getColor(selectTextColor));
        int childCount = ((ViewGroup)rootView).getChildCount();
        for (int i = 0; i <childCount ; i++) {
            final int finalI = i;
            ((ViewGroup)rootView).getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ontabpostionListener != null) {
                        ontabpostionListener.onPositionTab(finalI);
                    }
                }
            });
        }
    }

    public void beanSelect(int position){
        if (mselect==position){
            return;
        }else {
            //之前被选中的图片和文字变色
            getImage(((ViewGroup)rootView).getChildAt(mselect),mselect).toggle();
            getTextView(((ViewGroup)rootView).getChildAt(mselect),mselect).setTextColor(getResources().getColor(notSelectTextColor));
            //现在变选中的文字变色
            getImage(((ViewGroup)rootView).getChildAt(position),position).toggle();
            getTextView(((ViewGroup)rootView).getChildAt(position),position).setTextColor(getResources().getColor(selectTextColor));
        }
        mselect = position;
    }

    private SelectImageView getImage(View parent , int position){
        return parent.findViewById(position==0?R.id.iv_one:position==1?R.id.iv_two:position==2?R.id.iv_three:position==3?R.id.iv_four:R.id.iv_one);
    }
    private TextView getTextView(View parent , int position){
        return parent.findViewById(position==0?R.id.tv_one:position==1?R.id.tv_two:position==2?R.id.tv_three:position==3?R.id.tv_four:R.id.tv_one);
    }

    public void setOnTabPositionListener(OnTabPositionListener ontablistener) {
        ontabpostionListener = ontablistener;
    }


    public interface OnTabPositionListener {
        void onPositionTab(int position);
    }
}
