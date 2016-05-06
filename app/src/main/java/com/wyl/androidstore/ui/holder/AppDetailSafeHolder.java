package com.wyl.androidstore.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.utils.UIUtils;

import java.util.List;

public class AppDetailSafeHolder extends BaseHolder<AppInfo> implements
		OnClickListener {
	private RelativeLayout mSafeLayout; 
	private LinearLayout mContentLayout;
	private ImageView mArrow;

	private ImageView[] mIv;
	private ImageView[] mDesIv;
	private TextView[] mDesTv;
	private LinearLayout[] mLayout;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_safe);
		mSafeLayout = (RelativeLayout) view.findViewById(R.id.safe_layout);
		mContentLayout = (LinearLayout) view.findViewById(R.id.safe_content);
		mContentLayout.getLayoutParams().height = 0;
		mArrow = (ImageView) view.findViewById(R.id.safe_arrow);
		mArrow.setTag(false);

		mSafeLayout.setOnClickListener(this);

		mIv = new ImageView[4];
		mIv[0] = (ImageView) view.findViewById(R.id.iv_1);
		mIv[1] = (ImageView) view.findViewById(R.id.iv_2);
		mIv[2] = (ImageView) view.findViewById(R.id.iv_3);
		mIv[3] = (ImageView) view.findViewById(R.id.iv_4);

		mDesIv = new ImageView[4];
		mDesIv[0] = (ImageView) view.findViewById(R.id.des_iv_1);
		mDesIv[1] = (ImageView) view.findViewById(R.id.des_iv_2);
		mDesIv[2] = (ImageView) view.findViewById(R.id.des_iv_3);
		mDesIv[3] = (ImageView) view.findViewById(R.id.des_iv_4);

		mDesTv = new TextView[4];
		mDesTv[0] = (TextView) view.findViewById(R.id.des_tv_1);
		mDesTv[1] = (TextView) view.findViewById(R.id.des_tv_2);
		mDesTv[2] = (TextView) view.findViewById(R.id.des_tv_3);
		mDesTv[3] = (TextView) view.findViewById(R.id.des_tv_4);

		mLayout = new LinearLayout[4];
		mLayout[0] = (LinearLayout) view.findViewById(R.id.des_layout_1);
		mLayout[1] = (LinearLayout) view.findViewById(R.id.des_layout_2);
		mLayout[2] = (LinearLayout) view.findViewById(R.id.des_layout_3);
		mLayout[3] = (LinearLayout) view.findViewById(R.id.des_layout_4);

		return view;
	}

	@Override
	protected void refreshView() {
		AppInfo info = getData();
		// ��Ӧ�Źٷ�����ȫ���޹��ȵ�ͼƬ���ص�ַ
		List<String> safeUrl = info.getSafeUrl();
		// С���򹴵����ص�ַ
		List<String> safeDesUrl = info.getSafeDesUrl();
		// С���򹴺����������Ϣ
		List<String> safeDes = info.getSafeDes();
		// ������������ɫ���й�����ɫ�Ƚ���Ŀ
		List<Integer> safeDesColor = info.getSafeDesColor();

		for (int i = 0; i < 4; i++) {
			if (i < safeUrl.size() && i < safeDesUrl.size()
					&& i < safeDes.size() && i < safeDesColor.size()) {
				bitmapUtils.display(mIv[i], HttpHelper.URL + "image?name="
						+ safeUrl.get(i));
				bitmapUtils.display(mDesIv[i], HttpHelper.URL + "image?name="
						+ safeDesUrl.get(i));
				mDesTv[i].setText(safeDes.get(i));
				int color;
				int colorType = safeDesColor.get(i);
				if (colorType >= 1 && colorType <= 3) {
					color = Color.rgb(255, 153, 0);
				} else if (colorType == 4) {
					color = Color.rgb(0, 177, 62);
				} else {
					color = Color.rgb(122, 122, 122);
				}
				mDesTv[i].setTextColor(color);
				mIv[i].setVisibility(View.VISIBLE);
				mLayout[i].setVisibility(View.VISIBLE);
			} else {
				mIv[i].setVisibility(View.GONE);
				mLayout[i].setVisibility(View.GONE);
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.safe_layout:
			expand();
			break;

		default:
			break;
		}
	}

	private void expand() {
		final LayoutParams layoutParams = mContentLayout.getLayoutParams();
		int targetHeight;
		int height = mContentLayout.getMeasuredHeight();
		boolean flag = (Boolean) mArrow.getTag();
		if (flag) {
			mArrow.setTag(false);
			targetHeight = 0;
		} else {
			mArrow.setTag(true);
			targetHeight = measureContentHeight();// �����ĸ߶�
		}
		mSafeLayout.setEnabled(false);
		ValueAnimator va = ValueAnimator.ofInt(height, targetHeight);
		va.addUpdateListener(new AnimatorUpdateListener() {
			// λ�ø��µ�ʱ��
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				layoutParams.height = (Integer) animation.getAnimatedValue();
				mContentLayout.setLayoutParams(layoutParams);
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
				mArrow.setImageResource(flag ? R.drawable.arrow_up
						: R.drawable.arrow_down);
				mSafeLayout.setEnabled(true);
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});
		va.setDuration(300);
		va.start();
	}

	private int measureContentHeight() {
		//���ֵĿ�
		int width = mContentLayout.getMeasuredWidth();
		//  �������ø߶Ȱ������
		mContentLayout.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
		// ��ȷ��ֵ
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
		// ����ֵ 
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);
		// ���²���
		mContentLayout.measure(widthMeasureSpec, heightMeasureSpec);
		return mContentLayout.getMeasuredHeight();
	}

}
