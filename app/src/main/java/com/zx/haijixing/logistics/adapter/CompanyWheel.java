package com.zx.haijixing.logistics.adapter;

import com.contrarywind.adapter.WheelAdapter;
import com.zx.haijixing.logistics.entry.CompanyEntry;

import java.util.ArrayList;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/5 18:45
 *@描述 公司选择
 */
public class CompanyWheel implements WheelAdapter<String> {
    ArrayList<CompanyEntry> companyEntries;

    public CompanyWheel(ArrayList<CompanyEntry> companyEntries) {
        this.companyEntries = companyEntries;
    }

    @Override
    public int getItemsCount() {
        return companyEntries.size();
    }

    @Override
    public String getItem(int index) {
        return companyEntries.get(index).getcName();
    }

    @Override
    public int indexOf(String o) {
        return companyEntries.indexOf(o);
    }
}
