package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;

/**
 * 类名: CleanCacheFinishActivity<br>
 * 功能:(清理缓存执行)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/17 14:22
 */
public class CleanCacheFinishActivity  extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.iv_clean_cache_finish)
    ImageView ivFinish;

    @BindView(R.id.tv_clean_cache_finish)
    TextView tvFinish;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    tvFinish.setText(String.valueOf(msg.obj)+"MB");
                    break;
                case 2:
                    Glide.with(CleanCacheFinishActivity.this).load(R.drawable.clean_cache_finish).into(ivFinish);
                    Toast.makeText(CleanCacheFinishActivity.this, "清理完成", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

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
        mTopBar.setTitle(R.string.clean_cache);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, CleanCacheFinishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        thread = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i <= 200; i+=5) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    msg.obj = i;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }
        };
        thread.start();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.clean_cache_finish_activity);
        ButterKnife.bind(this);
        initTopBar();
        Glide.with(CleanCacheFinishActivity.this).load(R.drawable.gif_clean_cache_finish).into(ivFinish);
    }

    @Override
    protected void initVariables() {

    }
}
