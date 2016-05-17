package com.wyl.androidstore.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.wyl.androidstore.R;
import com.wyl.androidstore.manager.ThreadManager;
import com.wyl.androidstore.utils.UIUtils;

/**
 * 根据状态加载页面
 * <p/>
 * Created by Leon Wu on 2016/5/417:50.
 * Email: yuanliang.wu@weimob.com
 */
public abstract class LoadingPage extends FrameLayout {
    public static final int STATE_UNKNOW = 0;// 未知状态
    public static final int STATE_LOADING = 1; // 加载状态
    public static final int STATE_ERROR = 2; // 错误状态
    public static final int STATE_EMPTY = 3; // 加载完毕 错误状态
    public static final int STATE_SUCCEED = 4; // 加载成功


    // 显示的4个界面
    private View mLoadingView;// 加载时显示的View
    private View mErrorView;// 加载出错显示的View
    private View mEmptyView;// 加载没有数据显示的View
    private View mSucceedView;// 加载成功显示的View

    private int mState = STATE_UNKNOW;  // 当前的状态

    // 构造方法
    public LoadingPage(Context context) {
        super(context);
        init();
        //show();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(UIUtils.getColor(R.color.bg_page));
        // 创建加载中的界面
        mLoadingView = createLoadingView();
        if (mLoadingView != null) {
            // 添加到FrameLayout上
            this.addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        mErrorView = createErrorView();
        if (mErrorView != null) {
            this.addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        mEmptyView = createEmptyView();
        if (mEmptyView != null) {
            this.addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        showPageSafe();
    }

    /*创建空的界面*/
    private View createEmptyView() {
        return UIUtils.inflate(R.layout.loading_page_empty);
    }

    /*创建错误界面*/
    private View createErrorView() {
        View view = UIUtils.inflate(R.layout.loading_page_error);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    //根据状态不同显示不同的界面
    private void showPage() {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == STATE_UNKNOW || mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }
        if (mState == STATE_SUCCEED && mSucceedView == null) {
            mSucceedView = createLoadedView();
            this.addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        if (mSucceedView != null) {
            mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE : View.INVISIBLE);
        }
    }

    //交给实现该类的去实现
    public abstract View createLoadedView();

    /*创建加载界面的view*/
    private View createLoadingView() {
        // View view = View.inflate(getActivity(), R.layout.loading_page_load,
        // null);
        return UIUtils.inflate(R.layout.loading_page_load);
    }

    private boolean needReset() {
        return mState == STATE_ERROR || mState == STATE_EMPTY;
    }

    // 改变状态
    public synchronized void show() {
        if (needReset()) {
            mState = STATE_UNKNOW;
        }
        if (mState == STATE_UNKNOW) {
            mState = STATE_LOADING;
            // 开启线程 加载
            ThreadManager.creatLongPool().execute(new LoadingTask());
        }
        showPageSafe();
    }

    private void showPageSafe() {
        UIUtils.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                showPage();
            }
        });
    }

    // 每个界面请求的数据都不一样
    public abstract LoadResult load();


    public enum LoadResult {
        ERROR(2), EMPTY(3), SUCCEDD(4);

        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    //  异步加载
    public class LoadingTask implements Runnable {
        @Override
        public void run() {
            final LoadResult load = load();
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//
//			}
            UIUtils.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    int value = load.getValue();
                    mState = value;
                    showPage();

                }
            });
        }
    }
}
