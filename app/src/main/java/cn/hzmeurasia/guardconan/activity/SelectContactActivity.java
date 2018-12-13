package cn.hzmeurasia.guardconan.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.adapter.SelectContactAdapter;
import cn.hzmeurasia.guardconan.base.BaseActivity;
import cn.hzmeurasia.guardconan.entity.Contacts;

/**
 * 类名: SelectContactActivity<br>
 * 功能:(联系人页面)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 9:04
 */
public class SelectContactActivity extends BaseActivity {

    private static final String TAG = "SelectContactActivity";

    @BindView(R.id.top_bar)
    QMUITopBarLayout mTopBar;

    @BindView(R.id.rv_select_contact)
    RecyclerView mRecyclerView;

    private List<Contacts> mContactsList = new ArrayList<>();

    private SelectContactAdapter adapter;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, SelectContactActivity.class);
        context.startActivity(intent);
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
        mTopBar.setTitle(R.string.select_contacts);
        mTopBar.setBackgroundResource(R.drawable.top_bar_bg);

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.select_contact_activity);
        ButterKnife.bind(this);
        initTopBar();
        //没有权限时，调用requestPermission方法，弹出权限申请对话框 ，回调OnRequestPermissionRelust函数
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        Log.d(TAG, "initViews: 读取到的联系人列表字段数");
        if (mContactsList!=null) {
            adapter = new SelectContactAdapter(mContactsList);
            mRecyclerView.setAdapter(adapter);
        }

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    /**
     * 读取联系人
     */
    private void readContacts() {
        Cursor cursor = null;
        try {
            //cursor指针 query询问 contract协议 kinds种类
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d(TAG, "readContacts: name" + name);
                    Log.d(TAG, "readContacts: phone" + phone);
                    Contacts contacts = new Contacts(name,phone);
                    mContactsList.add(contacts);
                }
//                //notify公布
//                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "您取消了授权,无法获取到联系人", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        
    }
}
