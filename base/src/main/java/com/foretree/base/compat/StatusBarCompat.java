package com.foretree.base.compat;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class StatusBarCompat {


    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0+
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4+
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);
        }

        //如果状态栏颜色太白就高亮
        if (color > 0xffe0e0e0) {
            setStatusBarLight(window);
        }
    }

    public static void setStatusBarColor(Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0+
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
        //如果状态栏颜色太白就高亮
        if (color > 0xffe0e0e0) {
            setStatusBarLight(window);
        }
    }

    /**
     * 设置状态栏高亮
     *
     * @param window
     */
    public static void setStatusBarLight(Window window) {
        String miuiVersionName = MIUI.getMIUIVersionName();
        if (!TextUtils.isEmpty(miuiVersionName)) {
            //MIUI
            if ("V9".equalsIgnoreCase(miuiVersionName)) {
                //MIUI9
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                MIUI.setStatusBarDarkMode(window, true);
            }

        } else {
            //其它机型
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //6.0+
                //window.setStatusBarColor(Color.BLACK);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                //6.0以下部分机
                MEIZU.setStatusBarDarkIcon(window, true);
            }
        }
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setStatusBarTranslucent(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * MIUI
     * author  dengyuhan
     * created 2017/4/7 13:03
     */
    public static class MIUI {
        public static final int FLAG_BAR = 0;
        public static final int FLAG_BAR_BLACK = 1;
        public static final int FLAG_BAR_NOBLACK = 2;
        private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";

        /**
         * @param darkmode true深色
         * @param window
         */
        public static void setStatusBarDarkMode(Window window, boolean darkmode) {
            Class<? extends Window> clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String getMIUIVersionName() {
            try {
                BuildProperties prop = BuildProperties.newInstance();
                String name = prop.getProperty(KEY_MIUI_VERSION_NAME, "");
                return name;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static boolean isMIUI() {
            try {
                BuildProperties prop = BuildProperties.newInstance();
                String name = prop.getProperty(KEY_MIUI_VERSION_NAME, "");
                return TextUtils.isEmpty(name) ? false : true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Deprecated
        private static boolean isMIUIV6() {
            try {
                final BuildProperties prop = BuildProperties.newInstance();
                String name = prop.getProperty(KEY_MIUI_VERSION_NAME, "");
                if ("V6".equals(name)) {
                    return true;
                } else {
                    return false;
                }
//            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
//                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
//                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
            } catch (final IOException e) {
                return false;
            }
        }

    }

    public static class MEIZU {

        private static Method mSetStatusBarColorIcon;
        private static Method mSetStatusBarDarkIcon;
        private static Field mStatusBarColorFiled;
        private static int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 0;

        static {
            try {
                mSetStatusBarColorIcon = Activity.class.getMethod("setStatusBarDarkIcon", int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                mSetStatusBarDarkIcon = Activity.class.getMethod("setStatusBarDarkIcon", boolean.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                mStatusBarColorFiled = WindowManager.LayoutParams.class.getField("statusBarColor");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            try {
                Field field = View.class.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR");
                SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = field.getInt(null);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断颜色是否偏黑色
         *
         * @param color 颜色
         * @param level 级别
         * @return
         */
        public static boolean isBlackColor(int color, int level) {
            int grey = toGrey(color);
            return grey < level;
        }

        /**
         * 颜色转换成灰度值
         *
         * @param rgb 颜色
         * @return　灰度值
         */
        public static int toGrey(int rgb) {
            int blue = rgb & 0x000000FF;
            int green = (rgb & 0x0000FF00) >> 8;
            int red = (rgb & 0x00FF0000) >> 16;
            return (red * 38 + green * 75 + blue * 15) >> 7;
        }

        /**
         * 设置状态栏字体图标颜色
         *
         * @param activity 当前activity
         * @param color    颜色
         */
        public static void setStatusBarDarkIcon(Activity activity, int color) {
            if (mSetStatusBarColorIcon != null) {
                try {
                    mSetStatusBarColorIcon.invoke(activity, color);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                boolean whiteColor = isBlackColor(color, 50);
                if (mStatusBarColorFiled != null) {
                    setStatusBarDarkIcon(activity, whiteColor, whiteColor);
                    setStatusBarDarkIcon(activity.getWindow(), color);
                } else {
                    setStatusBarDarkIcon(activity, whiteColor);
                }
            }
        }

        /**
         * 设置状态栏字体图标颜色(只限全屏非activity情况)
         *
         * @param window 当前窗口
         * @param color  颜色
         */
        public static void setStatusBarDarkIcon(Window window, int color) {
            try {
                setStatusBarColor(window, color);
                if (Build.VERSION.SDK_INT > 22) {
                    setStatusBarDarkIcon(window.getDecorView(), true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 设置状态栏字体图标颜色
         *
         * @param activity 当前activity
         * @param dark     是否深色 true为深色 false 为白色
         */
        public static void setStatusBarDarkIcon(Activity activity, boolean dark) {
            if (!"Meizu".equals(Build.BRAND)) return;
            setStatusBarDarkIcon(activity, dark, true);
        }

        private static boolean changeMeizuFlag(WindowManager.LayoutParams winParams, String flagName, boolean on) {
            try {
                Field f = winParams.getClass().getDeclaredField(flagName);
                f.setAccessible(true);
                int bits = f.getInt(winParams);
                Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
                f2.setAccessible(true);
                int meizuFlags = f2.getInt(winParams);
                int oldFlags = meizuFlags;
                if (on) {
                    meizuFlags |= bits;
                } else {
                    meizuFlags &= ~bits;
                }
                if (oldFlags != meizuFlags) {
                    f2.setInt(winParams, meizuFlags);
                    return true;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return false;
        }

        /**
         * 设置状态栏颜色
         *
         * @param view
         * @param dark
         */
        private static void setStatusBarDarkIcon(View view, boolean dark) {
            int oldVis = view.getSystemUiVisibility();
            int newVis = oldVis;
            if (dark) {
                newVis |= SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                newVis &= ~SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (newVis != oldVis) {
                view.setSystemUiVisibility(newVis);
            }
        }

        /**
         * 设置状态栏颜色
         *
         * @param window
         * @param color
         */
        private static void setStatusBarColor(Window window, int color) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            if (mStatusBarColorFiled != null) {
                try {
                    int oldColor = mStatusBarColorFiled.getInt(winParams);
                    if (oldColor != color) {
                        mStatusBarColorFiled.set(winParams, color);
                        window.setAttributes(winParams);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 设置状态栏字体图标颜色(只限全屏非activity情况)
         *
         * @param window 当前窗口
         * @param dark   是否深色 true为深色 false 为白色
         */
        public static void setStatusBarDarkIcon(Window window, boolean dark) {
            if (Build.VERSION.SDK_INT < 23) {
                changeMeizuFlag(window.getAttributes(), "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark);
            } else {
                View decorView = window.getDecorView();
                if (decorView != null) {
                    setStatusBarDarkIcon(decorView, dark);
                    setStatusBarColor(window, 0);
                }
            }
        }

        private static void setStatusBarDarkIcon(Activity activity, boolean dark, boolean flag) {
            if (mSetStatusBarDarkIcon != null) {
                try {
                    mSetStatusBarDarkIcon.invoke(activity, dark);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                if (flag) {
                    setStatusBarDarkIcon(activity.getWindow(), dark);
                }
            }
        }
    }

    /**
     * @author dengyuhan
     * @desc
     * @create 2015/9/8 10:27
     */
    public static class BuildProperties {
        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

    }
}
