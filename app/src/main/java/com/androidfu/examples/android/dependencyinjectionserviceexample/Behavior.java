package com.androidfu.examples.android.dependencyinjectionserviceexample;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public abstract class Behavior {
    private static final String TAG = Behavior.class.getSimpleName();
    private final URL url;
    private final String verb;

    public Behavior(@NonNull final URL url, @NonNull @Size(min = 3) final String verb) {
        this.url = url;
        this.verb = verb;
    }

    void execute() {
        try {
            HttpsURLConnection httpsURLConnection = this.buildHttpsURLConnection(url, verb);
            if ("POST".equals(verb)) {
                // Data to POST goes here.
            }
            httpsURLConnection.connect();
            this.useResult(this.composeResult(httpsURLConnection), httpsURLConnection.getResponseCode());
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private HttpsURLConnection buildHttpsURLConnection(URL url, String verb) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        // Create the SSL connection
        SSLContext sc;
        sc = SSLContext.getInstance("TLS");
        sc.init(null, null, new java.security.SecureRandom());
        httpsURLConnection.setSSLSocketFactory(sc.getSocketFactory());

        httpsURLConnection.setRequestMethod(verb);
        httpsURLConnection.setDoInput(true);
        return httpsURLConnection;
    }


    private String composeResult(HttpsURLConnection httpsURLConnection) throws IOException {
        String response = "";
        if (httpsURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            return response;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            response += inputLine;
        }
        return response;
    }

    abstract public void useResult(String result, int statusCode);
}
