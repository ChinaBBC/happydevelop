package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zx.haijixing.R;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 14:45
 *@描述 待出发
 */
public class SendAdapter extends RecyclerView.Adapter<SendAdapter.SendViewHolder> {

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_send_data, viewGroup, false);
        SendViewHolder sendViewHolder = new SendViewHolder(inflate);
        return sendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendViewHolder sendViewHolder, int i) {
        sendViewHolder.button1.setOnClickListener(onClickListener);
        sendViewHolder.button2.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class SendViewHolder extends RecyclerView.ViewHolder{
        Button button1,button2;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            button1 = itemView.findViewById(R.id.send_data_change_order);
            button2 = itemView.findViewById(R.id.send_data_print_order);
        }
    }
}
