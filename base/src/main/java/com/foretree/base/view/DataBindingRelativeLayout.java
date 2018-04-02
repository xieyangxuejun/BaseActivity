package com.foretree.base.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * author  xieyangxuejun
 */
public abstract class DataBindingRelativeLayout<B extends ViewDataBinding> extends RelativeLayout {

    protected B mBinding;

    public DataBindingRelativeLayout(Context context) {
        this(context, null);
    }

    public DataBindingRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataBindingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        onAfterViews(context);
    }

    protected void initView(Context context) {
        /*View inflate = View.inflate(context, getLayoutId(), this);
        mBinding = DataBindingUtil.bind(inflate);*/
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, true);
    }

    protected abstract int getLayoutId();

    protected abstract void onAfterViews(Context context);

}
