package com.wyl.androidstore.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.protocal.HomePotocol;
import com.wyl.androidstore.ui.BaseListView;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.adapter.ListBaseAdapter;
import com.wyl.androidstore.ui.holder.HomePictureHolder;

import java.util.List;

/**
 * Created by Leon Wu on 2016/5/511:15.
 * Email: yuanliang.wu@weimob.com
 */
public class HomeFragment extends BaseFragment {
    private List<AppInfo> mDatas;
    private List<String> pictureUrl;
    private HomeAdapter homeAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("onActivityCreated");
    }

    @Override
    public View createLoadedView() {
        BaseListView view = new BaseListView(getActivity());
        if (pictureUrl != null && pictureUrl.size() > 0) {
            HomePictureHolder holder = new HomePictureHolder();
            holder.setData(pictureUrl);
            view.addHeaderView(holder.getContentView());
        }
        homeAdapter = new HomeAdapter(mDatas, view);
        homeAdapter.startObserver();
        view.setAdapter(homeAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeAdapter != null)
            homeAdapter.startObserver();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (homeAdapter != null)
            homeAdapter.stopObserver();
    }

    private class HomeAdapter extends ListBaseAdapter {

        public HomeAdapter(List<AppInfo> datas, AbsListView listView) {
            super(datas, listView);
        }

        @Override
        protected List<AppInfo> onLoadMore() {
            HomePotocol potocol = new HomePotocol();
            return potocol.load(mDatas.size());
        }
    }

    @Override
    public LoadingPage.LoadResult load() {
        HomePotocol homePotocol = new HomePotocol();
        mDatas = homePotocol.load(0);
        pictureUrl = homePotocol.getPictureUrl();

        //  加载数据    下载 json字符串 , 解析json
        return check(mDatas);
    }
}
