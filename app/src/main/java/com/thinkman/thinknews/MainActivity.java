package com.thinkman.thinknews;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkman.thinkactivity.BaseActivity;
import com.thinkman.thinknews.fragment.NewsFragment;
import com.thinkman.thinkviewpagerindicator.view.indicator.FragmentListPageAdapter;
import com.thinkman.thinkviewpagerindicator.view.indicator.IndicatorViewPager;
import com.thinkman.thinkviewpagerindicator.view.indicator.ScrollIndicatorView;
import com.thinkman.thinkviewpagerindicator.view.indicator.slidebar.ColorBar;
import com.thinkman.thinkviewpagerindicator.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawer = null;

    private IndicatorViewPager mIndicatorViewPager;
    private LayoutInflater mInflate;
    private String[] mTabNames = { "热门精选", "国内", "国际", "体育", "科技", "娱乐"};
    private ScrollIndicatorView mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.setActionBar(R.mipmap.ic_list_white, R.string.app_name);
        this.setOnActionBarLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });

        initViewPager();
    }

    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        mIndicator = (ScrollIndicatorView) findViewById(R.id.main_indicator);
        mIndicator.setScrollBar(new ColorBar(this, Color.RED, 5));

        // 设置滚动监听
        int selectColorId = R.color.tab_top_text_2;
        int unSelectColorId = R.color.tab_top_text_1;
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, selectColorId, unSelectColorId));

        viewPager.setOffscreenPageLimit(2);
        mIndicatorViewPager = new IndicatorViewPager(mIndicator, viewPager);
        mInflate = LayoutInflater.from(getApplicationContext());
        mIndicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        HashMap<Integer, Fragment> mMapFragments = new HashMap<Integer, Fragment>();

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return mTabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = mInflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(mTabNames[position % mTabNames.length]);
            textView.setTextColor(MainActivity.this.getResources().getColor(R.color.tab_top_text_1));
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            if (mMapFragments.containsKey(position)) {
                return mMapFragments.get(position);
            }

            NewsFragment fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(NewsFragment.INTENT_INT_INDEX, position);
            fragment.setArguments(bundle);
            mMapFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentListPageAdapter.POSITION_NONE;
        }

    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
