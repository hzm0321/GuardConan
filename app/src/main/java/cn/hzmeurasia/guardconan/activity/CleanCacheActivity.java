package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.util.QMUIPackageHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.CleanCacheAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.CacheInfo;

/**
 * 类名: CleanCacheActivity<br>
 * 功能:(清理缓存)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/17 9:24
 */
public class CleanCacheActivity extends BaseActivity {

    private static final String TAG = "CleanCacheActivity";

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.iv_clean_cache_home)
    ImageView ivHome;

    @BindView(R.id.rv_clean_cache_home)
    RecyclerView recyclerView;

    @BindView(R.id.rbtn_clean_cache_home)
    QMUIRoundButton rbtnHome;

    @BindView(R.id.rbtn_clean_cache_home_alpha)
    QMUIRoundButton rbtnHomeAlpha;

    @BindView(R.id.tv_clean_cache_package_name)
    TextView tvPackageName;

    @BindView(R.id.tv_clean_cache_already_size)
    TextView tvSize;

    protected static final int SCANNING = 100;

    protected static final int FINISH = 101;

    private long cacheMemory;

    private List<CacheInfo> cacheInfos = new ArrayList<>();

    private List<CacheInfo> mCacheInfos = new ArrayList<>();

    private PackageManager packageManager;

    private CleanCacheAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SCANNING:
                    PackageInfo info = (PackageInfo) msg.obj;
                    tvPackageName.setText(info.packageName);
                    tvSize.setText(android.text.format.Formatter.formatFileSize(CleanCacheActivity.this, cacheMemory));
                    //主线程添加变化后集合
                    mCacheInfos.clear();
                    mCacheInfos.addAll(cacheInfos);
                    //刷新
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(mCacheInfos.size()-1);
                    break;
                case FINISH:
                    //扫描完成
                    Toast.makeText(CleanCacheActivity.this, "扫描完成", Toast.LENGTH_SHORT).show();
                    rbtnHomeAlpha.setVisibility(View.GONE);
                    rbtnHome.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick({R.id.rbtn_clean_cache_home,R.id.rbtn_clean_cache_home_alpha})
    void OnClick(View view){
        switch(view.getId()) {
            case R.id.rbtn_clean_cache_home:
                finish();
                CleanCacheFinishActivity.startAct(CleanCacheActivity.this);
                break;
            default:
                break;
        }
    }

    private Thread thread;

    /**
     * 初始化工具栏
     */
    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTopBar.setBackgroundResource(R.drawable.top_bar_bg);
        mTopBar.setTitle(R.string.scan_cache);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CleanCacheActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        Log.d(TAG, "loadData: 加载数据的方法执行了");
        thread = new Thread(){
            @Override
            public void run() {
                //遍历手机里所有程序
                cacheInfos.clear();
                List<PackageInfo> infos = packageManager.getInstalledPackages(0);
                for (PackageInfo info : infos) {
                    getCacheSize(info);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what = SCANNING;
                    handler.sendMessage(msg);
                }
                Message msg = Message.obtain();
                msg.what = FINISH;
                handler.sendMessage(msg);
            }
        };
        thread.start();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.clean_cache_activity);
        ButterKnife.bind(this);
        initTopBar();
        Glide.with(CleanCacheActivity.this).load(R.drawable.gif_clean_cache_home).into(ivHome);
        packageManager = getPackageManager();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CleanCacheAdapter(mCacheInfos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.interrupt();
        }
    }

    private void getCacheSize(PackageInfo info) {
        long cacheSize = 0;
        CacheInfo cacheInfo = new CacheInfo();
        cacheInfo.cacheSize = 8888;
        cacheInfo.packagename = info.packageName;
        cacheInfo.appName = info.applicationInfo.loadLabel(packageManager).toString();
        cacheInfo.appIcon = info.applicationInfo.loadIcon(packageManager);
        cacheInfos.add(cacheInfo);
        cacheMemory += cacheSize;
    }



}
