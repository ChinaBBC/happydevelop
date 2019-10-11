package com.zx.haijixing.logistics.adapter;

import com.contrarywind.adapter.WheelAdapter;
import com.zx.haijixing.logistics.entry.ProductEntry;

import java.util.List;
/**
 *
 *@作者 zx
 *@创建日期 2019/9/4 10:23
 *@描述 物流产品的滚轮
 */
public class ProductAdapter implements WheelAdapter<String> {

    private List<ProductEntry> productEntries;

    public ProductAdapter(List<ProductEntry> productEntries) {
        this.productEntries = productEntries;
    }

    @Override
    public int getItemsCount() {
        return productEntries.size();
    }

    @Override
    public String getItem(int index) {
        return productEntries.get(index).getDictValue();
    }

    @Override
    public int indexOf(String o) {
        return productEntries.indexOf(o);
    }
}
