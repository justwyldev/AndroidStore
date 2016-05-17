package com.wyl.androidstore;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.protocal.DetailProtocol;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.holder.AppDetailBottomHolder;
import com.wyl.androidstore.ui.holder.AppDetailDesHolder;
import com.wyl.androidstore.ui.holder.AppDetailInfoHolder;
import com.wyl.androidstore.ui.holder.AppDetailSafeHolder;
import com.wyl.androidstore.ui.holder.AppDetailScreenHolder;
import com.wyl.androidstore.utils.UIUtils;

/**
 * Created by Leon Wu on 2016/5/515:32.
 * Email: yuanliang.wu@weimob.com
 */
public class DetailActivity extends BaseActivity {
    public static final String PACKAGENAME = "packageName";
    private String packageName;
    private AppInfo appInfo;
    private FrameLayout mInfoLayout, mSafeLayout, mDesLayout, mBottomLayout;
    private HorizontalScrollView mScreenLayout;
    private AppDetailInfoHolder mInfoHolder;
    private AppDetailSafeHolder mSafeHolder;
    private AppDetailScreenHolder mScreenHolder;
    private AppDetailDesHolder mDesHolder;
    private AppDetailBottomHolder mBottomHolder;

    @Override
    protected void initView() {
        super.initView();
        LoadingPage loadingPage = new LoadingPage(this) {
            @Override
            public LoadResult load() {
                return DetailActivity.this.load();
            }

            @Override
            public View createLoadedView() {
                return DetailActivity.this.createLoadedView();
            }
        };
        setContentView(loadingPage);
        loadingPage.show();
    }
/*	*/

    /**
     * actionBar的点击事件
     *//*
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
    protected View createLoadedView() {
        View view = UIUtils.inflate(R.layout.activity_detail);
        // 添加信息区域
        mInfoLayout = (FrameLayout) view.findViewById(R.id.detail_info);
        mInfoHolder = new AppDetailInfoHolder();
        mInfoHolder.setData(appInfo);
        mInfoLayout.addView(mInfoHolder.getContentView());
        // 添加安全区域
        mSafeLayout = (FrameLayout) view.findViewById(R.id.detail_safe);
        mSafeHolder = new AppDetailSafeHolder();
        mSafeHolder.setData(appInfo);
        mSafeLayout.addView(mSafeHolder.getContentView());
        // 截图区域
        mScreenLayout = (HorizontalScrollView) view
                .findViewById(R.id.detail_screen);
        mScreenHolder = new AppDetailScreenHolder();
        mScreenHolder.setData(appInfo);
        mScreenLayout.addView(mScreenHolder.getContentView());
        // 介绍区域
        mDesLayout = (FrameLayout) view.findViewById(R.id.detail_des);
        mDesHolder = new AppDetailDesHolder();
        mDesHolder.setData(appInfo);
        mDesLayout.addView(mDesHolder.getContentView());
        // 底部区域
        mBottomLayout = (FrameLayout) view.findViewById(R.id.bottom_layout);
        mBottomHolder = new AppDetailBottomHolder();
        mBottomHolder.setData(appInfo);
        mBottomLayout.addView(mBottomHolder.getContentView());
        mBottomHolder.startObserver();
        return view;
    }

    @Override
    protected void onDestroy() {
        if (mBottomHolder != null) {
            mBottomHolder.stopObserver();
        }
        super.onDestroy();
    }

    /**
     * 加载数据
     */
    protected LoadingPage.LoadResult load() {
        DetailProtocol protocol = new DetailProtocol();
        protocol.setPackageName(packageName);
        appInfo = protocol.load(0);
        if (appInfo == null || TextUtils.isEmpty(appInfo.getPackageName())) {
            return LoadingPage.LoadResult.ERROR;
        }
        return LoadingPage.LoadResult.SUCCEDD;
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        if (intent != null) { // 在意图中获取packageName,在服务器中获取数据
            packageName = intent.getStringExtra(PACKAGENAME);
        }
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
