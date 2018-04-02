package com.foretree.base.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * author  xieyangxuejun
 */
public abstract class DataBindingLinearLayout<B extends ViewDataBinding> extends LinearLayout {

    protected B mBinding;

    public DataBindingLinearLayout(Context context) {
        this(context, null);
    }

    public DataBindingLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataBindingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        onAfterViews(context);
    }

    protected void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        /*View inflate = View.inflate(context, getLayoutId(), this);
        mBinding = DataBindingUtil.bind(inflate);*/
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, true);
    }

    protected abstract int getLayoutId();

    protected abstract void onAfterViews(Context context);

}
