package cn.hzmeurasia.guardconan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.entity.AppManager;
import cn.hzmeurasia.guardconan.utils.DensityUtil;
import cn.hzmeurasia.guardconan.utils.EngineUtils;

/**
 * 类名: AppManagerAdapter<br>
 * 功能:(软件管理列表适配类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 15:42
 */
public class AppManagerAdapter extends BaseAdapter {

    private List<AppManager> mUserAppManager;

    private List<AppManager> mSystemManager;

    private Context context;

    public AppManagerAdapter(List<AppManager> UserAppManager, List<AppManager> SystemManager, Context context) {
        super();
        mUserAppManager = UserAppManager;
        mSystemManager = SystemManager;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mUserAppManager.size() + mSystemManager.size() + 2;
    }

    @Override
    public Object getItem(int i) {
        if (i == 1) {
            return null;
        } else if (i == (mUserAppManager.size() + 1)) {
            return null;
        }
        AppManager appManager;
        if (i < (mUserAppManager.size() + 1)) {
            //用户程序,因为多了一个TextView,所以减1
            appManager = mUserAppManager.get(i - 1);
        } else {
            //系统程序
            int location = i - mUserAppManager.size() - 2;
            appManager = mSystemManager.get(location);
        }
        return appManager;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //i=0,textView
        if (i == 0) {
            TextView tv = getTextView();
            tv.setText("用户程序: " + mUserAppManager.size() + "个");
            return tv;
        } else if (i == (mUserAppManager.size() + 1)) {
            TextView tv = getTextView();
            tv.setText("系统程序: " + mSystemManager.size() + "个");
            return tv;
        }
        //获取到当前App对象
        AppManager appManager;
        if (i < (mUserAppManager.size() + 1)) {
            appManager = mUserAppManager.get(i - 1);
        } else {
            //系统应用
            appManager = mSystemManager.get(i - mUserAppManager.size() - 2);
        }
        ViewHolder viewHolder = null;
        if (view != null & view instanceof LinearLayout) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            viewHolder = new ViewHolder();
            view = View.inflate(context, R.layout.app_manager_item, null);
            viewHolder.mAppIconImgv = view.findViewById(R.id.imgv_appicon);
            viewHolder.mAppLocationTV = view.findViewById(R.id.tv_appisroom);
            viewHolder.mAppSizeTV = view.findViewById(R.id.tv_appsize);
            viewHolder.mAppNameTV = view.findViewById(R.id.tv_appname);
            viewHolder.mLuanchAppTV = view.findViewById(R.id.tv_launch_app);
            viewHolder.mSettingAppTV = view.findViewById(R.id.tv_setting_app);
            viewHolder.mShareAppTV = view.findViewById(R.id.tv_share_app);
            viewHolder.mUninstallTV = view.findViewById(R.id.tv_uninstall_app);
            viewHolder.mAppOptionLL = view.findViewById(R.id.ll_option_app);
            view.setTag(viewHolder);
        }
        if (appManager != null) {
            viewHolder.mAppLocationTV.setText(appManager.getAppLocation(appManager.isInRoom));
            viewHolder.mAppIconImgv.setImageDrawable(appManager.icon);
            viewHolder.mAppSizeTV.setText(android.text.format.Formatter.formatFileSize(context, appManager.appSize));
            viewHolder.mAppNameTV.setText(appManager.appName);
            if (appManager.isSelected) {
                viewHolder.mAppOptionLL.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mAppOptionLL.setVisibility(View.GONE);
            }
        }
        MyClickListener listener = new MyClickListener(appManager);
        viewHolder.mLuanchAppTV.setOnClickListener(listener);
        viewHolder.mSettingAppTV.setOnClickListener(listener);
        viewHolder.mShareAppTV.setOnClickListener(listener);
        viewHolder.mUninstallTV.setOnClickListener(listener);
        return view;
    }

    static class ViewHolder{
        /** 启动App */
        TextView mLuanchAppTV;
        /** 卸载App */
        TextView mUninstallTV;
        /** 分享App */
        TextView mShareAppTV;
        /** 设置App */
        TextView mSettingAppTV;
        /** app 图标 */
        ImageView mAppIconImgv;
        /** app位置 */
        TextView mAppLocationTV;
        /** app大小 */
        TextView mAppSizeTV;
        /** app名称 */
        TextView mAppNameTV;
        /** 操作App的线性布局 */
        LinearLayout mAppOptionLL;
    }

    private TextView getTextView() {
        TextView tv = new TextView(context);
        tv.setBackgroundColor(context.getResources()
                .getColor(com.qmuiteam.qmui.R.color.qmui_config_color_gray_9));
        tv.setPadding(DensityUtil.dip2px(context, 5),
                DensityUtil.dip2px(context, 5),
                DensityUtil.dip2px(context, 5),
                DensityUtil.dip2px(context, 5));
        tv.setTextColor(context.getResources().getColor(R.color.qmui_config_color_gray_2));
        return tv;
    }

    class MyClickListener implements View.OnClickListener {

        private AppManager appManager;

        public MyClickListener(AppManager appManager) {
            super();
            this.appManager = appManager;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_launch_app:
                    // 启动应用
                    EngineUtils.startApplication(context, appManager);
                    break;
                case R.id.tv_share_app:
                    // 分享应用
                    EngineUtils.shareApplication(context, appManager);
                    break;
                case R.id.tv_setting_app:
                    // 设置应用
                    EngineUtils.SettingAppDetail(context, appManager);
                    break;
                case R.id.tv_uninstall_app:
                    // 卸载应用,需要注册广播接收者
                    if(appManager.packageName.equals(context.getPackageName())){
                        Toast.makeText(context, "您没有权限卸载此应用！", 0).show();
                        return;
                    }
                    EngineUtils.uninstallApplication(context, appManager);
                    break;
                default:
                    break;
            }
        }
    }

}
