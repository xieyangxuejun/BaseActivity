package com.foretree.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.foretree.base.delegate.ActivityDelegateImpl;
import com.foretree.base.delegate.IActivityDelegate;


/**
 * Project AndroidBase
 * Created by xieyangxuejun on 17-9-11.
 */

public class BaseDelegateActivity<D extends IActivityDelegate> extends AppCompatActivity {
    private D mDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        get().onCreateBefore(savedInstanceState);
        super.onCreate(savedInstanceState);
        get().onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        get().onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        get().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        get().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        get().onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        get().onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        get().finish();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
        get().overridePendingTransition(enterAnim,exitAnim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        get().onActivityResult(requestCode, resultCode, data);
    }


    public D get() {
        if (mDelegate == null) {
            mDelegate = createDelegate();
        }
        return mDelegate;
    }

    public D createDelegate(){
        return (D) new ActivityDelegateImpl(this);
    }
}
