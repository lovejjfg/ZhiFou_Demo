package com.lovejjfg.zhifou.view;

import android.os.Bundle;
import android.util.Log;

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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.lovejjfg.sview.SupportActivity;
import com.lovejjfg.zhifou.R;
import com.lovejjfg.zhifou.util.BaiduMapUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends SupportActivity {
    @Bind(R.id.map_view)
    MapView mapView;

    private BaiduMap mBaiduMap;
    private static final String TAG = MapActivity.class.getSimpleName();
    private static final double LONGITUDE = 121.463484;//经度
    private static final double LATITUDE = 31.28484;//纬度
    private static final int DISTANCE = 500;//距离
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mBaiduMap = mapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
        //开启城市热力图
//        mBaiduMap.setBaiduHeatMapEnabled(true);

        mBaiduMap.setMyLocationEnabled(true);
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
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            double distance = DistanceUtil.getDistance(latLng, new LatLng(LATITUDE, LONGITUDE));
            Log.e(TAG, "onCreate: 测量距离：" + distance);
            //判断点pt是否在，以pCenter为中心点，radius为半径的圆内。
            boolean circleContainsPoint = SpatialRelationUtil.isCircleContainsPoint(new LatLng(LATITUDE, LONGITUDE), DISTANCE, latLng);
            showToast(circleContainsPoint ? "到达指定的范围:" + distance : "未到达指定的范围:" + distance);
//            if (circleContainsPoint) {
//                BaiduMapUtil.stopClient();
//            }
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location);
            if (marker != null) {
                marker.remove();
            }
            MarkerOptions options =  new MarkerOptions()
                    .zIndex(20)
                    .draggable(true)
                    .icon(icon)
                    .position(latLng);
            marker = (Marker) (mBaiduMap.addOverlay(options));

            mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
                public void onMarkerDrag(Marker marker) {
                    //拖拽中
                    Log.e(TAG, "onMarkerDrag: 拖拽中..");
                }

                public void onMarkerDragEnd(Marker marker) {
                    //拖拽结束
                    Log.e(TAG, "onMarkerDrag: 拖拽结束..");

                }

                public void onMarkerDragStart(Marker marker) {
                    //开始拖拽
                    Log.e(TAG, "onMarkerDrag: 开始拖拽..");
                }
            });

            //设定中心点坐标
            //LatLng cenpt = new LatLng(30.663791,104.07281);
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(mBaiduMap.getMaxZoomLevel() - 2)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        };
        //地图状态改变相关接口
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                Log.e(TAG, "onMapStatusChangeStart: ..");

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                Log.e(TAG, "onMapStatusChange: ..");
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                Log.e(TAG, "onMapStatusChangeFinish: ..");
            }
        });
        BaiduMapUtil.registerLocationListener(locationListener);
        BaiduMapUtil.requestLocation();
    }

    @OnClick(R.id.location)
    public void onClick() {
        BaiduMapUtil.requestLocation();
    }
}
