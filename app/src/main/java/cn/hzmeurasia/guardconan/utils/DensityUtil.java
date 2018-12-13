package cn.hzmeurasia.guardconan.utils;

import android.content.Context;

/**
 * 类名: DensityUtil<br>
 * 功能:(dp与px互转的工具类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 16:42
 */
public class DensityUtil {
    /**
     * dip转换像素px
     */
    public static int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) dpValue;
    }

    /**
     * 像素px转换为dip
     */
    public static int px2dip(Context context, float pxValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) pxValue;
    }
}
