package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.share.OtherConstants;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 15:38
 *@描述 物流动态
 */
public class LogisticsMoveAdapter extends RecyclerView.Adapter<LogisticsMoveAdapter.LogisticsMoveViewHolder> {

    private int loadType = OtherConstants.LOAD_MOVE;
    private View.OnClickListener onClickListener;

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public LogisticsMoveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logistics_move_data, viewGroup, false);
        LogisticsMoveViewHolder logisticsMoveViewHolder = new LogisticsMoveViewHolder(inflate);
        return logisticsMoveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LogisticsMoveViewHolder logisticsMoveViewHolder, int i) {
        if (loadType == OtherConstants.LOAD_ALLOT){
            logisticsMoveViewHolder.allot.setVisibility(View.VISIBLE);
            logisticsMoveViewHolder.allot.setOnClickListener(onClickListener);
            logisticsMoveViewHolder.status.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class LogisticsMoveViewHolder extends RecyclerView.ViewHolder{

        Button allot;
        TextView status;
        public LogisticsMoveViewHolder(@NonNull View itemView) {
            super(itemView);
            allot = itemView.findViewById(R.id.move_data_count_allot);
            status = itemView.findViewById(R.id.move_data_status);
        }
    }
}
