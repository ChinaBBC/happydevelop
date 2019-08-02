package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.util.HaiTool;

import java.util.List;

import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/8 16:23
 *@描述 车辆路线
 */
public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.LinesViewHolder> {

    private List<DriverClassEntry> driverClassEntries;

    public LinesAdapter(List<DriverClassEntry> driverClassEntries) {
        this.driverClassEntries = driverClassEntries;
    }

    @NonNull
    @Override
    public LinesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lines_data, viewGroup, false);
        LinesViewHolder linesViewHolder = new LinesViewHolder(inflate);
        return linesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinesViewHolder linesViewHolder, int i) {
        if (driverClassEntries.size()>0){
            DriverClassEntry driverClassEntry = driverClassEntries.get(i);
            linesViewHolder.sendWay.setText(driverClassEntry.getProductName());
            linesViewHolder.carNum.setText(driverClassEntry.getIdcard());
            linesViewHolder.start.setText(driverClassEntry.getLineStartName());
            linesViewHolder.end.setText(driverClassEntry.getLineEndName());
            linesViewHolder.startTime.setText(driverClassEntry.getStartTime());
            linesViewHolder.endTime.setText(driverClassEntry.getEndTime());
            String differTime = driverClassEntry.getDifferTime();
            long time = Long.parseLong(ZxStringUtil.isEmpty(differTime) ? "0" : differTime);
            String tim = HaiTool.calculateTime(time);
            linesViewHolder.times.setText(tim);
        }
    }

    @Override
    public int getItemCount() {
        return driverClassEntries.size();
    }

    class LinesViewHolder extends RecyclerView.ViewHolder{
        TextView sendWay,carNum,start,end,startTime,endTime,times;
        public LinesViewHolder(@NonNull View itemView) {
            super(itemView);
            sendWay = itemView.findViewById(R.id.lines_data_send_way);
            carNum = itemView.findViewById(R.id.lines_data_car_number);
            start = itemView.findViewById(R.id.lines_data_start);
            end = itemView.findViewById(R.id.lines_data_end);
            startTime = itemView.findViewById(R.id.lines_data_start_time);
            times = itemView.findViewById(R.id.lines_data_times);
            endTime = itemView.findViewById(R.id.lines_data_end_time);
        }
    }
}
