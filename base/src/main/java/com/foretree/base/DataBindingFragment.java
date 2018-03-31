package com.foretree.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class DataBindingFragment<B extends ViewDataBinding> extends BaseCompatFragment {
    protected B mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public View getContentView() {
        return mBinding.getRoot();
    }
}