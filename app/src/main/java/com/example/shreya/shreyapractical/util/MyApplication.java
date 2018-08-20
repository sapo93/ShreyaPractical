package com.example.shreya.shreyapractical.util;

import android.app.Application;

import com.example.shreya.shreyapractical.data.modal.MyObjectBox;

import io.objectbox.BoxStore;

public class MyApplication extends Application {

    public static final String TAG = "ObjectBoxExample";
    public static final boolean EXTERNAL_DIR = false;

    public static BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(MyApplication.this).build();
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
