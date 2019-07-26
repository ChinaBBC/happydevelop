package com.zx.haijixing.driver.contract;

import com.zx.haijixing.driver.entry.OrderTotalEntry;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/18 16:32
 *@描述 位置回调
 */
public interface IResultPositionListener {
    void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag);
}
