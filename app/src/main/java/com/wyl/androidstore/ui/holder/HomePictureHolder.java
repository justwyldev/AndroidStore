package com.wyl.androidstore.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.utils.UIUtils;

import java.util.LinkedList;
import java.util.List;

public class HomePictureHolder extends BaseHolder<List<String>> {
    private ViewPager viewPager;
    private AutoPlayRunnable runnable;

    @Override
    protected View initView() {
        viewPager = new ViewPager(UIUtils.getContext());
        viewPager.setLayoutParams(new AbsListView.LayoutParams(
                LayoutParams.MATCH_PARENT, UIUtils
                .getDimens(R.dimen.list_head_height)));
        viewPager.setAdapter(new MyPageAdapter());
        runnable = new AutoPlayRunnable();
        viewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    runnable.stop();
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    runnable.start();
                }
                return false;  // 返回false
            }
        });
        return viewPager;
    }

    @Override
    protected void refreshView() {

        viewPager.setCurrentItem(1000 * getData().size());

        runnable.start();
    }

    private class AutoPlayRunnable implements Runnable {
        private boolean mShouldAutoPlay; // 是否需要自动播放
        private int AUTO_PLAY_INTERVAL = 5000; // 间隔时间

        public void start() {
            if (!mShouldAutoPlay) {
                mShouldAutoPlay = true;
                UIUtils.removeCallBacks(this);
                UIUtils.postDelay(this, AUTO_PLAY_INTERVAL);
            }
        }

        public void stop() {
            if (mShouldAutoPlay) {
                mShouldAutoPlay = false;
                UIUtils.removeCallBacks(this);
            }
        }

        @Override
        public void run() {
            if (mShouldAutoPlay) {
                UIUtils.removeCallBacks(this);// 首先移除之前的
                int postion = viewPager.getCurrentItem();
                viewPager.setCurrentItem(postion + 1, true);// 平滑的滑动
                UIUtils.postDelay(this, AUTO_PLAY_INTERVAL);
            }
        }

    }

    private class MyPageAdapter extends PagerAdapter {
        // 构建ImageView 缓存
        List<ImageView> mViewCache = new LinkedList<ImageView>();

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
            mViewCache.add((ImageView) object); // 缓存ImageView
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView;
            if (mViewCache.size() > 0) {
                imageView = mViewCache.remove(0);
            } else {
                imageView = new ImageView(UIUtils.getContext());
            }
            int index = position % getData().size();
            String url = getData().get(index);
            bitmapUtils
                    .display(imageView, HttpHelper.URL + "image?name=" + url);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View v, Object o) {
            return v == o;
        }

    }
}