package com.foretree.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * Project AndroidBase
 * Created by xieyangxuejun on 17-9-11.
 */

public abstract class DataBindingActivity<B extends ViewDataBinding> extends BaseCompatActivity {
    private B mBinding;

    @Override
    public void setContentView(View view) {
        mBinding = DataBindingUtil.bind(view);
        super.setContentView(mBinding.getRoot());
    }

    public B getBinding() {
        return mBinding;
    }

    public void setBinding(B b) {
        mBinding = b;
    }
}
