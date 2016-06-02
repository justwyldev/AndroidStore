package com.wyl.androidstore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * BaseActivity基类
 * Created by Leon Wu on 2016/5/317:30.
 * Email: yuanliang.wu@weimob.com
 */
public class BaseActivity extends ActionBarActivity {

    /**
     * 记录所有活动的Activity，由于经常增删使用LinkedList效率较高
     */
    private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    private static BaseActivity mForegroundActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        init();
        initView();
        initActionBar();
        initListener();
    }

    /**
     * 初始化ActivitonBar
     */
    protected void initActionBar() {
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化操作
     */
    protected void init() {

    }

    /**
     * 初始化箭头监听事件
     */
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mForegroundActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mForegroundActivity = null;
    }


    /**
     * 获取前台Activity
     *
     * @return
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    @Override
    public void finish() {
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        super.finish();

    }

    /**
     * 退出程序
     */
    public void exitApp() {
        killAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 清空所有activity
     */
    private void killAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }


    public static void startActivity(Context context, Class<?> clz) {
        Intent it = new Intent(context, clz);
        context.startActivity(it);
    }
}
