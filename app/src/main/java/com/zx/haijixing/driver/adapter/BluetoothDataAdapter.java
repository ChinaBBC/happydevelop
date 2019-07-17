package com.zx.haijixing.driver.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.haijixing.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/13 10:09
 *@描述 蓝牙
 */
public class BluetoothDataAdapter extends BaseAdapter {
    List<BluetoothDevice> data = new ArrayList<>();

    public BluetoothDataAdapter(List<BluetoothDevice> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlueViewHolder blueViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_data,parent,false);
            blueViewHolder = new BlueViewHolder();
            blueViewHolder.name = convertView.findViewById(R.id.bluetooth_data_name);
            convertView.setTag(blueViewHolder);
        }else {
            blueViewHolder = (BlueViewHolder) convertView.getTag();
        }

        blueViewHolder.name.setText(data.get(position).getName()+"toto");
        return convertView;
    }

    class BlueViewHolder{
        TextView name;
    }
}
