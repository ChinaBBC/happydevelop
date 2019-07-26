package com.zx.haijixing.driver.adapter;

import com.contrarywind.adapter.WheelAdapter;
import com.zx.haijixing.driver.entry.GoodsTypePriceEntry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/24 8:42
 *@描述 品类选择
 */
public class GoodTypeWheel implements WheelAdapter<String> {

    List<GoodsTypePriceEntry.GoodType> goodTypes;

    public GoodTypeWheel(List<GoodsTypePriceEntry.GoodType> goodTypes) {
        this.goodTypes = goodTypes;
    }

    @Override
    public int getItemsCount() {
        return goodTypes.size();
    }

    @Override
    public String getItem(int index) {
        return goodTypes.get(index).getClassName();
    }

    @Override
    public int indexOf(String o) {
        return goodTypes.indexOf(o);
    }
}
