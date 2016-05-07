package com.wyl.androidstore.fragment;

import android.view.View;

import com.wyl.androidstore.ui.LoadingPage;

/**
 * Created by Leon Wu on 2016/5/511:16.
 * Email: yuanliang.wu@weimob.com
 */
public class RecommendFragment extends BaseFragment {
    @Override
    public View createLoadedView() {
        return null;
    }

    @Override
    public LoadingPage.LoadResult load() {
        return  LoadingPage.LoadResult.ERROR;
    }
}
