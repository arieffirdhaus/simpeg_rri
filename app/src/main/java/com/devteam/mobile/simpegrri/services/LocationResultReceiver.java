package com.devteam.mobile.simpegrri.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.google.android.gms.maps.model.LatLng;

import com.devteam.mobile.simpegrri.utils.Constants;

public class LocationResultReceiver extends ResultReceiver {
    private Callback mCallback;

    public LocationResultReceiver(Handler handler) {
        super(handler);
    }

    public interface Callback {
        void onLocationReady(LatLng latLng);

        void onServiceReady();
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == LocationService.SERVICE_OP_INITIALIZE) {
            if (mCallback != null) mCallback.onServiceReady();
        }

        if (resultCode == LocationService.SERVICE_OP_LOCATION) {
            LatLng latLng = resultData.getParcelable(Constants.INTENT_LATLNG);
            if (mCallback != null) mCallback.onLocationReady(latLng);
        }


    }
}
