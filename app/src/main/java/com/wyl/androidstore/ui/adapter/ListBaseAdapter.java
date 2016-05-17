package com.wyl.androidstore.ui.adapter;

import android.content.Intent;
import android.widget.AbsListView;

import com.wyl.androidstore.DetailActivity;
import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.bean.DownloadInfo;
import com.wyl.androidstore.manager.DownloadManager;
import com.wyl.androidstore.ui.holder.BaseHolder;
import com.wyl.androidstore.ui.holder.ListBaseHolder;
import com.wyl.androidstore.utils.UIUtils;

import java.util.List;

/**
 * Created by Leon Wu on 2016/5/515:17.
 * Email: yuanliang.wu@weimob.com
 */
public class ListBaseAdapter extends DefaultAdapter<AppInfo> implements DownloadManager.DownloadObserver {
    public ListBaseAdapter(List<AppInfo> datas, AbsListView listView) {
        super(datas, listView);
    }

    @Override
    public BaseHolder<AppInfo> getHolder() {
        return new ListBaseHolder();
    }

    @Override
    protected void onItemClickInner(int position) {
        super.onItemClickInner(position);
        List<AppInfo> datas = getDatas();
        if (position < datas.size()) {
            Intent intent = new Intent(UIUtils.getContext(),
                    DetailActivity.class);
            AppInfo info = datas.get(position);
            intent.putExtra(DetailActivity.PACKAGENAME, info.getPackageName());
            UIUtils.startActivity(intent);
        }
    }

    public void startObserver() {
        DownloadManager.getInstance().registerObserver(this);
    }

    public void stopObserver() {
        DownloadManager.getInstance().unRegisterObserver(this);
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshHolder(info);
    }

    private void refreshHolder(final DownloadInfo info) {
        List<BaseHolder> displayedHolders = getDisplayedHolders();
        for (int i = 0; i < displayedHolders.size(); i++) {
            BaseHolder baseHolder = displayedHolders.get(i);
            if (baseHolder instanceof ListBaseHolder) {
                final ListBaseHolder holder = (ListBaseHolder) baseHolder;
                AppInfo appInfo = holder.getData();
                if (appInfo.getId() == info.getId()) {
                    UIUtils.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.refreshState(info.getDownloadState(),
                                    (int) (info.getCurrentSize() * 100 / info
                                            .getAppSize()));
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onDownloadProgressed(DownloadInfo info) {
        refreshHolder(info);
    }
}
