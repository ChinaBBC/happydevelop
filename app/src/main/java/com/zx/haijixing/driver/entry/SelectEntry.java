package com.zx.haijixing.driver.entry;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/25 17:53
 *@描述 是否选择
 */
public class SelectEntry {
    boolean isSelect;

    public SelectEntry(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
