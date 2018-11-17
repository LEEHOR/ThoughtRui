package com.coahr.thoughtrui.Utils.imageLoader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.coahr.thoughtrui.commom.Constants;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 11:23
 * 基础配置
 */

public class GlideCache implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
//        String storageDirectory = StoreSpaceUtils.getSDCardPath();
//        String downloadDirectoryPath = storageDirectory + "/GlideCache";
        //设置缓存的大小为100M
        int cacheSize = 100 * 1000 * 1000;
        builder.setDiskCache(new DiskLruCacheFactory(Constants.SAVE_DIR_GLIDE_CACHE, cacheSize));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
