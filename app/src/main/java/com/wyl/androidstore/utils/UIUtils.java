package com.wyl.androidstore.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.wyl.androidstore.BaseActivity;
import com.wyl.androidstore.BaseApplication;

/**
 * Created by Leon Wu on 2016/5/317:23.
 * Email: yuanliang.wu@weimob.com
 */
public class UIUtils {

    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * px--dip
     */

    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取字符数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * 获取颜色id
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * 根据id获取尺寸
     */
    public static int getDimens(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /**
     * 在主线程中执行代码
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        if (isRunOnMainThread()) {
            //执行代码
            runnable.run();
        } else {
            post(runnable);
        }
    }


    public static void post(Runnable runnable) {
        Handler handler = getHandler();
        handler.post(runnable);
    }

    /**
     * 移除一个执行的对象
     */
    public static void removeCallBacks(Runnable r) {
        getHandler().removeCallbacks(r);
    }

    /**
     * 延迟执行
     */
    public static void postDelay(Runnable runnable, long delay) {
        Handler handler = getHandler();
        handler.postDelayed(runnable, delay);
    }

    private static Handler getHandler() {
        return BaseApplication.getHandler();
    }

    public static boolean isRunOnMainThread() {
        return android.os.Process.myTid() == getMainThreadTid();
    }

    private static int getMainThreadTid() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 开启Activity
     */
    public static void startActivity(Intent intent) {
        if (BaseActivity.getForegroundActivity() != null) {
            BaseActivity.getForegroundActivity().startActivity(intent);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

    /**
     * 退出程序
     */
    public static void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static String getString(int id) {
        return getResources().getString(id);
    }

    public static void showToast(String str) {
        Toast.makeText(UIUtils.getContext(), str, 0).show();
    }

    public static void showToast(int id) {
        Toast.makeText(UIUtils.getContext(), id, 0).show();
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }
}
