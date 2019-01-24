package com.coahr.thoughtrui.Utils.BaiDuLocation;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.socks.library.KLog;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 12:44
 */
public class BaiduLocationHelper {
    // 百度地图获取经纬度
    private LocationClient locationClient;
    private static final int UPDATE_TIME = 10*1000; //默认三秒定位一次
    private static int LOCATION_COUTNS = 0;

    private Set<OnLocationCallBack> locationCallBacks = new HashSet<>();

    public BaiduLocationHelper() {//由于我在dagger2中定义为单例依赖注入，所以这里不用做单例模式写法

        if (locationClient == null) {
            initlocation();
        }
    }

    private void initlocation() {
        locationCallBacks.clear();
        locationClient = new LocationClient(BaseApplication.mContext);
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIgnoreKillProcess(true);//stop后不杀死定位进程
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。国测局经纬度坐标系:gcj02
        // 百度墨卡托坐标系:bd09 百度经纬度坐标系:bd09ll
        option.setProdName("ThoughtRui"); // 设置产品线名称。
        option.setScanSpan(UPDATE_TIME); // 设置定时定位的时间间隔。单位毫秒
        option.setIsNeedAddress(true);
        option.disableCache(true);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        // 注册位置监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    locationClient.start();
                    return;
                }
                StringBuffer sb = new StringBuffer(256);
                sb.append("Time : ");
                sb.append(location.getTime());
                sb.append("\nError code : ");
                sb.append(location.getLocType());
                sb.append("\nLatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nLontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nRadius : ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    sb.append("\nSpeed : ");
                    sb.append(location.getSpeed());
                    sb.append("\nSatellite : ");
                    sb.append(location.getSatelliteNumber());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    sb.append("\nAddress : ");
                    sb.append(location.getAddrStr());
                }
                LOCATION_COUTNS++;
                sb.append("\n检查位置更新次数：");
                sb.append(String.valueOf(LOCATION_COUTNS));
                if (location.getAddrStr() != null
                        && !"".equals(location.getAddrStr())) {
                    for (OnLocationCallBack callBack : locationCallBacks) {
                        callBack.onLocationSuccess(location);
                    }
                } else {
                    for (OnLocationCallBack callBack : locationCallBacks) {
                        callBack.onLocationFailure(location.getLocType());
                    }
                }
                sb.append("\nlocationAddress : ");
                sb.append(location.getAddrStr());
                KLog.d("百度定位",sb.toString());
            }
        });


    }


    public void startLocation(){
        if (locationClient.isStarted()) {
            locationClient.stop();
            locationClient.start();
        }else {
            locationClient.start();
        }
    }


    public void stopLocation(){
        locationClient.stop();
    }

    public void registerLocationCallback(OnLocationCallBack onLocationCallBack) {

        locationCallBacks.add(onLocationCallBack);
    }

    public void unRegisterLocationCallback(OnLocationCallBack onLocationCallBack) {
        locationCallBacks.remove(onLocationCallBack);
    }

    public interface OnLocationCallBack {

        void onLocationSuccess(BDLocation location);

        void onLocationFailure(int locType);//返回定位错误码

    }
}
