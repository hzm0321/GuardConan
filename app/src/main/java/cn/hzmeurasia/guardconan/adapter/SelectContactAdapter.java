package cn.hzmeurasia.guardconan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hzmeurasia.guardconan.MyApplication;
import cn.hzmeurasia.guardconan.R;
import cn.hzmeurasia.guardconan.activity.AddBlackNumberActivity;
import cn.hzmeurasia.guardconan.activity.SelectContactActivity;
import cn.hzmeurasia.guardconan.entity.Contacts;

/**
 * 类名: SelectContactAdapter<br>
 * 功能:(联系人适配类)<br>
 * 作者:黄振敏 <br>
 * 日期:2018/12/13 9:48
 */
public class SelectContactAdapter extends RecyclerView.Adapter<SelectContactAdapter.ViewHolder> {

    private static final String TAG = "SelectContactAdapter";
    private List<Contacts> mContacts;

    public SelectContactAdapter(List<Contacts> mContacts) {
        this.mContacts = mContacts;
    }

    @NonNull
    @Override
    public SelectContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.select_contact_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(MyApplication.getContext(), AddBlackNumberActivity.class);
                Log.d(TAG, "onClick: 适配器中name值"+mContacts.get(position).getPhone());
                intent.putExtra("phone", mContacts.get(position).getPhone());
                intent.putExtra("name", mContacts.get(position).getName());
                MyApplication.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectContactAdapter.ViewHolder holder, int position) {
        Contacts contacts = mContacts.get(position);
        holder.tvName.setText(contacts.getName());
        holder.tvPhone.setText(contacts.getPhone());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_select_contact_name)
        TextView tvName;

        @BindView(R.id.tv_select_contact_phone)
        TextView tvPhone;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
