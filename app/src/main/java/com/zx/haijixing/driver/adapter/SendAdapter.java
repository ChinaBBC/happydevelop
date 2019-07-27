package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;

import java.util.List;

import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 14:45
 *@描述 待出发
 */
public class SendAdapter extends RecyclerView.Adapter<SendAdapter.SendViewHolder> {

    private List<OrderTotalEntry.OrderEntry> list;
    private IResultPositionListener iResultPositionListener;

    public SendAdapter(List<OrderTotalEntry.OrderEntry> list) {
        this.list = list;
    }

    public void setiResultPositionListener(IResultPositionListener iResultPositionListener) {
        this.iResultPositionListener = iResultPositionListener;
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
        if (list.size()>0){
            OrderTotalEntry.OrderEntry orderEntry = list.get(i);
            sendViewHolder.address.setText(orderEntry.getSenderAddress());
            sendViewHolder.status.setText("待出发");
            sendViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            sendViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            sendViewHolder.sendWay.setText(orderEntry.getProductName());
            sendViewHolder.receiveShop.setText(orderEntry.getSenderName());
            sendViewHolder.line.setText(orderEntry.getLinkName());
            sendViewHolder.phone.setText(orderEntry.getSenderPhone());
            sendViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            sendViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(orderEntry.getType().equals("1")?"寄付":"到付")+")");
            sendViewHolder.address.setText(orderEntry.getSenderAddress());
            sendViewHolder.item.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType",orderEntry.getStatus())
                    .navigation());

            int dadanFlag = Integer.parseInt(ZxStringUtil.isEmpty(orderEntry.getDadanFlag())?"0":orderEntry.getDadanFlag());
            if (dadanFlag == 0){
                sendViewHolder.button1.setVisibility(View.VISIBLE);
                sendViewHolder.button2.setText("打单");
            }else {
                sendViewHolder.button2.setText("补打单");
                sendViewHolder.button1.setVisibility(View.GONE);
            }
            sendViewHolder.button1.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType",OtherConstants.CHANGE_ORDER+"")
                    .withString("linesId",orderEntry.getLineId())
                    .navigation());
            sendViewHolder.button2.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.PRINT)
                    .withString("waybillId",orderEntry.getWaybillId())
                    .withString("printStatus",orderEntry.getDadanFlag())
                    .navigation());
            sendViewHolder.select.setImageResource(orderEntry.isSelect()?R.mipmap.select_yes_solid:R.mipmap.select_no);
            sendViewHolder.select.setVisibility(View.VISIBLE);
            sendViewHolder.select.setOnClickListener(v -> {
                if (dadanFlag == 1){
                    if (orderEntry.isSelect()){
                        sendViewHolder.select.setImageResource(R.mipmap.select_no);
                        orderEntry.setSelect(false);
                    }else {
                        sendViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                        orderEntry.setSelect(true);
                    }
                    iResultPositionListener.positionResult(null,1);
                }else {
                    ZxToastUtil.centerToast("请先打单");
                }

            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder{
        Button button1,button2;
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,status;
        ImageView select;
        View item;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            button1 = itemView.findViewById(R.id.send_data_change_order);
            button2 = itemView.findViewById(R.id.send_data_print_order);
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
            select = itemView.findViewById(R.id.send_data_select);
            item = itemView.findViewById(R.id.send_data_common);
        }
    }
}
