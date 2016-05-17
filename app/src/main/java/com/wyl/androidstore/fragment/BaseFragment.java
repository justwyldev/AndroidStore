package com.wyl.androidstore.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.LoadingPage.LoadResult;
import com.wyl.androidstore.utils.UIUtils;
import com.wyl.androidstore.utils.ViewUtils;

import java.util.List;

/**
 * 抽取基类Fragment
 * Created by Leon Wu on 2016/5/417:50.
 * Email: yuanliang.wu@weimob.com
 */
public abstract class BaseFragment extends Fragment {

    /***
     * 加载状态View
     */
    protected LoadingPage mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = new LoadingPage(UIUtils.getContext()) {
                @Override
                public LoadResult load() {
                    return BaseFragment.this.load();
                }

                @Override
                public View createLoadedView() {
                    return BaseFragment.this.createLoadedView();
                }
            };
            // 把所有界面添加到 帧布局上
            // show(); // 一遍加载一遍显示界面
        } else {
            // 由于之前的framelayout并没有销毁 而且还缓存到了 ViewPager 中 需要先把之前的在ViewPager中移除
            ViewUtils.removeSelfFromParent(mContentView);
        }

        return mContentView;
    }

    /**
     * 检查数据完整性
     */
    protected LoadResult check(Object data) {
        if (data == null) {
            return LoadResult.ERROR;
        } else if (data instanceof List) {
            List list = (List) data;
            if (list.size() == 0) {
                return LoadResult.EMPTY;
            }
        }
        return LoadResult.SUCCEDD;
    }

    /**
     * 显示具体界面
     */
    public abstract View createLoadedView();

    /**
     * 请求网络加载数据
     */
    public abstract LoadResult load();

    public void show() {
        if (mContentView != null)
            mContentView.show();
    }

}
