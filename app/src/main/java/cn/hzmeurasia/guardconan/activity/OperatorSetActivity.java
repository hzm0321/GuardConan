package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;

/**
 * 类名: OperatorSetActivity<br>
 * 功能:(流量统计引导页)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/19 10:10
 */
public class OperatorSetActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.spinner)
    AppCompatSpinner mSelectSP;

    @BindView(R.id.rbtn_operator_finish)
    QMUIRoundButton rbtnFinish;

    private String[] operators = { "中国移动", "中国联通", "中国电信" };

    private ArrayAdapter mSelectadapter;

    private SharedPreferences msp;

    @OnClick(R.id.rbtn_operator_finish)
    void OnClick(View view){
        switch(view.getId()) {
            case R.id.rbtn_operator_finish:
                SharedPreferences.Editor editor = msp.edit();
                editor.putBoolean("isset_operator", true);
                editor.apply();
                TrafficMonitoringActivity.startAct(OperatorSetActivity.this);
                finish();
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
        mTopBar.setTitle(R.string.carrierOpera);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, OperatorSetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        mSelectadapter = new ArrayAdapter(this, R.layout.spinner_operatorset_item, R.id.tv_provide, operators);
        mSelectSP.setAdapter(mSelectadapter);

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.operatorset_activity);
        ButterKnife.bind(this);
        msp = getSharedPreferences("config", MODE_PRIVATE);
        initTopBar();
    }

    @Override
    protected void initVariables() {

    }
}
