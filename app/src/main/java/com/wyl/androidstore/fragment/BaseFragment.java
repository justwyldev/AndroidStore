package com.wyl.androidstore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.utils.UIUtils;
import com.wyl.androidstore.utils.ViewUtils;

import java.util.List;

/**
 * Created by Leon Wu on 2016/5/417:50.
 * Email: yuanliang.wu@weimob.com
 */
public abstract class BaseFragment extends Fragment {
    protected LoadingPage mContentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = new LoadingPage(UIUtils.getContext()) {
                @Override
                public View createLoadedView() {
                    return BaseFragment.this.createLoadedView();
                }

                @Override
                public LoadResult load() {
                    return BaseFragment.this.load();
                }
            };
        } else {
            ViewUtils.removeSelfFromParent(mContentView);
        }
        return mContentView;
    }

    /**
     * 请求网络加载
     *
     * @return
     */
    public abstract LoadingPage.LoadResult load();

    /**
     * 显示具体界面
     *
     * @return
     */
    public abstract View createLoadedView();


    /**
     * 检查数据的完整性
     *
     * @param data
     * @return
     */
    protected LoadingPage.LoadResult check(Object data) {
        if (data == null) {
            return LoadingPage.LoadResult.ERROR;
        } else if (data instanceof List) {
            List list = (List) data;
            if (list.size() == 0) {
                return LoadingPage.LoadResult.EMPTY;
            }
        }
        return LoadingPage.LoadResult.SUCCEDD;
    }

    /**
     *加载显示LoadingPage
     */
    public void show() {
        if (mContentView != null) {
            mContentView.show();
        }
    }
}
