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

import android.view.View;
import android.view.ViewGroup;

import com.thinkman.thinkviewpagerindicator.view.indicator.slidebar.ScrollBar;

import java.util.LinkedHashSet;
import java.util.Set;


public interface Indicator {

	public void setAdapter(IndicatorAdapter adapter);

	public void setOnItemSelectListener(OnItemSelectedListener onItemSelectedListener);

	public OnItemSelectedListener getOnItemSelectListener();

	public void setOnTransitionListener(OnTransitionListener onTransitionListener);

	public OnTransitionListener getOnTransitionListener();

	public void setScrollBar(ScrollBar scrollBar);

	public IndicatorAdapter getAdapter();

	public void setCurrentItem(int item);

	public void setCurrentItem(int item, boolean anim);

	public View getItemView(int item);

	public int getCurrentItem();

	public int getPreSelectItem();

	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

	public static abstract class IndicatorAdapter {
		private Set<DataSetObserver> observers = new LinkedHashSet<DataSetObserver>(2);

		public abstract int getCount();

		public abstract View getView(int position, View convertView, ViewGroup parent);

		public void notifyDataSetChanged() {
			for (DataSetObserver dataSetObserver : observers) {
				dataSetObserver.onChange();
			}
		}

		public void registDataSetObserver(DataSetObserver observer) {
			observers.add(observer);
		}

		public void unRegistDataSetObserver(DataSetObserver observer) {
			observers.remove(observer);
		}

	}

	static interface DataSetObserver {
		public void onChange();
	}

	public static interface OnItemSelectedListener {

		public void onItemSelected(View selectItemView, int select, int preSelect);
	}

	public static interface OnTransitionListener {
		public void onTransition(View view, int position, float selectPercent);
	}
}
