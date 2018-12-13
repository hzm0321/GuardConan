package cn.hzmeurasia.guardconan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.AppManagerAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.AppManager;
import cn.hzmeurasia.guardconan.utils.AppInfoParser;

/**
 * 类名: AppManagerActivity<br>
 * 功能:(软件管家)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 14:48
 */
public class AppManagerActivity extends BaseActivity {

    @BindView(R.id.tv_app_manager_phone_memory)
    TextView tvPhoneMemory;

    @BindView(R.id.tv_app_manager_sd_memory)
    TextView tvSdMemory;

    @BindView(R.id.tv_app_number)
    TextView tvAppNumber;

    @BindView(R.id.lv_app_manager)
    ListView lvAppManager;

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    private List<AppManager> appManagers;

    private List<AppManager> userApp = new ArrayList<>();

    private List<AppManager> systemApp = new ArrayList<>();

    private AppManagerAdapter adapter;

    private UninstallRececiver receciver;

    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 10:
                    if(adapter == null){
                        adapter = new AppManagerAdapter(userApp, systemApp, AppManagerActivity.this);
                    }
                    lvAppManager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 15:
                    adapter.notifyDataSetChanged();
                    break;
            }
        };
    };



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
        mTopBar.setTitle(R.string.app_manager);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AppManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.app_manager_activity);
        ButterKnife.bind(this);
        initTopBar();
        //注册广播
        receciver = new UninstallRececiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receciver, intentFilter);
        getMemoryFromPhone();
        initData();
        initListener();
    }

    private void initListener() {
        lvAppManager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                if (adapter != null) {
                    new Thread(){
                        public void run() {
                            AppManager mappInfo  = (AppManager) adapter.getItem(position);
                            //记住当前条目的状态
                            boolean flag = mappInfo.isSelected;
                            //先将集合中所有条目的AppInfo变为未选中状态
                            for(AppManager appInfo :userApp){
                                appInfo.isSelected = false;
                            }
                            for(AppManager appInfo : systemApp){
                                appInfo.isSelected = false;
                            }
                            if(mappInfo != null){
                                //如果已经选中，则变为未选中
                                if(flag){
                                    mappInfo.isSelected = false;
                                }else{
                                    mappInfo.isSelected = true;
                                }
                                mHander.sendEmptyMessage(15);
                            }
                        };
                    }.start();

                }


            }
        });

        lvAppManager.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem >= userApp.size()+1){
                    tvAppNumber.setText("系统程序："+systemApp.size()+"个");
                }else{
                    tvAppNumber.setText("用户程序："+userApp.size()+"个");
                }
            }
        });
    }

    @Override
    protected void initVariables() {

    }

    /**
     * 拿到手机和SD卡剩余内存
     */
    private void getMemoryFromPhone() {
        long avail_sd = Environment.getExternalStorageDirectory()
                .getFreeSpace();
        long avail_rom = Environment.getDataDirectory().getFreeSpace();
        //格式化内存
        String str_avail_sd = Formatter.formatFileSize(this, avail_sd);
        String str_avail_rom = Formatter.formatFileSize(this, avail_rom);
        tvPhoneMemory.setText("剩余手机内存：" + str_avail_rom);
        tvSdMemory.setText("剩余SD卡内存：" + str_avail_sd);
    }

    /**
     * 广播
     */
    class UninstallRececiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播
            initData();
        }
    }

    private void initData() {
        appManagers = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                appManagers.clear();
                userApp.clear();
                systemApp.clear();
                appManagers.addAll(AppInfoParser.getAppManagers(AppManagerActivity.this));
                for (AppManager appManager : appManagers) {
                    if (appManager.isUserApp) {
                        userApp.add(appManager);
                    } else {
                        systemApp.add(appManager);
                    }
                }
                mHander.sendEmptyMessage(10);
            }
        }.start();
    }

}
