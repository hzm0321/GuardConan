package cn.hzmeurasia.guardconan.entity;

import android.graphics.drawable.Drawable;

/**
 * 类名: AppMessage<br>
 * 功能:(程序锁实体类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/20 15:17
 */
public class AppMessage {
    /** 应用程序包名 */
    public String packageName;
    /** 应用程序图标 */
    public Drawable icon;
    /** 应用程序名称 */
    public String appName;
    /** 应用程序路径 */
    public String apkPath;
    /**应用程序是否加锁*/
    public boolean isLock;
}
