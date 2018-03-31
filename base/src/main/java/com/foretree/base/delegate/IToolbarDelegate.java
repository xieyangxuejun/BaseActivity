package com.foretree.base.delegate;

import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;

public interface IToolbarDelegate {

    View getToolbarView(ViewGroup parent);

    void bindToolbarView(View toolbarView);

    void bindLeftView(View parent);

    void bindTitleView(View parent);

    void bindRightView(View parent);

    void setLeftViewStyle(CharSequence text, @DrawableRes int icoRes);

    void setToolbarTitle(CharSequence title);

    void setTitleBackground(@DrawableRes int background);

    void setRightViewStyle(CharSequence text, @DrawableRes int icoRes);

    View getLeftView();

    View getRightView();
}