package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.utils.PrefUtils;

/**
 * 类名: AntiTheftActivity<br>
 * 功能:(手机防盗)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/14 10:37
 */
public class AntiTheftActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.group_list_view)
    QMUIGroupListView mGroupListView;

    @BindView(R.id.group_list_view_message_function)
    QMUIGroupListView mMessageGroupListView;

    private boolean protectFlag = true;

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
        mTopBar.setTitle(R.string.anti_theft);

    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AntiTheftActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.anti_theft_activity);
        ButterKnife.bind(this);
        initTopBar();
        initGroupListView();
    }

    @Override
    protected void initVariables() {

    }

    private void initGroupListView() {
        QMUICommonListItemView lvSecurePhone = mGroupListView.createItemView("安全号码");
        lvSecurePhone.setOrientation(QMUICommonListItemView.HORIZONTAL);
        lvSecurePhone.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        protectFlag = PrefUtils.getBoolean(PREF_IS_PROTECT, true, AntiTheftActivity.this);
        String protectText;
        if (protectFlag) {
            protectText = "防盗保护已经开启";
        } else {
            protectText = "防盗保护没有开启";

        }
        final QMUICommonListItemView lvProtect = mGroupListView.createItemView(protectText);
        lvProtect.setOrientation(QMUICommonListItemView.HORIZONTAL);
        lvProtect.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        if (protectFlag) {
            lvProtect.getSwitch().setChecked(true);
        }
        lvProtect.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lvProtect.setText("防盗保护已经开启");
                    PrefUtils.putBoolean(PREF_IS_PROTECT, true, AntiTheftActivity.this);
                } else {
                    lvProtect.setText("防盗保护没有开启");
                    PrefUtils.putBoolean(PREF_IS_PROTECT, false, AntiTheftActivity.this);
                }
            }
        });

        QMUICommonListItemView lvGuid = mGroupListView.createItemView("重新进入安全向导");
        lvGuid.setOrientation(QMUICommonListItemView.HORIZONTAL);
        lvGuid.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView lvAlarmMusic = mMessageGroupListView.createItemView(
                ContextCompat.getDrawable(AntiTheftActivity.this,R.drawable.ic_music),
                "播放报警音乐",
                "#*alarm*#",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUICommonListItemView lvGPS = mMessageGroupListView.createItemView(
                ContextCompat.getDrawable(AntiTheftActivity.this,R.drawable.ic_gps),
                "GPS追踪",
                "#*location*#",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUICommonListItemView lvSreenLock = mMessageGroupListView.createItemView(
                ContextCompat.getDrawable(AntiTheftActivity.this,R.drawable.ic_screen_lock),
                "远程锁屏",
                "#*lockScreen*#",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        QMUICommonListItemView lvDelete = mMessageGroupListView.createItemView(
                ContextCompat.getDrawable(AntiTheftActivity.this,R.drawable.ic_delete),
                "远程删除数据",
                "#*wipedata*#",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);


        QMUIGroupListView.newSection(AntiTheftActivity.this)
                .addItemView(lvSecurePhone, null)
                .addItemView(lvProtect,null)
                .addItemView(lvGuid,null)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(AntiTheftActivity.this)
                .addItemView(lvAlarmMusic, null)
                .addItemView(lvGPS,null)
                .addItemView(lvSreenLock,null)
                .addItemView(lvDelete,null)
                .addTo(mMessageGroupListView);



    }
}
