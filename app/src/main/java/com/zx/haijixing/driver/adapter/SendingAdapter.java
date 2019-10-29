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

import java.util.ArrayList;
import java.util.List;

import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

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
    private ArrayList<String> permissions;

    public SendingAdapter(List<OrderTotalEntry.OrderEntry> list) {
        this.list = list;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public void setiResultPositionListener(IResultPositionListener iResultPositionListener) {
        this.iResultPositionListener = iResultPositionListener;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
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

            String status = orderEntry.getStatus();
            sendingViewHolder.createTime.setText("下单："+orderEntry.getCreateTime());
            sendingViewHolder.orderNumber.setText("运单号："+orderEntry.getWaybillNo());
            sendingViewHolder.sendWay.setText(orderEntry.getProductName());
            sendingViewHolder.receiveShop.setText(orderEntry.getIncomeName());
            sendingViewHolder.line.setText(orderEntry.getLinkName());
            sendingViewHolder.phone.setText(orderEntry.getIncomePhone());
            sendingViewHolder.count.setText(orderEntry.getCategory()+"/"+orderEntry.getTotalNum()+"件");
            String sendType = orderEntry.getType();
            sendingViewHolder.pay.setText("￥"+orderEntry.getPrice()+"元("+(sendType.equals("1")?"寄付":"到付")+")");
            sendingViewHolder.address.setText(orderEntry.getIncomeAddress());
            sendingViewHolder.item.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.DRIVER_ORDER_DETAIL)
                    .withString("orderId",orderEntry.getWaybillId())
                    .withString("detailType", status)
                    .withString("priceFlag",orderEntry.getMakepriceFlag())
                    .withString("linesId",orderEntry.getLineId())
                    .withString("modify",sendType)
                    .navigation());

            if ("6".equals(status)){
                sendingViewHolder.status.setText("未签收");
                //sendingViewHolder.select.setVisibility(View.GONE);
                sendingViewHolder.button1.setVisibility(View.GONE);
                sendingViewHolder.button2.setVisibility(View.GONE);
                sendingViewHolder.print.setVisibility(View.GONE);
            }else{
                int dadanFlag = Integer.parseInt(ZxStringUtil.isEmpty(orderEntry.getDadanFlag())?"0":orderEntry.getDadanFlag());
                sendingViewHolder.status.setText("配送中");
                sendingViewHolder.print.setText(dadanFlag==0?"打单":"补打单");
                sendingViewHolder.print.setVisibility(View.VISIBLE);
                //sendingViewHolder.select.setVisibility(View.VISIBLE);
                sendingViewHolder.button2.setVisibility(View.VISIBLE);
                sendingViewHolder.print.setOnClickListener(v ->{
                            if ("1".equals(sendType) && "0".equals(orderEntry.getMakepriceFlag())){
                                ZxToastUtil.centerToast("请联系管理员确认收款");
                            }else {
                                ARouter.getInstance().build(PathConstant.PRINT)
                                        .withString("waybillId",orderEntry.getWaybillId())
                                        .withString("printStatus",orderEntry.getDadanFlag())
                                        .navigation();
                            }
                        }
                );
                if (loginType.equals(OtherConstants.LOGIN_DRIVER)){
                    sendingViewHolder.button2.setText("0".equals(orderEntry.getMakepriceFlag())?"确认收款":"配送完成");
                    if ("1".equals(sendType)){
                        sendingViewHolder.button1.setVisibility(View.GONE);
                    }else {
                        if (permissions != null)
                            sendingViewHolder.button1.setVisibility(permissions.contains(OtherConstants.PERMISSION_CHANGE_MONEY)?View.VISIBLE:View.GONE);
                    }
                    sendingViewHolder.button1.setOnClickListener(v -> {
                        iResultPositionListener.positionResult(orderEntry,3);
                    });
                    /*sendingViewHolder.select.setImageResource(orderEntry.isSelect()?R.mipmap.select_yes_solid:R.mipmap.select_no);
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
                    });*/

                }else {
                    //sendingViewHolder.select.setVisibility(View.GONE);
                    if ("1".equals(sendType)){
                        sendingViewHolder.button2.setVisibility(View.GONE);
                    }else {
                        if (permissions != null)
                            sendingViewHolder.button2.setVisibility(permissions.contains(OtherConstants.PERMISSION_CHANGE_MONEY)?View.VISIBLE:View.GONE);
                    }
                    sendingViewHolder.button2.setText("修改价格");
                    ///sendingViewHolder.button1.setText("查看物流");
                    sendingViewHolder.button1.setVisibility(View.GONE);
                    /*sendingViewHolder.button1.setOnClickListener(v -> ARouter.getInstance().build(PathConstant.CHECK_LOGISTICS)
                            .withString("waybillNo",orderEntry.getWaybillNo())
                            .navigation());*/

                }
                sendingViewHolder.button2.setOnClickListener(v -> {
                    if ("0".equals(orderEntry.getMakepriceFlag()))
                        iResultPositionListener.positionResult(orderEntry,2);
                    else
                        iResultPositionListener.positionResult(orderEntry,4);
                });
            }





        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SendingViewHolder extends RecyclerView.ViewHolder{

        Button button1,button2,print;
        TextView createTime,orderNumber,sendWay,receiveShop,line,phone,count,address,pay,status;
        ImageView select,receiveImg;
        View item;
        public SendingViewHolder(@NonNull View itemView) {
            super(itemView);
            button1 = itemView.findViewById(R.id.sending_data_sure_pay);
            button2 = itemView.findViewById(R.id.sending_data_send_complete);
            print = itemView.findViewById(R.id.sending_data_print_order);

            createTime = itemView.findViewById(R.id.send_data_time);
            orderNumber = itemView.findViewById(R.id.send_data_order);
            sendWay = itemView.findViewById(R.id.send_data_way);
            receiveImg = itemView.findViewById(R.id.send_data_img1);
            receiveShop = itemView.findViewById(R.id.send_data_shop);
            line = itemView.findViewById(R.id.send_data_lines);
            phone = itemView.findViewById(R.id.send_data_phone);
            count = itemView.findViewById(R.id.send_data_number);
            address = itemView.findViewById(R.id.send_data_address);
            pay = itemView.findViewById(R.id.send_data_pay);
            status = itemView.findViewById(R.id.send_data_status);
            //select = itemView.findViewById(R.id.sending_data_select);
            item = itemView.findViewById(R.id.sending_data_item);
        }
    }
}
