package com.wyl.androidstore.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Fragment工厂
 * <p/>
 * Created by Leon Wu on 2016/5/417:48.
 * Email: yuanliang.wu@weimob.com
 */
public class FragmentFactory {

    public static final int TAB_HOME = 0;
    public static final int TAB_APP = 1;
    public static final int TAB_GAME = 2;
    public static final int TAB_SUBJECT = 3;
    public static final int TAB_CATEGORY = 4;
    public static final int TAB_TOP = 5;
    public static final int TAB_RECOMMEND = 6;

    private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

    /**
     * 创建Fragment
     *
     * @param index
     * @return
     */
    public static BaseFragment createFragment(int index) {
        BaseFragment fragment = mFragments.get(index);
        //创建未被创建的Fragment
        if (fragment == null) {
            switch (index) {
                case TAB_HOME:
                    fragment = new HomeFragment();
                    break;
                case TAB_APP:
                    fragment = new AppFragment();
                    break;
                case TAB_GAME:
                    fragment = new GameFragment();
                    break;
                case TAB_SUBJECT:
                    fragment = new SubjectFragment();
                    break;
                case TAB_CATEGORY:
                    fragment = new CategoryFragment();
                    break;
                case TAB_TOP:
                    fragment = new TopFragment();
                    break;
                case TAB_RECOMMEND:
                    fragment = new RecommendFragment();
                    break;
            }
            mFragments.put(index, fragment);//保存创建的fragmen
        }
        return fragment;
    }
}
