package com.wyl.androidstore.fragment;

import android.view.View;

import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.protocal.AppProtocol;
import com.wyl.androidstore.ui.BaseListView;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.adapter.ListBaseAdapter;

import java.util.List;

/**
 * AppFragment
 * Created by Leon Wu on 2016/5/511:15.
 * Email: yuanliang.wu@weimob.com
 */
public class AppFragment extends BaseFragment {
    private List<AppInfo> datas;
    private ListBaseAdapter listBaseAdapter;

    @Override
    public View createLoadedView() {
        BaseListView view = new BaseListView(getActivity());
        listBaseAdapter = new ListBaseAdapter(datas, view);
        listBaseAdapter.stopObserver();
        view.setAdapter(listBaseAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listBaseAdapter != null) {
            listBaseAdapter.startObserver();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listBaseAdapter != null)
            listBaseAdapter.stopObserver();
    }

    @Override
    public LoadingPage.LoadResult load() {
        AppProtocol potocol = new AppProtocol();
        datas = potocol.load(0);
        return check(datas);
    }

}
