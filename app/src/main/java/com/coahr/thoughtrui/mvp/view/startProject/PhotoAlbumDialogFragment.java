package com.coahr.thoughtrui.mvp.view.startProject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ScreenUtils;
import com.coahr.thoughtrui.Utils.imageLoader.Imageloader;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
  * Created by Leehor
  * on 2018/11/15
  * on 18:02
  * 图片浏览页面
  */

public class PhotoAlbumDialogFragment extends AppCompatDialogFragment {

    @BindView(R.id.fl_photo_album)
    FrameLayout flPhotoAlbum;
    @BindView(R.id.vp_photo_album)
    ViewPager vpPhotoAlbum;
    @BindView(R.id.tv_photo_number)
    TextView tvPhotoNumber;

    Unbinder unbinder;

    //照片的url list
    private List<String> imgList;

    //照片数量
    private int photoCount = 0;


    //从相册中哪一张开始看

    private int mFirstSee = 0;

    public static PhotoAlbumDialogFragment newInstance() {
        PhotoAlbumDialogFragment fragment = new PhotoAlbumDialogFragment();
        Bundle args = new Bundle();
        //传参
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmentdialog_photo_album, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        PhotoAblumAdapter ablumAdapter = new PhotoAblumAdapter(imgList);
        vpPhotoAlbum.setAdapter(ablumAdapter);
        vpPhotoAlbum.setCurrentItem(mFirstSee);
        tvPhotoNumber.setText(mFirstSee + 1 + "/" + photoCount);
        vpPhotoAlbum.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvPhotoNumber.setText(position + 1 + "/" + photoCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpPhotoAlbum.setCurrentItem(mFirstSee);
        flPhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoAlbumDialogFragment.this.dismiss();
            }
        });

    }


    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
        photoCount = imgList.size();
    }

    public void setFirstSeePosition(int postion) {
        mFirstSee = postion;
    }


    public class PhotoAblumAdapter extends PagerAdapter {
        private List<String> imgList;
        private int[] imgArray;
        private int size;
        private final int cacheCount = 3;//内存中缓存多少个Imageview
        private List<ImageView> imageViews = new ArrayList<ImageView>();

        public PhotoAblumAdapter(List<String> imgList) {
            this.imgList = imgList;
            size = this.imgList.size();
            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(BaseApplication.mContext);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(BaseApplication.mContext), ScreenUtils.getScreenWidth(BaseApplication.mContext)));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Imageloader.loadImage(imgList.get(i), imageView);
                imageViews.add(imageView);
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (size > cacheCount) {
                container.removeView(imageViews.get(position));
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewGroup parent = (ViewGroup) imageViews.get(position).getParent();
            if (parent != null) {
                parent.removeView(imageViews.get(position));
            }
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

            window.setWindowAnimations(R.style.Photo_See_Animation);
        }
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
