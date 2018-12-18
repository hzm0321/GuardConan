package cn.hzmeurasia.guardconan.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
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
import cn.hzmeurasia.guardconan.entity.TaskInfo;

/**
 * 类名: ProcessManagerAdapter<br>
 * 功能:(进程管理适配类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/18 14:38
 */
public class ProcessManagerAdapter extends RecyclerView.Adapter<ProcessManagerAdapter.ViewHolder> {

    private List<TaskInfo> mTaskInfos;

    public ProcessManagerAdapter(List<TaskInfo> mTaskInfos) {
        this.mTaskInfos = mTaskInfos;
    }

    @NonNull
    @Override
    public ProcessManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.progress_manager_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProcessManagerAdapter.ViewHolder holder, int position) {
        TaskInfo taskInfo = mTaskInfos.get(position);
        holder.iv.setImageDrawable(taskInfo.appIcon);
        holder.tvName.setText(taskInfo.appName);
        holder.tvMemory.setText(Math.toIntExact(taskInfo.appMemory));
        holder.cb.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return mTaskInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_progress_manager)
        ImageView iv;

        @BindView(R.id.tv_item_progress_manager_name)
        TextView tvName;

        @BindView(R.id.tv_item_progress_manager_memory)
        TextView tvMemory;

        @BindView(R.id.cb_item_progress_manager)
        AppCompatCheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
