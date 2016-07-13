package com.thinkman.thinkutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thinkman.thinkutils.activity.BaseActivityWithTranslucentBar;
import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkviewpagerindicator.view.indicator.Indicator;
import com.thinkman.thinkviewpagerindicator.view.indicator.IndicatorViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivityWithTranslucentBar {

    private IndicatorViewPager indicatorViewPager = null;
    private LayoutInflater inflate;

    @BindView(R.id.guide_viewPager)
    ViewPager viewPager;

    @BindView(R.id.guide_indicator)
    Indicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        ButterKnife.bind(this);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());
        indicatorViewPager.setAdapter(adapter);
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {
        private int[] images = { R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4 };

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_guide, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new ImageView(getApplicationContext());
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            convertView.setBackgroundResource(images[position]);
            ((ImageView)convertView).setScaleType(ImageView.ScaleType.FIT_XY);

            if (3 == position) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return images.length;
        }
    };

}
