package com.foretree.base.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * author  xieyangxuejun
 */
public abstract class DataBindingFrameLayout<B extends ViewDataBinding> extends FrameLayout {

    private B mBinding;

    public DataBindingFrameLayout(Context context) {
        this(context, null);
    }

    public DataBindingFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataBindingFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayoutParams();
        initView(context);
        onAfterViews(context);
    }

    protected void initLayoutParams() {
    }

    protected void initView(Context context) {
        /*View inflate = View.inflate(context, getLayoutId(), this);
        mBinding = DataBindingUtil.bind(inflate);*/
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, true);
    }

    protected abstract int getLayoutId();

    protected abstract void onAfterViews(Context context);

    public B getBinding() {
        return mBinding;
    }

}
