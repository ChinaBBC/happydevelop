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

import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/4 10:28
 *@描述 订单
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    List<OrderTotalEntry.OrderEntry> orderEntries;

    public OrderAdapter(List<OrderTotalEntry.OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_data, viewGroup, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(inflate);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        if (orderEntries.size()>0){
            OrderTotalEntry.OrderEntry orderEntry = orderEntries.get(i);
            orderViewHolder.address.setText(orderEntry.getSenderAddress());
            String statusStr = orderEntry.getStatus();
            int status = Integer.parseInt(ZxStringUtil.isEmpty(statusStr)?"0":statusStr);

            switch (status){
                case OtherConstants.DETAIL_WAIT_RECEIVE:
                    orderViewHolder.status.setText("待接单");
                    orderViewHolder.status.setTextColor(Color.parseColor("#FF7E00"));
                    break;
                case OtherConstants.DETAIL_WAIT_SEND:
                    orderViewHolder.status.setText("待出发");
                    orderViewHolder.status.setTextColor(Color.parseColor("#FF7E00"));
                    break;
                case OtherConstants.DETAIL_SENDING:
                    orderViewHolder.status.setText("配送中");
                    orderViewHolder.status.setTextColor(Color.parseColor("#FF7E00"));
                    break;
                case OtherConstants.DETAIL_COMPLETE:
                    orderViewHolder.status.setText("已完成");
                    orderViewHolder.status.setTextColor(Color.parseColor("#30703f"));
                    break;
                case OtherConstants.DETAIL_CANCEL:
                    orderViewHolder.status.setText("已取消");
                    orderViewHolder.status.setTextColor(Color.parseColor("#666666"));
                    break;
            }

            orderViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            orderViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            orderViewHolder.sendWay.setText(orderEntry.getProductName());
            orderViewHolder.receiveShop.setText(orderEntry.getSenderName());
            orderViewHolder.line.setText(orderEntry.getLinkName());
            orderViewHolder.phone.setText(orderEntry.getSenderPhone());
            orderViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            orderViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(orderEntry.getType().equals("1")?"寄付":"到付")+")");
            orderViewHolder.address.setText(orderEntry.getSenderAddress());
            orderViewHolder.item.setOnClickListener(v -> {
                ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                        .withString("orderId",orderEntry.getWaybillId())
                        .withString("detailType", statusStr)
                        .navigation();
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderEntries.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,status;
        View item;

        public OrderViewHolder(@NonNull View itemView) {
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
            item = itemView.findViewById(R.id.order_data_item);
        }
    }
}
