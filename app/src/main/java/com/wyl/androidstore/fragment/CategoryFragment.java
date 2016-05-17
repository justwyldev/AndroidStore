package com.wyl.androidstore.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.wyl.androidstore.bean.CategoryInfo;
import com.wyl.androidstore.protocal.CategoryProtocol;
import com.wyl.androidstore.ui.BaseListView;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.adapter.DefaultAdapter;
import com.wyl.androidstore.ui.holder.BaseHolder;
import com.wyl.androidstore.ui.holder.CategoryHolder;
import com.wyl.androidstore.ui.holder.CategoryTitleHolder;

import java.util.List;

/**
 * 分类Fragment
 * Created by Leon Wu on 2016/5/511:15.
 * Email: yuanliang.wu@weimob.com
 */
public class CategoryFragment extends BaseFragment {
    private List<CategoryInfo> mDatas = null;
    private CategortAdapter adapter;
    @Override
    public View createLoadedView() {
        BaseListView listView=new BaseListView(getActivity());
        adapter=new CategortAdapter(mDatas, listView);
        listView.setAdapter(adapter);
        return listView;
    }
    @Override
    public LoadingPage.LoadResult load() {
        CategoryProtocol protocol=new CategoryProtocol();
        mDatas = protocol.load(0);
        return check(mDatas);
    }

    public class CategortAdapter extends DefaultAdapter<CategoryInfo> {
        private int mCurrentPosition;
        public CategortAdapter(List<CategoryInfo> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder() {
            CategoryInfo groupInfo = getDatas().get(mCurrentPosition);
            if (groupInfo.isTitle()) {
                return new CategoryTitleHolder();
            } else {
                return new CategoryHolder();
            }
        }
        @Override
        public boolean hasMore() {
            return false;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            mCurrentPosition = position;
            return super.getView(position, convertView, parent);
        }
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }
        //告诉ListView每个位置是哪种样式的item
        @Override
        public int getItemViewTypeInner(int position) {
            CategoryInfo groupInfo = getDatas().get(position);
            if (groupInfo.isTitle()) {
                return super.getItemViewTypeInner(position) + 1;
            } else {
                return super.getItemViewTypeInner(position);
            }
        }

    }
}
