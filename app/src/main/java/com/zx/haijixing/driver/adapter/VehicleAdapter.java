package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zx.haijixing.R;
import com.zx.haijixing.share.RoutePathConstant;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/9 17:31
 *@描述 车辆管理
 */
public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vehicle_data, viewGroup, false);
        VehicleViewHolder vehicleViewHolder = new VehicleViewHolder(inflate);
        return vehicleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder vehicleViewHolder, int i) {
        vehicleViewHolder.item.setOnClickListener(v -> ARouter.getInstance().build(RoutePathConstant.TRUCK_CHANGE).navigation());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout item;
        TextView number;
        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.vehicle_data_item);
            number = itemView.findViewById(R.id.vehicle_data_number);
        }
    }
}
