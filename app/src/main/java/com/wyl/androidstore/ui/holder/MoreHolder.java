package com.wyl.androidstore.ui.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.wyl.androidstore.R;
import com.wyl.androidstore.ui.adapter.DefaultAdapter;
import com.wyl.androidstore.utils.UIUtils;

/**
 * 加载MoreHolder肯定要设置当前Holder 的数据
 * Created by Leon Wu on 2016/5/515:10.
 * Email: yuanliang.wu@weimob.com
 */
public class MoreHolder extends BaseHolder<Integer> implements View.OnClickListener {
    public static final int HAS_MORE = 0;
    public static final int NO_MORE = 1;
    public static final int ERROR = 2;
    private RelativeLayout rl_more_loading, rl_more_error;
    private DefaultAdapter<?> adapter;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.list_more_loading);
        rl_more_loading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        rl_more_error = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        return view;
    }

    public MoreHolder(DefaultAdapter<?> adapter, boolean hasMore) {
        super();
        this.adapter = adapter;
        setData(hasMore ? HAS_MORE : NO_MORE);
    }


    @Override
    protected void refreshView() {
        Integer state = getData();
        rl_more_loading.setVisibility(state == HAS_MORE ? View.VISIBLE : View.GONE);
        rl_more_error.setVisibility(state == ERROR ? View.VISIBLE : View.GONE);
        rl_more_error.setOnClickListener(this);
    }

    // 最终界面会调用getContentView() ,加载更多
    @Override
    public View getContentView() {
        if (getData() == HAS_MORE) {
            loadMore();
        }
        return super.getContentView();
    }

    // 加载更多的数据也是显示到ListView上 交给Adapter去加载
    private void loadMore() {
        adapter.loadMore();
    }

    @Override
    public void onClick(View v) {
        adapter.loadMore();
    }

}