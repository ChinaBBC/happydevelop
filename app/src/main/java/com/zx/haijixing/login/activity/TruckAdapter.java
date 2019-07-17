package com.zx.haijixing.login.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.zx.haijixing.R;
import com.zx.haijixing.login.entry.TruckTypeEntry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/15 13:48
 *@描述 车辆信息
 */
public class TruckAdapter implements WheelAdapter<String> {
    List<TruckTypeEntry> list;

    public TruckAdapter(List<TruckTypeEntry> list) {
        this.list = list;
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position).getName();
    }

    @Override
    public int indexOf(String o) {
        return list.indexOf(o);
    }
}
