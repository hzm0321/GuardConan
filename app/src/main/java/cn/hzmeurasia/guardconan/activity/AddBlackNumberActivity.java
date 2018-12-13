package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.MyApplication;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.db.BlackNumberDb;

/**
 * 类名: AddBlackNumberActivity<br>
 * 功能:(添加黑名单界面)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/12 16:33
 */
public class AddBlackNumberActivity extends BaseActivity {

    private static final String TAG = "AddBlackNumberActivity";

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.et_add_black_number_phone)
    TextInputEditText etPhone;

    @BindView(R.id.et_add_black_number_name)
    TextInputEditText etName;

    @BindView(R.id.cb_phone)
    AppCompatCheckBox cbPhone;

    @BindView(R.id.cb_message)
    AppCompatCheckBox cbMessage;

    @BindView(R.id.qmui_round_button_black_number_contact_add)
    QMUIRoundButton rbContactAdd;

    QMUITipDialog tipDialog;

    private String tipText;

    private boolean flag;

    private int id;

    @OnClick(R.id.qmui_round_button_black_number_contact_add)
    void onClick(View view) {
        switch(view.getId()) {
            case R.id.qmui_round_button_black_number_contact_add:
                SelectContactActivity.startAct(AddBlackNumberActivity.this);
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
        if (flag) {
            mTopBar.setTitle(R.string.edit_black_number);
        } else {
            mTopBar.setTitle(R.string.add_black_number);
        }
        mTopBar.addRightImageButton(R.drawable.ic_done, R.id.top_bar)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isDone()) {
                            showTipDialog();
                            insertDb();
                        }

                    }
                });
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, AddBlackNumberActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        Log.d(TAG, "loadData: 加载数据");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.add_black_number_activity);
        ButterKnife.bind(this);
        Log.d(TAG, "initViews: 加载视图");
        initData();
        initTopBar();

    }

    /**
     * 判断是编辑还是增加页面,初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        flag = intent.getBooleanExtra("flag", false);
        id = intent.getIntExtra("id", 0);
        if (flag) {
            //从编辑页面跳转而来
            Log.d(TAG, "initData: 电话号码"+intent.getIntExtra("phone",0));
            etPhone.setText(String.valueOf(intent.getStringExtra("phone")));
            etName.setText(intent.getStringExtra("name"));
            Log.d(TAG, "initData: "+intent.getStringExtra("checkPhone"));
            Log.d(TAG, "initData: "+intent.getStringExtra("checkMessage"));
            if ("电话拦截".equals(intent.getStringExtra("checkPhone"))) {
                cbPhone.setChecked(true);
            } else {
                cbPhone.setChecked(false);
            }
            if ("短信拦截".equals(intent.getStringExtra("checkMessage"))) {
                cbMessage.setChecked(true);
            } else {
                cbMessage.setChecked(false);
            }
        }

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Intent intent1 = getIntent();
        etName.setText(intent1.getStringExtra("name"));
        etPhone.setText(intent1.getStringExtra("phone"));
    }

    /**
     * 判断选项是否填写
     * @return
     */
    private boolean isDone() {
        if (etPhone.getText().toString().trim().length() == 0 ||
                etName.getText().toString().trim().length() == 0) {
            Toast.makeText(MyApplication.getContext(), "电话或姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!cbPhone.isChecked() && !cbMessage.isChecked()) {
            Toast.makeText(MyApplication.getContext(), "请选择拦截模式!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 显示TipDialog提示
     */
    private void showTipDialog() {
        if (flag) {
            tipText = "修改完成";
        } else {
            tipText = "添加完成";
        }
        tipDialog = new QMUITipDialog.Builder(AddBlackNumberActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(tipText)
                .create();
        tipDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
                finish();
            }
        }, 1000);
    }

    /**
     * 数据插入数据库
     */
    private void insertDb() {
        BlackNumberDb blackNumberDb = new BlackNumberDb();
        blackNumberDb.setNumber(etPhone.getText().toString());
        blackNumberDb.setName(etName.getText().toString());
        if (cbPhone.isChecked()) {
            blackNumberDb.setPhone("电话拦截");
        }else {
            blackNumberDb.setPhone("");
        }
        if (cbMessage.isChecked()) {
            blackNumberDb.setMessage("短信拦截");
        } else {
            blackNumberDb.setMessage("");
        }
        if (flag) {
            blackNumberDb.updateAll("id = ?", String.valueOf(id));
        } else {
            blackNumberDb.save();
        }
    }


}
