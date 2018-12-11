package cn.hzmeurasia.guardconan.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.OptionsAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.Options;

/**
 * 类名: HomeActivity<br>
 * 功能:(主页)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/11 9:29
 */
public class HomeActivity extends BaseActivity {

    private Options[] mOptions;
    private List<Options> optionsList = new ArrayList<>();
    private OptionsAdapter adapter;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initVariables() {
        mOptions = new Options[]{new Options("手机防盗", R.drawable.option01),new Options("通讯卫士", R.drawable.option02),
                new Options("软件管家", R.drawable.option03),new Options("手机杀毒", R.drawable.option04),
                new Options("缓存清理", R.drawable.option05),new Options("进程管理", R.drawable.option06),
                new Options("流量统计", R.drawable.option07),new Options("高级工具", R.drawable.option08),
                new Options("设置中心", R.drawable.option09)};
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.home_activity);
        //去除标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //加载卡片视图界面
        for (int i = 0; i < mOptions.length; i++) {
            optionsList.add(mOptions[i]);
        }
        RecyclerView recyclerView = findViewById(R.id.rv_home);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OptionsAdapter(optionsList);
        recyclerView.setAdapter(adapter);
    }


}
