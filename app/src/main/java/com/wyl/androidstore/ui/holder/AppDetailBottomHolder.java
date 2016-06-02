package com.wyl.androidstore.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.bean.DownloadInfo;
import com.wyl.androidstore.manager.DownloadManager;
import com.wyl.androidstore.utils.UIUtils;

/**
 * 加载详情页底部的数据
 * Created by Leon Wu on 2016/5/5
 * Email: yuanliang.wu@weimob.com
 */
public class AppDetailBottomHolder extends BaseHolder<AppInfo> implements
		OnClickListener, DownloadManager.DownloadObserver {
	private Button mBtnProgress;
	private FrameLayout mLayout;
	private int mState;
	private int mProgress;
	private ProgressBar mProgeressView;
	private DownloadManager mDownloadManager;
	private TextView tv_load_process;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.app_detail_bottom);
		mBtnProgress = (Button) view.findViewById(R.id.progress_btn);
		mBtnProgress.setOnClickListener(this);

		mLayout = (FrameLayout) view.findViewById(R.id.progress_layout);
		mProgeressView = (ProgressBar) view.findViewById(R.id.pb_load_process);
		tv_load_process = (TextView) view.findViewById(R.id.tv_load_process);
		mProgeressView.setId(R.id.detail_progress);
		mProgeressView.setOnClickListener(this);
		// mProgeressView.setProgressDrawable(UIUtils.getResources().getDrawable(
		// R.drawable.progress_drawable));
		mProgeressView.setMax(100);
		// mProgeressView.setProgress(0);
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		// mLayout.addView(mProgeressView, params);
		return view;
	}

	@Override
	protected void refreshView() {
		refreshState(mState, mProgress);
	}

	public void refreshState(int state, int progress) {
		mState = state;
		mProgress = progress;
		switch (mState) {
		case DownloadManager.STATE_NONE:
			mProgeressView.setVisibility(View.GONE);
			mBtnProgress.setVisibility(View.VISIBLE);
			mBtnProgress
					.setText(UIUtils.getString(R.string.app_state_download));
			break;
		case DownloadManager.STATE_PAUSE:
			mProgeressView.setVisibility(View.VISIBLE);
			mProgeressView.setProgress(progress);
			mBtnProgress.setVisibility(View.GONE);
			tv_load_process.setVisibility(View.VISIBLE);
			tv_load_process.setText(UIUtils.getString(R.string.app_state_paused));
			break;
		case DownloadManager.STATE_ERROR:
			mProgeressView.setVisibility(View.GONE);
			tv_load_process.setVisibility(View.GONE);
			mBtnProgress.setVisibility(View.VISIBLE);
			mBtnProgress.setText(R.string.app_state_error);
			break;
		case DownloadManager.STATE_WAITING:
			mProgeressView.setVisibility(View.VISIBLE);
			mProgeressView.setProgress(progress);
			tv_load_process.setVisibility(View.VISIBLE);
			tv_load_process.setText(UIUtils.getString(R.string.app_state_waiting));
			mBtnProgress.setVisibility(View.GONE);
			break;
		case DownloadManager.STATE_DOWNLOADING:
			mProgeressView.setVisibility(View.VISIBLE);
			mProgeressView.setProgress(progress);
			tv_load_process.setVisibility(View.VISIBLE);
			tv_load_process.setText(progress+"%");
			// mProgeressView.setCenterText("");
			mBtnProgress.setVisibility(View.GONE);
			break;
		case DownloadManager.STATE_DOWNLOED:
			mProgeressView.setVisibility(View.GONE);
			tv_load_process.setVisibility(View.GONE);
			mBtnProgress.setVisibility(View.VISIBLE);
			mBtnProgress.setText(R.string.app_state_downloaded);
			break;
		default:
			break;
		}
	}

	@Override
	public void setData(AppInfo data) {

		if (mDownloadManager == null) {
			mDownloadManager = DownloadManager.getInstance();
		}
		DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data
				.getId());
		if (downloadInfo != null) {
			mState = downloadInfo.getDownloadState();
			mProgress = (int) (downloadInfo.getCurrentSize() * 100 / downloadInfo
					.getAppSize());
		} else {
			mState = DownloadManager.STATE_NONE;
			mProgress = 0;
		}
		super.setData(data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_progress:
		case R.id.progress_btn:
			if (mState == DownloadManager.STATE_NONE
					|| mState == DownloadManager.STATE_PAUSE
					|| mState == DownloadManager.STATE_ERROR) {
				mDownloadManager.download(getData());
			} else if (mState == DownloadManager.STATE_WAITING
					|| mState == DownloadManager.STATE_DOWNLOADING) {
				mDownloadManager.pause(getData());
			} else if (mState == DownloadManager.STATE_DOWNLOED) {
				mDownloadManager.install(getData());
			}
			break;
		default:
			break;
		}
	}

	public void startObserver() {
		mDownloadManager.registerObserver(this);
	}

	public void stopObserver() {
		mDownloadManager.unRegisterObserver(this);
	}

	@Override
	public void onDownloadStateChanged(DownloadInfo info) {
		refreshHolder(info);
	}

	@Override
	public void onDownloadProgressed(DownloadInfo info) {
		refreshHolder(info);
	}

	private void refreshHolder(final DownloadInfo info) {
		AppInfo appInfo = getData();
		if (appInfo.getId() == info.getId()) {
			UIUtils.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					refreshState(info.getDownloadState(), (int) (info
							.getCurrentSize() * 100 / info.getAppSize()));
				}
			});
		}
	}

}
