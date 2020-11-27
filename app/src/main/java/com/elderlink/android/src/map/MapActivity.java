package com.elderlink.android.src.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elderlink.android.R;
import com.elderlink.android.src.BaseActivity;
import com.elderlink.android.src.map.interfaces.MapActivityView;
import com.elderlink.android.src.map.models.ElderMarker;
import com.elderlink.android.src.map.models.GpsTracker;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, MapActivityView {

    private Context mContext;
    private MapView mMapView;
    private ArrayList<ElderMarker> mElderMarkerList;

    private TextView mTvDefault;
    private TextView mTvName;
    private TextView mTvAddress;

    private GpsTracker mGpsTracker;
    private FusedLocationSource mLocationSource;

    private boolean mDouble = false;
    private boolean mFirstSelect = false;
    private String mSelectedPhone = "";
    private String mSelectedLatitude;
    private String mSelectedLongitude;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initialize();
    }

    void initialize() {
        mElderMarkerList = new ArrayList<>();

        mTvDefault = findViewById(R.id.map_tv_default);
        mTvName = findViewById(R.id.map_tv_name);
        mTvAddress = findViewById(R.id.map_tv_address);

        mContext = this;
        mMapView = findViewById(R.id.map_mapview);
        mMapView.getMapAsync(this);
        mLocationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLogoClickEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setLocationButtonEnabled(true);

        mGpsTracker = new GpsTracker(this);
        double latitude = mGpsTracker.getLatitude();    // 위도
        double longitude = mGpsTracker.getLongitude();  // 경도
        LatLng location = new LatLng(latitude, longitude);  // 현재 위치 객체

        CameraPosition cameraPosition = new CameraPosition(location, 9.5);   // 카메라 위치, 줌 조절, 10km 조절, 11.1
        naverMap.setCameraPosition(cameraPosition);

        naverMap.setLocationSource(mLocationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        getElderList();

    }

    void getElderList() {
        final MapService mapService = new MapService(this, this);
        mapService.tryGetElderList();
        showProgressDialog();
    }

    public void mapOnClick(View view) {
        switch (view.getId()) {
            case R.id.map_iv_back:
                finish();
                break;
            case R.id.map_iv_go_map:
                if (isPackageInstalled("com.nhn.android.nmap")) {
                    String url = "nmap://place?lat=".concat(mSelectedLatitude).concat("&lng=").concat(mSelectedLongitude).concat("&appname=com.softsquared.omo.android");
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(appIntent);
                } else {
//                    String url = "https://map.naver.com/v5";
                    String url = "market://details?id=com.nhn.android.nmap";
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(webIntent);
                }
                break;
            case R.id.map_constraint_call:
                if (mDouble) return;
                mDouble = true;
                String p = mSelectedPhone.substring(0, 3).concat("-").concat(mSelectedPhone.substring(3, 7).concat("-").concat(mSelectedPhone.substring(7, 11)));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:".concat(p))));
                break;
        }

    }

    @Override
    public void tryGetElderListSuccess(ArrayList<ElderMarker> results) {
        hideProgressDialog();

        mElderMarkerList.addAll(results);

        for (final ElderMarker elder : results) {
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull NaverMap naverMap) {
                    Marker marker = new Marker(new LatLng(Double.parseDouble(elder.getLatitude()), Double.parseDouble(elder.getLongitude())));
                    marker.setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            if (!mFirstSelect) {
                                mFirstSelect = true;
                                mTvDefault.setVisibility(View.GONE);
                            }

                            mTvName.setText(elder.getName().concat(" 어르신"));
                            mTvAddress.setText(elder.getAddress());

                            mSelectedPhone = elder.getPhone();
                            mSelectedLatitude = elder.getLatitude();
                            mSelectedLongitude = elder.getLongitude();

                            return true;
                        }
                    });

                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker));


                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int getDeviceDpi = displayMetrics.densityDpi;

                    if (getDeviceDpi == 320) {
                        marker.setWidth(42);
                        marker.setHeight(75);
                    } else if (getDeviceDpi == 160) {
                        marker.setWidth(21);
                        marker.setHeight(38);
                    } else if (getDeviceDpi == 240) {
                        marker.setWidth(32);
                        marker.setHeight(57);
                    } else if (getDeviceDpi == 480) {
                        marker.setWidth(63);
                        marker.setHeight(113);
                    } else if (getDeviceDpi == 640) {
                        marker.setWidth(84);
                        marker.setHeight(150);
                    }

                    marker.setMap(naverMap);
                }
            });
        }
    }

    @Override
    public void tryGetElderListFailure(String message) {
        hideProgressDialog();
        showCustomToastShort(message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mLocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {

        }
    }

    boolean isPackageInstalled(String packageName) {
        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName.trim(), PackageManager.GET_META_DATA);
            ApplicationInfo appInfo = pi.applicationInfo;
            Log.d("revelyview", "Enabled: " + appInfo.enabled);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("revelyview", "패키지가 설치 되어 있지 않습니다.");
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mDouble = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
