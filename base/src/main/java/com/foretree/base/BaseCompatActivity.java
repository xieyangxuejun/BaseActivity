package com.foretree.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.foretree.base.compat.NavigationBarCompat;
import com.foretree.base.compat.StatusBarCompat;
import com.foretree.base.delegate.ISystemBarDelegate;
import com.foretree.base.utils.InputMethodManagerUtil;

/**
 * Project AndroidBase
 * Created by xieyangxuejun on 17-9-11.
 */

public abstract class BaseCompatActivity extends BaseDelegateActivity implements ISystemBarDelegate {
    private View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        bindSystemBar();
        super.onCreate(savedInstanceState);
        this.initIntent(getIntent());
        setContentView(getContentViewId());
        this.onAfterViews();
    }

    protected void initIntent(Intent intent) {

    }

    protected abstract @LayoutRes
    int getContentViewId();

    protected abstract void onAfterViews();

    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(this).inflate(layoutResID, null);
        this.setContentView(mContentView);
    }

    @Override
    public void setContentView(View view) {
        LinearLayoutCompat newContentView = new LinearLayoutCompat(this);
        newContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        newContentView.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT));
        newContentView.addView(view);
        super.setContentView(newContentView);

    }

    @Override
    public void bindSystemBar() {
        int statusBarColor = getStatusBarColor();
        if (statusBarColor > 0) {
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(statusBarColor));
        }
        int navigationBarColor = getNavigationBarColor();
        if (navigationBarColor > 0) {
            NavigationBarCompat.setNavigationBarColor(getWindow(), getResources().getColor(navigationBarColor));
        }
    }

    @Override
    public int getStatusBarColor() {
        return android.R.color.white;
    }

    @Override
    public int getNavigationBarColor() {
        return 0;
    }


    public View getContentView() {
        return mContentView;
    }

    @Override
    public void onBackPressed() {
        if (isTouchHideSoftInput()) {
            InputMethodManagerUtil.hideSoftInputFromWindow(this);
        }
        super.onBackPressed();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_away_from_right_in, R.anim.activity_away_from_left_out);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_away_from_left_in, R.anim.activity_away_from_right_out);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isTouchHideSoftInput()) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 是否根据触摸自动收起键盘
     *
     * @return
     */
    protected boolean isTouchHideSoftInput() {
        return false;
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public boolean isNavigationBarShow() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        Point realSize = new Point();
        display.getSize(size);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(realSize);
        }
        return realSize.y != size.y;
    }


    public int getNavigationBarHeight(Activity activity) {
        if (!isNavigationBarShow()) {
            return 0;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId);
    }

    public Activity getActivity() {
        return BaseCompatActivity.this;
    }
}