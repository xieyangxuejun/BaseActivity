package com.foretree.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Project AndroidBase
 * Created by xieyangxuejun on 17-9-11.
 */
public class DataBindingRecyclerHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public B binding;

    public DataBindingRecyclerHolder(B binding) {
        this(binding.getRoot());
        this.binding = binding;
    }


    public DataBindingRecyclerHolder(View v) {
        super(v);
    }
}
