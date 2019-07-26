package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.share.pub.entry.NotifyEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 16:33
 *@描述 消息中心
 */
public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder> {

    private List<NotifyEntry> notifyEntries;
    private IResultPositionListener iResultPositionListener;

    public NotifyAdapter(List<NotifyEntry> notifyEntries) {
        this.notifyEntries = notifyEntries;
    }

    public void setiResultPositionListener(IResultPositionListener iResultPositionListener) {
        this.iResultPositionListener = iResultPositionListener;
    }

    @NonNull
    @Override
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notify_center, viewGroup, false);
        NotifyViewHolder notifyViewHolder = new NotifyViewHolder(inflate);
        return notifyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder notifyViewHolder, int i) {
        if (notifyEntries.size()>0){
            NotifyEntry notifyEntry = notifyEntries.get(i);
            notifyViewHolder.title.setText(notifyEntry.getTitle());
            notifyViewHolder.item.setOnClickListener(v -> iResultPositionListener.positionResult(null,1));
            String content = notifyEntry.getContent();
            try {
                JSONObject jsonObject = new JSONObject(content);
                notifyViewHolder.line.setText(jsonObject.getString("address"));
                notifyViewHolder.number.setText(jsonObject.getString("total"));
                notifyViewHolder.order.setText("运单号："+jsonObject.getString("waybillNo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return notifyEntries.size();
    }

    class NotifyViewHolder extends RecyclerView.ViewHolder{
        TextView title,number,order,line;
        ConstraintLayout item;
        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notify_center_title);
            number = itemView.findViewById(R.id.notify_center_number);
            order = itemView.findViewById(R.id.notify_center_order);
            line = itemView.findViewById(R.id.notify_center_line);
            item = itemView.findViewById(R.id.notify_center_item);
        }
    }
}
