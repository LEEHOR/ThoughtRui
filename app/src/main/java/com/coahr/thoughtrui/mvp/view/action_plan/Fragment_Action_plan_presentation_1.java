package com.coahr.thoughtrui.mvp.view.action_plan;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coahr.thoughtrui.DBbean.ProjectsDB;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.LoadCity.JsonBean;
import com.coahr.thoughtrui.Utils.LoadCity.JsonFileReader;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.Fragment_action_plan_pre_1_c;
import com.coahr.thoughtrui.mvp.model.Bean.ThreeAdapter.SubjectListBean;
import com.coahr.thoughtrui.mvp.presenter.Fragment_action_plan_pre_1_P;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoAdapter;
import com.coahr.thoughtrui.mvp.view.startProject.adapter.PagerFragmentPhotoListener;
import com.coahr.thoughtrui.widgets.AltDialog.PhotoAlbumDialogFragment;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.google.gson.Gson;
import com.landptf.view.ButtonM;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/4/12
 * 描述：行动计划提报1
 */
public class Fragment_Action_plan_presentation_1 extends BaseFragment<Fragment_action_plan_pre_1_c.Presenter> implements Fragment_action_plan_pre_1_c.View {
    @Inject
    Fragment_action_plan_pre_1_P p;
    @BindView(R.id.plan_1_tittle)
    MyTittleBar plan1Tittle;
    @BindView(R.id.plan_plan_time)
    TextView planPlanTime;
    @BindView(R.id.plan_planer)
    TextView planPlaner;
    @BindView(R.id.plan_province)
    TextView planProvince;
    @BindView(R.id.plan_dealer_name)
    TextView planDealerName;
    @BindView(R.id.plan_templet)
    TextView planTemplet;
    @BindView(R.id.plan_quota_1)
    TextView planQuota1;
    @BindView(R.id.plan_quota_2)
    TextView planQuota2;
    @BindView(R.id.plan_take_photo)
    ImageView planTakePhoto;
    @BindView(R.id.plan_recyclerView)
    RecyclerView planRecyclerView;
    @BindView(R.id.plan_1_next)
    ButtonM plan1Next;
    /**
     * 经销商名称
     */
    private ArrayList<String> project_name = new ArrayList<>();
    private String select_projectId;
    /**
     * 指标联动
     */
    private ArrayList<SubjectListBean.DataBean.QuestionListRoot> quotaList_1 = new ArrayList<>();
    private ArrayList<ArrayList<SubjectListBean.DataBean.QuestionListRoot.valueBean>> quotaList_2 = new ArrayList<>();
    private String quota_1_id,getQuota_2_id;
    /*
     省市区三级列表联动数据结构
      */
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private String select_city;

    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (!isLoaded) {
                        if (thread == null) {//如果已创建就不再重新创建子线程了
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getJson();
                                }
                            });
                            thread.start();
                        }
                    } else {
                        showCityPickerView();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    showCityPickerView();
                    break;
                case MSG_LOAD_FAILED:
                    isLoaded = false;
                    break;
            }
        }
    };
    /**
     * 图片
     */
    private GridLayoutManager gridLayoutManager;
    private PagerFragmentPhotoAdapter adapter;
    private ArrayList<String> imageList=new ArrayList<>();
    private PhotoAlbumDialogFragment photoAlbumDialogFragment;

    @Override
    public Fragment_action_plan_pre_1_c.Presenter getPresenter() {
        return p;
    }

    public static Fragment_Action_plan_presentation_1 newInstance() {
        return new Fragment_Action_plan_presentation_1();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_action_plan_presentation_1;
    }

    @Override
    public void initView() {
        plan1Next.setFillet(true);
        plan1Next.setRadius(5);
        plan1Next.setBackColor(getResources().getColor(R.color.material_blue_900));
        plan1Next.setTextColor(getResources().getColor(R.color.colorWhite));
        plan1Next.setShape(GradientDrawable.RECTANGLE);
        plan1Tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
        adapter = new PagerFragmentPhotoAdapter();
        gridLayoutManager = new GridLayoutManager(BaseApplication.mContext, 3);
        planRecyclerView.setLayoutManager(gridLayoutManager);
        planRecyclerView.setAdapter(adapter);
        adapter.setIsDel(true);
        adapter.setListener(new PagerFragmentPhotoListener() {
            @Override
            public void onClick(List<String> imagePathList, int position) {
                photoAlbumDialogFragment = PhotoAlbumDialogFragment.newInstance();
                photoAlbumDialogFragment.setImgList(imageList);
                photoAlbumDialogFragment.setFirstSeePosition(position);
                FragmentManager fragmentManager = getFragmentManager();
                photoAlbumDialogFragment.show(fragmentManager, TAG);
            }

            @Override
            public void onLongClick(List<String> imagePathList, int position) {

            }

            @Override
            public void onDel(List<String> imagePathList, int position) {
                imageList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.setImageList(imageList);
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.plan_province, R.id.plan_dealer_name, R.id.plan_templet, R.id.plan_quota_1, R.id.plan_quota_2, R.id.plan_take_photo, R.id.plan_1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plan_province:  //城市选择
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                break;
            case R.id.plan_dealer_name:
                p.getProjectName(Constants.sessionId);
                break;
            case R.id.plan_templet:
                break;
            case R.id.plan_quota_1:
                if (select_projectId != null) {
                    getQuota();
                } else {
                    ToastUtils.showLong(getResources().getString(R.string.toast_37));
                }
                break;
            case R.id.plan_quota_2:
                break;
            case R.id.plan_take_photo:
                openMulti(10);
                break;
            case R.id.plan_1_next:
                break;
        }
    }


    @Override
    public void getProjectNameSuccess(List<ProjectsDB> projectsDBList) {
        showProjectName(projectsDBList);
    }

    @Override
    public void getProjectNameFailure(String fail) {
        ToastUtils.showLong(fail);
    }

    @Override
    public void getSubjectListSuccess(SubjectListBean subjectListBean) {
        if (subjectListBean != null
                && subjectListBean.getData().getQuestionList() != null
                && subjectListBean.getData().getQuestionList().size() > 0) {
            showQuota(subjectListBean.getData().getQuestionList());
        }
    }

    @Override
    public void getSubjectListFailure(String failure, int code) {
        ToastUtils.showLong(failure);
    }

    //====================================================经销商名称==========================================
    private void showProjectName(List<ProjectsDB> projectsDBList) {
        project_name.clear();
        for (int i = 0; i < projectsDBList.size(); i++) {
            project_name.add(projectsDBList.get(i).getPname());
        }
        OptionsPickerView pvOption_project = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                select_projectId = projectsDBList.get(options1).getPid();
                planDealerName.setText(project_name.get(options1));
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_3))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        pvOption_project.setPicker(project_name);
        pvOption_project.show();
    }

    //==================================================指标选择器============================================

    /**
     * 获取指标
     */
    private void getQuota() {
        Map map = new HashMap();
        map.put("projectId", select_projectId);
        map.put("sessionid", Constants.sessionId);
        p.getSubjectList(map);
    }

    private void showQuota(List<SubjectListBean.DataBean.QuestionListRoot> questionListRootList) {
        quotaList_1.clear();
        quotaList_2.clear();
        quotaList_1.addAll(questionListRootList);
        for (int i = 0; i < questionListRootList.size(); i++) {
            if (questionListRootList.get(i).getValue() != null && questionListRootList.get(i).getValue().size() > 0) {
                ArrayList<SubjectListBean.DataBean.QuestionListRoot.valueBean> quesListBean_1s = new ArrayList<>();
                quesListBean_1s.addAll(questionListRootList.get(i).getValue());
                quotaList_2.add(quesListBean_1s);
            }

        }

        OptionsPickerView pvOption_quota = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                planQuota1.setText(quotaList_1.get(options1).getPickerViewText());
                quota_1_id=quotaList_1.get(options1).getId();
                if (quotaList_2 != null && quotaList_2.size() > 0) {
                    planQuota2.setText(quotaList_2.get(options1).get(options2).getPickerViewText());
                    quota_1_id=quotaList_2.get(options1).get(options2).getId();
                }
            }
        })
                .setTitleText(getResources().getString(R.string.phrases_28))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)
                .build();
        if (quotaList_2 != null && quotaList_2.size() > 0) {
            pvOption_quota.setPicker(quotaList_1, quotaList_2);

        } else {
            pvOption_quota.setPicker(quotaList_1);
       }
        pvOption_quota.show();
    }
//==================================================城市列表选择器=============================================

    /**
     * 城市列表选择器
     */
    private void showCityPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(_mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                select_city = options2Items.get(options1).get(options2);

                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2);
                planProvince.setText(tx);

            }
        })
                .setTitleText(getResources().getString(R.string.phrases_2))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    /**
     * 将城市json字符串解析为javabean
     *
     * @param result
     * @return
     */
    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 将各个转载指定数组
     */
    public void getJson() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = JsonFileReader.getJson(BaseApplication.mContext, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 拍照
     */
    private void openMulti(int count) {
        setPath();
        RxGalleryFinal rxGalleryFinal = RxGalleryFinal
                .with(_mActivity)
                .image()
                .multiple();
        rxGalleryFinal.maxSize(count)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        List<MediaBean> mediaBeanList = imageMultipleResultEvent.getResult();
                        if (mediaBeanList != null && mediaBeanList.size() > 0) {
                            imageList.clear();
                            for (int i = 0; i <mediaBeanList.size() ; i++) {
                                imageList.add(mediaBeanList.get(i).getOriginalPath());
                            }
                            adapter.setNewData(imageList);
                            adapter.setImageList(imageList);
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
    /**
     * 设置 照片路径 和 裁剪路径
     */
    private void setPath() {
        RxGalleryFinalApi.setImgSaveRxDir(new File(Constants.SAVE_DIR_TAKE_PHOTO));
        RxGalleryFinalApi.setImgSaveRxCropDir(new File(Constants.SAVE_DIR_ZIP_PHOTO));//裁剪会自动生成路径；也可以手动设置裁剪的路径；
    }

    /**
     * 设置视频保存路径
     */
  /*  private void setPath() {
        RxGalleryFinalApi.setImgSaveRxDir(new File(Constants.SAVE_DIR_TAKE_PHOTO));
        RxGalleryFinalApi.setImgSaveRxCropDir(new File(Constants.SAVE_DIR_ZIP_PHOTO));//裁剪会自动生成路径；也可以手动设置裁剪的路径；
    }*/
}
