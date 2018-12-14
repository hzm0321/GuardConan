package cn.hzmeurasia.guardconan.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 类名: PrefUtils<br>
 * 功能:(本地xml存储工具类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/14 10:21
 */
public class PrefUtils {
    public static void putBoolean(String key, boolean value, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void putString(String key, String value, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defValue, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void putInt(String key, int value, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void remove(String key, Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }
}
