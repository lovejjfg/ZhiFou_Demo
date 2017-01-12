package com.lovejjfg.zhifou.view;

import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lovejjfg.sview.SupportActivity;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.ui.recycleview.MapSearchAdapter;
import com.lovejjfg.zhifou.ui.recycleview.OnItemClickListener;
import com.lovejjfg.zhifou.util.BaiduMapUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends SupportActivity implements OnItemClickListener {
    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.view_location)
    View mLocation;
    @Bind(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private BaiduMap mBaiduMap;
    private static final String TAG = MapActivity.class.getSimpleName();
    private static final double LONGITUDE = 121.463484;//经度
    private static final double LATITUDE = 31.28484;//纬度
    private static final int DISTANCE = 500;//距离
    private boolean isFirst = true;
    private float targetDegree;
    private SensorManager mSensorManager;
    private Sensor mAcceleSensor;
    private Sensor mMagneticSensor;
    private boolean updateMap = true;
    private PoiSearch mPoiSearch;
    private ArrayList<Marker> markers = new ArrayList<>();
    private ArrayList<BitmapDescriptor> icons = new ArrayList<>();
    private LatLng currentLatLng;
    private LatLng locationLatlng;
    private TranslateAnimation finishAnimation;
    private TranslateAnimation startAnimation;
    private MapSearchAdapter mapSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initSenser();
        initStartAnim();
        initFinishAnim();
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);

        mBaiduMap = mapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
        //开启城市热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);
        mBaiduMap.showMapPoi(true);
        //显示指南针
        mBaiduMap.setCompassPosition(new Point(100, 100));
        mBaiduMap.setCompassIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.arrow));

        mapSearchAdapter = new MapSearchAdapter();
        mapSearchAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mapSearchAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initIcons();
        initSearch();


//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city("上海")
//                .keyword("美食")
//                .pageNum(10));

        BDLocationListener locationListener = location -> {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nlocation:").append(location.getCity()).append("-").append(location.getAddrStr()).append("-").append(location.getBuildingName());
            sb.append("\ncode : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlongitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            Log.e(TAG, "onCreate: " + sb.toString());
            locationLatlng = new LatLng(location.getLatitude(), location.getLongitude());
            double distance = DistanceUtil.getDistance(locationLatlng, new LatLng(LATITUDE, LONGITUDE));
            Log.e(TAG, "onCreate: 测量距离：" + distance);
            //判断点pt是否在，以pCenter为中心点，radius为半径的圆内。
//            boolean circleContainsPoint = SpatialRelationUtil.isCircleContainsPoint(new LatLng(LATITUDE, LONGITUDE), DISTANCE, currentLatLng);
//            showToast(circleContainsPoint ? "到达指定的范围:" + distance : "未到达指定的范围:" + distance);
//            if (circleContainsPoint) {
//                BaiduMapUtil.stopClient();
//            }
//            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location);
//            if (marker != null) {
//                marker.remove();
//            }
//            MarkerOptions options = new MarkerOptions()
//                    .zIndex(20)
//                    .draggable(true)
//                    .icon(icon);
//                    .position(currentLatLng);
//            marker = (Marker) (mBaiduMap.addOverlay(options));

            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);
            // 构造定位数据
            Log.e(TAG, "onCreate: 方向是：" + targetDegree);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(targetDegree).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            if (isFirst) {
                isFirst = false;
                // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                        .fromResource(R.mipmap.arrow);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
                mBaiduMap.setMyLocationConfigeration(config);


                //设定中心点坐标
                //LatLng cenpt = new LatLng(30.663791,104.07281);
//                mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//                    public void onMarkerDrag(Marker marker) {
//                        //拖拽中
//                        Log.e(TAG, "onMarkerDrag: 拖拽中..");
//                    }
//
//                    public void onMarkerDragEnd(Marker marker) {
//                        //拖拽结束
//                        Log.e(TAG, "onMarkerDrag: 拖拽结束..");
//
//                    }
//
//                    public void onMarkerDragStart(Marker marker) {
//                        //开始拖拽
//                        Log.e(TAG, "onMarkerDrag: 开始拖拽..");
//                    }
            }

            if (updateMap) {
                updateMap = false;
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(currentLatLng == null ? locationLatlng : currentLatLng)
                        .zoom(mBaiduMap.getMaxZoomLevel() - 4)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                //改变地图状态
                mBaiduMap.animateMapStatus(mMapStatusUpdate);
            }
            //定义地图状态

        };
        //地图状态改变相关接口
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                Log.e(TAG, "onMapStatusChangeStart: ..");
//                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, mLocation.getBottom() - 10);
//                animation.setDuration(20);
//                mLocation.setAnimation(animation);
//                animation.setFillAfter(true);
//                animation.start();
                mLocation.startAnimation(startAnimation);


            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                float zoom = mapStatus.zoom;
//                Log.e(TAG, "onMapStatusChange: .." + zoom);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
//                Log.e(TAG, "onMapStatusChangeFinish: .." + mapStatus.zoom);
                //// TODO: 2017/1/10 show info when selected.
                mLocation.startAnimation(finishAnimation);
//                mPoiSearch.searchNearby(new PoiNearbySearchOption().keyword("美食").location(mapStatus.target).pageNum(10).radius(2000));
            }
        });
        BaiduMapUtil.registerLocationListener(locationListener);
        BaiduMapUtil.start();
    }

    private void initSearch() {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                Log.e(TAG, "onGetPoiResult: ....." + poiResult.getAllPoi().size());
                mapSearchAdapter.setList(poiResult.getAllPoi());
                finishAnimation.cancel();
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Log.e(TAG, "onGetPoiDetailResult: " + poiDetailResult.getName());
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Log.e(TAG, "onGetPoiIndoorResult: " + poiIndoorResult.getmArrayPoiInfo().toString());
            }
        };

        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

    }

    @NonNull
    private TranslateAnimation initStartAnim() {
        startAnimation = new TranslateAnimation(0, 0, 0, -10);
        startAnimation.setDuration(100);
        return startAnimation;
    }

    @NonNull
    private TranslateAnimation initFinishAnim() {
        finishAnimation = new TranslateAnimation(0, 0, 0, -30);
        finishAnimation.setDuration(300);
        finishAnimation.setInterpolator(new DecelerateInterpolator());
        finishAnimation.setFillAfter(false);
//        finishAnimation.setRepeatCount(Integer.MAX_VALUE);
        return finishAnimation;
    }

    private void initIcons() {
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markb));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markc));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markd));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marke));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markf));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markg));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markh));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_marki));
        icons.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markj));
    }

    private float[] mMageneticValues = new float[3];
    private float[] mAcceleValues = new float[3];

    private void initSenser() {
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mAcceleSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }


    @OnClick(R.id.location)
    public void onClick() {
        updateMap = true;
        BaiduMapUtil.requestLocation();
//        startActivity(MapSearchActivity.createStartIntent(this, getWindow().getDecorView().getRight(), 0, locationLatlng));
    }

    @Override
    protected void onStart() {
        BaiduMapUtil.start();
        mSensorManager.registerListener(mOrientationSensorEventListener, mAcceleSensor, SensorManager.SENSOR_DELAY_NORMAL); //注册加速度传感器监听
        mSensorManager.registerListener(mOrientationSensorEventListener, mMagneticSensor, SensorManager.SENSOR_DELAY_NORMAL);//注册磁场传感器监听

        super.onStart();
    }

    @Override
    protected void onPause() {
        BaiduMapUtil.stopClient();
        mSensorManager.unregisterListener(mOrientationSensorEventListener);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mPoiSearch.destroy();
        mapView = null;
        super.onDestroy();
    }


    private SensorEventListener mOrientationSensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {
            Log.d(TAG, " onAccuracyChanged()");
        }


        @Override
        public void onSensorChanged(SensorEvent event) {

//            int sensorType = event.sensor.getType();
//            Log.d(TAG, " onSensorChanged()  sensorType = " + sensorType);
            //通过加速度传感器的mAcceleValues和磁场传感器的mMageneticValues，来计算方位传感器的value

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mAcceleValues = event.values;
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mMageneticValues = event.values;
            }
            calculateOrientation();
        }
    };

    public void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, mAcceleValues, mMageneticValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        float newDegree = (values[0] + 360.0f) % 360;
        targetDegree = Math.abs(targetDegree - newDegree) > 2 ? newDegree : targetDegree;

    }


    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult.getAllPoi() != null) {
            resetMarks();
            List<PoiInfo> allPoi = poiResult.getAllPoi();
            if (allPoi != null) {
                currentLatLng = allPoi.get(0).location;
                for (int i = 0; i < allPoi.size(); i++) {
                    MarkerOptions options = new MarkerOptions().position(allPoi.get(i).location)
                            .animateType(MarkerOptions.MarkerAnimateType.grow)
                            .icon(icons.get(i));
                    markers.add((Marker) mBaiduMap.addOverlay(options));
                }
            }
        }

    }

    public void onGetPoiResult(PoiInfo poiResult) {
        resetMarks();

        MarkerOptions options = new MarkerOptions().position(poiResult.location)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .icon(icons.get(0));
        markers.add((Marker) mBaiduMap.addOverlay(options));

    }

    private void resetMarks() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    @Override
    public void onItemClick(View itemView, ImageView image, int id) {
        onGetPoiResult(mapSearchAdapter.mItems.get(id));
    }
}
