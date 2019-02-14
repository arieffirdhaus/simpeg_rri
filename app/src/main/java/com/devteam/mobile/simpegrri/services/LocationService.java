package com.devteam.mobile.simpegrri.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.devteam.mobile.simpegrri.utils.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class LocationService extends Service
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final String TAG = LocationService.class.getSimpleName();
    public static final int SERVICE_OP_LOCATION = 0;
    public static final int SERVICE_OP_INITIALIZE = 1;

    private GoogleApiClient mClient;
    private LocationRequest mLocationRequest;
    private LatLng mLatLng;
    private Location mLastLocation;
    private ResultReceiver mReceiver;

    private boolean mIsAlreadyInitialized = false;
    private IBinder mBinder = new LocationBinder();
    private LocationServiceCallback mCallback;

    private final long FASTEST_INTERVAL = 1000 * 1;
    private final long MEDIUM_INTERVAL = 1000 * 2;

    public void setCallback(LocationServiceCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createLocationRequest();
        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mClient.connect();
    }

    @Override
    public void onDestroy() {
        mIsAlreadyInitialized = false;
        stopLocationRequest();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mReceiver = intent.getParcelableExtra(Constants.INTENT_LOCATION_RESULT_RECEIVER);
        if(!mClient.isConnected()) {
            mClient.connect();
        } else {
            startLocationUpdates();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(!mClient.isConnected()) {
            mClient.connect();
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mLatLng = new LatLng( location.getLatitude(),  location.getLongitude());
        if(mReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.INTENT_LATLNG, mLatLng);

            if(!mIsAlreadyInitialized) {
                Log.d(TAG, "f");
                mReceiver.send(SERVICE_OP_INITIALIZE, null);
                mIsAlreadyInitialized = true;
            }

            mReceiver.send(SERVICE_OP_LOCATION, bundle);
        }
    }

    protected void startLocationUpdates() {
        if (mClient != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            LocationServices.FusedLocationApi.requestLocationUpdates(mClient,
                    mLocationRequest, this);
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(MEDIUM_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void stopLocationRequest() {
        if(mClient != null &&
                LocationServices.FusedLocationApi != null
                && mClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this);
        }

        mClient.disconnect();
    }

    protected String getLocationName(LatLng latLng) {
        Geocoder coder = new Geocoder(this, Locale.getDefault());
        try{
            List<Address> addresses = coder.getFromLocation(latLng.latitude, latLng.longitude,1);
            String locationName = addresses.get(0).getLocality();

            return locationName;
        } catch (Exception e) {
            return "Unknown Location";
        }
    }

    public class LocationBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }
}
