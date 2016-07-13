package com.thinkman.thinkutils.activity;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;

public class ThinkActivityManager {
	private static ThinkActivityManager instance;
	private static Set<Activity> activitys;

	private ThinkActivityManager() {
	}

	public static ThinkActivityManager getInstance() {
		if (instance == null)
			instance = new ThinkActivityManager();
		if (activitys == null)
			activitys = new HashSet<Activity>();
		return instance;
	}

	public void addActivity(Activity activity) {
		activitys.add(activity);
	}

	public void delectActivity(Activity activity) {
		if ( activitys.contains(activity) ) {
			activitys.remove(activity);
		}
	}

	public void finishAllActivity() {
		for (Activity activity : activitys) {
			if (activity != null)
				activity.finish();
		}
	}
}
