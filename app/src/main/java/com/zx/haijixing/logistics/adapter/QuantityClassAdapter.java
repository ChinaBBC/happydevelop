package com.zx.haijixing.logistics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zx.haijixing.R;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/10/29 16:27
 *@描述 批量管理班次
 */
public class QuantityClassAdapter extends RecyclerView.Adapter<QuantityClassAdapter.QuantityViewHolder> {

    private List<String> lineList = null;
    private Context context;
    @NonNull
    @Override
    public QuantityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_quantity_class, viewGroup, false);
        QuantityViewHolder quantityViewHolder = new QuantityViewHolder(inflate);
        return quantityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuantityViewHolder quantityViewHolder, int i) {
        quantityViewHolder.lineName.setOnClickListener(v -> {
            quantityViewHolder.timeList.setVisibility(View.VISIBLE);
        });
        quantityViewHolder.arrow.setOnClickListener(v ->{

        });

        quantityViewHolder.timeList.setAdapter(new TimeAdapter());
        quantityViewHolder.timeList.setOnItemClickListener((parent, view, position, id) -> {

        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class QuantityViewHolder extends RecyclerView.ViewHolder{
        TextView lineName;
        ImageView arrow;
        ListView timeList;
        public QuantityViewHolder(@NonNull View itemView) {
            super(itemView);
            lineName = itemView.findViewById(R.id.item_quantity_lineName);
            arrow = itemView.findViewById(R.id.item_quantity_arrow);
            timeList = itemView.findViewById(R.id.item_quantity_timeList);
        }
    }

    class TimeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TimeViewHolder timeViewHolder = null;
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quantity_class_time,null);
                timeViewHolder = new TimeViewHolder();
                timeViewHolder.select = convertView.findViewById(R.id.item_quantity_time_select);
                timeViewHolder.time = convertView.findViewById(R.id.item_quantity_time_time);
                convertView.setTag(timeViewHolder);
            }else {
                timeViewHolder = (TimeViewHolder) convertView.getTag();
            }


            return convertView;
        }

        class TimeViewHolder{
            TextView time;
            ImageView select;
        }
    }
}
