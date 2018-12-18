package cn.hzmeurasia.guardconan.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.MyApplication;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.entity.ScanAppInfo;

/**
 * 类名: VirusScanAdapter<br>
 * 功能:(病毒扫描适配类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/18 10:20
 */
public class VirusScanAdapter extends RecyclerView.Adapter<VirusScanAdapter.ViewHolder> {

    private List<ScanAppInfo> mScanAppInfos;

    public VirusScanAdapter(List<ScanAppInfo> mScanAppInfos) {
        this.mScanAppInfos = mScanAppInfos;
    }

    @NonNull
    @Override
    public VirusScanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.virus_scan_speed_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VirusScanAdapter.ViewHolder holder, int position) {
        ScanAppInfo scanAppInfo = mScanAppInfos.get(position);
        holder.tvPackageName.setText(scanAppInfo.appName);
        holder.ivPackageIcon.setImageDrawable(scanAppInfo.appicon);
    }

    @Override
    public int getItemCount() {
        return mScanAppInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_virus_scan_speed)
        ImageView ivPackageIcon;

        @BindView(R.id.tv_item_virus_scan_speed_package_name)
        TextView tvPackageName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
