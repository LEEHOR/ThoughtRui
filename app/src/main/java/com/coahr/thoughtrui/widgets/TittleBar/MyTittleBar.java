package com.coahr.thoughtrui.widgets.TittleBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coahr.thoughtrui.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 16:30
 */
public class MyTittleBar extends RelativeLayout {

    private String arrayString;
    private View view;

    public MyTittleBar(Context context) {
        super(context,null);
    }

    public MyTittleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyTittleBar);
        arrayString = array.getString(R.styleable.MyTittleBar_tittle);
        array.recycle();
        view = LayoutInflater.from(getContext()).inflate(R.layout.layout_my_tittlebar, this, true);
        getTvTittle().setText(arrayString);
    }

    public View getRoot(){
        return (RelativeLayout)view.findViewById(R.id.root_title);
    }

    /**
     * 获取标题
     */

    public TextView getTvTittle()
    {
        return (TextView) view.findViewById(R.id.tv_title);
    }

    public TextView getTvTittle(int resId)
    {
        return (TextView) view.findViewById(resId);
    }




    public ImageView getLeftIcon()
    {
        return (ImageView) view.findViewById(R.id.iv_left);
    }

    public ImageView getLeftIcon(int resId)
    {
        return (ImageView) view.findViewById(resId);
    }



    public ImageView getRightIcon()
    {
        return (ImageView) view.findViewById(R.id.iv_right);
    }

    public ImageView getRightIcon(int resId)
    {
        return (ImageView) view.findViewById(resId);
    }



    public TextView getRightText()
    {
        return (TextView) view.findViewById(R.id.tv_right);
    }

    public TextView getRightText(int resId)
    {
        return (TextView) view.findViewById(resId);
    }

}
