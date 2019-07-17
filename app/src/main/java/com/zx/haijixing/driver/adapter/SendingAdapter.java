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
 *@创建日期 2019/7/5 15:14
 *@描述 配送中
 */
public class SendingAdapter extends RecyclerView.Adapter<SendingAdapter.SendingViewHolder> {

    private int loginType = 0;
    private View.OnClickListener onClickListener;

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sending_data, viewGroup, false);
        SendingViewHolder sendingViewHolder = new SendingViewHolder(inflate);
        return sendingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendingViewHolder sendingViewHolder, int i) {
        if (loginType == 1){
            sendingViewHolder.button1.setText("查看物流");
            sendingViewHolder.button1.setBackgroundResource(R.drawable.shape_5dp_a47f);
            sendingViewHolder.button2.setText("修改价格");
        }
        sendingViewHolder.button2.setOnClickListener(onClickListener);
        sendingViewHolder.button1.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class SendingViewHolder extends RecyclerView.ViewHolder{

        Button button1,button2;
        public SendingViewHolder(@NonNull View itemView) {
            super(itemView);
            button1 = itemView.findViewById(R.id.sending_data_sure_pay);
            button2 = itemView.findViewById(R.id.sending_data_send_complete);
        }
    }
}
