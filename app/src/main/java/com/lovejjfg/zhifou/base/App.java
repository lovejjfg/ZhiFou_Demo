package com.lovejjfg.zhifou.base;

import android.app.Application;
import android.util.Log;

import com.antfortune.freeline.FreelineCore;
import com.baidu.location.LocationClient;
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
