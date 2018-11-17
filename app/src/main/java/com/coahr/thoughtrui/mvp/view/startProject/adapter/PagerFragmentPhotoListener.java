package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import com.coahr.thoughtrui.DBbean.ImagesDB;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/16
 * on 10:11
 */
public interface PagerFragmentPhotoListener {
    void onClick(List<String> imagePathList, int position);
    void onLongClick(List<String> imagePathList,int position);
    void onDel(List<String> imagePathList,int position);
}
