package com.wyl.androidstore.ui.holder;

import android.view.View;

import com.lidroid.xutils.BitmapUtils;
import com.wyl.androidstore.R;
import com.wyl.androidstore.http.image.BitmapHelper;
import com.wyl.androidstore.utils.UIUtils;

/**
 * BaseHolder
 * Created by Leon Wu on 2016/5/316:29.
 * Email: yuanliang.wu@weimob.com
 */
public abstract class BaseHolder<Data> {
    protected View mContentView;
    protected Data data;
    protected BitmapUtils bitmapUtils;


    public BaseHolder() {

        mContentView = initView();
        mContentView.setTag(this);

        if (bitmapUtils == null) {
            bitmapUtils = BitmapHelper.getBitmapUtils(UIUtils.getContext());
            bitmapUtils.configDefaultLoadingImage(R.drawable.ic_default);
            bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_default);
        }
    }


    public View getContentView() {
        return mContentView;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        refreshView(); //刷新UI
    }

    protected abstract void refreshView();

    protected abstract View initView();

    public void recycle() {

    }

}
