package com.yuan.storage;

import android.app.Application;

import com.yuan.storage.Store;

/**
 * Created by shucheng.qu on 2018/3/15
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Store.init(this,"testFile");
    }
}
