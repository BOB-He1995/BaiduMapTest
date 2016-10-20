package com.example.baidumaptest;

import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private MapView mapView;
	
	private BaiduMap baiduMap;
	
	private LocationManager locationManager;
	
	private String provider;
	
	private boolean isFirstLocate=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		Button buttonfl=(Button)findViewById(R.id.btn_friends);
		buttonfl.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent=new Intent(MainActivity.this, friendlist.class);
				startActivity(intent);
			}
		});
		
		mapView=(MapView) findViewById(R.id.map_view);
		baiduMap=mapView.getMap();
		baiduMap.setMyLocationEnabled(true);
		
		locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList=locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider=LocationManager.GPS_PROVIDER;
		}else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
			provider=LocationManager.NETWORK_PROVIDER;

		} else {
			return;
		}
		Location location=locationManager.getLastKnownLocation(provider);
		if(location!=null){
			navigateTo(location);
		}
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}
	
	private void navigateTo(Location location){
		if(isFirstLocate) {
			LatLng ll=new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(ll);
			baiduMap.animateMapStatus(update);
			update=MapStatusUpdateFactory.zoomTo(16f);
			baiduMap.animateMapStatus(update);
			isFirstLocate=false;
		}
		MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
		locationBuilder.latitude(location.getLatitude());
		locationBuilder.longitude(location.getLongitude());
		MyLocationData locationData=locationBuilder.build();
		baiduMap.setMyLocationData(locationData);
	}
	
	LocationListener locationListener=new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){
		}
		
		@Override
		public void onProviderEnabled(String provider){
		}
		
		@Override
		public void onLocationChanged(Location location){
			if(location!=null){
				navigateTo(location);
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		baiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		if(locationManager!=null){
			locationManager.removeUpdates(locationListener);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

}
