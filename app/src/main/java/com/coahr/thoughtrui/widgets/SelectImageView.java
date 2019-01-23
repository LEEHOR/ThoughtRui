package com.coahr.thoughtrui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.coahr.thoughtrui.R;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 10:12
 * 点击切换图片的ImageView
 */
public class SelectImageView extends AppCompatImageView implements Checkable {
    private Drawable mDrawable;
    private Drawable mSelectImage;
    private boolean isCheck;
    public SelectImageView(Context context) {
        super(context,null);
    }

    public SelectImageView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        /**获取默认属性src的Drawable并用成员变量保存*/
        mDrawable=getDrawable();
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.SelectImageView);
        /**获取自定义属性selector_src的Drawable并用成员变量保存*/
        Drawable arrayDrawable= array.getDrawable(R.styleable.SelectImageView_select_src);
        mSelectImage=arrayDrawable;
        /**获取自定义属性checked并用成员变量保存*/
        isCheck=array.getBoolean(R.styleable.SelectImageView_image_checked,false);
        setChecked(isCheck);
        if (arrayDrawable !=null && isCheck){
            /**如果在布局中设置了selector_src与checked = true，我们就要设置ImageView的图片为mSelectorDrawable */
            setImageDrawable(arrayDrawable);
        }
        array.recycle();
    }


    @Override
    public void setChecked(boolean b) {
        this.isCheck=b;
    }

    @Override
    public boolean isChecked() {
        return isCheck;
    }

    @Override
    public void toggle() {
        /**此处依据是否选中来设置不同的图片*/
        if (!isChecked()) {
            setImageDrawable(mSelectImage);
            setChecked(true);
        } else {
            setImageDrawable(mDrawable);
            setChecked(false);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    /**外部通过调用此方法传入checked参数，然后把值传入给setChecked（）方法改变当前的选中状态*/
    public void toggle(boolean checked){
        setChecked(checked);
        toggle();
    }

}
