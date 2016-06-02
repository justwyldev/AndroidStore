package com.wyl.androidstore.ui.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyl.androidstore.AboutActivity;
import com.wyl.androidstore.R;
import com.wyl.androidstore.bean.UserInfo;
import com.wyl.androidstore.http.HttpHelper;
import com.wyl.androidstore.manager.ThreadManager;
import com.wyl.androidstore.protocal.UserProtocol;
import com.wyl.androidstore.utils.UIUtils;

import java.util.List;

/**
 * 侧滑Menu
 * Created by Leon Wu on 2016/5/316:27.
 * Email: yuanliang.wu@weimob.com
 */
public class MenuHolder extends BaseHolder<UserInfo> implements View.OnClickListener {

    private RelativeLayout mUpdatesLayout, mAboutLayout, mExitLayout, mPhotoLayout;
    private ImageView mPhoto;
    private TextView mTvUserName, mTvUserEmail;
    private UserInfo mInfo;

    @Override
    protected View initView() {
        View view = UIUtils.inflate(R.layout.menu_list);
        mPhotoLayout = (RelativeLayout) view.findViewById(R.id.photo_layout);
        mPhotoLayout.setOnClickListener(this);

        mUpdatesLayout = (RelativeLayout) view.findViewById(R.id.updates_layout);
        mUpdatesLayout.setOnClickListener(this);

        mAboutLayout = (RelativeLayout) view.findViewById(R.id.about_layout);
        mAboutLayout.setOnClickListener(this);

        mExitLayout = (RelativeLayout) view.findViewById(R.id.exit_layout);
        mExitLayout.setOnClickListener(this);

        mPhoto = (ImageView) view.findViewById(R.id.image_photo);
        mTvUserName = (TextView) view.findViewById(R.id.user_name);
        mTvUserEmail = (TextView) view.findViewById(R.id.user_email);

        return view;
    }

    @Override
    public void refreshView() {
        if (mInfo != null) {
            bitmapUtils.display(mPhoto, HttpHelper.URL + "image?name=" + mInfo.getUrl());
            mTvUserName.setText(mInfo.getName());
            mTvUserEmail.setText(mInfo.getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updates_layout:
                UIUtils.showToast("已经是最新版本了V1.0！");
                break;
            case R.id.about_layout:
                Intent intent = new Intent(UIUtils.getContext(), AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UIUtils.getContext().startActivity(intent);
                break;
            case R.id.exit_layout:
                UIUtils.exitApp();
                break;
            case R.id.photo_layout:
                ThreadManager.creatLongPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        UserProtocol protocol = new UserProtocol();
                        List<UserInfo> loadInfo = protocol.load(0);
                        if (loadInfo != null && loadInfo.size() > 0) {
                            mInfo = loadInfo.get(0);
                            UIUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshView();
                                }
                            });
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

}
