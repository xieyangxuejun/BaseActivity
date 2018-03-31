package com.foretree.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseCompatFragment extends Fragment {
    private View mContentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArguments(getArguments() == null ? new Bundle() : getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getContentViewId(), container, false);
        return mContentView;
    }

    protected void initArguments(Bundle arguments) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onAfterViews();
    }

    protected abstract @LayoutRes
    int getContentViewId();

    protected abstract void onAfterViews();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public View getContentView() {
        return mContentView;
    }
}