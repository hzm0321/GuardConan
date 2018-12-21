package cn.hzmeurasia.guardconan.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.ProcessManagerAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.TaskInfo;
import cn.hzmeurasia.guardconan.utils.SystemInfoUtils;
import cn.hzmeurasia.guardconan.utils.TaskInfoParser;

/**
 * 类名: ProgressManagerActivity<br>
 * 功能:(进程管理)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/18 11:56
 */
public class ProgressManagerActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.tv_progress_manager_running)
    TextView tvRunning;

    @BindView(R.id.tv_progress_manager_memory)
    TextView tvMemory;

    @BindView(R.id.rv_progress_manager)
    RecyclerView rvProgress;

    @BindView(R.id.tv_user_runningprocess)
    TextView mProcessNumTV;

    private List<TaskInfo> runningTaskInfos;

    private List<TaskInfo> userTaskInfos = new ArrayList<>();

    private List<TaskInfo> sysTaskInfos = new ArrayList<>();

    private ActivityManager manager;

    private int runningProgressCount;

    private long totalMem;

    ProcessManagerAdapter adapter;

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
        mTopBar.setTitle(R.string.progress_manager);
        mTopBar.addRightImageButton(R.drawable.ic_more_vert,R.id.top_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, ProgressManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        runningProgressCount = SystemInfoUtils.getRunningPocessCount(ProgressManagerActivity.this);
        long totalAvailMem = SystemInfoUtils.getAvailMem(this);
        totalMem = SystemInfoUtils.getTotalMem();
        tvRunning.setText("运行中的进程: " + runningProgressCount);
        tvMemory.setText("可用/总内存: " + android.text.format.Formatter.formatFileSize(this, totalAvailMem) + "/" +
                android.text.format.Formatter.formatFileSize(this, totalMem));
        userTaskInfos.clear();
        sysTaskInfos.clear();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.progress_manager_activity);
        ButterKnife.bind(this);
        initTopBar();
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
