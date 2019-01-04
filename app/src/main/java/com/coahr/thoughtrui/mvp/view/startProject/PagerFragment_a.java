package com.coahr.thoughtrui.mvp.view.startProject;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coahr.thoughtrui.DBbean.SubjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.DensityUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.FileIOUtils;
import com.coahr.thoughtrui.Utils.FileIoUtils.SaveOrGetAnswers;
import com.coahr.thoughtrui.Utils.JDBC.DataBaseWork;
import com.coahr.thoughtrui.Utils.Permission.OnRequestPermissionListener;
import com.coahr.thoughtrui.Utils.Permission.RequestPermissionUtils;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseChildFragment;
import com.coahr.thoughtrui.mvp.constract.PagerFragment_aC;
import com.coahr.thoughtrui.mvp.model.Bean.EvenBus_recorderType;
import com.coahr.thoughtrui.mvp.model.Bean.PagePostEvent;
import com.coahr.thoughtrui.mvp.model.Bean.isCompleteBean;
import com.coahr.thoughtrui.mvp.presenter.PagerFragment_aP;
import com.coahr.thoughtrui.mvp.view.StartProjectActivity;
import com.coahr.thoughtrui.mvp.view.decoration.SpacesItemDecoration;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.AudioListenerComplete;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.EvaluateInputDialogFragment;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
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
public class PagerFragment_a extends BaseChildFragment<PagerFragment_aC.Presenter> implements PagerFragment_aC.View,View.OnClickListener,AudioListenerComplete {

  @Inject
    PagerFragment_aP p;
    @BindView(R.id.quest_title)  //题目
    TextView quest_title;
    @BindView(R.id.radio_group)  //单选组
    RadioGroup radio_group;
    @BindView(R.id.rbtn_t)   //单选是
    RadioButton rbtn_t;
    @BindView(R.id.rbtn_f)    //单选否
    RadioButton rbtn_f;
    @BindView(R.id.project_detail)  //描述
    TextView project_detail;
    @BindView(R.id.user_remark)  //题目说明
            TextView user_remark;
    @BindView(R.id.take_audio)
    TextView take_audio;  //录音
    @BindView(R.id.iv_play_audio)
    ImageView iv_play_audio;  //播放录音
    @BindView(R.id.tv_delete_audio)
    TextView tv_delete_audio;
    @BindView(R.id.take_photo)  //拍照
    TextView take_photo;
    @BindView(R.id.audio_name)
    TextView audio_name;
    @BindView(R.id.project_imageRecycler)
    RecyclerView project_imageRecycler;  //图片展示
    @BindView(R.id.left_lin)
    LinearLayout left_lin;    //上翻页
    @BindView(R.id.right_lin)
    LinearLayout right_lin;  //下翻页
    private String dbProjectId;
    private int imageSize=0; //图片个数
    private PagerFragmentPhotoAdapter adapter;
    private GridLayoutManager photoManager;
    private boolean isDelete=false; //是否在删除操作
    private SubjectsDB subjectsDB;
    private String ht_projectId;
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;
    private SpacesItemDecoration spacesItemDecoration;
    EvaluateInputDialogFragment dialogFragment = EvaluateInputDialogFragment.newInstance();//评论输入窗口
    String answers = null;
    String remark=null;
    private boolean isAnswer = false, isPhotos = false;
    private int countSize;
    private String name_project;
    private boolean isRecorder; //录音状态
    private boolean isHaveRecorder; //是否有录音
    private String recorderPath; //录音地址
    private String ht_id;
    private int number;

    public static PagerFragment_a newInstance(int position, String DbProjectId, String ht_ProjectId, int countSize,String name_project,String ht_id) {
        PagerFragment_a pagerFragment_a=new PagerFragment_a();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putString("DbProjectId",DbProjectId);
        bundle.putString("ht_ProjectId", ht_ProjectId);
        bundle.putInt("countSize",countSize);
        bundle.putString("name_project",name_project);
        bundle.putString("ht_id",ht_id);
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
        dbProjectId = getArguments().getString("DbProjectId");
        ht_projectId = getArguments().getString("ht_ProjectId");
        countSize = getArguments().getInt("countSize");
        name_project = getArguments().getString("name_project");
        ht_id = getArguments().getString("ht_id");
        List<SubjectsDB> subjectsDBS = DataBaseWork.DBSelectBy_Where(SubjectsDB.class, new String[]{"number"}, "ht_id=?", ht_id);
        if (subjectsDBS != null && subjectsDBS.size()>0) {
            number = subjectsDBS.get(0).getNumber();
        }
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (take_photo.getText().equals("点击拍照")) {
                    if (imageSize < 10) {
                        openMulti((10 - imageSize));
                    } else {
                        ToastUtils.showLong("图片数量足够");
                    }
                }
                if (take_photo.getText().equals("取消删除")) {
                    isDelete = false;
                    take_photo.setText("点击拍照");
                    p.getSubject(dbProjectId, ht_projectId,_mActivity,number,ht_id);
                }

            }
        });
        dialogFragment.setOnInputCallback(new EvaluateInputDialogFragment.InputCallback() {
            @Override
            public void onInputSend(String input, AppCompatDialogFragment dialog) {
                if (input != null && !input.equals("")) {
                    KLog.d("输入", input);
                    user_remark.setText(input);
                    p.saveAnswers(answers, input, ht_projectId,number,ht_id);
                    dialogFragment.dismiss();
                }
            }
        });
        user_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(_mActivity.getSupportFragmentManager(), TAG);
            }
        });
        take_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecorder) {
                    if (!isHaveRecorder) {
                        isRecorder = true;
                        getAudioPermission();
                    } else {
                        showDialog("提示","当前题目有录音，是否删除重录");
                    }
                } else {
                    ToastUtils.showLong("当前正在录音");
                }

            }
        });
        StartProjectActivity.setAudioListenerComplete(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spacesItemDecoration = new SpacesItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 2), DensityUtils.dp2px(BaseApplication.mContext, 2), getResources().getColor(R.color.colorPrimaryDark));

    }

    @Override
    public void initData() {
        adapter = new PagerFragmentPhotoAdapter();
        photoManager = new GridLayoutManager(BaseApplication.mContext,5);
        project_imageRecycler.setLayoutManager(photoManager);
        project_imageRecycler.setAdapter(adapter);
        if (project_imageRecycler.getItemDecorationCount() == 0) {
            project_imageRecycler.addItemDecoration(spacesItemDecoration);
        } else {
            int itemDecorationCount = project_imageRecycler.getItemDecorationCount();
            for (int i = 1; i < itemDecorationCount; i++) {
                project_imageRecycler.removeItemDecorationAt(i);
            }

        }

        KLog.d("参数", number, dbProjectId, ht_projectId);
        p.getSubject(dbProjectId, ht_projectId,_mActivity,number,ht_id);
        radio_group.setOnCheckedChangeListener(new RadioGroupListener());
        adapter.setListener(new PagerAdapterListener());
        left_lin.setOnClickListener(this);
        right_lin.setOnClickListener(this);


    }

    @Override
    public void getSubjectSuccess(SubjectsDB subjectsDB) {
        this.subjectsDB = subjectsDB;
        quest_title.setText(subjectsDB.getTitle()); //题目
        String options = subjectsDB.getOptions();
        if (options != null) {    //选项
            String[] split = options.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    if (i == 0) {
                        rbtn_t.setText(split[0]);
                    }
                    if (i == 1) {
                        rbtn_f.setText(split[1]);
                    }
                }
            }
        }
        String description = subjectsDB.getDescription();
        if (description != null && !description.equals("")) {
            project_detail.setVisibility(View.VISIBLE);
            project_detail.setText(description);
        } else {
            project_detail.setVisibility(View.GONE);
        }

    }

    @Override
    public void getSubjectFailure(String failure) {

    }

    @Override
    public void getImageSuccess(List<String> imagePathList) {
        KLog.d("图片", imagePathList.size());
        if (imagePathList.size() > 0) {
            isPhotos = true;
            imageSize = imagePathList.size();
            if (isDelete) {
                adapter.setIsDel(true);
                adapter.setNewData(imagePathList);
                adapter.setImageList(imagePathList);
            } else {
                adapter.setIsDel(false);
                adapter.setNewData(imagePathList);
                adapter.setImageList(imagePathList);
            }
        } else {
            take_photo.setText("点击拍照");
            isDelete = false;
            adapter.setIsDel(false);
            adapter.setNewData(imagePathList);
            adapter.setImageList(imagePathList);
        }

    }

    @Override
    public void getImageFailure() {
        isDelete = false;
        isPhotos = true;
    }

    @Override
    public void getAnswerSuccess(String message) {
        if (message != null) {
            String[] split = message.split("&");
            if (split != null && split.length > 0) {
                for (int i = 0; i < split.length; i++) {
                    if (i==0) {
                        String s = split[0];
                        String string = SaveOrGetAnswers.getString(s, ":");
                        if (string != null && !string.equals("") && !string.equals("null")) {
                            answers=string;
                            isAnswer=true;
                            if (string.equals("是")){
                                rbtn_t.toggle();
                            }
                            if (string.equals("否")){
                                rbtn_f.toggle();
                            }
                            KLog.d("选择" + string);
                        }
                    }

                    if (i==1){
                          String s1 = split[1];
                          String string1 = SaveOrGetAnswers.getString(s1, ":");
                          if (string1 !=null && !string1.equals("") && !string1.equals("null")){
                              remark=string1;
                            user_remark.setText(string1);
                          }
                    }
                }
            }
        }


    }

    @Override
    public void getAnswerFailure() {
        isAnswer=false;
    }

    @Override
    public void DeleteImageSuccess(String Massage) {
        ToastUtils.showLong(Massage);
       // p.getSubject(dbProjectId, ht_projectId, position);
        p.getImage(ht_projectId,_mActivity,number,ht_id);
    }

    @Override
    public void DeleteImageFailure(String Massage) {
        ToastUtils.showLong(Massage);
    }

    @Override
    public void saveAnswersSuccess() {
        ToastUtils.showShort("答案保存成功");
    }

    @Override
    public void saveAnswersFailure() {
        ToastUtils.showShort("答案保存失败");
    }

    @Override
    public void SaveImagesSuccess() {
            ToastUtils.showShort("图片保存成功");
    }

    @Override
    public void SaveImagesFailure() {
        ToastUtils.showShort("图片保存失败");
    }

    @Override
    public void getAudioSuccess(List<String> audioList) {
        if (audioList != null && audioList.size()>0) {
            isHaveRecorder=true;
            recorderPath= audioList.get(0);
            String e = FileIOUtils.getE(recorderPath, "/");
            audio_name.setText(e);
            tv_delete_audio.setVisibility(View.VISIBLE);
            tv_delete_audio.setTextColor(getResources().getColor(R.color.material_red_A700));
        }
    }

    @Override
    public void getAudioFailure(String failure) {
        isHaveRecorder=false;
        tv_delete_audio.setVisibility(View.INVISIBLE);
    }


    /**
   * 拍照
   */
  private void openMulti(int count){
      setPath();
    RxGalleryFinal rxGalleryFinal = RxGalleryFinal
            .with(getActivity())
            .image()
            .multiple();
    rxGalleryFinal.maxSize(count)
            .imageLoader(ImageLoaderType.GLIDE)
            .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {

              @Override
              protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                  List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();
                  if (mediaBeanList != null && mediaBeanList.size() > 0) {
                      KLog.d(mediaBeanList.get(0).getOriginalPath());
                      p.SaveImages(mediaBeanList,ht_projectId,number,ht_id);
                  }
              }

              @Override
              public void onComplete() {
                super.onComplete();
                Toast.makeText(BaseApplication.mContext, "OVER", Toast.LENGTH_SHORT).show();
              }
            })
            .openGallery();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_lin:
                KLog.d("position",number);
                if (number > 0) {
                    if (isComplete()) {
                        EventBus.getDefault().post(new isCompleteBean(true,number,1));
                    } else {
                        ToastUtils.showLong("当前题目未完成");
                    }
                }
                break;
            case R.id.right_lin:
                if (number < countSize) {
                    if (isComplete()) {
                        EventBus.getDefault().post(new isCompleteBean(true,number,2));
                    } else {
                        ToastUtils.showLong("当前题目未完成");
                    }
                }
                break;
        }
    }

    /**
     * 录音回调
     * @param audioPath
     */
    @Override
    public void AudioSuccess(String audioPath) {
        KLog.d("录音回调",audioPath);
        isRecorder=false;
        p.getAudio(ht_projectId,_mActivity,number,ht_id);
    }

    /**
     * 图片监听
     */
    class PagerAdapterListener implements PagerFragmentPhotoListener{

        @Override
        public void onClick(List<String> imageList, int position) {
            photoAlbumDialogFragment = PhotoAlbumDialogFragment.newInstance();
            photoAlbumDialogFragment.setImgList(imageList);
            photoAlbumDialogFragment.setFirstSeePosition(position);
            FragmentManager fragmentManager = getFragmentManager();
            photoAlbumDialogFragment.show(fragmentManager, TAG);
        }

        @Override
        public void onLongClick(List<String> imageList, int position) {
            take_photo.setText("取消删除");
                adapter.setIsDel(true);
            adapter.setImageList(imageList);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onDel(List<String> imageList, int position) {
            isDelete = true;
            p.DeleteImage(imageList.get(position));
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
                getAudioPermission();
                dialog.dismiss();
            }
        }).build().show();
    }

    class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton button = radio_group.findViewById(i);

            if (!button.isPressed()) {
                return;
            }
            if (rbtn_t.isChecked()) {
                answers = "是";
            }
            if (rbtn_f.isChecked()) {
                answers = "否";
            }
            KLog.d("选择", answers);
            p.saveAnswers(answers, remark, ht_projectId, number,ht_id);
        }
    }

    /**
     * 设置 照片路径 和 裁剪路径
     */
    private void setPath() {

        RxGalleryFinalApi.setImgSaveRxDir(new File(Constants.SAVE_DIR_TAKE_PHOTO));
        RxGalleryFinalApi.setImgSaveRxCropDir(new File(Constants.SAVE_DIR_ZIP_PHOTO));//裁剪会自动生成路径；也可以手动设置裁剪的路径；
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 判断是否完成
     *
     * @return
     */
    private boolean isComplete() {
        if (isPhotos && isAnswer) {
            return true;
        }
        return false;
    }

    /**
     * 动态获取录音权限
     */
    private void getAudioPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RequestPermissionUtils.requestPermission(_mActivity, new OnRequestPermissionListener() {
                        @Override
                        public void PermissionSuccess(List<String> permissions) {
                            EventBus.getDefault().postSticky(new EvenBus_recorderType(1,String.valueOf(number),Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number+"_"+ht_id ,Constants.SAVE_DIR_VOICE_TEM));
                        }

                        @Override
                        public void PermissionFail(List<String> permissions) {
                            Toast.makeText(_mActivity, "获取权限失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void PermissionHave() {
                            EventBus.getDefault().postSticky(new EvenBus_recorderType(1,String.valueOf(number),Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/"+ number+"_"+ht_id ,Constants.SAVE_DIR_VOICE_TEM));
                        }
                    }, Manifest.permission.RECORD_AUDIO
                     , Manifest.permission.WRITE_EXTERNAL_STORAGE
                     , Manifest.permission.READ_EXTERNAL_STORAGE);

        } else {
            EventBus.getDefault().postSticky(new EvenBus_recorderType(1,String.valueOf(number),Constants.SAVE_DIR_PROJECT_Document + ht_projectId + "/" + number+"_"+ht_id ,Constants.SAVE_DIR_VOICE_TEM));
        }
    }
}
