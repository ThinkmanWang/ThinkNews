package com.thinkman.thinkviewpagerindicator.view.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.thinkman.thinkviewpagerindicator.view.indicator.slidebar.ScrollBar;


public class ScrollIndicatorView extends HorizontalScrollView implements Indicator {
	private SFixedIndicatorView fixedIndicatorView;

	public ScrollIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		fixedIndicatorView = new SFixedIndicatorView(context);
		addView(fixedIndicatorView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		setHorizontalScrollBarEnabled(false);
		setSplitAuto(true);
	}

	public void setSplitAuto(boolean splitAuto) {
		setFillViewport(splitAuto);
		fixedIndicatorView.setSplitAuto(splitAuto);
	}

	public boolean isSplitAuto() {
		return fixedIndicatorView.isSplitAuto();
	}

	@Override
	public void setAdapter(IndicatorAdapter adapter) {
		if (getAdapter() != null) {
			getAdapter().unRegistDataSetObserver(dataSetObserver);
		}
		fixedIndicatorView.setAdapter(adapter);
		adapter.registDataSetObserver(dataSetObserver);
	}

	@Override
	public void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener) {
		fixedIndicatorView.setOnItemSelectListener(onItemSelectedListener);
	}

	@Override
	public IndicatorAdapter getAdapter() {
		return fixedIndicatorView.getAdapter();
	}

	private DataSetObserver dataSetObserver = new DataSetObserver() {

		@Override
		public void onChange() {
			if (mTabSelector != null) {
				removeCallbacks(mTabSelector);
			}
			positionOffset = 0;
			setCurrentItem(fixedIndicatorView.getCurrentItem(), false);
		}
	};

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTabSelector != null) {
			post(mTabSelector);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
	}

	private Runnable mTabSelector;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (fixedIndicatorView.getCount() > 0) {
			animateToTab(fixedIndicatorView.getCurrentItem());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (unScrollPosition != -1) {
			final View tabView = fixedIndicatorView.getChildAt(unScrollPosition);
			if (tabView != null) {
				final int scrollPos = tabView.getLeft() - (getMeasuredWidth() - tabView.getMeasuredWidth()) / 2;
				if (scrollPos >= 0) {
					smoothScrollTo(scrollPos, 0);
					unScrollPosition = -1;
				}
			}
		}
	}

	private void animateToTab(final int position) {
		if (position < 0 || position > fixedIndicatorView.getCount() - 1) {
			return;
		}
		final View tabView = fixedIndicatorView.getChildAt(position);
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
		mTabSelector = new Runnable() {
			public void run() {
				final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mTabSelector = null;
			}
		};
		post(mTabSelector);
	}

	@Override
	public void setCurrentItem(int item) {
		setCurrentItem(item, true);
	}

	@Override
	public void setCurrentItem(int item, boolean anim) {
		int count = fixedIndicatorView.getCount();
		if (count == 0) {
			return;
		}
		if (item < 0) {
			item = 0;
		} else if (item > count - 1) {
			item = count - 1;
		}
		unScrollPosition = -1;
		if (positionOffset < 0.02f || positionOffset > 0.98f) {
			if (anim) {
				animateToTab(item);
			} else {
				final View tabView = fixedIndicatorView.getChildAt(item);
				final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
				if (scrollPos >= 0) {
					scrollTo(scrollPos, 0);
				} else {
					unScrollPosition = item;
				}
			}
		}
		fixedIndicatorView.setCurrentItem(item, anim);
	}

	private int unScrollPosition = -1;

	@Override
	public int getCurrentItem() {
		return fixedIndicatorView.getCurrentItem();
	}

	@Override
	public OnItemSelectedListener getOnItemSelectListener() {
		return fixedIndicatorView.getOnItemSelectListener();
	}

	@Override
	public void setOnTransitionListener(OnTransitionListener onPageScrollListener) {
		fixedIndicatorView.setOnTransitionListener(onPageScrollListener);
	}

	@Override
	public OnTransitionListener getOnTransitionListener() {
		return fixedIndicatorView.getOnTransitionListener();
	}

	@Override
	public void setScrollBar(ScrollBar scrollBar) {
		fixedIndicatorView.setScrollBar(scrollBar);
	}

	private float positionOffset;

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		this.positionOffset = positionOffset;
		final View tabView = fixedIndicatorView.getChildAt(position);
		if (tabView == null) {
			return;
		}
		final View tabView2 = fixedIndicatorView.getChildAt(position + 1);
		float offset = (tabView.getWidth() + (tabView2 == null ? tabView.getWidth() : tabView2.getWidth())) / 2 * positionOffset;
		final int scrollPos = (int) (tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2 + offset);
		if (scrollPos >= 0) {
			scrollTo(scrollPos, 0);
		}
		fixedIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
	}

	@Override
	public int getPreSelectItem() {
		return fixedIndicatorView.getPreSelectItem();
	}

	@Override
	public View getItemView(int item) {
		return fixedIndicatorView.getItemView(item);
	}

	private static class SFixedIndicatorView extends FixedIndicatorView {

		private boolean isAutoSplit;

		public SFixedIndicatorView(Context context) {
			super(context);
		}

		public boolean isSplitAuto() {
			return isAutoSplit;
		}

		public void setSplitAuto(boolean isAutoSplit) {
			if (this.isAutoSplit != isAutoSplit) {
				this.isAutoSplit = isAutoSplit;
				if (!isAutoSplit) {
					setSplitMethod(SPLITMETHOD_WRAP);
				}
				requestLayout();
				invalidate();
			}
		}

		@Override
		protected void onMeasure(int widthSpec, int heightSpec) {
			if (isAutoSplit) {
				ScrollIndicatorView group = (ScrollIndicatorView) getParent();
				int layoutWidth = group.getMeasuredWidth();
				if (layoutWidth != 0) {
					int totalWidth = 0;
					int count = getChildCount();
					int maxCellWidth = 0;
					for (int i = 0; i < count; i++) {
						int width = measureChildWidth(getChildAt(i), widthSpec, heightSpec);
						maxCellWidth = maxCellWidth < width ? width : maxCellWidth;
						totalWidth += width;
					}

					if (totalWidth > layoutWidth) {
						group.setFillViewport(false);
						setSplitMethod(SPLITMETHOD_WRAP);
					} else if (maxCellWidth * count > layoutWidth) {
						group.setFillViewport(true);
						setSplitMethod(SPLITMETHOD_WEIGHT);
					} else {
						group.setFillViewport(true);
						setSplitMethod(SPLITMETHOD_EQUALS);
					}
				}
			}
			super.onMeasure(widthSpec, heightSpec);
		}

		private int measureChildWidth(View view, int widthSpec, int heightSpec) {
			LayoutParams p = (LayoutParams) view.getLayoutParams();
			int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, getPaddingLeft() + getPaddingRight(), FrameLayout.LayoutParams.WRAP_CONTENT);
			int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, getPaddingTop() + getPaddingBottom(), p.height);
			view.measure(childWidthSpec, childHeightSpec);
			return view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
		}

	}
}
