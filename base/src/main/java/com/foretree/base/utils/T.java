package com.foretree.base.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Toast;

public final class T {
    private static Toast instance;

    private T() {

    }

    /*public static Toast getInstance(Context context) {
        if (instance == null) {
            instance = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
        return instance;
    }*/
    public static Toast getInstance(Context context) {
        if (instance != null) {
            instance.cancel();
        }
        instance = Toast.makeText(context, "", Toast.LENGTH_LONG);
        return instance;
    }

    /**
     * 较长时间的吐司
     *
     * @param context
     * @param res     string资源id
     */
    public static void showToastLong(Context context, int res) {
        showToastLong(context, context.getText(res));
    }

    /**
     * 较短时间的吐司
     *
     * @param context
     * @param res     string资源id
     * @desc
     */
    public static void showToastShort(Context context, int res) {
        showToastShort(context, context.getText(res));
    }

    /**
     * 较长时间的吐司
     *
     * @param context
     * @param text
     * @desc
     */
    public static void showToastLong(Context context, CharSequence text) {
        try {
            Toast toast = getInstance(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(text);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 较短时间的吐司
     *
     * @param context
     * @param text
     * @desc
     */
    public static void showToastShort(Context context, CharSequence text) {
        Toast toast = getInstance(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();
    }


    /**
     * 指定位置吐司
     *
     * @param context
     * @param gravity
     * @param text
     * @desc
     */
    public static void showToastGravity(Context context, CharSequence text, int yOffset, int gravity) {
        Toast toast = getInstance(context);
        toast.setText(text);
        toast.setGravity(gravity, 0, yOffset);
        toast.show();
    }
    /**
     * 指定位置吐司
     *
     * @param context
     * @param gravity
     * @param text
     * @desc
     */
    public static void showToastGravity(Context context, CharSequence text, int gravity) {
        Toast toast = getInstance(context);
        toast.setText(text);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 显示自定义View
     *
     * @param context
     * @param v
     * @param gravity
     */
    public static void showToastCustom(Context context, View v, int gravity) {
        Toast toast = getInstance(context);
        toast.setView(v);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }


    /**
     * 常用土司
     *
     * @param context
     * @param text
     */
    public static void showToastCommon(Context context, CharSequence text) {
        showToastLong(context, text);
        //showToastCommon(context, text, GRAVITY_DEFAULT);
    }

    /**
     * 常用土司
     *
     * @param context
     * @param res
     */
    public static void showToastCommon(Context context, @StringRes int res) {
        showToastLong(context, res);
        //showToastCommon(context, text, GRAVITY_DEFAULT);
    }


    /**
     * 常规的吐司
     *
     * @param context
     * @desc
     */
    public static void showToastCommon(Context context, View v, int gravity) {
        Toast toast = getInstance(context);
        if (gravity != -1) {
            toast.setGravity(gravity, 0, 0);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(v);
        toast.show();
    }


    public static int GRAVITY_DEFAULT = -1;


    public static void cancel(Context context) {
        getInstance(context).cancel();
    }

}