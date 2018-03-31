package com.foretree.base.compat;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.Window;

public class NavigationBarCompat {

    public static void setNavigationBarColor(Window window, @ColorInt int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(color);
        }
    }
}