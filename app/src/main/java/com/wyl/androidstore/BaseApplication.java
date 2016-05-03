package com.wyl.androidstore;

import android.app.Application;
import android.os.Handler;

/**
 * Created by Leon Wu on 2016/5/317:24.
 * Email: yuanliang.wu@weimob.com
 */
public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    private static int mMainThreadId;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mHandler=new Handler();
    }

    public static BaseApplication getApplication(){
        return mInstance;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getHandler() {
        return mHandler;
    }


}
