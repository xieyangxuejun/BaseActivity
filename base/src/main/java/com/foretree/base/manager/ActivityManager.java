package com.foretree.base.manager;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ActivityManager {
    /**
	 * activity的容器map
	 */
	private static Map<String, Activity> activities = new HashMap<>();

	/**
	 * 添加activity到容器
	 * @desc 
	 * @param activity
	 */
	public static void addActivity(Activity activity) {
		activities.put(activity.getClass().getName(), activity);
	}
	
	/**
	 * 从容器移除activity
	 * @desc 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		activities.remove(activity.getClass().getName());
	}

	/**
	 * 销毁activity并从容器中移除
	 * @desc 
	 * @param clazz
	 */
	public static void finishActivity(Class... clazz) {
		for (Class c : clazz) {
			finishActivity(c);
		}
	}

	/**
	 * 销毁单个activity并从容器中移除
	 * @desc
	 * @param clazz
	 */
	public static void finishActivity(Class clazz) {
		finishActivity(clazz.getName());
	}

	public static void finishActivity(String className) {
		Activity activity = activities.get(className);
		if (activity != null) {
			activity.finish();
			activities.remove(className);
		}
	}

	/**
	 * 销毁容器中所有activity并从容器中移除
	 * @desc 
	 */
	public static void finishAll() {
		for (Map.Entry<String,Activity> entry:activities.entrySet()) {
			Activity activity = entry.getValue();
			if (activity!=null){
				activity.finish();
			}
		}
		activities.clear();
	}

	/**
	 * 销毁容器中参数以外的所有Activity
	 * @desc
	 */
	public static void finishAllByOutside(Class outsideClass) {
		Iterator<Map.Entry<String, Activity>> iterator = activities.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Activity> entry=iterator.next();
			if (!entry.getKey().equals(outsideClass.getName())){
				entry.getValue().finish();
				iterator.remove();
			}
		}
	}
	
	/**
	 * 是否已打开该activity|容器中是否有该activity
	 * @desc 
	 * @param clazz
	 * @return
	 */
	public static boolean containsActivity(Class clazz) {
		return activities.containsKey(clazz.getName());
	}

	public static Map<String, Activity> getActivities() {
		return activities;
	}

	public static int getActivityCount() {
		return activities.size();
	}

	public static boolean isContainsActivity(Class clazz) {
		return activities.containsKey(clazz.getName());
	}

}