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
 *@创建日期 2019/7/5 14:26
 *@描述 待接单
 */
public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ReceiveViewHolder> {

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
    public ReceiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_receive_data, viewGroup, false);
        ReceiveViewHolder receiveViewHolder = new ReceiveViewHolder(inflate);
        return receiveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveViewHolder receiveViewHolder, int i) {
        if (loginType == 1)
            receiveViewHolder.sure.setText("修改订单");
        receiveViewHolder.sure.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder{

        Button sure;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            sure = itemView.findViewById(R.id.receive_data_sure_receive);
        }
    }
}
