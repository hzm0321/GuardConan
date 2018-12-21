package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.utils.PrefUtils;

/**
 * 类名: SettingsActivity<br>
 * 功能:(设置中心)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/21 9:02
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.group_list_view_settings)
    QMUIGroupListView mGroupListView;

    private boolean flagBlackNumber = true;

    private boolean flagAppSetting = true;

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
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.settings_activity);
        ButterKnife.bind(this);
        initTopBar();
        initGroupListView();
    }

    @Override
    protected void initVariables() {

    }

    private void initGroupListView() {
        flagBlackNumber = PrefUtils.getBoolean(PREF_IS_BLACK_NUMBER, true, SettingsActivity.this);
        flagAppSetting = PrefUtils.getBoolean(PREF_IS_APP_LOCK, true, SettingsActivity.this);
        String blackNumberText;
        String appLockTest;
        if (flagBlackNumber) {
            blackNumberText = "黑名单拦截已开启";
        } else {
            blackNumberText = "黑名单拦截已关闭";

        }
        if (flagAppSetting) {
            appLockTest = "程序锁已开启";
        } else {
            appLockTest = "程序锁已关闭";
        }

        final QMUICommonListItemView lvBlackNumber = mGroupListView.createItemView("黑名单拦截设置");
        lvBlackNumber.setDetailText(blackNumberText);
        lvBlackNumber.setOrientation(QMUICommonListItemView.HORIZONTAL);
        lvBlackNumber.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        if (flagBlackNumber) {
            lvBlackNumber.getSwitch().setChecked(true);
        }
        lvBlackNumber.getSwitch().setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                lvBlackNumber.setDetailText("黑名单拦截已开启");
                PrefUtils.putBoolean(PREF_IS_BLACK_NUMBER, true, SettingsActivity.this);
            } else {
                lvBlackNumber.setDetailText("黑名单拦截已关闭");
                PrefUtils.putBoolean(PREF_IS_BLACK_NUMBER, false, SettingsActivity.this);
            }
        });

        final QMUICommonListItemView lvAppLock = mGroupListView.createItemView("程序锁设置");
        lvAppLock.setDetailText(appLockTest);
        lvAppLock.setOrientation(QMUICommonListItemView.HORIZONTAL);
        lvAppLock.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        if (flagAppSetting) {
            lvAppLock.getSwitch().setChecked(true);
        }
        lvAppLock.getSwitch().setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                lvAppLock.setDetailText("程序锁已开启");
                PrefUtils.putBoolean(PREF_IS_APP_LOCK, true, SettingsActivity.this);
            } else {
                lvAppLock.setDetailText("程序锁已关闭");
                PrefUtils.putBoolean(PREF_IS_APP_LOCK, false, SettingsActivity.this);
            }
        });

        QMUIGroupListView.newSection(SettingsActivity.this)
                .addItemView(lvBlackNumber,null)
                .addItemView(lvAppLock,null)
                .addTo(mGroupListView);


    }
}
