package com.wyl.androidstore.fragment;

import android.view.View;

import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.protocal.GameProtocol;
import com.wyl.androidstore.ui.BaseListView;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.adapter.ListBaseAdapter;

import java.util.List;

/**
 * Created by Leon Wu on 2016/5/511:15.
 * Email: yuanliang.wu@weimob.com
 */
public class GameFragment extends BaseFragment {
    private List<AppInfo> datas;
    @Override
    public View createLoadedView() {
        BaseListView view=new BaseListView(getActivity());
        view.setAdapter(new ListBaseAdapter(datas,view));
        return view;
    }

    @Override
    public LoadingPage.LoadResult load() {
        GameProtocol protocol=new GameProtocol();
        datas = protocol.load(0);
        return check(datas);
    }

}
