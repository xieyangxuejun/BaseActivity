package com.foretree.base.delegate;

import android.support.annotation.ColorRes;

public interface ISystemBarDelegate {

    void bindSystemBar();

    @ColorRes int getStatusBarColor();

    @ColorRes int getNavigationBarColor();
}