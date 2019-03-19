package com.coahr.thoughtrui.mvp.view.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coahr.thoughtrui.R;
import com.coahr.thoughtrui.Utils.LoadCity.JsonBean;
import com.coahr.thoughtrui.Utils.LoadCity.JsonFileReader;
import com.coahr.thoughtrui.Utils.ToastUtils;
import com.coahr.thoughtrui.commom.Constants;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.coahr.thoughtrui.mvp.Base.BaseFragment;
import com.coahr.thoughtrui.mvp.constract.ProjectDealer_c;
import com.coahr.thoughtrui.mvp.model.Bean.Dealer_List;
import com.coahr.thoughtrui.mvp.presenter.ProjectDealer_P;
import com.coahr.thoughtrui.widgets.TittleBar.MyTittleBar;
import com.google.gson.Gson;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import butterknife.BindView;

/**
 * @author Leehor
 * 版本：
 * 创建日期：2019/2/27
 * 描述：经销商选择页面
 */
public class DealerFragment extends BaseFragment<ProjectDealer_c.Presenter> implements ProjectDealer_c.View, View.OnClickListener {
    @Inject
    ProjectDealer_P p;
    private String template_id;

    @BindView(R.id.tv_select_address)
    TextView tv_select_address;
    @BindView(R.id.tv_select_project)
    TextView tv_select_project;
    @BindView(R.id.tv_project_id)
    TextView tv_project_id;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.dealer_Tittle)
    MyTittleBar dealer_Tittle;
    //经销商id
    private String Dealer_id;
    /*
    省市区三级列表联动数据结构
     */
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    //选择的城市
    private String city;
    /**
     * 开启handler线程
     */
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

    public static DealerFragment Instance(String TemplateId) {
        DealerFragment dealerFragment = new DealerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Template_id", TemplateId);
        dealerFragment.setArguments(bundle);
        return dealerFragment;
    }

    @Override
    public ProjectDealer_c.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_dealer_select;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            template_id = getArguments().getString("Template_id");
        }
        tv_select_address.setOnClickListener(this);
        tv_select_project.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        dealer_Tittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _mActivity.onBackPressed();
            }
        });
    }

    @Override
    public void initData() {


    }

    @Override
    public void getProjectDealerSuccess(Dealer_List dealer_list) {
        if (dealer_list != null) {
            if (dealer_list.getDealerList() != null && dealer_list.getDealerList().size() > 0) {
                showDealerPickerView(dealer_list.getDealerList());
            } else {
                ToastUtils.showLong("暂无经销商");
            }

        }
    }

    @Override
    public void getProjectDealerFailure(String fail) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address:
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                break;
            case R.id.tv_select_project:
                if (TextUtils.isEmpty(tv_select_address.getText()) || tv_select_address.getText().toString().trim().equals("")) {
                    ToastUtils.showLong("请选择省份");
                    return;
                }
                getDealerList(city);
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(tv_select_address.getText()) || tv_select_address.getText().equals("")) {
                    ToastUtils.showLong("请选择省份");
                    return;
                }
                if (TextUtils.isEmpty(tv_select_project.getText()) || tv_select_project.getText().toString().trim().equals("")) {
                    ToastUtils.showLong("请选择经销商");
                    return;
                }
                start(ProjectDetailFragment.newInstance(null, template_id, Dealer_id, 1));
                break;
        }
    }

    /**
     * 获取经销商列表
     */
    private void getDealerList(String city) {
        Map map = new HashMap();
        map.put("city", city);
        p.getProjectDealer(map);
    }

    /**
     * 城市列表选择器
     */
    private void showCityPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                city = options2Items.get(options1).get(options2);
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2);
                //options3Items.get(options1).get(options2).get(options3);
                tv_select_address.setText(tx);
                tv_select_project.setText("");
                tv_project_id.setText("");
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    /**
     * 经销商列表选择器
     *
     * @param list
     */
    private void showDealerPickerView(List<Dealer_List.DealerListBean> list) {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tv_select_project.setText(list.get(options1).getName());
                tv_project_id.setText(Constants.user_type==1?list.get(options1).getSale_code():Constants.user_type==2?list.get(options1).getService_code():list.get(options1).getSale_code());
                Dealer_id=list.get(options1).getId();
            }
        })

                .setTitleText("经销商列表")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

    /**
     * 将json解析
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
     * 加载json
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
}
