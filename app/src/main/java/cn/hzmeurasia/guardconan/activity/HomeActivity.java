package cn.hzmeurasia.guardconan.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.OptionsAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.Options;
import cn.hzmeurasia.guardconan.utils.MD5Utils;
import cn.hzmeurasia.guardconan.utils.PrefUtils;

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
        //加载卡片视图界面
        for (int i = 0; i < mOptions.length; i++) {
            optionsList.add(mOptions[i]);
        }
        RecyclerView recyclerView = findViewById(R.id.rv_home);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OptionsAdapter(optionsList);
        adapter.setOnItemClickListener(new OptionsAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                showSafeDialog();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 显示手机防盗弹窗
     */
    private void showSafeDialog() {
        String password = PrefUtils.getString(PREF_PASSWORD, "", this);
        //判断是否存在密码
        if (!TextUtils.isEmpty(password)) {
            showInputPasswordDialog();
        } else {
            showSetPasswordDialog();
        }
    }

    /**
     * 设置密码弹框
     */
    private void showSetPasswordDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.dialog_set_password, null);
        Button btnOk = view.findViewById(R.id.btn_confirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        final TextInputEditText etPassword = view.findViewById(R.id.et_password);
        final TextInputEditText etPasswordConfirm = view.findViewById(R.id.et_password_confirm);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPassword.getText().toString().trim();
                String passwordConfirm = etPasswordConfirm.getText().toString().trim();
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
                    if (password.equals(passwordConfirm)) {
                        PrefUtils.putString(PREF_PASSWORD, MD5Utils.encode(password), HomeActivity.this);
                        AntiTheftActivity.startAct(HomeActivity.this);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

    }

    /**
     * 输入密码弹框
     */
    private void showInputPasswordDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = View.inflate(this, R.layout.dialog_confirm_password, null);
        Button btnOk = view.findViewById(R.id.btn_confirm);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        final TextInputEditText etPassword = view.findViewById(R.id.et_confirm_password);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etPassword.getText().toString().trim();
                //获取保存的密码
                String savePassword = PrefUtils.getString(PREF_PASSWORD, "", HomeActivity.this);
                //密码判空
                if (!TextUtils.isEmpty(password)) {
                    if (MD5Utils.encode(password).equals(savePassword)) {
                        AntiTheftActivity.startAct(HomeActivity.this);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
    }


}
