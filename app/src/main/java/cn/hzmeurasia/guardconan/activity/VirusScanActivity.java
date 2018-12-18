package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;

/**
 * 类名: VirusScanActivity<br>
 * 功能:(病毒扫描)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/17 22:52
 */
public class VirusScanActivity  extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.tv_virus_scan_home)
    TextView tvHome;

    @BindView(R.id.rl_virus_scan_home)
    RelativeLayout relativeLayout;

    private SharedPreferences mSP;

    @OnClick({R.id.tv_virus_scan_home, R.id.rl_virus_scan_home})
    void OnClick(View view) {
        switch(view.getId()) {
            case R.id.rl_virus_scan_home:
                VirusScanSpeedActivity.startAct(VirusScanActivity.this);
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
        mTopBar.setTitle(R.string.virus_kill);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, VirusScanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.virus_scan_activity);
        ButterKnife.bind(this);
        initTopBar();
        mSP = getSharedPreferences("config", MODE_PRIVATE);

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        String s = mSP.getString("lastVirusScan", "您还没有查杀病毒");
        tvHome.setText(s);
    }
}
