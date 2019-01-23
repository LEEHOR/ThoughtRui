package com.coahr.thoughtrui.mvp.view.home.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Leehor
 * on 2018/11/12
 * on 18:58
 */
public class viewPager extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
