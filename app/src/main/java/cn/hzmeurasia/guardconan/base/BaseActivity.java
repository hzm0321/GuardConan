package cn.hzmeurasia.guardconan.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 类名: BaseActivity<br>
 * 功能:(Activity基类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/10 10:33
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG_LOG = "123123";
    public static final String PREF_AUTO_UPDATE = "auto_update";
    public static final String PREF_PASSWORD = "password";
    public static final String PREF_CONFIG = "config";
    public static final String PREF_BIND_SIM = "bind_sim";
    public static final String PREF_PHONE_NUMBER = "phone_number";
    public static final String PREF_IS_PROTECT = "is_protect";
    public static final String PREF_IS_BLACK_NUMBER = "is_black_number";
    public static final String PREF_IS_APP_LOCK = "is_app_lock";
    public static final String PREF_ADDRESS_STYLE = "address_style";
    public static final String PREF_LAST_X = "lastX";
    public static final String PREF_LAST_Y = "lastY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initVariables();
        initViews(savedInstanceState);
        loadData();

    }

    /**
     * 加载数据，包括网络数据，缓存数据，用户数据，调用服务器接口获取的数据
     */
    protected abstract void loadData();
    /**
     * 初始化视图，加载layout布局文件，初始化控件，为控件挂上事件
     */
    protected abstract void initViews(Bundle savedInstanceState);
    /**
     * 初始化变量，包括启动Activity传过来的变量和Activity内的变量
     */
    protected abstract void initVariables();
}
