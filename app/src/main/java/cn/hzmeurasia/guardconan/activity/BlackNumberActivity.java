package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.MyApplication;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.BlackNumberAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.db.BlackNumberDb;

/**
 * 类名: BlackNumberActivity<br>
 * 功能:(通讯卫士)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/12 9:34
 */
public class BlackNumberActivity extends BaseActivity {

    private static final String TAG = "BlackNumberActivity";

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.iv_black_number_empty)
    ImageView ivEmpty;

    @BindView(R.id.fab_black_number_add)
    FloatingActionButton fab;

    @BindView(R.id.tv_black_number_empty)
    TextView tvEmptry;

    @BindView(R.id.swipe_recycler_view_black_number_list)
    SwipeMenuRecyclerView mRecyclerView;

    private List<BlackNumberDb> mBlackNumberDbList;

    private BlackNumberAdapter adapter;

    private boolean flag = false;

    @OnClick({R.id.fab_black_number_add})
    void OnClick(View view) {
        switch(view.getId()) {
            case R.id.fab_black_number_add:
                AddBlackNumberActivity.startAct(MyApplication.getContext());
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
        mTopBar.setTitle(R.string.communication);
        mTopBar.addRightImageButton(R.drawable.ic_autorenew, R.id.iv_black_number_empty)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BlackNumberActivity.this, "点击了刷新", Toast.LENGTH_SHORT).show();
                        refreshList();
                    }
                });

    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, BlackNumberActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

        Log.d(TAG, "loadData: 数据库中的字段数 "+mBlackNumberDbList.size());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.black_number_activity);
        ButterKnife.bind(this);
        //去除标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //初始化工具栏
        initTopBar();
        mBlackNumberDbList = LitePal.findAll(BlackNumberDb.class);
        checkEmpty();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new BlackNumberAdapter(mBlackNumberDbList);
        adapter.setCheckEmpty(new BlackNumberAdapter.BlackNumberCheckEmpty() {
            @Override
            public void DataSizeChange() {
                checkEmpty();
            }
        });
        //创建侧滑菜单
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem editItem = new SwipeMenuItem(MyApplication.getContext())
                        .setText("编辑")
                        .setWidth(150)
                        .setHeight(height)
                        .setTextColorResource(R.color.white)
                        .setBackgroundColorResource(R.color.qmui_config_color_gray_2);
                leftMenu.addMenuItem(editItem);
            }
        };
        //侧滑"编辑"点击事件
        SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                menuBridge.closeMenu();
                //左侧还是右侧菜单
                int direction = menuBridge.getDirection();
                Log.d(TAG, "onItemClick: 位置 "+position);
                if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                    flag = true;
                    BlackNumberDb blackNumberDb = mBlackNumberDbList.get(position);
                    Intent intent = new Intent(MyApplication.getContext(), AddBlackNumberActivity.class);
                    intent.putExtra("flag", flag);
                    intent.putExtra("id", blackNumberDb.getId());
                    intent.putExtra("phone", blackNumberDb.getNumber());
                    intent.putExtra("name", blackNumberDb.getName());
                    intent.putExtra("checkPhone", blackNumberDb.getPhone());
                    intent.putExtra("checkMessage", blackNumberDb.getMessage());
                    startActivity(intent);
                }

            }
        };
        //侧滑监听
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initVariables() {

    }

    /**
     * 检查列表是否为空,并选择显示空和非空界面
     */
    public void checkEmpty() {
        mBlackNumberDbList.clear();
        mBlackNumberDbList.addAll(LitePal.findAll(BlackNumberDb.class));
        if (mBlackNumberDbList.size() == 0) {
            ivEmpty.setVisibility(View.VISIBLE);
            tvEmptry.setVisibility(View.VISIBLE);
        } else {
            ivEmpty.setVisibility(View.GONE);
            tvEmptry.setVisibility(View.GONE);
        }
    }

    /**
     * 更新列表
     */
    private void refreshList() {
        mBlackNumberDbList.clear();
        mBlackNumberDbList.addAll(LitePal.findAll(BlackNumberDb.class));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkEmpty();
        flag = false;
        refreshList();
    }
}
