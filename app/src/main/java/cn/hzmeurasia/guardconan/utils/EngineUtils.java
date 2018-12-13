package cn.hzmeurasia.guardconan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import cn.hzmeurasia.guardconan.MyApplication;
import cn.hzmeurasia.guardconan.entity.AppManager;

/**
 * 类名: EngineUtils<br>
 * 功能:(业务工具类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 16:43
 */
public class EngineUtils {
    /**
     * 分享应用
     */
    public static void shareApplication(Context context, AppManager AppManager) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                "推荐您使用一款软件，名称叫：" + AppManager.appName
                        + "下载路径：https://play.google.com/store/apps/details?id="
                        + AppManager.packageName);
        context.startActivity(intent);
    }

    /**
     * 开启应用程序
     */
    public static void startApplication(Context context,AppManager AppManager) {
        // 打开这个应用程序的入口activity。
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(AppManager.packageName);
        if (intent != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "该应用没有启动界面", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 开启应用设置页面
     */
    public static  void SettingAppDetail(Context context,AppManager AppManager) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // dat=package:com.itheima.mobileguard
        intent.setData(Uri.parse("package:" + AppManager.packageName));
        context.startActivity(intent);

    }

    /**卸载应用*/
    public static  void uninstallApplication(Context context,AppManager AppManager) {
        if (AppManager.isUserApp) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + AppManager.packageName));
            context.startActivity(intent);
        }else{
            Toast.makeText(MyApplication.getContext(), "您没有权限", Toast.LENGTH_SHORT).show();

        }
    }
}
