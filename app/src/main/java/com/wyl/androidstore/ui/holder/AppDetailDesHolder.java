package com.wyl.androidstore.ui.holder;

import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.utils.UIUtils;

public class AppDetailDesHolder extends BaseHolder<AppInfo> implements
        OnClickListener {
    private RelativeLayout mLayout;
    private TextView mContent, mAuthor;
    private ImageView mArrow;
    private ScrollView mScrollView;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.app_detail_des);
        mContent = (TextView) view.findViewById(R.id.des_content);
        mAuthor = (TextView) view.findViewById(R.id.des_author);
        mLayout = (RelativeLayout) view.findViewById(R.id.des_layout);
        mLayout.setOnClickListener(this);
        mArrow = (ImageView) view.findViewById(R.id.des_arrow);
        mArrow.setTag(false);
        return view;
    }

    @Override
    protected void refreshView() {
        AppInfo info = getData();
        mContent.setText(info.getDes());
        mAuthor.setText(UIUtils.getString(R.string.app_detail_author)
                + info.getAuthor());
        mContent.getLayoutParams().height = measureShortHeight();
    }

    private int measureLongHeight() {
        int width = mContent.getMeasuredWidth();
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(2000, MeasureSpec.AT_MOST);
        TextView tv = getTextView();
        tv.measure(widthMeasureSpec, heightMeasureSpec);
        return tv.getMeasuredHeight();
    }

    private int measureShortHeight() {
        int width = mContent.getMeasuredWidth();
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(2000, MeasureSpec.AT_MOST);
        TextView tv = getTextView();
        tv.setLines(7);
        tv.setMaxLines(7);
        tv.measure(widthMeasureSpec, heightMeasureSpec);
        return tv.getMeasuredHeight();
    }

    private TextView getTextView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        tv.setText(getData().getDes());
        return tv;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.des_layout:
                expand();
                break;
            default:
                break;
        }
    }

    private void expand() {
        final LayoutParams params = mContent.getLayoutParams();
        int shortHeight = measureShortHeight();
        int longHeight = measureLongHeight();
        ValueAnimator va;
        final boolean flag = (Boolean) mArrow.getTag();
        if (flag) {
            mArrow.setTag(false);
            va = ValueAnimator.ofInt(longHeight, shortHeight);
        } else {
            mArrow.setTag(true);
            va = ValueAnimator.ofInt(shortHeight, longHeight);
        }
        mLayout.setEnabled(false);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator va) {
                params.height = (Integer) va.getAnimatedValue();
                mContent.setLayoutParams(params);
                if (!flag) {
                    if (mScrollView == null) {
                        mScrollView = getScrollView();
                    }
                    if (mScrollView != null) {
                        mScrollView.scrollTo(0, mScrollView.getHeight());
                    }
                }
            }
        });
        va.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                boolean flag = (Boolean) mArrow.getTag();
                mArrow.setImageResource(flag ? R.drawable.arrow_up : R.drawable.arrow_down);
                mLayout.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }
        });
        va.setDuration(300);
        va.start();
    }

    /**
     * �ҵ����ո����� ScollView
     */
    protected ScrollView getScrollView() {
        ScrollView scrollView = null;
        View currentView = mLayout;
        while (true) {
            ViewParent parent = currentView.getParent();
            if (parent == null || !(parent instanceof View)) {
                break;
            } else if (parent instanceof ScrollView) {
                scrollView = (ScrollView) parent;
                break;
            } else {
                currentView = (View) parent;
            }
        }
        return scrollView;
    }

}
