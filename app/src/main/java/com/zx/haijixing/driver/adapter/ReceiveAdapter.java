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

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 14:26
 *@描述 待接单
 */
public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ReceiveViewHolder> {

    private String loginType = OtherConstants.LOGIN_DRIVER;
    private IResultPositionListener iResultPositionListener;
    private List<OrderTotalEntry.OrderEntry> list;
    private boolean isRefresh = false;

    public ReceiveAdapter(List<OrderTotalEntry.OrderEntry> list) {
        this.list = list;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public void setOnClickListener(IResultPositionListener iResultPositionListener) {
        this.iResultPositionListener = iResultPositionListener;
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
        if (!OtherConstants.LOGIN_DRIVER.equals(loginType))
            receiveViewHolder.sure.setText("修改订单");
        if (list.size()>0){
            OrderTotalEntry.OrderEntry orderEntry = list.get(i);
            receiveViewHolder.sure.setOnClickListener(v -> iResultPositionListener.positionResult(orderEntry,0));
            receiveViewHolder.address.setText(orderEntry.getSenderAddress());
            receiveViewHolder.status.setText("待接单");
            receiveViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            receiveViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            receiveViewHolder.sendWay.setText(orderEntry.getProductName());
            receiveViewHolder.receiveShop.setText(orderEntry.getSenderName());
            receiveViewHolder.line.setText(orderEntry.getLinkName());
            receiveViewHolder.phone.setText(orderEntry.getSenderPhone());
            receiveViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            receiveViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(orderEntry.getType().equals("1")?"寄付":"到付")+")");
            String timeEnd = orderEntry.getTimeEnd();
            long timeStamp = Long.parseLong(ZxStringUtil.isEmpty(timeEnd)?"0":timeEnd);

            if (isRefresh){
                long l = timeStamp - 60 * 1000;
                orderEntry.setTimeEnd(l<0?"0":l+"");
                receiveViewHolder.downTime.setText(l <= 0?"(超时)":calculateTime(l));
            }else {
                receiveViewHolder.downTime.setText(timeStamp <= 0?"(超时)":calculateTime(timeStamp));
            }
            receiveViewHolder.address.setText(orderEntry.getSenderAddress());


            receiveViewHolder.select.setImageResource(orderEntry.isSelect()?R.mipmap.select_yes_solid:R.mipmap.select_no);
            receiveViewHolder.select.setVisibility(View.VISIBLE);
            receiveViewHolder.select.setOnClickListener(v -> {
                if (orderEntry.isSelect()){
                    receiveViewHolder.select.setImageResource(R.mipmap.select_no);
                    orderEntry.setSelect(false);
                }else {
                    receiveViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                    orderEntry.setSelect(true);
                }
                iResultPositionListener.positionResult(null,1);
            });
            receiveViewHolder.item.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType",orderEntry.getStatus())
                    .navigation());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder{

        Button sure;
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,downTime,status;
        ImageView select;
        View item;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            sure = itemView.findViewById(R.id.receive_data_sure_receive);
            item = itemView.findViewById(R.id.receive_data_item);
            downTime = itemView.findViewById(R.id.receive_data_sTime);
            select = itemView.findViewById(R.id.receive_data_select);


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
        }
    }

    private String calculateTime(long timeStamp){

        long day = timeStamp / (1000 * 60 * 60 * 24);
        long hour = (timeStamp / (1000 * 60 * 60) - day * 24);
        long min = ((timeStamp / (60 * 1000)) - day * 24 * 60 - hour * 60);
        String timeStr = "("+day + "天" + hour + "小时" + min + "分)";
        return  timeStr;
    }
}
