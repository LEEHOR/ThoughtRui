package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import com.coahr.thoughtrui.DBbean.ImagesDB;

/**
 * Created by Leehor
 * on 2018/11/16
 * on 10:11
 */
public interface PagerFragmentPhotoListener {
    void onClick(ImagesDB imagesDB);
    void onLongClick(ImagesDB imagesDB);
    void onDel(ImagesDB imagesDB);
}
