package com.coahr.thoughtrui.mvp.view.startProject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.AnswersDB;
import com.coahr.thoughtrui.DBbean.ImagesDB;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.Base.BaseContract;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.presenter.PagerFragment_aP;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.StartProjectAdapter;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * Created by Leehor
 * on 2018/11/15
 * on 18:31
 * 单选题类型
 */
public class PagerFragment_a extends BaseChildFragment<PagerFragment_aC.Presenter> implements PagerFragment_aC.View {

  @Inject
    PagerFragment_aP p;
    @BindView(R.id.quest_title)
    TextView quest_title;
    @BindView(R.id.radio_group)  //单选组
    RadioGroup radio_group;
    @BindView(R.id.rbtn_t)   //单选是
    RadioButton rbtn_t;
    @BindView(R.id.rbtn_f)    //单选否
    RadioButton rbtn_f;
    @BindView(R.id.project_detail)  //题目
    TextView project_detail;
    @BindView(R.id.user_remark)  //题目说明
    EditText user_remark;
    @BindView(R.id.take_photo)  //拍照
    TextView take_photo;
    @BindView(R.id.project_imageRecycler)
    RecyclerView project_imageRecycler;  //图片展示

    @BindView(R.id.myTitle)  //toolbar
    MyTittleBar myTitle;

    private int position;
    private String dbProjectId;

    private List<String> imagePath;//图片地址
    private List<ImagesDB> imagesDBS;
    private int imageSize=0; //图片个数
    private PagerFragmentPhotoAdapter adapter;
    private GridLayoutManager photoManager;
    private boolean isDelete=false; //是否在删除操作


    public static PagerFragment_a newInstance(int position,String DbProjectId){
        PagerFragment_a pagerFragment_a=new PagerFragment_a();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putString("DbProjectId",DbProjectId);
        pagerFragment_a.setArguments(bundle);
        return pagerFragment_a;
    }

    @Override
    public PagerFragment_aC.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_pager_type_a;
    }

    @Override
    public void initView() {
        myTitle.getRightText().setVisibility(View.VISIBLE);
        myTitle.getRightText().setText("题目列表");
        myTitle.getRightText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KLog.d("点击","右侧");
            }
        });

        myTitle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("提示","结束答题");
            }
        });
      take_photo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (imageSize<10) {
                openMulti((10-imageSize));
            } else {
                ToastUtils.showLong("图片数量足够");
            }
        }
      });
    }

    @Override
    public void initData() {
        adapter = new PagerFragmentPhotoAdapter();
        photoManager = new GridLayoutManager(BaseApplication.mContext,5);
        project_imageRecycler.setLayoutManager(photoManager);
        project_imageRecycler.setAdapter(adapter);
        position = getArguments().getInt("position");
        dbProjectId = getArguments().getString("DbProjectId");
        myTitle.getTvTittle().setText("第"+(position+1)+"题");
        p.getSubject(dbProjectId,position);
        radio_group.setOnCheckedChangeListener(new RadioGroupListener());
        adapter.setListener(new PagerAdapterListener());
    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
            project_detail.setText(subjectsDB.getTitle());
            subjectsDB.getOptions();
            ToastUtils.showLong("题目获取"+subjectsDB.getHt_id());

    }

    @Override
    public void getSubjectFailure(String failure) {

    }

    @Override
    public void getImageSuccess(List<ImagesDB> imagesDBList) {
        this.imagesDBS=imagesDBList;
        for (int i = 0; i <imagesDBList.size() ; i++) {
            imagePath.add(imagesDBList.get(i).getImagePath());
        }
        imageSize = imagesDBList.size();
        if (isDelete){
            adapter.setIsDel(true);
            adapter.setNewData(imagesDBList);
        } else {
            adapter.setIsDel(false);
            adapter.setNewData(imagesDBList);
        }


    }

    @Override
    public void getImageFailure() {

    }

    @Override
    public void getAnswerSuccess(List<AnswersDB> answersDBList) {

    }

    @Override
    public void getAnswerFailure() {

    }

    @Override
    public void DeleteImageSuccess(String Massage) {
        ToastUtils.showLong("删除"+Massage+"成功");
        p.getSubject(dbProjectId,position);
    }

    @Override
    public void DeleteImageFailure(String Massage) {
            ToastUtils.showLong("删除"+Massage+"失败");
    }


    /**
   * 拍照
   */
  private void openMulti(int count){
    RxGalleryFinal rxGalleryFinal = RxGalleryFinal
            .with(getActivity())
            .image()
            .multiple();
    rxGalleryFinal.maxSize(count)
            .imageLoader(ImageLoaderType.GLIDE)
            .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {

              @Override
              protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                List<MediaBean> result = imageMultipleResultEvent.getResult();
                Log.d("选择图片","已选择" + imageMultipleResultEvent.getResult().size() + "张图片");
                Toast.makeText(BaseApplication.mContext, "已选择" + imageMultipleResultEvent.getResult().size() + "张图片", Toast.LENGTH_SHORT).show();
              }

              @Override
              public void onComplete() {
                super.onComplete();
                Toast.makeText(BaseApplication.mContext, "OVER", Toast.LENGTH_SHORT).show();
              }
            })
            .openGallery();
    }

    /**
     * 图片监听
     */
    class PagerAdapterListener implements PagerFragmentPhotoListener{

        @Override
        public void onClick(ImagesDB imagesDB) {
            PhotoAlbumDialogFragment fragment = PhotoAlbumDialogFragment.newInstance();
            fragment.setImgList(imagePath);
            fragment.setFirstSeePosition(position);
            FragmentManager fragmentManager = getFragmentManager();
            fragment.show(fragmentManager, TAG);
        }

        @Override
        public void onLongClick(ImagesDB imagesDB) {
                adapter.setIsDel(true);
                adapter.setNewData(imagesDBS);
        }

        @Override
        public void onDel(ImagesDB imagesDB) {
                p.DeleteImage(imagesDB.getId(),imagesDB.getImageName());
        }
    }
    private void showDialog(String title, String Content) {
        new MaterialDialog.Builder(_mActivity)
                .title(title)
                .content(Content)
                .negativeText("取消")
                .positiveText("确认")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
               _mActivity.onBackPressed();
            }
        }).build().show();
    }
    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

        }
    }
}
