package com.wyl.androidstore.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.AppInfo;
import com.wyl.androidstore.bean.DownloadInfo;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.manager.DownloadManager;
import com.wyl.androidstore.ui.widgets.ProgressArc;
import com.wyl.androidstore.utils.UIUtils;

/**
 * Created by Leon Wu on 2016/5/515:19.
 * Email: yuanliang.wu@weimob.com
 */
public class ListBaseHolder extends BaseHolder<AppInfo> implements
        View.OnClickListener {
    private ImageView icon;
    private TextView tvTitle, tvSize, tvDes, mActionText;
    private RatingBar rb;
    private RelativeLayout mActionLayout;
    private FrameLayout mProgressLayout;
    private DownloadManager downloadManager;
    private int mState;
    private float mProgress;
    private ProgressArc mProgressArc;

    public ListBaseHolder() {
        super();
    }

    // 初始化控件
    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.list_item);
        icon = (ImageView) view.findViewById(R.id.item_icon);
        tvTitle = (TextView) view.findViewById(R.id.item_title);
        tvSize = (TextView) view.findViewById(R.id.item_size);
        tvDes = (TextView) view.findViewById(R.id.item_bottom);
        rb = (RatingBar) view.findViewById(R.id.item_rating);
        mActionLayout = (RelativeLayout) view.findViewById(R.id.item_action);
        mActionLayout.setBackgroundResource(R.drawable.list_item_action_bg);
        mActionLayout.setOnClickListener(this);

        mProgressLayout = (FrameLayout) view.findViewById(R.id.action_progress);
        mProgressArc = new ProgressArc(UIUtils.getContext());
        int arcDiameter = UIUtils.dip2px(26);
        // 设置圆的直径
        mProgressArc.setArcDiameter(arcDiameter);
        // 设置进度条的颜色
        mProgressArc.setProgressColor(UIUtils.getColor(R.color.progress));
        int size = UIUtils.dip2px(27);
        mProgressLayout.addView(mProgressArc, new ViewGroup.LayoutParams(size,
                size));

        mActionText = (TextView) view.findViewById(R.id.action_txt);

        return view;
    }

    public void setData(AppInfo data) {
        if (downloadManager == null) {
            downloadManager = DownloadManager.getInstance();
        }
        DownloadInfo downloadInfo = downloadManager.getDownloadInfo(data
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

    // 给控件设置 数据
    @Override
    protected void refreshView() {
        AppInfo appInfo = getData();
        tvTitle.setText(appInfo.getName());
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),
                appInfo.getSize()));
        tvDes.setText(appInfo.getDes());
        rb.setRating(appInfo.getStars());

        String url = appInfo.getIconUrl();
        bitmapUtils.display(icon, HttpHelper.URL + "image?name=" + url);
        refreshState(mState, mProgress);

    }

    public void refreshState(int state, float progress) {
        mState = state;
        mProgress = progress;
        switch (mState) {
            case DownloadManager.STATE_NONE:
                mProgressArc.seForegroundResource(R.drawable.ic_download);
                // 是否画进度条，不画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_download);
                break;
            case DownloadManager.STATE_PAUSE:
                mProgressArc.seForegroundResource(R.drawable.ic_resume);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_paused);
                break;
            case DownloadManager.STATE_ERROR:
                mProgressArc.seForegroundResource(R.drawable.ic_redownload);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_error);
                break;
            case DownloadManager.STATE_WAITING:
                mProgressArc.seForegroundResource(R.drawable.ic_pause);
                // 是否画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                mProgressArc.setProgress(progress / 100, false);
                mActionText.setText(R.string.app_state_waiting);
                break;
            case DownloadManager.STATE_DOWNLOADING:
                mProgressArc.seForegroundResource(R.drawable.ic_pause);
                // 画进度条
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                mProgressArc.setProgress(progress / 100, true);
                mActionText.setText(mProgress + "%");
                break;
            case DownloadManager.STATE_DOWNLOED:
                mProgressArc.seForegroundResource(R.drawable.ic_install);
                mProgressArc.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                mActionText.setText(R.string.app_state_downloaded);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_action) {
            if (mState == DownloadManager.STATE_NONE
                    || mState == DownloadManager.STATE_PAUSE
                    || mState == DownloadManager.STATE_ERROR) {
                downloadManager.download(getData());
            } else if (mState == DownloadManager.STATE_WAITING
                    || mState == DownloadManager.STATE_DOWNLOADING) {
                downloadManager.pause(getData());
            } else if (mState == DownloadManager.STATE_DOWNLOED) {
                downloadManager.install(getData());
            }
        }
    }

}