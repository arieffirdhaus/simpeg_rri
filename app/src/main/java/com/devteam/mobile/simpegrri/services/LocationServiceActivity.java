package com.devteam.mobile.simpegrri.services;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.devteam.mobile.simpegrri.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public abstract class LocationServiceActivity extends AppCompatActivity implements LocationResultReceiver.Callback {

    protected LocationService mLocationService;
    protected LatLng mLatLng;
    protected LocationService.LocationBinder mBinder;
    protected Bundle mSavedInstanceState;
    private LocationResultReceiver mReceiver;
    private boolean mIsServiceReady = false;
    private boolean mIsInitial = true;

    private final String TAG = LocationServiceActivity.class.getSimpleName();

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (LocationService.LocationBinder) service;
            mLocationService = mBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReceiver = new LocationResultReceiver(new Handler());
        mReceiver.setCallback(this);

        mSavedInstanceState = savedInstanceState;
        Intent locationServiceIntent = new Intent(this, LocationService.class);
        locationServiceIntent.putExtra(Constants.INTENT_LOCATION_RESULT_RECEIVER, mReceiver);
        startService(locationServiceIntent);
        onChildActivityCreate(mSavedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on service resume");
        Intent locationServiceIntent = new Intent(this, LocationService.class);
        locationServiceIntent.putExtra(Constants.INTENT_LOCATION_RESULT_RECEIVER, mReceiver);
        startService(locationServiceIntent);
        onChildResume();
    }

    @Override
    protected void onDestroy() {
        if(mLocationService != null) mLocationService.stopLocationRequest();
        mReceiver.setCallback(null);
        Intent locationServiceIntent = new Intent(this, LocationService.class);
        stopService(locationServiceIntent);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(mLocationService != null) mLocationService.stopLocationRequest();
        mReceiver.setCallback(null);
        Intent locationServiceIntent = new Intent(this, LocationService.class);
        stopService(locationServiceIntent);

        super.onPause();
    }

    @Override
    protected void onStop() {
        if(mLocationService != null) mLocationService.stopLocationRequest();
        Intent locationServiceIntent = new Intent(this, LocationService.class);
        stopService(locationServiceIntent);
        mSavedInstanceState = null;
        try {
            unbindService(mServiceConnection);
        } catch (IllegalArgumentException ignored) {

        }
        onChildActivityStop();

        super.onStop();
    }

    /**
     * Activity onCreate(Bundle savedInstanceState) is already used in
     * parent class. Use this to inflate view or do anything that is supposedly on initial onStart
     * @param savedInstanceState
     */
    protected abstract void onChildActivityCreate(@Nullable Bundle savedInstanceState);

    /**
     * Activity onStop() is already used in
     * parent class. Use this to do anything that is supposedly on initial onStop
     */
    protected void onChildActivityStop(){
        mIsServiceReady = false;
    }

    protected abstract void doInitialRestApi();
    protected void onChildResume(){}

    protected String getLastLatLngString() {
        if(mLatLng == null) {
            Log.d(TAG, "lat lng null");
            return "";
        }

        Log.d(TAG, mLatLng.latitude + "," + mLatLng.longitude);
        return mLatLng.latitude + "," + mLatLng.longitude;
    }

    protected String getLocationNameFromLatLng(LatLng latLng) {
        if(latLng != null) {
            Geocoder coder = new Geocoder(this, Locale.getDefault());
            try{
                List<Address> addresses = coder.getFromLocation(latLng.latitude, latLng.longitude,1);
                String locationName = addresses.get(0).getLocality();

                return locationName;
            } catch (Exception e) {
                return "Unknown Location";
            }
        }

        return "Unknown Location";
    }

    @Override
    public void onLocationReady(LatLng latLng) {
        mLatLng = latLng;
    }

    @Override
    public void onServiceReady() {
        doInitialRestApi();
    }
}
