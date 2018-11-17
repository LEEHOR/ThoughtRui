package com.coahr.thoughtrui.mvp.view.startProject.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.imageLoader.Imageloader;

import java.util.List;

/**
 * Created by Leehor
 * on 2018/11/16
 * on 10:08
 */
public class PagerFragmentPhotoAdapter extends BaseQuickAdapter<ImagesDB,BaseViewHolder> {

   private PagerFragmentPhotoListener listener;
   private boolean isDel=false;
    public PagerFragmentPhotoAdapter() {
        super(R.layout.item_fragment_photo,null);
    }
    public void setIsDel(boolean isDel){
        this.isDel=isDel;
    }
    @Override
    protected void convert(BaseViewHolder helper, final ImagesDB item) {
        Imageloader.loadImage(item.getImagePath(), (ImageView) helper.getView(R.id.photo_image));
        if (isDel){
            helper.getView(R.id.photo_image_de).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.photo_image_de).setVisibility(View.GONE);
        }
        helper.getView(R.id.photo_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(item);
                }
            }
        });
        helper.getView(R.id.photo_image).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                    listener.onLongClick(item);
                }
                return false;
            }
        });
        helper.getView(R.id.photo_image_de).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDel(item);
                }
            }
        });
    }

    public void setListener(PagerFragmentPhotoListener listener) {
        this.listener = listener;
    }
}
