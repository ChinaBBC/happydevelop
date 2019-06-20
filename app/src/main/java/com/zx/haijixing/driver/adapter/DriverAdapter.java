package com.zx.haijixing.driver.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.driver.DriverInfo;


public class DriverAdapter extends PagedListAdapter<DriverInfo,DriverAdapter.DriverViewHolder> {

public static final DiffUtil.ItemCallback<DriverInfo> diffCallback = new DriverInfoCallback();

    public DriverAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test_skeleton, viewGroup, false);
        DriverViewHolder driverViewHolder = new DriverViewHolder(inflate);
        return driverViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder driverViewHolder, int i) {
        driverViewHolder.test.setText(getItem(i).getName());
    }

    class DriverViewHolder extends RecyclerView.ViewHolder{

        TextView test;
        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            test = itemView.findViewById(R.id.test_text);
        }
    }

    private static class DriverInfoCallback extends DiffUtil.ItemCallback<DriverInfo>{

        @Override
        public boolean areItemsTheSame(@NonNull DriverInfo driverInfo, @NonNull DriverInfo t1) {
            return driverInfo.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DriverInfo driverInfo, @NonNull DriverInfo t1) {
            return driverInfo == t1;
        }
    }
}
