package com.lovejjfg.zhifou.util;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Joe on 2017/1/9.
 * Email lovejjfg@gmail.com
 */

public class BaiduMapUtil {
    private static LocationClient mLocationClient;

    public static void initLocationClient(Context context) {
        if (mLocationClient == null) {
            SDKInitializer.initialize(context.getApplicationContext());
            mLocationClient = new LocationClient(context.getApplicationContext());
        }
    }

    public static BDLocation getLastLocation() {
        return mLocationClient.getLastKnownLocation();
    }

    public static void initSDK(Context context) {
        SDKInitializer.initialize(context.getApplicationContext());
    }


    public static void requestLocation(BDLocationListener listener) {
        if (mLocationClient == null) {
            throw new RuntimeException("please call initLocationClient() method at first!");
        }
        mLocationClient.requestNotifyLocation();
        mLocationClient.registerLocationListener(listener);
        if (mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {
            mLocationClient.start();
        }
    }

    public static void requestLocation() {
        if (mLocationClient == null) {
            throw new RuntimeException("please call initLocationClient() method at first!");
        }
//        mLocationClient.requestNotifyLocation();
        if (mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        } else {
            mLocationClient.start();
        }
    }

    public static void stopClient() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    public static void setLocOption(LocationClientOption option) {
        mLocationClient.setLocOption(option);
    }

    public static void registerNotify(BDNotifyListener bdNotifyListener) {
        mLocationClient.registerNotify(bdNotifyListener);
    }

    public static void registerLocationListener(BDLocationListener listener) {
        mLocationClient.registerLocationListener(listener);
    }

    public static boolean isStarted() {
        return mLocationClient != null && mLocationClient.isStarted();
    }

    public static void start() {
        if (isStarted()) {
            return;
        }
        mLocationClient.start();
    }
}
