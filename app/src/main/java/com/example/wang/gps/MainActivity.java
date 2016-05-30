package com.example.wang.gps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
private TextView x;
    private TextView y;
    private Button ok;
    private LocationManager locationManager;
    public Location ll;
    private Calendar cc;
    MapView mMapView;
    BaiduMap map;
    LatLng lll;
    MapStatusUpdate u;
    LocationListener locationListener;
    BaiduMapOptions baiduMapOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.che);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("123");
        mMapView = (MapView) findViewById(R.id.bmapView);
         map=mMapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .zoomBy(3);
        map.animateMapStatus(mapStatusUpdate);

       // map.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        x=(TextView)findViewById(R.id.x);
        y=(TextView)findViewById(R.id.textView);
        ok=(Button)findViewById(R.id.button);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        testGps();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义Maker坐标点
                LatLng point = new LatLng(ll.getLatitude(), ll.getLongitude());
//构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.mal);
//构建MarkerOption，
// 用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
//在地图上添加Marker，并显示
                map.addOverlay(option);
                lll=new LatLng(ll.getLatitude(),ll.getLongitude());
                u = MapStatusUpdateFactory.newLatLng(lll);
                map.animateMapStatus(u);
                x.setText("纬度为: " + Double.valueOf(ll.getLatitude()).toString());
                y.setText("经度为: " + Double.valueOf(ll.getLongitude()).toString());
            }
        });

    }
    private void testGps() {

        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            getLocation();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            return;
        }
        else{
            Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
            getLocation();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            return;
        }
    }
    private Location getLocation(){

          locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        Log.d("hh", "newlocation");
        Log.i("kk", "时间：" + location.getTime());
        Log.i("kkl", "经度："+location.getLongitude());
        Log.i("hh", "纬度："+location.getLatitude());
        Log.i("ss", "海拔：" + location.getAltitude());
        lll=new LatLng(location.getLatitude(),location.getLongitude());
        u = MapStatusUpdateFactory.newLatLng(lll);
        map.animateMapStatus(u);
        ll=location;
        x.setText("纬度为：" + Double.valueOf(location.getLatitude()).toString());
        y.setText("经度为："+Double.valueOf(location.getLongitude()).toString());
        }
             public void onStatusChanged(String provider, int status, Bundle extras) {}

             public void onProviderEnabled(String provider) {
                 Log.d("pp","proe");
             }

             public void onProviderDisabled(String provider) {
                 Log.d("prod","prodigy");
             }
         };
        return ll;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
