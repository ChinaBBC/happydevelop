package com.zx.haijixing.driver.adapter;

import com.contrarywind.adapter.WheelAdapter;
import com.zx.haijixing.driver.entry.DriverClassEntry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/20 14:00
 *@描述 班次选择
 */
public class DriverClassWheel implements WheelAdapter<String> {
    List<DriverClassEntry> driverClassEntries;

    public DriverClassWheel(List<DriverClassEntry> driverClassEntries) {
        this.driverClassEntries = driverClassEntries;
    }

    @Override
    public int getItemsCount() {
        return driverClassEntries.size();
    }

    @Override
    public String getItem(int index) {
        DriverClassEntry driverClassEntry = driverClassEntries.get(index);
        return driverClassEntry.getLineStartName()+"-"+driverClassEntry.getLineEndName();
    }

    @Override
    public int indexOf(String o) {
        return driverClassEntries.indexOf(o);
    }
}
