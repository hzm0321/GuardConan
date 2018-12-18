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
import cn.hzmeurasia.guardconan.db.BlackNumberDb;

/**
 * 类名: BlackNumberAdapter<br>
 * 功能:(黑名单列表适配类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/12 11:27
 */
public class BlackNumberAdapter  extends RecyclerView.Adapter<BlackNumberAdapter.ViewHolder> {

    private List<BlackNumberDb> mBlackNumberDbs;

    private DeleteClickListener deleteClickListener;

    private static final String TAG = "BlackNumberAdapter";

    public void setDeleteClickListener(DeleteClickListener deleteCilckListener) {
        this.deleteClickListener = deleteCilckListener;
    }

    public BlackNumberAdapter(List<BlackNumberDb> blackNumberDbList) {
        this.mBlackNumberDbs = blackNumberDbList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.black_number_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                deleteClickListener.onClick(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BlackNumberDb blackNumberDbs = mBlackNumberDbs.get(position);
        holder.tvName.setText(blackNumberDbs.getName());
        holder.tvPhone.setText("(" + blackNumberDbs.getNumber() + ")");
        holder.tvCheck.setText(blackNumberDbs.getPhone()+"  "+blackNumberDbs.getMessage());
    }

    @Override
    public int getItemCount() {
        return mBlackNumberDbs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_black_number_item_name)
        TextView tvName;

        @BindView(R.id.tv_black_number_item_phone_number)
        TextView tvPhone;

        @BindView(R.id.tv_black_number_item__check_phone_message)
        TextView tvCheck;

        @BindView(R.id.iv_black_number_item_delete)
        ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface DeleteClickListener{
        void onClick(int position);
    }
}
