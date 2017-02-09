package com.lovejjfg.zhifou.base;

import android.app.Application;
import android.util.Log;

import com.antfortune.freeline.FreelineCore;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lovejjfg.sview.utils.ToastUtil;
import com.lovejjfg.zhifou.util.NetWorkUtils;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

/**
 * Created by Joe on 2016-04-05
 * Email: lovejjfg@gmail.com
 */
public class App extends Application {

    public static File CacheDirectory;
    public static NetWorkUtils netWorkUtils;
    public LocationClient mLocationClient = null;
//    public BDLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);
        LeakCanary.install(this);
        CacheDirectory = new File(getApplicationContext().getCacheDir(), "responses");
        netWorkUtils = NetWorkUtils.getsInstance(this);
        ToastUtil.initToast(getApplicationContext());
        Log.e("TAG", "APP:onCreate初始化。。。 ");
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

//    public static boolean getWifiStatus(Context context) {
//        ConnectivityManager mConnectivity = (ConnectivityManager)
//                context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
//        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
//            return false;
//        }
//        int netType = info.getType();
//        if (netType == ConnectivityManager.TYPE_WIFI) {
//            return info.isConnected();
//        } else {
//            return false;
//        }
//    }


}
