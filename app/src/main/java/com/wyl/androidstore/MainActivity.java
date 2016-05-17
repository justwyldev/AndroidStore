package com.wyl.androidstore;

import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.wyl.androidstore.fragment.BaseFragment;
import com.wyl.androidstore.fragment.FragmentFactory;
import com.wyl.androidstore.ui.holder.MenuHolder;
import com.wyl.androidstore.utils.UIUtils;

/**
 * MainActivity
 * Created by Leon Wu
 * Email: yuanliang.wu@weimob.com
 */
public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;//左上角Toggle
    private PagerTabStrip mPagerTabStrip;  //viewpager上的标签(导航条)
    private ViewPager mViewPager;
    private String[] mTabName;
    private FrameLayout mDrawerFrameLayout;
    private MenuHolder menuHolder;

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerFrameLayout = (FrameLayout) findViewById(R.id.start_drawer);
        menuHolder = new MenuHolder();
        mDrawerFrameLayout.addView(menuHolder.getContentView());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));

        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        //设置Tab选中时的颜色
        mPagerTabStrip.setTabIndicatorColor(Color.GREEN);
        //设置Tab背景色
        mPagerTabStrip.setBackgroundColor(Color.WHITE);
        //设置Tab间的距离
        mPagerTabStrip.setTextColor(Color.BLACK);
    }

    public class MainPageAdapter extends FragmentPagerAdapter {
        public MainPageAdapter(FragmentManager fm) {
            super(fm);
            mTabName = UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public Fragment getItem(int position) {
            //创建相应Fragment
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return mTabName.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName[position];
        }
    }


    /**
     * 初始化ActionBar
     */
    @Override
    protected void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_am, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mDrawerToggle.syncState();//和ActionBar关联
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MainPageChangeListener changeListener = new MainPageChangeListener();
        changeListener.onPageSelected(0);
        mViewPager.setOnPageChangeListener(changeListener);
    }

    private class MainPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            BaseFragment createFragment = FragmentFactory.createFragment(position);
            createFragment.show();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        }

    }
}
