package com.zx.haijixing.driver.adapter;

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
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 15:14
 *@描述 配送中
 */
public class SendingAdapter extends RecyclerView.Adapter<SendingAdapter.SendingViewHolder> {

    private String loginType = OtherConstants.LOGIN_DRIVER;
    private IResultPositionListener iResultPositionListener;
    private List<OrderTotalEntry.OrderEntry> list;

    public SendingAdapter(List<OrderTotalEntry.OrderEntry> list) {
        this.list = list;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public void setiResultPositionListener(IResultPositionListener iResultPositionListener) {
        this.iResultPositionListener = iResultPositionListener;
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
        if (list.size()>0){
            OrderTotalEntry.OrderEntry orderEntry = list.get(i);

            sendingViewHolder.address.setText(orderEntry.getSenderAddress());
            sendingViewHolder.status.setText("配送中");
            sendingViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            sendingViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            sendingViewHolder.sendWay.setText(orderEntry.getProductName());
            sendingViewHolder.receiveShop.setText(orderEntry.getSenderName());
            sendingViewHolder.line.setText(orderEntry.getLinkName());
            sendingViewHolder.phone.setText(orderEntry.getSenderPhone());
            sendingViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            sendingViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(orderEntry.getType().equals("1")?"寄付":"到付")+")");
            sendingViewHolder.address.setText(orderEntry.getSenderAddress());
            sendingViewHolder.item.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType",orderEntry.getStatus())
                    .navigation());

            if (!loginType.equals(OtherConstants.LOGIN_DRIVER)){
                sendingViewHolder.button2.setText("修改价格");
                sendingViewHolder.button1.setText("查看物流");
                sendingViewHolder.button1.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.CHECK_LOGISTICS)
                        .withString("waybillNo",orderEntry.getWaybillNo())
                        .navigation());
            }
            sendingViewHolder.button2.setOnClickListener(v -> iResultPositionListener.positionResult(orderEntry,2));
            sendingViewHolder.button1.setOnClickListener(v -> iResultPositionListener.positionResult(orderEntry,3));

            sendingViewHolder.select.setImageResource(orderEntry.isSelect()?R.mipmap.select_yes_solid:R.mipmap.select_no);
            sendingViewHolder.select.setVisibility(View.VISIBLE);
            sendingViewHolder.select.setOnClickListener(v -> {
                if (orderEntry.isSelect()){
                    sendingViewHolder.select.setImageResource(R.mipmap.select_no);
                    orderEntry.setSelect(false);
                }else {
                    sendingViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                    orderEntry.setSelect(true);
                }
                iResultPositionListener.positionResult(null,1);
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SendingViewHolder extends RecyclerView.ViewHolder{

        Button button1,button2;
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,status;
        ImageView select;
        View item;
        public SendingViewHolder(@NonNull View itemView) {
            super(itemView);
            button1 = itemView.findViewById(R.id.sending_data_sure_pay);
            button2 = itemView.findViewById(R.id.sending_data_send_complete);
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
            select = itemView.findViewById(R.id.sending_data_select);
            item = itemView.findViewById(R.id.sending_data_item);
        }
    }
}
