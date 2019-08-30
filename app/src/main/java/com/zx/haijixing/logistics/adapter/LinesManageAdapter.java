package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.share.PathConstant;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 16:16
 *@描述 路线管理
 */
public class LinesManageAdapter extends RecyclerView.Adapter<LinesManageAdapter.LinesManageViewHolder> {

    private List<LinesManageEntry> linesManageEntries;

    public LinesManageAdapter(List<LinesManageEntry> linesManageEntries) {
        this.linesManageEntries = linesManageEntries;
    }

    @NonNull
    @Override
    public LinesManageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lines_manage_data, viewGroup, false);
        LinesManageViewHolder linesManageViewHolder = new LinesManageViewHolder(inflate);
        return linesManageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinesManageViewHolder linesManageViewHolder, int i) {
        if (linesManageEntries.size()>0){
            LinesManageEntry linesManageEntry = linesManageEntries.get(i);
            linesManageViewHolder.manage.setOnClickListener(v -> {
                ARouter.getInstance().build(PathConstant.CLASSES_MANAGE)
                        .withString("linesId",linesManageEntry.getLineId())
                        .navigation();
            });
            linesManageViewHolder.fee.setOnClickListener(v ->
                    ARouter.getInstance().build(PathConstant.FEE)
                            .withString("linesId",linesManageEntry.getLineId())
                            .navigation());

            linesManageViewHolder.start.setText(linesManageEntry.getStartName());
            linesManageViewHolder.startLo.setText(linesManageEntry.getStartAddress());
            linesManageViewHolder.end.setText(linesManageEntry.getEndName());
            linesManageViewHolder.endLo.setText(linesManageEntry.getEndAddress());
            linesManageViewHolder.sendType.setText(linesManageEntry.getProductName());
            linesManageViewHolder.remark.setText(linesManageEntry.getRemark());

            int toPay = linesManageEntry.getToPay();
            int deposit = linesManageEntry.getDeposit();
            String myPay = null;
            if (toPay == 0 && deposit == 0){
                myPay = "到付/寄付";
            }else {
                myPay = (toPay == 0?"到付":"")+(deposit == 0?"寄付":"");
            }
            linesManageViewHolder.payWay.setText(myPay);
        }
    }

    @Override
    public int getItemCount() {
        return linesManageEntries.size();
    }

    class LinesManageViewHolder extends RecyclerView.ViewHolder{
        LinearLayout fee,manage;
        TextView sendType,start,end,payWay,startLo,endLo,remark;
        public LinesManageViewHolder(@NonNull View itemView) {
            super(itemView);
            fee = itemView.findViewById(R.id.lines_manage_fee);
            manage = itemView.findViewById(R.id.lines_manage_manage);

            sendType = itemView.findViewById(R.id.lines_manage_sendType);
            start = itemView.findViewById(R.id.lines_manage_start);
            end = itemView.findViewById(R.id.lines_manage_end);
            payWay = itemView.findViewById(R.id.lines_manage_pay_way);
            startLo = itemView.findViewById(R.id.lines_manage_locate1);
            endLo = itemView.findViewById(R.id.lines_manage_locate2);
            remark = itemView.findViewById(R.id.lines_manage_remark);
        }
    }
}
