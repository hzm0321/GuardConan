package cn.hzmeurasia.guardconan.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hzmeurasia.guardconan.entity.AppManager;

/**
 * 类名: AppInfoParser<br>
 * 功能:(TODO用一句话描述该类的功能)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 16:39
 */
public class AppInfoParser {
    /**
     * 获取手机里面的所有的应用程序
     * @param context 上下文
     * @return
     */
    public static List<AppManager> getAppManagers(Context context){
        //得到一个java保证的 包管理器。
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packInfos = pm.getInstalledPackages(0);
        List<AppManager> AppManagers = new ArrayList<AppManager>();
        for(PackageInfo packInfo:packInfos){
            AppManager AppManager = new AppManager();
            String packname = packInfo.packageName;
            AppManager.packageName = packname;
            Drawable icon = packInfo.applicationInfo.loadIcon(pm);
            AppManager. icon = icon;
            String appname = packInfo.applicationInfo.loadLabel(pm).toString();
            AppManager.appName = appname;
            //应用程序apk包的路径
            String apkpath = packInfo.applicationInfo.sourceDir;
            AppManager.apkPath = apkpath;
            File file = new File(apkpath);
            long appSize = file.length();
            AppManager.appSize = appSize;
            //应用程序安装的位置。
            //二进制映射  大bit-map
            int flags = packInfo.applicationInfo.flags;
            if((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags)!=0){
                //外部存储
                AppManager.isInRoom = false;
            }else{
                //手机内存
                AppManager.isInRoom = true;
            }
            if((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
                //系统应用
                AppManager.isUserApp = false;
            }else{
                //用户应用
                AppManager.isUserApp = true;
            }
            AppManagers.add(AppManager);
            AppManager = null;
        }
        return AppManagers;
    }
}
