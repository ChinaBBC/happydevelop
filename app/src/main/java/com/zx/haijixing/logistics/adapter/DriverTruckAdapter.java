package com.zx.haijixing.logistics.adapter;

import com.contrarywind.adapter.WheelAdapter;
import com.zx.haijixing.logistics.entry.DriverEntry;
import com.zx.haijixing.logistics.entry.TruckInfoEntry;
import com.zx.haijixing.share.OtherConstants;

import java.util.List;
/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 10:09
 *@描述 司机车辆选择
 */
public class DriverTruckAdapter implements WheelAdapter<String> {
    List<DriverEntry> driverEntries;
    List<TruckInfoEntry> truckInfoEntries;
    private int tag = OtherConstants.SELECT_DRIVER;

    public DriverTruckAdapter(List<DriverEntry> driverEntries, List<TruckInfoEntry> truckInfoEntries, int tag) {
        this.driverEntries = driverEntries;
        this.truckInfoEntries = truckInfoEntries;
        this.tag = tag;
    }

    @Override
    public int getItemsCount() {
        return tag == OtherConstants.SELECT_DRIVER?driverEntries.size():truckInfoEntries.size();
    }

    @Override
    public String getItem(int index) {
        return tag == OtherConstants.SELECT_DRIVER?driverEntries.get(index).getDriverName():truckInfoEntries.get(index).getIdcard();
    }

    @Override
    public int indexOf(String o) {
        return tag == OtherConstants.SELECT_DRIVER?driverEntries.indexOf(o):truckInfoEntries.indexOf(o);
    }
}
