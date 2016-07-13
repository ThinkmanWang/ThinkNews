/*
Copyright 2014 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.thinkman.thinkviewpagerindicator.view.indicator;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.thinkman.thinkviewpagerindicator.view.indicator.slidebar.ScrollBar;
import com.thinkman.thinkviewpagerindicator.view.viewpager.RecyclingPagerAdapter;
import com.thinkman.thinkviewpagerindicator.view.viewpager.SViewPager;

public class IndicatorViewPager {
	private Indicator indicatorView;
	private ViewPager viewPager;
	private IndicatorPagerAdapter adapter;
	private OnIndicatorPageChangeListener onIndicatorPageChangeListener;

	public IndicatorViewPager(Indicator indicator, ViewPager viewPager) {
		super();
		this.indicatorView = indicator;
		this.viewPager = viewPager;
		viewPager.setOnPageChangeListener(onPageChangeListener);
		indicatorView.setOnItemSelectListener(onItemSelectedListener);
	}

	public void setAdapter(IndicatorPagerAdapter adapter) {
		this.adapter = adapter;
		viewPager.setAdapter(adapter.getPagerAdapter());
		indicatorView.setAdapter(adapter.getIndicatorAdapter());
	}

	public void setCurrentItem(int item, boolean anim) {
		viewPager.setCurrentItem(item, anim);
		indicatorView.setCurrentItem(item, anim);
	}

	public void setOnIndicatorPageChangeListener(OnIndicatorPageChangeListener onIndicatorPageChangeListener) {
		this.onIndicatorPageChangeListener = onIndicatorPageChangeListener;
	}

	public void setIndicatorOnTransitionListener(Indicator.OnTransitionListener onTransitionListener) {
		indicatorView.setOnTransitionListener(onTransitionListener);
	}

	public void setIndicatorScrollBar(ScrollBar scrollBar) {
		indicatorView.setScrollBar(scrollBar);
	}

	public void setPageOffscreenLimit(int limit) {
		viewPager.setOffscreenPageLimit(limit);
	}

	public void setPageMargin(int marginPixels) {
		viewPager.setPageMargin(marginPixels);
	}

	public void setPageMarginDrawable(Drawable d) {
		viewPager.setPageMarginDrawable(d);
	}

	public void setPageMarginDrawable(int resId) {
		viewPager.setPageMarginDrawable(resId);
	}

	public int getPreSelectItem() {
		return indicatorView.getPreSelectItem();
	}

	public int getCurrentItem() {
		return viewPager.getCurrentItem();
	}

	public IndicatorPagerAdapter getAdapter() {
		return this.adapter;
	}

	public OnIndicatorPageChangeListener getOnIndicatorPageChangeListener() {
		return onIndicatorPageChangeListener;
	}

	public Indicator getIndicatorView() {
		return indicatorView;
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void notifyDataSetChanged() {
		if (this.adapter != null) {
			this.adapter.notifyDataSetChanged();
		}
	}

	private Indicator.OnItemSelectedListener onItemSelectedListener = new Indicator.OnItemSelectedListener() {

		@Override
		public void onItemSelected(View selectItemView, int select, int preSelect) {
			if (viewPager instanceof SViewPager) {
				viewPager.setCurrentItem(select, ((SViewPager) viewPager).isCanScroll());
			} else {
				viewPager.setCurrentItem(select, true);
			}
			// if (onIndicatorPageChangeListener != null) {
			// onIndicatorPageChangeListener.onIndicatorPageChange(preSelect,
			// select);
			// }
		}
	};

	public static interface OnIndicatorPageChangeListener {

		public void onIndicatorPageChange(int preItem, int currentItem);
	}

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			indicatorView.setCurrentItem(position, true);
			if (onIndicatorPageChangeListener != null) {
				onIndicatorPageChangeListener.onIndicatorPageChange(indicatorView.getPreSelectItem(), position);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	};

	public static interface IndicatorPagerAdapter {

		PagerAdapter getPagerAdapter();

		Indicator.IndicatorAdapter getIndicatorAdapter();

		void notifyDataSetChanged();

	}

	public static abstract class IndicatorViewPagerAdapter implements IndicatorPagerAdapter {

		private Indicator.IndicatorAdapter indicatorAdapter = new Indicator.IndicatorAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return IndicatorViewPagerAdapter.this.getViewForTab(position, convertView, parent);
			}

			@Override
			public int getCount() {
				return IndicatorViewPagerAdapter.this.getCount();
			}
		};

		private RecyclingPagerAdapter pagerAdapter = new RecyclingPagerAdapter() {

			@Override
			public int getCount() {
				return IndicatorViewPagerAdapter.this.getCount();
			}

			@Override
			public View getView(int position, View convertView, ViewGroup container) {
				return IndicatorViewPagerAdapter.this.getViewForPage(position, convertView, container);
			}

			@Override
			public float getPageWidth(int position) {
				return IndicatorViewPagerAdapter.this.getPageRatio(position);
			}

			@Override
			public int getItemPosition(Object object) {
				return IndicatorViewPagerAdapter.this.getItemPosition(object);
			}
		};

		public int getItemPosition(Object object) {
			return RecyclingPagerAdapter.POSITION_UNCHANGED;
		}

		public abstract int getCount();

		public abstract View getViewForTab(int position, View convertView, ViewGroup container);

		public abstract View getViewForPage(int position, View convertView, ViewGroup container);

		public float getPageRatio(int position) {
			return 1f;
		}

		@Override
		public void notifyDataSetChanged() {
			indicatorAdapter.notifyDataSetChanged();
			pagerAdapter.notifyDataSetChanged();
		}

		@Override
		public PagerAdapter getPagerAdapter() {
			return pagerAdapter;
		}

		@Override
		public Indicator.IndicatorAdapter getIndicatorAdapter() {
			return indicatorAdapter;
		}

	}

	public static abstract class IndicatorFragmentPagerAdapter implements IndicatorPagerAdapter {
		private FragmentListPageAdapter pagerAdapter;

		public IndicatorFragmentPagerAdapter(FragmentManager fragmentManager) {
			super();
			pagerAdapter = new FragmentListPageAdapter(fragmentManager) {

				@Override
				public int getCount() {
					return IndicatorFragmentPagerAdapter.this.getCount();
				}

				@Override
				public Fragment getItem(int position) {
					return IndicatorFragmentPagerAdapter.this.getFragmentForPage(position);
				}

				@Override
				public float getPageWidth(int position) {
					return IndicatorFragmentPagerAdapter.this.getPageRatio(position);
				}

				@Override
				public int getItemPosition(Object object) {
					return IndicatorFragmentPagerAdapter.this.getItemPosition(object);
				}
			};
		}

		private Indicator.IndicatorAdapter indicatorAdapter = new Indicator.IndicatorAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return IndicatorFragmentPagerAdapter.this.getViewForTab(position, convertView, parent);
			}

			@Override
			public int getCount() {
				return IndicatorFragmentPagerAdapter.this.getCount();
			}

		};

		public int getItemPosition(Object object) {
			return FragmentListPageAdapter.POSITION_UNCHANGED;
		}

		public Fragment getExitFragment(int position) {
			return pagerAdapter.getExitFragment(position);
		}

		public Fragment getCurrentFragment() {
			return pagerAdapter.getCurrentFragment();
		}

		public abstract int getCount();

		public abstract View getViewForTab(int position, View convertView, ViewGroup container);

		public abstract Fragment getFragmentForPage(int position);

		public float getPageRatio(int position) {
			return 1f;
		}

		@Override
		public void notifyDataSetChanged() {
			indicatorAdapter.notifyDataSetChanged();
			pagerAdapter.notifyDataSetChanged();
			;
		}

		@Override
		public PagerAdapter getPagerAdapter() {
			return pagerAdapter;
		}

		@Override
		public Indicator.IndicatorAdapter getIndicatorAdapter() {
			return indicatorAdapter;
		}

	}
}
