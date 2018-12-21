package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;

/**
 * 类名: AdvancedToolsActivity<br>
 * 功能:(高级工具)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/19 11:23
 */
public class AdvancedToolsActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.group_advanced_tools)
    QMUIGroupListView mGroupListView;

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
        mTopBar.setTitle(R.string.advanced_tools);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AdvancedToolsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.advanced_tools_activity);
        ButterKnife.bind(this);
        initTopBar();
        initGroupListView();
    }

    @Override
    protected void initVariables() {

    }

    private void initGroupListView() {

        QMUICommonListItemView phoneLocationItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(AdvancedToolsActivity.this,R.drawable.ic_location_searching),
                "号码归属地查询",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );
        phoneLocationItem.setTag(R.id.listitem_tag_1);

        QMUICommonListItemView messageBackupsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(AdvancedToolsActivity.this,R.drawable.ic_message_backups),
                "短信备份",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );
        messageBackupsItem.setTag(R.id.listitem_tag_2);

        QMUICommonListItemView messageRestoreItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(AdvancedToolsActivity.this,R.drawable.ic_message_restore),
                "短信还原",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );
        messageRestoreItem.setTag(R.id.listitem_tag_3);

        QMUICommonListItemView lockItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(AdvancedToolsActivity.this,R.drawable.ic_lock),
                "程序锁",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );
        lockItem.setTag(R.id.listitem_tag_4);

        View.OnClickListener onClickListener = view->{
            QMUICommonListItemView listItemView = (QMUICommonListItemView) view;
            int tag = (int) listItemView.getTag();
            switch(tag) {
                case R.id.listitem_tag_1:
                    NumBelongtoActivity.startAct(AdvancedToolsActivity.this);
                    break;
                case R.id.listitem_tag_2:
                case R.id.listitem_tag_3:
                    Toast.makeText(AdvancedToolsActivity.this, "正在开发中，敬请期待。", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.listitem_tag_4:
                    AppLockActivity.startAct(AdvancedToolsActivity.this);
                    break;
                default:
                    break;
            }
        };
        QMUIGroupListView.newSection(AdvancedToolsActivity.this)
                .addItemView(phoneLocationItem, onClickListener)
                .addItemView(messageBackupsItem,onClickListener)
                .addItemView(messageRestoreItem,onClickListener)
                .addItemView(lockItem,onClickListener)
                .addTo(mGroupListView);
    }
}
