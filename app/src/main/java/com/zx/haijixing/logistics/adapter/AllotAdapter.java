package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
 *@创建日期 2019/7/16 11:42
 *@描述 待派单
 */
public class AllotAdapter extends RecyclerView.Adapter<AllotAdapter.AllotViewHolder> {

    private List<OrderTotalEntry.OrderEntry> list;

    public AllotAdapter(List<OrderTotalEntry.OrderEntry> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AllotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wait_allot, viewGroup, false);
        AllotViewHolder allotViewHolder = new AllotViewHolder(inflate);
        return allotViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllotViewHolder allotViewHolder, int i) {

        if (list.size()>0){
            OrderTotalEntry.OrderEntry orderEntry = list.get(i);
            allotViewHolder.address.setText(orderEntry.getSenderAddress());
            allotViewHolder.status.setText("待派单");

            allotViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            allotViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            allotViewHolder.sendWay.setText(orderEntry.getProductName());
            allotViewHolder.receiveShop.setText(orderEntry.getSenderName());
            allotViewHolder.line.setText(orderEntry.getLinkName());
            allotViewHolder.phone.setText(orderEntry.getSenderPhone());
            allotViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            allotViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(orderEntry.getType().equals("1")?"寄付":"到付")+")");
            allotViewHolder.address.setText(orderEntry.getSenderAddress());
            allotViewHolder.item.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType",orderEntry.getStatus())
                    .navigation());


            allotViewHolder.allot.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.ALLOT)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("linesId",orderEntry.getLineId())
                    .navigation());
            allotViewHolder.change.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType",OtherConstants.CHANGE_ORDER+"")
                    .withString("linesId",orderEntry.getLineId())
                    .navigation());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AllotViewHolder extends RecyclerView.ViewHolder{
        Button allot,change;
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,status;
        View item;
        public AllotViewHolder(@NonNull View itemView) {
            super(itemView);
            allot = itemView.findViewById(R.id.allot_data_sure_allot);
            change = itemView.findViewById(R.id.allot_data_change_order);
            item = itemView.findViewById(R.id.allot_data_item);
            ImageView select = itemView.findViewById(R.id.allot_data_select);

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

            select.setVisibility(View.GONE);

        }
    }
}
