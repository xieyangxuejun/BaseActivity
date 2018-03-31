package com.foretree.baseactivity;

import com.foretree.base.DataBindingActivity;
import com.foretree.baseactivity.databinding.ActivityMainBinding;

public class MainActivity extends DataBindingActivity<ActivityMainBinding> {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onAfterViews() {
        getBinding().text.setText("hahahahahahha");
    }
}
