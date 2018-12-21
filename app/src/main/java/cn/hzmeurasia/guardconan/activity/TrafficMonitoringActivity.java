package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;

/**
 * 类名: TrafficMonitoringActivity<br>
 * 功能:(流量统计)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/19 9:16
 */
public class TrafficMonitoringActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

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
        mTopBar.setTitle(R.string.traffic_monitor);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, TrafficMonitoringActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.traffic_monitoring_activity);
        ButterKnife.bind(this);
        initTopBar();
    }

    @Override
    protected void initVariables() {

    }
}
