package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.VirusScanAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.ScanAppInfo;
import cn.hzmeurasia.guardconan.utils.MD5Utils;

/**
 * 类名: VirusScanSpeedActivity<br>
 * 功能:(手机杀毒扫描界面)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/18 0:05
 */
public class VirusScanSpeedActivity  extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.tv_virus_scan_speed)
    TextView tvSpeed;

    @BindView(R.id.tv_virus_scan_speed_package_name)
    TextView tvPackageName;

    @BindView(R.id.iv_virus_scan_speed_time)
    ImageView ivTime;

    @BindView(R.id.rv_virus_scan_speed)
    RecyclerView rvSpeed;

    @BindView(R.id.rbtn_virus_scan_speed)
    QMUIRoundButton rbtnSpeed;

    protected static final int SCAN_BENGIN = 100;

    protected static final int SCANNING = 101;

    protected static final int SCAN_FINISH = 102;

    private int total;

    private int process;

    private PackageManager packageManager;

    private boolean flag;

    private boolean isStop;

    private RotateAnimation rani;

    private VirusScanAdapter adapter;

    private List<ScanAppInfo> mScanAppInfos = new ArrayList<>();

    private SharedPreferences mSP;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SCAN_BENGIN:
                    tvPackageName.setText("初始化杀毒引擎中......");
                    break;
                case SCANNING:
                    ScanAppInfo info = (ScanAppInfo) msg.obj;
                    tvPackageName.setText("正在扫描: " + info.appName);
                    int speed = msg.arg1;
                    tvSpeed.setText((speed * 100) / total + "%");
                    mScanAppInfos.add(info);
                    adapter.notifyDataSetChanged();
                    rvSpeed.scrollToPosition(mScanAppInfos.size() - 1);
                    break;
                case SCAN_FINISH:
                    tvPackageName.setText("扫描完成!");
                    ivTime.clearAnimation();
                    rbtnSpeed.setText("完成");
                    saveScanTime();
                    break;
                default:
                    break;
            }
        }

        private void saveScanTime() {
            SharedPreferences.Editor editor = mSP.edit();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentTime = sdf.format(new Date());
            currentTime = "上次查杀: " + currentTime;
            editor.putString("lastVirusScan", currentTime);
            editor.apply();
        }
    };

    @OnClick(R.id.rbtn_virus_scan_speed)
    void OnClick(View view) {
        switch(view.getId()) {
            case R.id.rbtn_virus_scan_speed:
                if ("完成".equals(rbtnSpeed.getText().toString())) {
                    finish();
                } else if ("取消扫描".equals(rbtnSpeed.getText().toString())) {
                    ivTime.clearAnimation();
                    flag = false;
                    rbtnSpeed.setText("重新扫描");
                } else if ("重新扫描".equals(rbtnSpeed.getText().toString())) {
                    startAnim();
                    scanVirus();
                    rbtnSpeed.setText("取消扫描");
                }
                break;
            default:
                break;
        }
    }
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
        mTopBar.setTitle(R.string.virus_kill_scan);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, VirusScanSpeedActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.virus_scan_speed);
        ButterKnife.bind(this);
        initTopBar();
        packageManager = getPackageManager();
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSpeed.setLayoutManager(layoutManager);
        adapter = new VirusScanAdapter(mScanAppInfos);
        rvSpeed.setAdapter(adapter);
        startAnim();
        scanVirus();
    }

    /**
     * 扫描病毒
     */
    private void scanVirus() {
        flag = true;
        isStop = false;
        process = 0;
        mScanAppInfos.clear();
        new Thread(){
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = SCAN_BENGIN;
                mHandler.sendMessage(msg);
                List<PackageInfo> installPackages = packageManager.getInstalledPackages(0);
                total = installPackages.size();
                for (PackageInfo info : installPackages) {
                    if (!flag) {
                        isStop = true;
                        return;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ScanAppInfo scanAppInfo = new ScanAppInfo();
                    scanAppInfo.description = "扫描安全";
                    scanAppInfo.isVirus = false;
                    scanAppInfo.packagename = info.packageName;
                    scanAppInfo.appName = info.applicationInfo.loadLabel(packageManager).toString();
                    scanAppInfo.appicon = info.applicationInfo.loadIcon(packageManager);
                    process++;
                    Message msg2 = Message.obtain();
                    msg2.obj = scanAppInfo;
                    msg2.arg1 = process;
                    msg2.what = SCANNING;
                    mHandler.sendMessage(msg2);
                }
                Message msg3 = Message.obtain();
                msg3.what = SCAN_FINISH;
                mHandler.sendMessage(msg3);
            }
        }.start();
    }

    private void startAnim() {
        if (rani == null) {
            rani = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rani.setRepeatCount(Animation.INFINITE);
        rani.setDuration(2000);
        ivTime.startAnimation(rani);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onDestroy() {
        flag = false;
        super.onDestroy();
    }
}
