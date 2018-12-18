package cn.hzmeurasia.guardconan.entity;

import android.graphics.drawable.Drawable;

/**
 * 类名: TaskInfo<br>
 * 功能:(正在运行的App的信息)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/18 14:37
 */
public class TaskInfo {
    public String appName;
    public long appMemory;
    /**用来标记app是否被选中*/
    public boolean isChecked;
    public Drawable appIcon;
    public boolean isUserApp;
    public String  packageName;
}
