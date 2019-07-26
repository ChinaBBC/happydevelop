package com.zx.haijixing.driver.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 15:25
 *@描述 已完成和已取消
 */
public class CcAdapter extends RecyclerView.Adapter<CcAdapter.CcViewHolder> {

    private List<OrderTotalEntry.OrderEntry> orderEntries;

    public CcAdapter(List<OrderTotalEntry.OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    @NonNull
    @Override
    public CcViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_complete_cancel_data, viewGroup, false);
        CcViewHolder ccViewHolder = new CcViewHolder(inflate);
        return ccViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CcViewHolder ccViewHolder, int i) {
        if (orderEntries.size()>0){
            OrderTotalEntry.OrderEntry orderEntry = orderEntries.get(i);

            ccViewHolder.address.setText(orderEntry.getSenderAddress());
            String status = orderEntry.getStatus();
            if ((OtherConstants.DETAIL_COMPLETE+"").equals(status)){
                ccViewHolder.status.setText("已完成");
                ccViewHolder.status.setTextColor(Color.parseColor("#30703f"));
            }else {
                ccViewHolder.status.setText("已取消");
                ccViewHolder.status.setTextColor(Color.parseColor("#666666"));
            }
            ccViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            ccViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            ccViewHolder.sendWay.setText(orderEntry.getProductName());
            ccViewHolder.receiveShop.setText(orderEntry.getSenderName());
            ccViewHolder.line.setText(orderEntry.getLinkName());
            ccViewHolder.phone.setText(orderEntry.getSenderPhone());
            ccViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            ccViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(orderEntry.getType().equals("1")?"寄付":"到付")+")");
            ccViewHolder.address.setText(orderEntry.getSenderAddress());
            ccViewHolder.item.setOnClickListener(v -> {
                ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                        .withString("orderId",orderEntry.getWaybillId())
                        .withString("detailType", status)
                        .navigation();
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderEntries.size();
    }

    class CcViewHolder extends RecyclerView.ViewHolder{
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,status;
        View item;
        public CcViewHolder(@NonNull View itemView) {
            super(itemView);
            createTime = itemView.findViewById(R.id.send_data_time);
            orderNumber = itemView.findViewById(R.id.send_data_order);
            sendWay = itemView.findViewById(R.id.send_data_way);
            receiveShop = itemView.findViewById(R.id.send_data_shop);
            line = itemView.findViewById(R.id.send_data_lines);
            phone = itemView.findViewById(R.id.send_data_phone);
            count = itemView.findViewById(R.id.send_data_number);
            address = itemView.findViewById(R.id.send_data_address);
            pay = itemView.findViewById(R.id.send_data_pay);
            status = itemView.findViewById(R.id.send_data_status);
            item = itemView.findViewById(R.id.complete_cancel_item);
        }
    }
}
