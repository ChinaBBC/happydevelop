package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.util.HaiTool;

import java.util.List;

import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 15:38
 *@描述 物流动态
 */
public class LogisticsMoveAdapter extends RecyclerView.Adapter<LogisticsMoveAdapter.LogisticsMoveViewHolder> {

    private int loadType = OtherConstants.LOAD_MOVE;
    private List<LinesClassEntry> linesClassEntries;
    private AllotResultListener allotResultListener;

    public LogisticsMoveAdapter(List<LinesClassEntry> linesClassEntries) {
        this.linesClassEntries = linesClassEntries;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public void setAllotResultListener(AllotResultListener allotResultListener) {
        this.allotResultListener = allotResultListener;
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
        if (linesClassEntries.size()>0){

            LinesClassEntry linesClassEntry = linesClassEntries.get(i);
            if (loadType == OtherConstants.LOAD_ALLOT){
                logisticsMoveViewHolder.allot.setVisibility(View.VISIBLE);
                logisticsMoveViewHolder.allot.setOnClickListener(v -> {
                    allotResultListener.allotResult(linesClassEntry.getBakkiId());
                });
                logisticsMoveViewHolder.status.setVisibility(View.GONE);
            }else {
                logisticsMoveViewHolder.allot.setVisibility(View.GONE);
                logisticsMoveViewHolder.status.setVisibility(View.VISIBLE);
                logisticsMoveViewHolder.status.setText(getMyStatus(linesClassEntry.getBkkiStatus()));
            }

            logisticsMoveViewHolder.sendWay.setText(linesClassEntry.getProductName());
            logisticsMoveViewHolder.start.setText(linesClassEntry.getStartName());
            logisticsMoveViewHolder.startT.setText(linesClassEntry.getStartTime());
            logisticsMoveViewHolder.end.setText(linesClassEntry.getEndName());
            logisticsMoveViewHolder.endT.setText(linesClassEntry.getEndTime());

            logisticsMoveViewHolder.namePhone.setText(linesClassEntry.getDriverName()+" "+linesClassEntry.getIdcard());
            logisticsMoveViewHolder.count.setText("已装"+linesClassEntry.getTotalNum()+"件");
            logisticsMoveViewHolder.detail.setText(linesClassEntry.getGoodsStr());


            String differTime = linesClassEntry.getDifferTime();
            long aLong = Long.parseLong(ZxStringUtil.isEmpty(differTime) ? "0" : differTime);
            logisticsMoveViewHolder.timeAll.setText(HaiTool.calculateTime(aLong));

            String load = linesClassEntry.getLoad();
            String weight = linesClassEntry.getWeight();
            double all = Double.parseDouble(ZxStringUtil.isEmpty(load) ? "1" : load);
            double goIn = Double.parseDouble(ZxStringUtil.isEmpty(weight) ? "0" : weight);
            double all2 = all==0?1:all;
            double v = (goIn / all2) > 1 ? 1 : (goIn / all2);
            int percent = new Double(v*100).intValue();
            logisticsMoveViewHolder.progressBar.setProgress(percent);
        }
    }

    @Override
    public int getItemCount() {
        return linesClassEntries.size();
    }

    class LogisticsMoveViewHolder extends RecyclerView.ViewHolder{

        Button allot;
        TextView status,sendWay,start,startT,end,endT,timeAll,namePhone,count,detail;
        ProgressBar progressBar;
        public LogisticsMoveViewHolder(@NonNull View itemView) {
            super(itemView);
            allot = itemView.findViewById(R.id.move_data_count_allot);
            status = itemView.findViewById(R.id.move_data_status);
            sendWay = itemView.findViewById(R.id.move_data_sendWay);
            start = itemView.findViewById(R.id.move_data_start);
            startT = itemView.findViewById(R.id.move_data_startT);
            end = itemView.findViewById(R.id.move_data_end);
            endT = itemView.findViewById(R.id.move_data_endT);
            timeAll = itemView.findViewById(R.id.move_data_timeAll);
            namePhone = itemView.findViewById(R.id.move_data_nameNum);
            count = itemView.findViewById(R.id.move_data_count);
            progressBar = itemView.findViewById(R.id.move_data_progress);
            detail = itemView.findViewById(R.id.move_data_count_detail);

        }
    }

    public interface AllotResultListener{
        void allotResult(String bkId);
    }

    private String getMyStatus(String status){
        String myStatus = "等待中";//1装货中  , 2已发车  , 3已完成  4未发车
        switch (status){
            case "0":
                myStatus =  "等待中";
                break;
            case "1":
                myStatus =  "装货中";
                break;
            case "2":
                myStatus =  "已发车";
                break;
            case "3":
                myStatus =  "已完成";
                break;
            case "4":
                myStatus =  "未发车";
                break;
                default:break;
        }
        return myStatus;
    }
}
