package cn.hzmeurasia.guardconan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.utils.AddressQueryUtils;

/**
 * 类名: NumBelongtoActivity<br>
 * 功能:(TODO用一句话描述该类的功能)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/20 10:19
 */
public class NumBelongtoActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.et_number)
    EditText etNumber;

    @BindView(R.id.rbtn_search)
    QMUIRoundButton rbtnSearch;

    @BindView(R.id.tv_result)
    TextView tvResult;

    @OnClick(R.id.rbtn_search)
    void OnClick(View view){
        switch(view.getId()) {
            case R.id.rbtn_search:
                String number = etNumber.getText().toString().trim();
                String address = AddressQueryUtils.getAddress(NumBelongtoActivity.this, number);
                tvResult.setText(address);
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
        mTopBar.setTitle(R.string.number_belong_to);
    }

    public static void startAct(Context context) {
        Intent intent = new Intent(context, NumBelongtoActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.number_belong_to_activity);
        ButterKnife.bind(this);
        initTopBar();
        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String number = editable.toString().trim();
                String adress = AddressQueryUtils.getAddress(NumBelongtoActivity.this, number);
                tvResult.setText(adress);

            }
        });

    }

    @Override
    protected void initVariables() {

    }
}
