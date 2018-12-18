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
import cn.hzmeurasia.guardconan.entity.CacheInfo;

/**
 * 类名: CleanCacheAdapter<br>
 * 功能:(缓存扫描适配类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/17 10:41
 */
public class CleanCacheAdapter extends RecyclerView.Adapter<CleanCacheAdapter.ViewHolder> {

    private List<CacheInfo> mCacheInfos;

    public CleanCacheAdapter(List<CacheInfo> mCacheInfos) {
        this.mCacheInfos = mCacheInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.clean_cache_item, parent, false);
        ViewHolder hodler = new ViewHolder(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CacheInfo cacheInfo = mCacheInfos.get(position);
        holder.tvName.setText(cacheInfo.appName);
        holder.tvSize.setText(String.valueOf(cacheInfo.cacheSize));
        holder.ivClean.setImageDrawable(cacheInfo.appIcon);
    }

    @Override
    public int getItemCount() {
        return mCacheInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_clean_cache)
        ImageView ivClean;

        @BindView(R.id.tv_item_clean_cache_name)
        TextView tvName;

        @BindView(R.id.tv_item_clean_cache_size)
        TextView tvSize;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
