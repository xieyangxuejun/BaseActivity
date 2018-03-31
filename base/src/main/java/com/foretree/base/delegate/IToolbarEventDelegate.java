package com.foretree.base.delegate;

import android.view.View;

public interface IToolbarEventDelegate {


    void clickTitle(View v);

    void doubleClickTitle(View v);

    void clickRight(View v);

    void clickLeft(View v);
}