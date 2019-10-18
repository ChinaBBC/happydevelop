package com.zx.haijixing.driver.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.PrintEntry;
import com.zx.haijixing.driver.entry.SelectEntry;

import java.util.List;

import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 17:37
 *@描述 打印订单
 */
public class PrintAdapter extends RecyclerView.Adapter {
    private final static int HEAD_ITEM = 0;
    private final static int HEAD_ITEM_T = 1;
    private final static int BODY_ITEM = 2;
    private PrintEntry printEntry;
    private int selectTag = 0;
    private int selectHead = 1; //1未选择0选择
    private int selectHead_T = 1; //1未选择0选择
    private TotalPrintListener totalPrintListener;
    private List<SelectEntry> selectEntries;
    private boolean isPrint = false;

    public void setSelectEntries(List<SelectEntry> selectEntries) {
        this.selectEntries = selectEntries;
    }

    public PrintAdapter(TotalPrintListener totalPrintListener) {
        this.totalPrintListener = totalPrintListener;
    }

    public void setPrintEntry(PrintEntry printEntry) {
        this.printEntry = printEntry;
    }

    public void setSelectTag(int selectTag) {
        this.selectTag = selectTag;
    }

    public void setSelectHead(int selectHead) {
        this.selectHead = selectHead;
    }

    public void setSelectHead_T(int selectHead_T) {
        this.selectHead_T = selectHead_T;
    }

    public void setPrint(boolean print) {
        isPrint = print;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_ITEM;
        if (position == 1)
            return HEAD_ITEM_T;
        return BODY_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEAD_ITEM || viewType == HEAD_ITEM_T){
            View head = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_header, parent, false);
            viewHolder = new PrintHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_data, parent, false);
            viewHolder = new PrintViewHolder(body);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (printEntry != null){
            if (getItemViewType(i) == HEAD_ITEM ){
                PrintHeadViewHolder printHeadViewHolder = (PrintHeadViewHolder) viewHolder;
                printHeadViewHolder.start.setText(printEntry.getLineStartName().trim());
                printHeadViewHolder.end.setText(printEntry.getLineEndName().trim());
                printHeadViewHolder.orderNum.setText("运单号："+printEntry.getWaybillNo());
                printHeadViewHolder.sendMan.setText(printEntry.getSenderName());
                printHeadViewHolder.sendPhone.setText(printEntry.getSenderPhone());
                printHeadViewHolder.receiveMan.setText(printEntry.getIncomeName());
                printHeadViewHolder.receiveAddress.setText(printEntry.getIncomeAddress());
                printHeadViewHolder.receivePhone.setText(printEntry.getIncomePhone());
                String priceFlag = printEntry.getType();
                String payMoney = printEntry.getPayMoney();
                printHeadViewHolder.freight.setText(payMoney+("1".equals(priceFlag)?"元(寄付)":"元(到付)"));
                printHeadViewHolder.typeAndNum.setText(printEntry.getCategory()+printEntry.getTotalNum()+"件");
                printHeadViewHolder.remark.setText(printEntry.getRemark());
                printHeadViewHolder.word1.setText("客户回执单");
                if (isPrint){
                    printHeadViewHolder.select.setVisibility(View.GONE);
                    printHeadViewHolder.remark.setBackgroundColor(Color.TRANSPARENT);
                }else {
                    printHeadViewHolder.select.setVisibility(View.VISIBLE);
                    printHeadViewHolder.select.setImageResource(selectHead == 0?R.mipmap.select_yes_solid:R.mipmap.select_no);
                    printHeadViewHolder.select.setOnClickListener(v -> {
                        if (selectHead == 0){
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_no);
                            selectHead = 1;
                            selectTag--;
                        }else {
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                            selectHead = 0;
                            selectTag++;
                        }
                        totalPrintListener.totalResult(selectTag,selectHead,selectHead_T);
                    });
                    printHeadViewHolder.start.setOnClickListener(v -> {
                        if (selectHead == 0){
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_no);
                            selectHead = 1;
                            selectTag--;
                        }else {
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                            selectHead = 0;
                            selectTag++;
                        }
                        totalPrintListener.totalResult(selectTag,selectHead,selectHead_T);
                    });
                }
            }else if (getItemViewType(i) == HEAD_ITEM_T){
                PrintHeadViewHolder printHeadViewHolder = (PrintHeadViewHolder) viewHolder;
                printHeadViewHolder.start.setText(printEntry.getLineStartName().trim());
                printHeadViewHolder.end.setText(printEntry.getLineEndName().trim());
                printHeadViewHolder.orderNum.setText("运单号："+printEntry.getWaybillNo());
                printHeadViewHolder.sendMan.setText(printEntry.getSenderName());
                printHeadViewHolder.sendPhone.setText(printEntry.getSenderPhone());
                printHeadViewHolder.receiveMan.setText(printEntry.getIncomeName());
                printHeadViewHolder.receiveAddress.setText(printEntry.getIncomeAddress());
                printHeadViewHolder.receivePhone.setText(printEntry.getIncomePhone());
                String priceFlag = printEntry.getType();
                String payMoney = printEntry.getPayMoney();
                printHeadViewHolder.freight.setText(payMoney+("1".equals(priceFlag)?"元(寄付)":"元(到付)"));
                printHeadViewHolder.typeAndNum.setText(printEntry.getCategory()+printEntry.getTotalNum()+"件");
                printHeadViewHolder.remark.setText(printEntry.getRemark());
                printHeadViewHolder.word1.setText("司机回执单");
                if (isPrint){
                    printHeadViewHolder.select.setVisibility(View.GONE);
                    printHeadViewHolder.remark.setBackgroundColor(Color.TRANSPARENT);
                }else {
                    printHeadViewHolder.select.setVisibility(View.VISIBLE);
                    printHeadViewHolder.select.setImageResource(selectHead == 0?R.mipmap.select_yes_solid:R.mipmap.select_no);
                    printHeadViewHolder.select.setOnClickListener(v -> {
                        if (selectHead_T == 0){
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_no);
                            selectHead_T = 1;
                            selectTag--;
                        }else {
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                            selectHead_T = 0;
                            selectTag++;
                        }
                        totalPrintListener.totalResult(selectTag,selectHead,selectHead_T);
                    });
                    printHeadViewHolder.start.setOnClickListener(v -> {
                        if (selectHead == 0){
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_no);
                            selectHead = 1;
                            selectTag--;
                        }else {
                            printHeadViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                            selectHead = 0;
                            selectTag++;
                        }
                        totalPrintListener.totalResult(selectTag,selectHead,selectHead_T);
                    });
                }
            } else {
                PrintViewHolder printViewHolder = (PrintViewHolder) viewHolder;
                printViewHolder.start.setText(printEntry.getLineStartName().trim());
                printViewHolder.end.setText(printEntry.getLineEndName().trim());
                printViewHolder.orderNum.setText("运单号："+printEntry.getWaybillNo());
                printViewHolder.man.setText(printEntry.getIncomeName());
                printViewHolder.phone.setText(printEntry.getIncomePhone());
                printViewHolder.locate.setText(printEntry.getIncomeAddress());
                printViewHolder.type.setText(printEntry.getCategory());
                printViewHolder.number.setText(printEntry.getTotalNum()+"件");
                printViewHolder.detail.setText("("+printEntry.getGoodsNum()+")");
                printViewHolder.page.setText("第"+(i-1)+"/"+printEntry.getTotalNum()+"张");
                Bitmap image = CodeUtils.createImage(printEntry.getWaybillNo(), 110, 110, null);
                printViewHolder.qr.setImageBitmap(image);

                if (isPrint){
                    printViewHolder.select.setVisibility(View.GONE);
                }else{
                    int realPosition = getRealPosition(viewHolder);
                    SelectEntry selectEntry = selectEntries.get(realPosition<0?0:realPosition);
                    printViewHolder.select.setImageResource(selectEntry.isSelect()?R.mipmap.select_yes_solid:R.mipmap.select_no);
                    printViewHolder.select.setOnClickListener(v -> {
                        if (selectEntry.isSelect()){
                            printViewHolder.select.setImageResource(R.mipmap.select_no);
                            selectEntry.setSelect(false);
                            selectTag--;
                        }else {
                            printViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                            selectEntry.setSelect(true);
                            selectTag++;
                        }
                        totalPrintListener.totalResult(selectTag,selectHead,selectHead_T);
                    });
                    printViewHolder.page.setOnClickListener(v -> {
                        if (selectEntry.isSelect()){
                            printViewHolder.select.setImageResource(R.mipmap.select_no);
                            selectEntry.setSelect(false);
                            selectTag--;
                        }else {
                            printViewHolder.select.setImageResource(R.mipmap.select_yes_solid);
                            selectEntry.setSelect(true);
                            selectTag++;
                        }
                        totalPrintListener.totalResult(selectTag,selectHead,selectHead_T);
                    });
                }

            }
        }
    }
    public int getRealPosition(RecyclerView.ViewHolder viewHolder){
        int p = viewHolder.getLayoutPosition();
        return p-2;
    }
    @Override
    public int getItemCount() {
        if (printEntry != null){
            String totalNum = printEntry.getTotalNum();
            int  size = Integer.parseInt(ZxStringUtil.isEmpty(totalNum)?"0":totalNum);
            return size+2;
        }else {
            return 0;
        }

    }

    class PrintHeadViewHolder extends RecyclerView.ViewHolder{

        ImageView select ;
        TextView start,end,orderNum,sendMan,sendPhone,receiveMan,receivePhone,receiveAddress,freight,typeAndNum,word1,remark;
        public PrintHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            select = itemView.findViewById(R.id.print_header_img1);
            start = itemView.findViewById(R.id.print_header_start);
            end = itemView.findViewById(R.id.print_header_end);
            orderNum = itemView.findViewById(R.id.print_header_orderNum);
            sendMan = itemView.findViewById(R.id.print_header_send_man);
            sendPhone = itemView.findViewById(R.id.print_header_send_phone);
            receiveMan = itemView.findViewById(R.id.print_header_receive_man);
            receivePhone = itemView.findViewById(R.id.print_header_receive_phone);
            receiveAddress = itemView.findViewById(R.id.print_header_received_address);
            freight = itemView.findViewById(R.id.print_header_freight);
            typeAndNum = itemView.findViewById(R.id.print_header_typeAndNum);
            remark = itemView.findViewById(R.id.print_header_remark);

            word1 = itemView.findViewById(R.id.print_header_word1);
        }
    }
    class PrintViewHolder extends RecyclerView.ViewHolder{
        ImageView select,qr;
        TextView page,orderNum,start,end,man,phone,locate,type,number,detail;
        public PrintViewHolder(@NonNull View itemView) {
            super(itemView);
            select = itemView.findViewById(R.id.print_data_select);
            page = itemView.findViewById(R.id.print_data_page);
            orderNum = itemView.findViewById(R.id.print_data_orderNum);
            start = itemView.findViewById(R.id.print_data_start);
            end = itemView.findViewById(R.id.print_data_end);
            man = itemView.findViewById(R.id.print_data_receive_man);
            phone = itemView.findViewById(R.id.print_data_receive_phone);
            locate = itemView.findViewById(R.id.print_data_receive_locate);
            type = itemView.findViewById(R.id.print_data_type);
            number = itemView.findViewById(R.id.print_data_number);
            detail = itemView.findViewById(R.id.print_data_total_detail);
            qr = itemView.findViewById(R.id.print_data_qr);
        }
    }

    public interface TotalPrintListener{
        void totalResult(int total,int head,int headT);
    }
}
