package com.foretree.base.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.foretree.base.manager.ActivityManager;

/**
 * Project AndroidBase
 * Created by xieyangxuejun on 17-9-11.
 */

public class ActivityDelegateImpl implements IActivityDelegate {
    private AppCompatActivity mActivity;

    public ActivityDelegateImpl(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onCreateBefore(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (mActivity != null) {
            ActivityManager.addActivity(mActivity);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void finish() {
        if (mActivity != null) {
            ActivityManager.removeActivity(mActivity);
        }
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }
}
