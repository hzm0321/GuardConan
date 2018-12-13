package cn.hzmeurasia.guardconan.entity;

import android.graphics.drawable.Drawable;

/**
 * 类名: AppManager<br>
 * 功能:(软件管理实体类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 15:35
 */
public class AppManager {

    /** 应用程序包名 */
    public String packageName;
    /** 应用程序图标 */
    public Drawable icon;
    /** 应用程序名称 */
    public String appName;
    /** 应用程序路径 */
    public String apkPath;
    /** 应用程序大小 */
    public long appSize;
    /** 是否是手机存储 */
    public boolean isInRoom;
    /** 是否是用户应用 */
    public boolean isUserApp;
    /** 是否选中，默认都为false */
    public boolean isSelected = false;

    /**拿到App位置字符串*/
    public String getAppLocation(boolean isInRoom) {
        if (isInRoom) {
            return "手机内存";
        } else {
            return "外部存储";
        }
    }

}
