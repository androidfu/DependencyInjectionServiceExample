package com.androidfu.examples.android.dependencyinjectionserviceexample;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.text.TextUtils;
import android.util.Log;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class WeatherRestRequest extends Behavior {
    private static final String TAG = WeatherRestRequest.class.getSimpleName();

    WeatherRestRequest(@NonNull URL url, @NonNull @Size(min = 3) String verb) {
        super(url, verb);
    }

    @Override
    public void useResult(String result, int statusCode) {
        if (statusCode == HttpsURLConnection.HTTP_OK && !TextUtils.isEmpty(result)) {
            Log.i(TAG, result);
        }
    }
}
