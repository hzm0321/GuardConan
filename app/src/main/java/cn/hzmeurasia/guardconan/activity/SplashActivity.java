package cn.hzmeurasia.guardconan.activity;

import android.Manifest;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.AppInfo;
import cn.hzmeurasia.guardconan.service.DownloadService;
import cn.hzmeurasia.guardconan.utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类名: SplashActivity<br>
 * 功能:(欢迎页)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/10 10:19
 */
public class SplashActivity extends BaseActivity {

    public static final int UPDATE_FAIL = 0;
    public static final int UPDATE_SUCCESS = 1;
    private static final String TAG = "SplashActivity";

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private TextView mTextVersion;
    private AppInfo mLocalAppInfo;
    private AppInfo mRemoteAppInfo;
    private TextView mTvProgress;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case UPDATE_FAIL:
                    Toast.makeText(SplashActivity.this, "检查更新失败", Toast.LENGTH_SHORT).show();
                    break;
                case UPDATE_SUCCESS:
                    Toast.makeText(SplashActivity.this,"有新的版本",Toast.LENGTH_SHORT).show();
                    showUpdateDialog();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = findViewById(R.id.frame_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            layout.setSystemUiVisibility(View.INVISIBLE);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent intent = new Intent(this, DownloadService.class);
        //启动服务
        startService(intent);
        //绑定服务
        bindService(intent, connection, BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

    }

    /**
     * 加载更新数据
     */
    @Override
    protected void loadData() {
        getAppInfoFromServer();
    }

    private void getAppInfoFromServer() {
        final long timeStart = System.currentTimeMillis();
        String updateUrl = "http://hzmeurasia.cn/conan/update";
        HttpUtil.sendOkHttpRequest(updateUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = UPDATE_FAIL;
                handler.sendMessage(message);
                Log.d(TAG, "onFailure: 获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respondsTest = response.body().string();
                Gson gson = new Gson();
                mRemoteAppInfo = gson.fromJson(respondsTest, AppInfo.class);
                Log.d(TAG, "onResponse: 服务器版本信息: "+respondsTest);
                if (mRemoteAppInfo.getVersionCode() > mLocalAppInfo.getVersionCode()) {
                    //有更新,弹出对话框
                    Message message = new Message();
                    message.what = UPDATE_SUCCESS;
                    handler.sendMessage(message);
                } else {
                    //无更新,停顿一段时间进入主界面
                    long timeEnd = System.currentTimeMillis();
                    long timeUsed = timeEnd - timeStart;
                    try {
                        Thread.sleep(2000-timeEnd);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startHomeActivity();
                }
            }
        });
    }

    /**
     * 初始化视图
     * @param savedInstanceState
     */
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.splash_activity);
        mTextVersion = findViewById(R.id.tv_version);
        mTextVersion.setText("版本号:"+mLocalAppInfo.getVersionName());
    }

    @Override
    protected void initVariables() {
        mLocalAppInfo = getLocalAppInfo();
    }

    private AppInfo getLocalAppInfo() {
        AppInfo appInfo = new AppInfo();
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    /**
     * 显示更新对话框
     */
    private void showUpdateDialog() {
        new QMUIDialog.MessageDialogBuilder(SplashActivity.this)
                .setTitle("版本更新")
                .setMessage(mRemoteAppInfo.getDescription())
                .addAction("以后再说", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        startHomeActivity();
                    }
                })
                .addAction("立即更新", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        Log.d(TAG, "onClick: 下载链接: "+mRemoteAppInfo.getdownloadUrl());
                        downloadApk(mRemoteAppInfo.getdownloadUrl());
                    }
                })
                .setCanceledOnTouchOutside(false)
                .create().show();
    }

    /**
     * 开始下载新版本Apk
     * @param downloadUrl
     */
    private void downloadApk(String downloadUrl) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "没有找到sdcard", Toast.LENGTH_SHORT).show();
            return;
        }
        if (downloadBinder == null) {
            Log.d(TAG, "downloadApk: 下载服务为空");
            return;
        } else {
            downloadBinder.startDownload(downloadUrl);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝该请求,无法进行软件更新", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 进入主界面
     */
    private void startHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startHomeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
