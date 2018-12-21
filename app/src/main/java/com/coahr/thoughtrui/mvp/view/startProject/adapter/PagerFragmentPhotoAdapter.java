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
public class PagerFragmentPhotoAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

   private PagerFragmentPhotoListener listener;
   private List<String> imageList;
   private boolean isDel=false;
    public PagerFragmentPhotoAdapter() {
        super(R.layout.item_fragment_photo,null);
    }
    public void setImageList(List<String> imageList){
        this.imageList=imageList;

    }
    public void setIsDel(boolean isDel){
        this.isDel=isDel;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        Imageloader.loadImage(item, (ImageView) helper.getView(R.id.photo_image));
        if (isDel){
            helper.getView(R.id.photo_image_de).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.photo_image_de).setVisibility(View.GONE);
        }
        helper.getView(R.id.photo_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(imageList,helper.getAdapterPosition());
                }
            }
        });
        helper.getView(R.id.photo_image).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                    listener.onLongClick(imageList,helper.getAdapterPosition());
                }
                return false;
            }
        });
        helper.getView(R.id.photo_image_de).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDel(imageList,helper.getAdapterPosition());
                }
            }
        });
    }

    public void setListener(PagerFragmentPhotoListener listener) {
        this.listener = listener;
    }
}
