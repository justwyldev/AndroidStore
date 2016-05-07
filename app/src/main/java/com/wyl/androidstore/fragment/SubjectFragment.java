package com.wyl.androidstore.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.wyl.androidstore.bean.SubjectInfo;
import com.wyl.androidstore.protocal.SubjectProtocol;
import com.wyl.androidstore.ui.BaseListView;
import com.wyl.androidstore.ui.LoadingPage;
import com.wyl.androidstore.ui.adapter.DefaultAdapter;
import com.wyl.androidstore.ui.holder.BaseHolder;
import com.wyl.androidstore.ui.holder.SubjectHolder;
import com.wyl.androidstore.utils.UIUtils;

import java.util.List;

/**
 * Created by Leon Wu on 2016/5/511:15.
 * Email: yuanliang.wu@weimob.com
 */
public class SubjectFragment extends BaseFragment {
    private BaseListView mListView;
    private List<SubjectInfo> datas;

    @Override
    public View createLoadedView() {
        mListView=new BaseListView(UIUtils.getContext());
        SubjectAdapter adapter=new SubjectAdapter(datas, mListView);
        mListView.setAdapter(adapter);
        return mListView;
    }

    @Override
    public LoadingPage.LoadResult load() {
        SubjectProtocol protocol=new SubjectProtocol();
        datas = protocol.load(0);
        return  check(datas);
    }
    class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder() {
            return new SubjectHolder();
        }


        @Override
        public boolean hasMore() {
            return true;
        }

        @Override
        protected List<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol=new SubjectProtocol();

            return protocol.load(getDatas().size());
        }
        @Override
        protected void onItemClickInner(int position) {
            super.onItemClickInner(position);
            String des = datas.get(position).getDes();
            Toast.makeText(getActivity(), des, 0).show();
        }
    }
}
