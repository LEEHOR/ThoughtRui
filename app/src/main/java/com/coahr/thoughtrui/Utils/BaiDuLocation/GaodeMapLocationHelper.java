package com.coahr.thoughtrui.Utils.BaiDuLocation;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.coahr.thoughtrui.mvp.Base.BaseApplication;
import com.socks.library.KLog;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leehor
 * on 2018/12/1
 * on 0:04
 * 高德地图
 */
public class GaodeMapLocationHelper {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient;
    private Set<OnLocationCallBack> locationCallBacks = new HashSet<>();
    private static int LOCATION_COUTNS = 0;
    public GaodeMapLocationHelper() {
        if (mLocationClient == null) {
            initGaodeMap();
        }
    }

    public void initGaodeMap() {
        mLocationClient = new AMapLocationClient(BaseApplication.mContext);
        AMapLocationClientOption option = new AMapLocationClientOption();
        //option.setGpsFirst(true);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);
        mLocationClient.setLocationOption(option);

        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                StringBuffer sb = new StringBuffer(256);
                if (aMapLocation.getErrorCode() == 0) { //成功
                    sb.append("LocationType");
                    sb.append(aMapLocation.getLocationType());
                    sb.append("Time");
                    sb.append(aMapLocation.getTime());
                    sb.append("\nLatitude : ");
                    sb.append(aMapLocation.getLatitude());
                    sb.append("\nLontitude : ");
                    sb.append(aMapLocation.getLongitude());
                    sb.append("\nAddress : ");
                    sb.append(aMapLocation.getAddress());
                    LOCATION_COUTNS++;
                    sb.append("\n检查位置更新次数：");
                    sb.append(String.valueOf(LOCATION_COUTNS));
                    if (aMapLocation.getStreet() != null
                            && !"".equals(aMapLocation.getStreet())) {
                        for (OnLocationCallBack callBack : locationCallBacks) {
                            callBack.onLocationSuccess(aMapLocation);
                        }
                    } else {
                        for (OnLocationCallBack callBack : locationCallBacks) {
                            callBack.onLocationFailure(aMapLocation.getLocationType());
                        }
                    }
                } else {  //失败
                    for (OnLocationCallBack callBack : locationCallBacks) {
                        callBack.onLocationFailure(aMapLocation.getLocationType());
                    }

                }
                KLog.d("高德定位",sb.toString()+"\n"+aMapLocation.getErrorCode()+"\n"+aMapLocation.getErrorInfo());
            }
        });
    }

    public void startLocation(){
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }else {
            mLocationClient.startLocation();
        }
    }


    public void stopLocation(){
        mLocationClient.stopLocation();
    }
    public void registerLocationCallback(OnLocationCallBack onLocationCallBack) {

        locationCallBacks.add(onLocationCallBack);
    }

    public void unRegisterLocationCallback(OnLocationCallBack onLocationCallBack) {
        locationCallBacks.remove(onLocationCallBack);
    }

    public interface OnLocationCallBack {

        void onLocationSuccess(AMapLocation location);

        void onLocationFailure(int locType);//返回定位错误码

    }
}
