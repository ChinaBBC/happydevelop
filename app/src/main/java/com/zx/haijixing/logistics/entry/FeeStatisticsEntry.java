package com.zx.haijixing.logistics.entry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 16:32
 *@描述 运费运单统计
 */
public class FeeStatisticsEntry {
    List<String> days;
    List<String> rows;

    @Override
    public String toString() {
        return "FeeStatisticsEntry{" +
                "days=" + days +
                ", rows=" + rows +
                '}';
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }
}
