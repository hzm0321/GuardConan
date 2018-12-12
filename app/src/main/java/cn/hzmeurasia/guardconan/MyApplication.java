package cn.hzmeurasia.guardconan;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * 类名: MyApplication<br>
 * 功能:(全局Application)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/11 17:22
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //创建本地数据库
        LitePal.initialize(context);
        LitePal.getDatabase();
    }

    public static Context getContext() {
        return context;
    }
}
