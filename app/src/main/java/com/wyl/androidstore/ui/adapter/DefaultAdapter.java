package com.wyl.androidstore.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.wyl.androidstore.manager.ThreadManager;
import com.wyl.androidstore.ui.holder.BaseHolder;
import com.wyl.androidstore.ui.holder.MoreHolder;
import com.wyl.androidstore.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon Wu on 2016/5/515:04.
 * Email: yuanliang.wu@weimob.com
 */
public abstract class DefaultAdapter<Data> extends BaseAdapter implements AdapterView.OnItemClickListener, AbsListView.RecyclerListener {

    private static final int MORE_VIEW_TYPE = 0;
    private static final int ITEM_VIEW_TYPE = 1;
    private List<Data> datas;
    private MoreHolder holder;
    private AbsListView absListView;
    private List<BaseHolder> mDisplayedHolders;

    public List<BaseHolder> getDisplayedHolders() {
        synchronized (mDisplayedHolders) {
            return new ArrayList<BaseHolder>(mDisplayedHolders);
        }
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        if (null != view) {
            Object tag = view.getTag();
            if (tag instanceof BaseHolder) {
                BaseHolder holder = (BaseHolder) tag;
                synchronized (mDisplayedHolders) {
                    mDisplayedHolders.remove(holder);
                }
                holder.recycle();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (absListView != null && absListView instanceof ListView) {
            ListView listView = (ListView) absListView;
            int headerViewsCount = listView.getHeaderViewsCount();
            onItemClickInner(position - headerViewsCount); // 去掉头的数量
        } else {
            onItemClickInner(position);
        }
    }

    protected void onItemClickInner(int position) {

    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public DefaultAdapter(List<Data> datas, AbsListView absListView) {
        super();
        mDisplayedHolders = new ArrayList<BaseHolder>();
        this.absListView = absListView;
        setDatas(datas);
        absListView.setOnItemClickListener(this);
        absListView.setRecyclerListener(this);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return MORE_VIEW_TYPE;
        } else {
            return getItemViewTypeInner(position);
        }
    }

    public int getItemViewTypeInner(int position) {
        return ITEM_VIEW_TYPE;// 普通item的布局
    }

    @Override
    public int getViewTypeCount() {
        // 因为需要加载更多
        return super.getViewTypeCount() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 根据加载更多 对代码做了修改
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<Data> holder = null;
        if (convertView != null && convertView.getTag() instanceof BaseHolder) {
            holder = (BaseHolder<Data>) convertView.getTag();
        } else {
            if (getItemViewType(position) == MORE_VIEW_TYPE) {
                holder = getMoreHolder();
            } else {
                holder = getHolder();
            }
        }
        // 面向Holder编程
        if (getItemViewType(position) != MORE_VIEW_TYPE) {
            holder.setData(datas.get(position));
        }
        mDisplayedHolders.add(holder);
        return holder.getContentView();
    }

    private volatile boolean isLoading;

    public final void loadMore() {
        // 防止重复加载
        if (!isLoading) {
            isLoading = true;

            ThreadManager.creatLongPool().execute(new Runnable() {

                @Override
                public void run() {
                    final List<Data> datas = onLoadMore();
                    UIUtils.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (datas == null) {
                                getMoreHolder().setData(MoreHolder.ERROR);
                            } else if (datas.size() == 0) {
                                getMoreHolder().setData(MoreHolder.NO_MORE);
                            } else {
                                getMoreHolder().setData(MoreHolder.HAS_MORE);
                            }
                            if (datas != null) {
                                if (getDatas() != null) {
                                    getDatas().addAll(datas);
                                } else {
                                    setDatas(datas);
                                }
                            }
                            notifyDataSetChanged();
                            isLoading = false;
                        }
                    });
                }
            });
        }
    }

    // 交给子类去实现
    protected List<Data> onLoadMore() {
        return null;
    }

    private BaseHolder getMoreHolder() {
        if (holder == null) {
            holder = new MoreHolder(this, hasMore());
        }
        return holder;
    }

    public boolean hasMore() {
        return true;
    }

    public abstract BaseHolder<Data> getHolder();
}
